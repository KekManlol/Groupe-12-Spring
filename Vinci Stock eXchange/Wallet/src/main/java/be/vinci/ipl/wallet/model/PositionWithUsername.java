package be.vinci.ipl.wallet.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  @JsonIgnore
  private Long id;
  @Column(nullable = false)
  private String ticker;
  @Column(nullable = false)
  private String username;
  @Column(name = "unit_value", nullable = false)
  private float unitValue;
  @Column(nullable = false)
  private int quantity;
}

