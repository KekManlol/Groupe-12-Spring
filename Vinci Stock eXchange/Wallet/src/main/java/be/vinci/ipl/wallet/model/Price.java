package be.vinci.ipl.wallet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Price {

  private long id;

  private String ticker;

  private float price;

}
