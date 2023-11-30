package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.InvestorData;
import be.vinci.ipl.gateway.models.InvestorWithPassword;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Repository
@FeignClient(name = "investors")
public interface InvestorsProxy {

  @GetMapping("/investor/{username}")
  InvestorData readInvestor(@PathVariable String username);

  @PostMapping("/investor/{username}")
  void createInvestor(@PathVariable String username, InvestorWithPassword investorWithPassword);

  @PutMapping("/investor/{username}")
  void updateInvestor(@PathVariable String username, InvestorData investorData);

  @DeleteMapping("/investor/{username}")
  void deleteInvestor(@PathVariable String username);
}
