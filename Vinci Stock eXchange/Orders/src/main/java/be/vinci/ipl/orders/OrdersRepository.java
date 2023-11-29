package be.vinci.ipl.orders;

import be.vinci.ipl.orders.models.Order;
import be.vinci.ipl.orders.models.OrderSide;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepository extends CrudRepository<Order, Integer> {
    Optional<Order> findByGuid(String guid);

    Iterable<Order> findByOwner(String username);
    boolean existsByOwner(String username);

    Iterable<Order> findByTickerAndSide(String ticker, OrderSide side);
}
