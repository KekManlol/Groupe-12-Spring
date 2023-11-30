package be.vinci.ipl.execution.repositories;

import be.vinci.ipl.execution.models.Position;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name="price")
public interface PriceProxy {

  @PatchMapping("/price/{ticker}")
  void updateOne(@PathVariable String ticker, String newPrice);

}
