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

  private String GUIDSellingOrder;

  private String GUIDBuyingOrder;

  private int quantity;

  private float price;
}
