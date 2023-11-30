package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.Position;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Repository
@FeignClient(name = "wallets")
public interface WalletProxy {

  @GetMapping("/wallet/{username}")
  Iterable<Position> readAllOpenPositionsFromInvestor(@PathVariable String username);

  @GetMapping("/wallet/{username}/net-worth")
  Float readWalletNetValueFromInvestor(@PathVariable String username);

  @PostMapping("/wallet/{username}")
  Iterable<Position> addPositions(@PathVariable String username, Iterable<Position> positions);


}
