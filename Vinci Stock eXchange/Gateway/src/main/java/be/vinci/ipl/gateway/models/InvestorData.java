package be.vinci.ipl.gateway.models;

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
