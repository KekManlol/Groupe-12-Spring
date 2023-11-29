package be.vinci.ipl.wallet.repositories;

import be.vinci.ipl.wallet.model.Position;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends CrudRepository<Position, Long> {


  List<Position> findByUsername(String username);
}
