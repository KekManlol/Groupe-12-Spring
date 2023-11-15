package be.vinci.ipl.price.repositories;

import be.vinci.ipl.price.models.Price;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends CrudRepository<Price, Long> {
  Optional<Price> findByTicker(String ticker);

}
