package be.vinci.ipl.execution.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
  private String GUIDSellingOrder;

  @Column(nullable = false)
  private String GUIDBuyingOrder;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  private float price;


  public boolean invalid(){
    return ticker == null || ticker.isBlank() ||
        buyer == null || buyer.isBlank() ||
        seller == null || seller.isBlank() ||
        GUIDSellingOrder == null || GUIDSellingOrder.isBlank() ||
        GUIDBuyingOrder == null || GUIDBuyingOrder.isBlank() ||
        quantity <= 0 ||
        price <= 0;
  }
}
