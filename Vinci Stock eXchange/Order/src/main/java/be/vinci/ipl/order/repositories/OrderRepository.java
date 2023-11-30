package be.vinci.ipl.order.repositories;

import be.vinci.ipl.order.models.Order;
import be.vinci.ipl.order.models.OrderSide;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    Optional<Order> findByGuid(String guid);

    Iterable<Order> findByOwner(String username);
    boolean existsByOwner(String username);

    Iterable<Order> findByTickerAndSide(String ticker, OrderSide side);
}
