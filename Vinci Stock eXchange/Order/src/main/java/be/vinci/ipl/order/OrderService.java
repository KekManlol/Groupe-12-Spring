package be.vinci.ipl.order;

import be.vinci.ipl.order.data.MatchingProxy;
import be.vinci.ipl.order.models.Order;
import be.vinci.ipl.order.models.OrderSide;
import be.vinci.ipl.order.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final MatchingProxy matchingProxy;
    public OrderService(OrderRepository repository, MatchingProxy matchingProxy) {
        this.repository = repository;
        this.matchingProxy = matchingProxy;
    }

    /**
     * Create an order in repository
     *
     * @param order Order to create
     * @return Created order
     */
    public Order createOne(Order order) {
        matchingProxy.findMatches(order.getTicker());
        return repository.save(order);
    }

    /**
     * Reads an order in repository
     *
     * @param guid Guid of the order
     * @return The order or null if the order couldn't be found
     */
    public Order readOne(String guid) {
        Order order = repository.findByGuid(guid).orElse(null);
        return order;
    }

    /**
     * Updates an order's share quantity in repository
     *
     * @param guid Guid of the order
     * @param filled Quantity of shares already exchanged
     * @return true if the order was updated or false if the order couldn't be found
     */
    public boolean updateSharesQuantity(String guid, int filled) {
        Order order = repository.findByGuid(guid).orElse(null);
        if (order == null) return false;

        order.setFilled(filled);
        repository.save(order);
        return true;
    }

    /**
     * Reads all orders of a user
     *
     * @param username The username of the user
     * @return The list of orders from this user
     */
    public Iterable<Order> readFromUser(String username) {
        if (!repository.existsByOwner(username)) return null;
        return repository.findByOwner(username);
    }

    /**
     * Reads all open orders of derivative of a given side.
     *
     * @param ticker The financial instrument's identifier
     * @param side Side of the transaction
     * @return The list of open orders related to a financial instrument
     */
    public Iterable<Order> getOpenOrders(String ticker, OrderSide side) {
        Iterable<Order> orders = repository.findByTickerAndSide(ticker, side);
        return StreamSupport.stream(orders.spliterator(), false)
                .filter(order -> order.getFilled() < order.getQuantity())
                .toList();
    }
}
