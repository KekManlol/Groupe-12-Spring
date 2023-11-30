package be.vinci.ipl.orders;

import be.vinci.ipl.orders.models.Order;
import be.vinci.ipl.orders.models.OrderSide;
import be.vinci.ipl.orders.models.PatchDTO;
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



    @PostMapping("/order")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        if (order.getGuid() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Order newOrder = ordersService.createOne(order);
        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }

    @GetMapping("/order/{guid}")
    public ResponseEntity<Order> readOrder(@PathVariable String guid) {
        Order order = ordersService.readOne(guid);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PatchMapping("/order/{guid}")
    public ResponseEntity<Void> updateSharesQuantity(@PathVariable String guid, @RequestBody
        PatchDTO patchDTO) {
        boolean updated = ordersService.updateSharesQuantity(guid, patchDTO.getFilled());

        if (!updated) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/order/by-user/{username}")
    public ResponseEntity<Iterable<Order>> readAllOrdersFromUser(@PathVariable String username) {
        Iterable<Order> orders = ordersService.readFromUser(username);
        if (orders == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/order/open/by-ticker/{ticker}/{side}")
    public ResponseEntity<Iterable<Order>> readOpenOrders(@PathVariable String ticker, @PathVariable OrderSide side) {
        Iterable<Order> orders = ordersService.getOpenOrders(ticker, side);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
