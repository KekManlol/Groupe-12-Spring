package be.vinci.ipl.investor.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "wallets", url = "http://localhost:9005")
public interface WalletProxy {
  @GetMapping("/wallet/{username}/net-worth")
  Float getNetWorth(@PathVariable String username);
}
