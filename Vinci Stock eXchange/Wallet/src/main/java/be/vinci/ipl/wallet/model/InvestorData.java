package be.vinci.ipl.wallet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InvestorData {

  private String username;

  private String email;

  private String firstname;

  private String lastname;

  private String birthday;
}
