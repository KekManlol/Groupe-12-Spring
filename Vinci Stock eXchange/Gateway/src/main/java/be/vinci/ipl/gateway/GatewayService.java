package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.data.AuthenticationProxy;
import be.vinci.ipl.gateway.data.InvestorsProxy;
import be.vinci.ipl.gateway.exceptions.BadRequestException;
import be.vinci.ipl.gateway.exceptions.NotFoundException;
import be.vinci.ipl.gateway.exceptions.UnauthorizedException;
import be.vinci.ipl.gateway.models.Credentials;
import be.vinci.ipl.gateway.models.InvestorData;
import feign.FeignException;

public class GatewayService {
  InvestorsProxy investorsProxy;
  AuthenticationProxy authenticationProxy;
  public GatewayService(InvestorsProxy investorsProxy, AuthenticationProxy authenticationProxy) {
    this.investorsProxy = investorsProxy;
    this.authenticationProxy = authenticationProxy;

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

  public String connect(Credentials credentials) throws BadRequestException, UnauthorizedException {
    try {
      return authenticationProxy.connect(credentials);
    } catch (FeignException e) {
      if (e.status() == 400) throw new BadRequestException();
      else if (e.status() == 401) throw new UnauthorizedException();
      else throw e;
    }
  }
}
