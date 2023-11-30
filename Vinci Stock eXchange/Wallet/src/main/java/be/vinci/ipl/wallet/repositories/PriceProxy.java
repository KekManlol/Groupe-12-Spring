package be.vinci.ipl.wallet.repositories;

import be.vinci.ipl.wallet.model.Price;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "price", url = "http://localhost:9004")
public interface PriceProxy {
  @GetMapping("/price/{ticker}")
  Price getPriceByTicker(@PathVariable String ticker);
}
