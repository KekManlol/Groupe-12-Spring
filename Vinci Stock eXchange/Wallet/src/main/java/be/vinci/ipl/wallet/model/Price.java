package be.vinci.ipl.wallet.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Price {

  private long id;
  private String ticker;
  private float price;

}
