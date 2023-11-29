package be.vinci.ipl.price;

import be.vinci.ipl.price.models.Price;
import be.vinci.ipl.price.repositories.PriceRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

  private final PriceRepository repository;

  public PriceService(PriceRepository repository) {
    this.repository = repository;
  }

  /**
   * @param ticker the ticker's identifier.
   * @return the ticker or null if not found.
   */
  public Price readOne(String ticker) {
    return repository.findByTicker(ticker).orElse(null);
  }

  /**
   * @param newPrice the new price.
   * @return true if the price was updated, false if not.
   */
  public boolean updateOne(Price newPrice) {
    Optional<Price> price = repository.findByTicker(newPrice.getTicker());
    if(price.isEmpty()) return false;
    price.get().setPrice(newPrice.getPrice());
    repository.save(price.get());
    return true;
  }
}
