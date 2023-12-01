package be.vinci.ipl.execution.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "transactions")
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  @JsonIgnore
  private long id;

  @Column(nullable = false)
  private String ticker;

  @Column(nullable = false)
  private String seller;

  @Column(nullable = false)
  private String buyer;

  @Column(nullable = false)
  @JsonProperty("sell_order_guid")
  private String sellOrderGUID;

  @Column(nullable = false)
  @JsonProperty("buy_order_guid")
  private String buyOrderGUID;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  private float price;


  /**
   * @return checks if the transaction is invalid, returns true if it is the case or false if not.
   */
  public boolean invalid(){
    return ticker == null || ticker.isBlank() ||
        buyer == null || buyer.isBlank() ||
        seller == null || seller.isBlank() ||
        sellOrderGUID == null || sellOrderGUID.isBlank() ||
        buyOrderGUID == null || buyOrderGUID.isBlank() ||
        quantity <= 0 ||
        price < 0;
  }
}
