package be.vinci.ipl.price.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "prices")
public class Price {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  @JsonIgnore
  private long id;

  @Column(nullable = false)
  private String ticker;

  @Column(nullable = false)
  private float price;

  public Price(String ticker, float price) {
    this.ticker = ticker;
    this.price = price;
  }
}
