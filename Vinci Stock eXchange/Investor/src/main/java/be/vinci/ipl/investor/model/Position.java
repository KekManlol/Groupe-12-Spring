package be.vinci.ipl.investor.model;

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
  private float unitValue;
  private int quantity;
}
