package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.exceptions.BadRequestException;
import be.vinci.ipl.gateway.exceptions.NotFoundException;
import be.vinci.ipl.gateway.exceptions.UnauthorizedException;
import be.vinci.ipl.gateway.models.Credentials;
import be.vinci.ipl.gateway.models.InvestorData;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public class GatewayController {
  private GatewayService service;

  public GatewayController(GatewayService service) {
    this.service = service;
  }

  @GetMapping("/investor/{username}")
  public ResponseEntity<InvestorData> readInvestor(@PathVariable String username) {
    try {
      InvestorData investor = service.readInvestor(username);
      return new ResponseEntity<>(investor, HttpStatus.OK);
    } catch (UnauthorizedException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  /*
  @PutMapping("/investor/{username}")
  public ResponseEntity

   */



  @PostMapping("/authentication/connect")
  public ResponseEntity<String> connect(@RequestBody Credentials credentials) {
    try {
      String token = service.connect(credentials);
      return new ResponseEntity<>(token, HttpStatus.OK);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (UnauthorizedException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  /*
  @PutMapping("/authentication/{username}")
  public ResponseEntity<Void> updateCredentials(@PathVariable String username,
      @RequestBody Credentials credentials, @RequestHeader("Authorization") String token) {

    if (!Objects.equals(username, credentials.getUsername())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    service.updateCredentials(credentials);
  }
  */

}
