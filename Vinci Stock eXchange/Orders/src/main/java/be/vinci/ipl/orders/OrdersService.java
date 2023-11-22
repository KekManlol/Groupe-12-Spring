package be.vinci.ipl.orders;

import be.vinci.ipl.orders.models.Order;
import be.vinci.ipl.orders.models.OrderSide;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {
    private final OrdersRepository repository;

    public OrdersService(OrdersRepository repository) {
        this.repository = repository;
    }
    public boolean createOne(Order order) {
        repository.save(order);
        return true;
    }

    public Order getOne(String guid) {
        Order order = repository.findByGuid(guid).orElse(null);
        return order;
    }

    public boolean updateActionsQuantity(Order updatedOrder) {
        Order order = repository.findByGuid(updatedOrder.getGuid()).orElse(null);
        if (order == null) return false;

        order.setFilled(updatedOrder.getFilled());
        repository.save(order);
        return true;
    }

    public Iterable<Order> readFromUser(String username) {
        if (!repository.existsByOwner(username)) return null;
        return repository.findByOwner(username);
    }

    public Iterable<Order> getOpenOrders(String ticker, OrderSide side) {
        return repository.findByTickerAndSide(ticker, side);
    }
}
