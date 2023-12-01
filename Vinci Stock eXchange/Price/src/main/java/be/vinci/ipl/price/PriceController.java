package be.vinci.ipl.price;

import be.vinci.ipl.price.models.Price;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {
  private PriceService service;

  public PriceController(PriceService service){
    this.service = service;
  }

  /**
   * @param ticker the ticker's identifier.
   * @return a ResponseEntity containing the requested price.
   */
  @GetMapping("/price/{ticker}")
  public ResponseEntity<Price> readOne(@PathVariable String ticker) {
    Price price = service.readOne(ticker);

    return new ResponseEntity<>(price, HttpStatus.OK);
  }

  /**
   * @param ticker the ticker's identifier.
   * @param newPrice the new price of the ticker.
   * @return a ResponseEntity containing the updated price.
   */
  @PatchMapping("/price/{ticker}")
  public ResponseEntity<Price> updateOne(@PathVariable String ticker, @RequestBody String newPrice) {
    float price = Float.parseFloat(newPrice);
    Price updatedPrice = new Price(ticker, price);
    if (updatedPrice.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    boolean found = service.updateOne(updatedPrice);

    if (!found) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    else return new ResponseEntity<>(HttpStatus.OK);
  }
}
