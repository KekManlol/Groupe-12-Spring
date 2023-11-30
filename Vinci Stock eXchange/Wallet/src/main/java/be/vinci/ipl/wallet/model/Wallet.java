package be.vinci.ipl.wallet.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "wallets")
public class Wallet {

  @Id
  @Column(unique = true)
  @Size(max= 4, min = 4)
  private String ticker;
  private String username;
  @Column(name = "unit_value", nullable = false)
  private float unitValue;
  @Column(nullable = false)
  private int quantity;

}

