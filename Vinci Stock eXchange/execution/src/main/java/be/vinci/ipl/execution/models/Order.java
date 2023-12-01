package be.vinci.ipl.execution.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
