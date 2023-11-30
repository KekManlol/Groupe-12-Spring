package be.vinci.ipl.orders.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false)
    private String guid;

    @Column(updatable = false)
    private String owner;
    @Column(updatable = false)
    private int timestamp;

    @Column(updatable = false, nullable = false)
    private String ticker;

    @Column(updatable = false, nullable = false)
    private int quantity;

    @Column(updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderSide side;

    @Column(updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(name = "limit_price", updatable = false)
    private double limit;
    private int filled;
}
