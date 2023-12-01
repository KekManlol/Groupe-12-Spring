package be.vinci.ipl.matching.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

  private String ticker;

  private String seller;

  private String buyer;

  private String sell_order_guid;

  private String buy_order_guid;

  private int quantity;

  private float price;
}
