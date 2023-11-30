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
  private String sell_order_guid;

  @Column(nullable = false)
  private String buy_order_guid;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  private float price;


  /**
   * @return true if the transaction is not valid, false if it is considered valid.
   */
  public boolean invalid(){
    return ticker == null || ticker.isBlank() ||
        buyer == null || buyer.isBlank() ||
        seller == null || seller.isBlank() ||
        sell_order_guid == null || sell_order_guid.isBlank() ||
        buy_order_guid == null || buy_order_guid.isBlank() ||
        quantity <= 0 ||
        price <= 0;
  }
}
