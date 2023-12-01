package be.vinci.ipl.matching.data;

import be.vinci.ipl.matching.models.Order;
import be.vinci.ipl.matching.models.OrderSide;
import be.vinci.ipl.matching.models.PatchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "order")
public interface OrderProxy {

  @GetMapping("/order/open/by-ticker/{ticker}/{side}")
  Iterable<Order> findOrdersByTicker(@PathVariable String ticker, @PathVariable OrderSide side);

  @PatchMapping("/order/{guid}")
  boolean updateOrderQuantity(@PathVariable String guid, @RequestBody PatchDTO patchDTO);
}
