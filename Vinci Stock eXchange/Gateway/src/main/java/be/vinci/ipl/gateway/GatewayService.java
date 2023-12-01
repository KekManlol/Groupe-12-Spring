package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.data.AuthenticationProxy;
import be.vinci.ipl.gateway.data.InvestorsProxy;
import be.vinci.ipl.gateway.data.OrderProxy;
import be.vinci.ipl.gateway.data.PriceProxy;
import be.vinci.ipl.gateway.data.WalletProxy;
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
import com.fasterxml.jackson.databind.util.ArrayIterator;
import feign.FeignException;
import feign.FeignException.Forbidden;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {
  InvestorsProxy investorsProxy;
  AuthenticationProxy authenticationProxy;
  OrderProxy orderProxy;
  WalletProxy walletProxy;
  PriceProxy priceProxy;

  public GatewayService(InvestorsProxy investorsProxy, AuthenticationProxy authenticationProxy,
      OrderProxy orderProxy, WalletProxy walletProxy, PriceProxy priceProxy) {
    this.investorsProxy = investorsProxy;
    this.authenticationProxy = authenticationProxy;
    this.orderProxy = orderProxy;
    this.walletProxy = walletProxy;
    this.priceProxy = priceProxy;
  }

  /**
   * Read an Investor's information
   *
   * @param username Username of the investor
   * @return Investor information
   * @throws NotFoundException when investor was not found
   */
  public InvestorData readInvestor(String username) throws NotFoundException {
    try {
      return investorsProxy.readInvestor(username);
    } catch (FeignException e) {
      if (e.status() == 404) throw new NotFoundException();
      else throw e;
    }
  }

  /**
   * Create investor and credentials
   *
   * @param username
   * @param investorWithPassword
   * @throws BadRequestException when invalid investor or invalid password
   * @throws ConflictException when investor already created
   */
  public void createInvestor(String username, InvestorWithPassword investorWithPassword)
      throws BadRequestException, ConflictException {
    try {
      investorsProxy.createInvestor(username, investorWithPassword);
    } catch (FeignException e) {
      if (e.status() == 400) throw new BadRequestException();
      else if (e.status() == 409) throw new ConflictException();
      else throw e;
    }
  }


  /**
   * Update an investor information
   *
   * @param username Username of the investor
   * @param investorData New investor's information for the update
   * @throws BadRequestException when invalid new investor's information
   * @throws NotFoundException when investor was not found
   */
  public void updateInvestor(String username, InvestorData investorData)
      throws BadRequestException, NotFoundException {

    try {
      investorsProxy.updateInvestor(username, investorData);
    } catch (FeignException e) {
      if (e.status() == 400) throw new BadRequestException();
      else if (e.status() == 404) throw new NotFoundException();
      else throw e;
    }
  }

  /**
   * Delete an investor, their credentials and their wallets
   *
   * @param username Username of the investor
   * @throws BadRequestException when the investor's situation doesn't allow them to delete their account
   * @throws NotFoundException when the credentials were not found
   */
  public void deleteInvestor(String username) throws BadRequestException, NotFoundException {
    try {
      investorsProxy.deleteInvestor(username);
    } catch (FeignException e) {
      if (e.status() == 400) throw new BadRequestException();
      else if (e.status() == 404) throw new NotFoundException();
      else throw e;
    }
  }

  /**
   * Get connection token from credentials
   *
   * @param credentials Credentials of the user
   * @return Connection token
   * @throws BadRequestException when the credentials are invalid
   * @throws UnauthorizedException when the credentials are incorrect
   */
  public String connect(Credentials credentials) throws BadRequestException, UnauthorizedException {
    try {
      return authenticationProxy.connect(credentials);
    } catch (FeignException e) {
      if (e.status() == 400) throw new BadRequestException();
      else if (e.status() == 401) throw new UnauthorizedException();
      else throw e;
    }
  }

  /**
   * Update credentials of an existing investor
   *
   * @param username Username of the investor
   * @param credentials Credentials of the investor
   * @throws BadRequestException when invalid credentials
   * @throws NotFoundException when credentials were not found
   */
  public void updateCredentials(String username, Credentials credentials)
      throws BadRequestException, NotFoundException {
    try {
      authenticationProxy.updateCredentials(username, credentials);
    } catch (FeignException e) {
      if (e.status() == 400) throw new BadRequestException();
      else if (e.status() == 404) throw new NotFoundException();
      else throw e;
    }
  }

  /**
   * Place an order
   *
   * @param order Order to be placed
   * @return the new order
   * @throws BadRequestException when invalid order
   */
  public Order createOrder(Order order) throws BadRequestException {
    try {
      return orderProxy.createOrder(order);
    } catch (FeignException e) {
      if (e.status() == 400) throw new BadRequestException();
      else throw e;
    }
  }

  /**
   * Read all orders (open and completed) of an investor
   *
   * @param username Username of the investor
   * @return the list of all open and completed orders of an investor
   * @throws NotFoundException when the investor was not found
   */
  public Iterable<Order> readAllOrdersFromUser(String username) throws NotFoundException {
    try {
      return orderProxy.readAllOrdersFromUser(username);
    } catch (FeignException e) {
      if (e.status() == 404) throw new NotFoundException();
      else throw e;
    }
  }

  /**
   * Get investor's username from connection token
   *
   * @param token Connection token
   * @return Investor's username, or null if token invalid
   */
  public String verify(String token) {
    try {
      return authenticationProxy.verify(token);
    } catch (FeignException e) {
      if (e.status() == 401) return null;
      else throw e;
    }
  }

  /**
   * Read all open positions of an investor
   *
   * @param username Username of the investor
   * @return a list of all open positions of an investor
   * @throws NotFoundException when the investor was not found
   */
  public Iterable<Position> readAllOpenPositionsFromInvestor(String username)
      throws NotFoundException {
    try {
      return walletProxy.readAllOpenPositionsFromInvestor(username);
    } catch (FeignException e) {
      if (e.status() == 404) throw new NotFoundException();
      else throw e;
    }

  }

  /**
   * Add or remove cash from an investor's wallet
   *
   * @param username Username of the investor
   * @param cashDTO DTO with
   * @return the list of all open positions of the investor
   * @throws NotFoundException when the investor was not found
   */
  public Iterable<Position> addOrRemoveCashFromWallet(String username, CashDTO cashDTO) throws NotFoundException {
    try {
      Position cashPosition = new Position();
      cashPosition.setTicker("CASH");
      cashPosition.setQuantity((int) cashDTO.getCash());
      List<Position> positions = new ArrayList<>();
      positions.add(cashPosition);
      walletProxy.addPositions(username, positions);

      return walletProxy.readAllOpenPositionsFromInvestor(username);

    } catch (FeignException e) {
      if (e.status() == 404) throw new NotFoundException();
      else throw e;
    }
  }

  /**
   * Read an investor's wallet net value
   *
   * @param username Username of the investor
   * @return the net value of the wallet
   * @throws NotFoundException when the investor was not found
   */
  public Float readWalletNetValueFromInvestor(String username) throws NotFoundException {
    try {
      return walletProxy.readWalletNetValueFromInvestor(username);
    } catch (FeignException e) {
      if (e.status() == 404) throw new NotFoundException();
      else throw e;
    }
  }

  /**
   * Add or remove a quantity of a ticker from a wallet
   *
   * @param username Username of the investor
   * @param ticker Identifier of the financial instrument
   * @param quantityDTO DTO containing the quantity to add or remove
   * @return the list of all open positions of the investor
   * @throws NotFoundException when the investor was not found
   */
  public Iterable<Position> addOrRemoveTickerQuantityFromWallet(String username, String ticker,
      QuantityDTO quantityDTO) throws NotFoundException {
    try {
      Position position = new Position();
      position.setTicker(ticker);
      position.setQuantity((int) quantityDTO.getQuantity());
      List<Position> positions = new ArrayList<>();
      positions.add(position);
      walletProxy.addPositions(username, positions);

      return walletProxy.readAllOpenPositionsFromInvestor(username);

    } catch (FeignException e) {
      if (e.status() == 404) throw new NotFoundException();
      else throw e;
    }
  }

  /**
   * Read the price of a ticker
   *
   * @param ticker Identifier of the financial instrument
   * @return the current price of the financial instrument
   */
  public Price readPrice(String ticker) {
    return priceProxy.readPrice(ticker);
  }
}
