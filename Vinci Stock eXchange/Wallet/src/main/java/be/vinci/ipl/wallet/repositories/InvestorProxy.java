package be.vinci.ipl.wallet.repositories;

import be.vinci.ipl.wallet.model.InvestorData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "investors", url = "http://localhost:9001")
public interface InvestorProxy {
  @GetMapping("/investor/{username}")
  InvestorData readOne(@PathVariable String username);
}
