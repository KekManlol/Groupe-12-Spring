package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.exceptions.BadRequestException;
import be.vinci.ipl.gateway.exceptions.ConflictException;
import be.vinci.ipl.gateway.exceptions.NotFoundException;
import be.vinci.ipl.gateway.exceptions.UnauthorizedException;
import be.vinci.ipl.gateway.models.CashDTO;
import be.vinci.ipl.gateway.models.Credentials;
import be.vinci.ipl.gateway.models.InvestorData;
import be.vinci.ipl.gateway.models.InvestorWithPassword;
import be.vinci.ipl.gateway.models.Order;
import be.vinci.ipl.gateway.models.Position;
import be.vinci.ipl.gateway.models.QuantityDTO;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {
  private GatewayService service;

  public GatewayController(GatewayService service) {
    this.service = service;
  }

  @GetMapping("/investor/{username}")
  public ResponseEntity<InvestorData> readInvestor(
      @PathVariable String username,
      @RequestHeader("Authorization") String token) {

    String investorUsername = service.verify(token);
    if (investorUsername == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    else if (!Objects.equals(investorUsername, username)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    try {
      InvestorData investor = service.readInvestor(username);
      return new ResponseEntity<>(investor, HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/investor/{username}")
  public ResponseEntity<Void> createInvestor(
      @PathVariable String username,
      @RequestBody InvestorWithPassword investorWithPassword) {
    try {
      service.createInvestor(username, investorWithPassword);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (ConflictException e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @PutMapping("/investor/{username}")
  public ResponseEntity<Void> updateInvestor(
      @PathVariable String username,
      @RequestBody InvestorData investorData) {

    try {
      service.updateInvestor(username, investorData);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/investor/{username}")
  public ResponseEntity<Void> deleteInvestor(@PathVariable String username) {
    try {
      service.deleteInvestor(username);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

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


  @PutMapping("/authentication/{username}")
  public ResponseEntity<Void> updateCredentials(
      @PathVariable String username,
      @RequestBody Credentials credentials,
      @RequestHeader("Authorization") String token) {

    String investorUsername = service.verify(token);
    if (investorUsername == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    else if (!Objects.equals(investorUsername, username)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    try {
      service.updateCredentials(username, credentials);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/order")
  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
    try {
      Order createdOrder = service.createOrder(order);
      return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/order/by-user/{username}")
  public ResponseEntity<Iterable<Order>> readAllOrdersFromUser(
      @PathVariable String username,
      @RequestHeader("Authorization") String token) {

    String investorUsername = service.verify(token);
    if (investorUsername == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    else if (!Objects.equals(investorUsername, username)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    try {
      Iterable<Order> orders = service.readAllOrdersFromUser(username);
      return new ResponseEntity<>(orders, HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/wallet/{username}")
  public ResponseEntity<Iterable<Position>> readAllOpenPositionsFromInvestor(
      @PathVariable String username,
      @RequestHeader("Authorization") String token) {

    String investorUsername = service.verify(token);
    if (investorUsername == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    else if (!Objects.equals(investorUsername, username)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    try {
      Iterable<Position> positions = service.readAllOpenPositionsFromInvestor(username);
      return new ResponseEntity<>(positions, HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/wallet/{username}/cash")
  public ResponseEntity<Iterable<Position>> addOrRemoveCashFromWallet(
      @PathVariable String username,
      @RequestBody CashDTO cashDTO,
      @RequestHeader("Authorization") String token) {

    String investorUsername = service.verify(token);
    if (investorUsername == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    else if (!Objects.equals(investorUsername, username)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    try {
      Iterable<Position> positions = service.addOrRemoveCashFromWallet(username, cashDTO);
      return new ResponseEntity<>(positions, HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/wallet/{username}/net-worth")
  public ResponseEntity<Float> readWalletNetValueFromInvestor(@PathVariable String username,
      @RequestHeader("Authorization") String token){

    String investorUsername = service.verify(token);
    if (investorUsername == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    else if (!Objects.equals(investorUsername, username)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    try {
      Float netValue = service.readWalletNetValueFromInvestor(username);
      return new ResponseEntity<>(netValue, HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/wallet/{username}/position/{ticker}")
  public ResponseEntity<Iterable<Position>> addOrRemoveTickerQuantityFromWallet(
      @PathVariable String username,
      @PathVariable String ticker,
      @RequestBody QuantityDTO quantityDTO,
      @RequestHeader("Authorization") String token) {

    String investorUsername = service.verify(token);
    if (investorUsername == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    else if (!Objects.equals(investorUsername, username)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    try {
      Iterable<Position> positions = service.addOrRemoveTickerQuantityFromWallet(username, ticker,
          quantityDTO);
      return new ResponseEntity<>(positions, HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }

}
