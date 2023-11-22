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
    private String guid;
    
    private String owner;

    private String timestamp;

    @Column(nullable = false)
    private String ticker;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private OrderSide side;
    @Column(nullable = false)
    private OrderType type;

    private double limit;
    private int filled;
}
