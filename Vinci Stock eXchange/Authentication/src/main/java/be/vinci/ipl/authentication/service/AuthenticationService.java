package be.vinci.ipl.authentication.service;

import be.vinci.ipl.authentication.AuthenticationProperties;
import be.vinci.ipl.authentication.data.AuthenticationRepository;
import be.vinci.ipl.authentication.models.SafeCredentials;
import be.vinci.ipl.authentication.models.UnsafeCredentials;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  private final AuthenticationRepository repository;
  private final Algorithm jwtAlgorithm;
  private final JWTVerifier jwtVerifier;

  public AuthenticationService(AuthenticationRepository repository, AuthenticationProperties properties){
    this.repository = repository;
    this.jwtAlgorithm = Algorithm.HMAC512(properties.getSecret());
    this.jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0").build();
  }

  /**
   * Connects investor with unsafe credentials
   * @param unsafeCredentials The credentials with insecure password
   * @return The JWT token, or null if the investor couldn't be connected
   */
  public String connect(UnsafeCredentials unsafeCredentials) {
    SafeCredentials safeCredentials = repository.findById(unsafeCredentials.getUsername()).orElse(null);
    if (safeCredentials == null) return null;
    if (!BCrypt.checkpw(unsafeCredentials.getPassword(), safeCredentials.getHashedPassword())) return null;
    return JWT.create().withIssuer("auth0").withClaim("username", safeCredentials.getUsername()).sign(jwtAlgorithm);
  }

  /**
   * Verifies JWT token
   * @param token The JWT token
   * @return The investor's username, or null if the token couldn't be verified
   */
  public String verify(String token) {
    try {
      String username = jwtVerifier.verify(token).getClaim("username").asString();
      if (!repository.existsById(username)) return null;
      return username;
    } catch (JWTVerificationException e) {
      return null;
    }
  }

  /**
   * Creates credentials in repository
   * @param unsafeCredentials The credentials with insecure password
   * @return True if the credentials were created, or false if they already exist
   */
  public boolean createOne(UnsafeCredentials unsafeCredentials) {
    if (repository.existsById(unsafeCredentials.getUsername())) return false;
    String hashedPassword = BCrypt.hashpw(unsafeCredentials.getPassword(), BCrypt.gensalt());
    repository.save(unsafeCredentials.makeSafe(hashedPassword));
    return true;
  }

  /**
   * Updates credentials in repository
   * @param unsafeCredentials The credentials with insecure password
   * @return True if the credentials were updated, or false if they couldn't be found
   */
  public boolean updateOne(UnsafeCredentials unsafeCredentials) {
    if (!repository.existsById(unsafeCredentials.getUsername())) return false;
    String hashedPassword = BCrypt.hashpw(unsafeCredentials.getPassword(), BCrypt.gensalt());
    repository.save(unsafeCredentials.makeSafe(hashedPassword));
    return true;
  }

  /**
   * Deletes credentials in repository
   * @param username The investor's username
   * @return True if the credentials were deleted, or false if they couldn't be found
   */
  public boolean deleteOne(String username) {
    if (!repository.existsById(username)) return false;
    repository.deleteById(username);
    return true;
  }
}
