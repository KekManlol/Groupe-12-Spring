package be.vinci.ipl.wallet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "positions")
public class Position {
  @Id
  @Column(nullable = false, unique = true)

  private String ticker;
  @Column(nullable = false)
  private int quantity;
  @Column(name = "unit_value", nullable = false)
  private double unitValue;
  private String username;

}
