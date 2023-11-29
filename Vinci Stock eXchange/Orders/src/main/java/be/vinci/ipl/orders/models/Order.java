package be.vinci.ipl.orders.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private OrderSide side;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;

    private double limit;
    private int filled;
}
