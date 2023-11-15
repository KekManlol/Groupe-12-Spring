package be.vinci.ipl.orders;

import be.vinci.ipl.orders.models.Order;
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
}
