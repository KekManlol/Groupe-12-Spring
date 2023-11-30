package be.vinci.ipl.orders;

import be.vinci.ipl.orders.models.Order;
import be.vinci.ipl.orders.models.OrderSide;
import be.vinci.ipl.orders.models.PatchDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        if (order.getGuid() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Order newOrder = orderService.createOne(order);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @GetMapping("/order/{guid}")
    public ResponseEntity<Order> readOrder(@PathVariable String guid) {
        Order order = orderService.readOne(guid);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PatchMapping("/order/{guid}")
    public ResponseEntity<Void> updateSharesQuantity(@PathVariable String guid, @RequestBody
        PatchDTO patchDTO) {
        boolean updated = orderService.updateSharesQuantity(guid, patchDTO.getFilled());

        if (!updated) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/order/by-user/{username}")
    public ResponseEntity<Iterable<Order>> readAllOrdersFromUser(@PathVariable String username) {
        Iterable<Order> orders = orderService.readFromUser(username);
        if (orders == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/order/open/by-ticker/{ticker}/{side}")
    public ResponseEntity<Iterable<Order>> readAllOpenOrdersByTickerAndSide(@PathVariable String ticker, @PathVariable OrderSide side) {
        Iterable<Order> orders = orderService.getOpenOrders(ticker, side);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
