package be.vinci.ipl.gateway.models;

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
}
