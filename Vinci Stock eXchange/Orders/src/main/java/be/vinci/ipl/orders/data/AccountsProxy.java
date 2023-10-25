package be.vinci.ipl.orders.data;

import be.vinci.ipl.orders.models.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "accounts")
public interface AccountsProxy {
    @GetMapping("/accounts/{accountId}")
    Account getAccountFromAccountId(@PathVariable int accountId);
}
