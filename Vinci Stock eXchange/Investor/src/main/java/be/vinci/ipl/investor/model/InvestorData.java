package be.vinci.ipl.investor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "investors")
public class InvestorData {
  @Id
  @Column(nullable = false)
  private String username;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String lastname;
  @Column(nullable = false)
  private String birthday;
  public boolean invalid() {
    return username == null || username.isBlank() ||
        email == null || email.isBlank() ||
        lastname == null || lastname.isBlank() ||
        birthday == null || birthday.isBlank();
  }
}
