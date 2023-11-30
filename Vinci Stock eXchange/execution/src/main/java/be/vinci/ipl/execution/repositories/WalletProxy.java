package be.vinci.ipl.execution.repositories;

import be.vinci.ipl.execution.models.Position;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name="wallet")
public interface WalletProxy {

  @PostMapping("/wallet/{username}")
   void updateOne(@PathVariable String username, @RequestBody Position position);

}
