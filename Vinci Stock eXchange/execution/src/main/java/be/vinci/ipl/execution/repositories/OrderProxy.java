package be.vinci.ipl.execution.repositories;

import be.vinci.ipl.execution.models.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name="order")
public interface OrderProxy {

  @GetMapping("/order/{guid}")
  Order getOne(@PathVariable String guid);

  @PatchMapping("/order/{guid}")
  void updateOne(@PathVariable String guid, @RequestBody Order order);

}
