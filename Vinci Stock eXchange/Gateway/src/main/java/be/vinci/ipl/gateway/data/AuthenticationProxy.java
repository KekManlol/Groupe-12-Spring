package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.Credentials;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "authentication")
public interface AuthenticationProxy {

  @PostMapping("/authentication/connect")
  String connect(@RequestBody Credentials credentials);

  @PostMapping("/authentication/verify")
  String verify(@RequestBody String token);

  @PutMapping("/authentication/{username}")
  void updateCredentials(String username, @RequestBody Credentials credentials);
}
