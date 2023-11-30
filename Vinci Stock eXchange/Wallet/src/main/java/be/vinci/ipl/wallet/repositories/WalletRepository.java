package be.vinci.ipl.wallet.repositories;

import be.vinci.ipl.wallet.model.PositionWithUsername;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends CrudRepository<PositionWithUsername, Long> {
  List<PositionWithUsername> findByUsername(String username);

}
