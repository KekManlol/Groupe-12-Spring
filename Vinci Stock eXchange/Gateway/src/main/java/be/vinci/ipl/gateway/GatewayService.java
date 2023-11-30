package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.data.AuthenticationProxy;
import be.vinci.ipl.gateway.data.InvestorsProxy;
import be.vinci.ipl.gateway.data.OrderProxy;
import be.vinci.ipl.gateway.exceptions.BadRequestException;
import be.vinci.ipl.gateway.exceptions.ConflictException;
import be.vinci.ipl.gateway.exceptions.NotFoundException;
import be.vinci.ipl.gateway.exceptions.UnauthorizedException;
import be.vinci.ipl.gateway.models.Credentials;
import be.vinci.ipl.gateway.models.InvestorData;
import be.vinci.ipl.gateway.models.InvestorWithPassword;
import be.vinci.ipl.gateway.models.Order;
import feign.FeignException;
import feign.FeignException.Forbidden;

public class GatewayService {
  InvestorsProxy investorsProxy;
  AuthenticationProxy authenticationProxy;
  OrderProxy orderProxy;
  public GatewayService(InvestorsProxy investorsProxy, AuthenticationProxy authenticationProxy,
      OrderProxy orderProxy) {
    this.investorsProxy = investorsProxy;
    this.authenticationProxy = authenticationProxy;
    this.orderProxy = orderProxy;

  }
  public InvestorData readInvestor(String username) throws UnauthorizedException, NotFoundException {
    try {
      return investorsProxy.readInvestor(username);
    } catch (FeignException e) {
      if (e.status() == 400) throw new UnauthorizedException();
      else if (e.status() == 404) throw new NotFoundException();
      else throw e;
    }
  }
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

  public void deleteInvestor(String username) throws BadRequestException, NotFoundException {
    try {
      investorsProxy.deleteInvestor(username);
    } catch (FeignException e) {
      if (e.status() == 400) throw new BadRequestException();
      else if (e.status() == 404) throw new NotFoundException();
      else throw e;
    }
  }

  public String connect(Credentials credentials) throws BadRequestException, UnauthorizedException {
    try {
      return authenticationProxy.connect(credentials);
    } catch (FeignException e) {
      if (e.status() == 400) throw new BadRequestException();
      else if (e.status() == 401) throw new UnauthorizedException();
      else throw e;
    }
  }

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

  public Order createOrder(Order order) throws BadRequestException {
    try {
      return orderProxy.createOrder(order);
    } catch (FeignException e) {
      if (e.status() == 400) throw new BadRequestException();
      else throw e;
    }
  }

  public Iterable<Order> readAllOrdersFromUser(String username) throws NotFoundException {
    try {
      return orderProxy.readAllOrdersFromUser(username);
    } catch (FeignException e) {
      if (e.status() == 404) throw new NotFoundException();
      else throw e;
    }
  }

  public String verify(String token) {
    try {
      return authenticationProxy.verify(token);
    } catch (FeignException e) {
      if (e.status() == 401) return null;
      else throw e;
    }
  }
}
