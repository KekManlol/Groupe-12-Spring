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
@Entity(name = "wallets")
public class PositionWithUsername {

  @Id
  @Column(unique = true)
  @Size(max= 4, min = 4)
  private String ticker;
  @Column(nullable = false)
  private String username;
  @Column(name = "unit_value", nullable = false)
  private float unitValue = 1.0f;
  @Column(nullable = false)
  private int quantity;

}

