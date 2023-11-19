package be.vinci.ipl.investor.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InvestorWithPassword{
  @OneToOne
  private InvestorData investorData;
  private String password;
  public boolean invalid() {
    return investorData.invalid() ||
        password == null || password.isBlank();
  }
}
