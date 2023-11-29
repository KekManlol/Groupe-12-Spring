package be.vinci.ipl.execution.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Position {
  private String ticker;
  private int quantity;
  private double unitValue;


  public Position(String ticker, int quantity, double unitValue){
    this.ticker = ticker;
    this.quantity = quantity;
    this.unitValue = unitValue;
  }
}
