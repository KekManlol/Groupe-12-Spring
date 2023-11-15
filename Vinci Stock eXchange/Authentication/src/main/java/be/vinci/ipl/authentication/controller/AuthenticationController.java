package be.vinci.ipl.authentication.controller;

import be.vinci.ipl.authentication.models.UnsafeCredentials;
import be.vinci.ipl.authentication.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  private AuthenticationService service;

  public AuthenticationController(AuthenticationService service){
    this.service = service;
  }

  @PostMapping("/authentication/connect")
  public ResponseEntity<String> connect(@RequestBody UnsafeCredentials unsafeCredentials){

    if (unsafeCredentials.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    String token = service.connect(unsafeCredentials);
    if (token == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    return new ResponseEntity<>(token, HttpStatus.OK);
  }

  @PostMapping("/authentication/verify")
  public ResponseEntity<String> verify(@RequestBody String token){

    String verified = service.verify(token);
    if (verified == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    return new ResponseEntity<>(verified, HttpStatus.OK);
  }

  @PostMapping("/authentication/{username}")
  public ResponseEntity<Void> createOne(@PathVariable String username,
                                        @RequestBody UnsafeCredentials unsafeCredentials){

    if (!unsafeCredentials.getUsername().equals(username) || unsafeCredentials.invalid())
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    boolean created = service.createOne(unsafeCredentials);
    if (!created) return new ResponseEntity<>(HttpStatus.CONFLICT);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/authentication/{username}")
  public ResponseEntity<Void> updateOne(@PathVariable String username,
                                        @RequestBody UnsafeCredentials unsafeCredentials){

    if (!unsafeCredentials.getUsername().equals(username) || unsafeCredentials.invalid())
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    boolean updated = service.updateOne(unsafeCredentials);
    if (!updated) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/authentication/{username}")
  public ResponseEntity<Void> deleteOne(@PathVariable String username){

    boolean deleted = service.deleteOne(username);
    if (!deleted) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
