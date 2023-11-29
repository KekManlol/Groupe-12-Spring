package be.vinci.ipl.investor.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InvestorWithPassword{

  private InvestorData investorData;
  private String password;
  public boolean invalid() {
    return investorData.invalid() ||
        password == null || password.isBlank();
  }
}
