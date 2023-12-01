package be.vinci.ipl.order;

import be.vinci.ipl.order.models.Order;
import be.vinci.ipl.order.models.OrderSide;
import be.vinci.ipl.order.models.PatchDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Places a new order
     *
     * @param order The order to be created
     * @return The created order or a BAD_REQUEST response if the user attempted to force a GUID
     */
    @PostMapping("/order")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        if (order.getGuid() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Order newOrder = orderService.createOne(order);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    /**
     * Reads an order by its GUID
     *
     * @param guid GUID of the order
     * @return The order specified by the GUID or a NOT_FOUND response if the order couldn't be found
     */
    @GetMapping("/order/{guid}")
    public ResponseEntity<Order> readOrder(@PathVariable String guid) {
        Order order = orderService.readOne(guid);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    /**
     * Updates the number of shares of an order already exchanged
     *
     * @param guid     GUID of the order to be updated
     * @param patchDTO DTO containing the filled value to be updated
     * @return OK response if the order was updated, NOT_FOUND response otherwise
     */
    @PatchMapping("/order/{guid}")
    public ResponseEntity<Void> updateFilled(@PathVariable String guid, @RequestBody
        PatchDTO patchDTO) {
        boolean updated = orderService.updateFilled(guid, patchDTO.getFilled());

        if (!updated) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Reads all orders from a specific user
     *
     * @param username Username of the user
     * @return All orders from the user or NOT_FOUND response if no orders are found
     */
    @GetMapping("/order/by-user/{username}")
    public ResponseEntity<Iterable<Order>> readAllOrdersFromUser(@PathVariable String username) {
        Iterable<Order> orders = orderService.readFromUser(username);
        if (orders == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Reads all open orders by ticker and side
     *
     * @param ticker Ticker of the financial instrument
     * @param side   Side of the orders (BUY or SELL)
     * @return All open orders for the specified ticker and side
     */
    @GetMapping("/order/open/by-ticker/{ticker}/{side}")
    public ResponseEntity<Iterable<Order>> readAllOpenOrdersByTickerAndSide(@PathVariable String ticker, @PathVariable OrderSide side) {
        Iterable<Order> orders = orderService.getOpenOrders(ticker, side);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
