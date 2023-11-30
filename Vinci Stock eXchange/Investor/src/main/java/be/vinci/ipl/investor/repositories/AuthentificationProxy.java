package be.vinci.ipl.investor.repositories;


import be.vinci.ipl.investor.model.UnsafeCrendential;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "authentification", url = "http://localhost:9003")
public interface AuthentificationProxy {
  @PostMapping("/authentication/{username}")
  boolean createOne(@PathVariable String username, @RequestBody UnsafeCrendential unsafeCrendential);

  @DeleteMapping("/authentication/{username}")
  boolean deleteOne(@PathVariable String username);
}
