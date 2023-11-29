package be.vinci.ipl.wallet.model;

import java.util.ArrayList;
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
public class InvestorData {
  private String username;
  private String email;
  private String lastname;
  private String birthday;
  private List<Position> positions;
}
