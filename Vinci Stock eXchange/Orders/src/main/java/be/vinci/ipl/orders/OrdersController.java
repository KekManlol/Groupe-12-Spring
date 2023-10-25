package be.vinci.ipl.orders;

import be.vinci.ipl.orders.models.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/orders/create/{accountId}")
    public ResponseEntity<Order> createOne(@PathVariable int accountId,
                                        @RequestBody Order order) {
        if (!Objects.equals(accountId, order.getAccountId())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        boolean created = ordersService.createOne(order);
        if (!created) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PatchMapping("/orders/status/update/{id}/{status}")
    public ResponseEntity<Void> updateStatus(@PathVariable int id, @PathVariable String status)

}
