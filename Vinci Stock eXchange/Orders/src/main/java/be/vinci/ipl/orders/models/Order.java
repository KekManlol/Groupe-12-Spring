package be.vinci.ipl.orders.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
public class Order {
    @Id
    private int id;

    @Column(name = "account_id", nullable = false)
    private int accountId;

    private enum type {
        LIMIT,MARKET;
    }
    private enum side {
        BUYER,SELLER;
    }

    private enum status {
        NEW,EXECUTED,PARTIALLY_EXECUTED;
    }
}
