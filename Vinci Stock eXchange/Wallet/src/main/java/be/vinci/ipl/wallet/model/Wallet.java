package be.vinci.ipl.wallet.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import jakarta.persistence.OneToOne;
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
  private String ticker;
  private String username;
  @Column(name = "unit_value", nullable = false)
  private double unitValue;
  @Column(nullable = false)
  private int quantity;

}

