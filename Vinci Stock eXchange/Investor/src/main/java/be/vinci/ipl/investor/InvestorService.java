package be.vinci.ipl.investor;

import be.vinci.ipl.investor.model.InvestorData;
import be.vinci.ipl.investor.model.InvestorWithPassword;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class InvestorService {
  private final InvestorRepository repository;

  public InvestorService(InvestorRepository repository) {
    this.repository = repository;
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
   */
  public boolean createOne(InvestorWithPassword investorWithPassword){
    InvestorData investorData = investorWithPassword.getInvestorData();
    if (repository.existsById(investorData.getUsername())) return false;
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
  public boolean deleteOne(String username) {
    if (!repository.existsById(username)) return false;
    repository.deleteById(username);
    return true;
  }
}
