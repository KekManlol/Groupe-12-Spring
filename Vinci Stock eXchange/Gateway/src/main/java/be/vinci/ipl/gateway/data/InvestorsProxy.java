package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.InvestorData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "investors")
public interface InvestorsProxy {

  @GetMapping("/investor/{username}")
  InvestorData readInvestor(@PathVariable String username);
}
