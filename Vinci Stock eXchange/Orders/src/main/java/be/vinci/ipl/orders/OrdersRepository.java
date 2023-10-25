package be.vinci.ipl.orders;

import be.vinci.ipl.orders.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends CrudRepository<Order, Integer> {
}
