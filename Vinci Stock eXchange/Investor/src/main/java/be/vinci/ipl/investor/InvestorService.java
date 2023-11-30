package be.vinci.ipl.investor;

import be.vinci.ipl.investor.model.InvestorData;
import be.vinci.ipl.investor.model.InvestorWithPassword;
import be.vinci.ipl.investor.model.UnsafeCrendential;
import be.vinci.ipl.investor.repositories.AuthenticationProxy;
import be.vinci.ipl.investor.repositories.WalletProxy;
import org.springframework.stereotype.Service;

@Service
public class InvestorService {
  private final InvestorRepository repository;
  private final AuthenticationProxy authenticationProxy;
  private final WalletProxy walletProxy;

  public InvestorService(InvestorRepository repository, AuthenticationProxy authentificationProxy,
      WalletProxy walletProxy) {
    this.repository = repository;
    this.authenticationProxy = authentificationProxy;
    this.walletProxy = walletProxy;
  }


  /**
   * Reads an investor in repository
   * @param username the username of the investor
   * @return the investor, or null if the investor couldn't be found
   */
  public InvestorData readOne(String username){
    return repository.findById(username).orElse(null);
  }
  /**
   * Creates an investor with a password in repository
   * @param investorWithPassword The investor with insecure password
   * @return True if the credentials were created, or false if they already exist
   *
   */
  public boolean createOne(InvestorWithPassword investorWithPassword){
    UnsafeCrendential unsafeCrendential = new UnsafeCrendential();
    String username = investorWithPassword.getInvestorData().getUsername();
    unsafeCrendential.setPassword(investorWithPassword.getPassword());
    unsafeCrendential.setUsername(investorWithPassword.getInvestorData().getUsername());
    authenticationProxy.createOne(username, unsafeCrendential);
    repository.save(investorWithPassword.getInvestorData());
    return true;
  }
  /**
   * Updates an investor in repository
   * @param investorData New values of the investorData
   * @return true if the investor was updated, or false if the user couldn't be found
   */
  public boolean updateOne(InvestorData investorData) {
    if (!repository.existsById(investorData.getUsername())) return false;
    repository.save(investorData);
    return true;
  }
  /**
   * Deletes credentials in repository
   * @param username The username of the investor
   * @return True if the credentials and his wallet were deleted, or false if they couldn't be found
   */
  public int deleteOne(String username) {


    // delete credendtials
    boolean found = authenticationProxy.deleteOne(username);
    if (!found) return -1;
    repository.deleteById(username);

    // "delete" wallets
    float netWorth = walletProxy.getNetWorth(username);
    if (netWorth == 0) return -2;

    return 1;
  }
}
