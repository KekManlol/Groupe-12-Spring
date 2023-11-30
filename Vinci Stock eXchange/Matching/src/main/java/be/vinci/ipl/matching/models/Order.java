package be.vinci.ipl.matching.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Order {

    private String guid;

    private String owner;

    private int timestamp;

    private String ticker;

    private int quantity;

    private OrderSide side;

    private OrderType type;

    private double limit;

    private int filled;
}
