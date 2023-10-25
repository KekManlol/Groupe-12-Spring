package be.vinci.ipl.orders;

import be.vinci.ipl.orders.data.AccountsProxy;
import be.vinci.ipl.orders.models.Order;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {
    private final OrdersRepository repository;
    private final AccountsProxy accountsProxy;

    public OrdersService(OrdersRepository repository, AccountsProxy accountsProxy) {
        this.repository = repository;
        this.accountsProxy = accountsProxy;
    }
    public boolean createOne(Order order) {
        if (accountsProxy.getAccountFromAccountId(order.getAccountId()) == null) return false;
        repository.save(order);
        return true;
    }
}
