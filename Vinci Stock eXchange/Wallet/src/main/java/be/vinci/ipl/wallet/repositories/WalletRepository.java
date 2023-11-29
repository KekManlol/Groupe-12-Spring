package be.vinci.ipl.wallet.repositories;

import be.vinci.ipl.wallet.WalletService;
import be.vinci.ipl.wallet.model.InvestorData;
import be.vinci.ipl.wallet.model.Position;
import be.vinci.ipl.wallet.model.Wallet;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long> {
  List<Wallet> findByUsername(String username);

}
