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
import be.vinci.ipl.gateway.models.Price;
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

  /**
   * Reads investor data
   *
   * @param username Investor's username
   * @param token    Authentication token
   * @return Investor data or
   *         UNAUTHORIZED if the token is invalid,
   *         FORBIDDEN if the token doesn't match the investor,
   *         NOT_FOUND if the investor is not found
   */
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
  /**
   * Creates a new investor
   *
   * @param username             Investor's username
   * @param investorWithPassword Investor data with password
   * @return CREATED response if successfully created,
   *         CONFLICT response if investor already exists,
   *         BAD_REQUEST response if invalid investor
   */
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

  /**
   * Updates investor data
   *
   * @param username     Investor's username
   * @param investorData Updated investor data
   * @return OK response if the update is successful,
   *         BAD_REQUEST if the request is malformed,
   *         NOT_FOUND if the investor is not found
   */
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

  /**
   * Deletes an investor
   *
   * @param username Investor's username
   * @return OK response if the deletion is successful,
   *         BAD_REQUEST if the request is malformed,
   *         NOT_FOUND if the investor is not found
   */
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

  /**
   * Authenticates an investor and generates an access token
   *
   * @param credentials Investor credentials (username and password)
   * @return The connection token if authentication is successful,
   *         BAD_REQUEST if the request is malformed,
   *         UNAUTHORIZED if the token is invalid,
   *         FORBIDDEN if the token doesn't match the investor,
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


  /**
   * Updates investor credentials
   *
   * @param username    Investor's username
   * @param credentials New investor credentials (username and password)
   * @param token       Authentication token
   * @return OK response if the credentials are updated successfully,
   *         BAD_REQUEST if the request is malformed,
   *         UNAUTHORIZED if the token is invalid,
   *         FORBIDDEN if the token doesn't match the investor,
   *         NOT_FOUND if the investor is not found
   */
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

  /**
   * Creates a new order
   *
   * @param order The order to be created
   * @return Created order or BAD_REQUEST response if the request is malformed
   */
  @PostMapping("/order")
  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
    try {
      Order createdOrder = service.createOrder(order);
      return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Reads all orders from a specific investor
   *
   * @param username Investor's username
   * @param token    Authentication token
   * @return All orders from the user or
   *         UNAUTHORIZED if the token is invalid,
   *         FORBIDDEN if the token doesn't match the investor,
   *         NOT_FOUND if the investor is not found
   */
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

  /**
   * Reads all open positions from an investor's wallet
   *
   * @param username Investor's username
   * @param token    Authentication token
   * @return All open positions from the investor's wallet or
   *         UNAUTHORIZED if the token is invalid,
   *         FORBIDDEN if the token doesn't match the investor,
   *         NOT_FOUND if the investor is not found
   */
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

  /**
   * Adds or removes cash from an investor's wallet
   *
   * @param username Investor's username
   * @param cashDTO  CashDTO containing the amount of cash to add or remove
   * @param token    Authentication token
   * @return Updated list of positions in the investor's wallet or
   *         UNAUTHORIZED if the token is invalid,
   *         FORBIDDEN if the token doesn't match the investor,
   *         NOT_FOUND if the investor is not found
   */
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

  /**
   * Reads the net worth of an investor's wallet
   *
   * @param username Investor's username
   * @param token    Authentication token
   * @return Net worth value or
   *         UNAUTHORIZED if the token is invalid,
   *         FORBIDDEN if the token doesn't match the investor,
   *         NOT_FOUND if the investor is not found
   */
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


  /**
   * Adds or removes a quantity of a specific ticker from an investor's wallet
   *
   * @param username     Investor's username
   * @param ticker       Ticker symbol for the position
   * @param quantityDTO  QuantityDTO containing the amount to add or remove
   * @param token        Authentication token
   * @return Updated list of positions in the investor's wallet or
   *         UNAUTHORIZED if the token is invalid,
   *         FORBIDDEN if the token doesn't match the investor,
   *         NOT_FOUND if the investor is not found
   */
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

  /**
   * Reads the price of a specific financial instrument
   *
   * @param ticker Identifier of the financial instrument
   * @return The price of the specified financial instrument
   */
  @GetMapping("/price/{ticker}")
  public ResponseEntity<Price> readPrice(@PathVariable String ticker) {
    Price price = service.readPrice(ticker);
    return new ResponseEntity<>(price, HttpStatus.OK);
  }

}
