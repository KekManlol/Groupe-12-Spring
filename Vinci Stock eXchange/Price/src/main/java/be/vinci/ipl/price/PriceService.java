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

  public Price readOne(String ticker) {
    return repository.findByTicker(ticker).orElse(null);
  }

  public boolean updateOne(Price price) {
    Optional<Price> oldPrice = repository.findByTicker(price.getTicker());
    if(oldPrice.isEmpty()) return false;
    repository.delete(oldPrice.get());
    repository.save(price);
    return true;
  }
}
