package be.vinci.ipl.matching.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Price {

  private long id;

  private String ticker;

  private float price;


}
