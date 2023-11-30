package be.vinci.ipl.wallet;

import be.vinci.ipl.wallet.model.Position;
import be.vinci.ipl.wallet.model.PositionWithUsername;
import be.vinci.ipl.wallet.repositories.InvestorProxy;
import be.vinci.ipl.wallet.repositories.PriceProxy;
import be.vinci.ipl.wallet.repositories.WalletRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
  private final WalletRepository repository;
  private final PriceProxy priceProxy;
  private final InvestorProxy investorProxy;

  public WalletService(WalletRepository repository, PriceProxy priceProxy,
      InvestorProxy investorProxy) {
    this.repository = repository;
    this.priceProxy = priceProxy;
    this.investorProxy = investorProxy;
  }
  public float getNetWorth(String username) {
    List<PositionWithUsername> positions = repository.findByUsername(username);
    if (investorProxy.readOne(username) == null) return -1;
    float netWorth = 0;
    for (PositionWithUsername position : positions) {
      netWorth += position.getQuantity() * priceProxy.getPriceByTicker(position.getTicker()).getPrice();
    }
    return netWorth;
  }
  public List<Position> getOpenPositions(String username) {
    List<PositionWithUsername> positions = repository.findByUsername(username);
    if (investorProxy.readOne(username) == null) return null;

    return positions.stream()
        .map(wallet -> {
          Position position = new Position();
          position.setTicker(wallet.getTicker());
          position.setQuantity(wallet.getQuantity());
          position.setUnitValue(wallet.getUnitValue());
          return position;
        })
        .filter(position -> position.getQuantity() > 0)
        .collect(Collectors.toList());
  }
  public List<Position> addPositions(String username, List<Position> newPositions) {
    List<PositionWithUsername> existingPositions = repository.findByUsername(username);
    if (investorProxy.readOne(username) == null) return null;

    for (Position position : newPositions) {
      PositionWithUsername existingPosition = existingPositions.stream()
          .filter(p -> p.getTicker().equals(position.getTicker()))
          .findFirst()
          .orElse(null);
      if (existingPosition != null) {
        existingPosition.setQuantity(existingPosition.getQuantity() + position.getQuantity());
        repository.save(existingPosition);
      }
      else{
        PositionWithUsername newPosition = new PositionWithUsername();
        newPosition.setUsername(username);
        newPosition.setTicker(position.getTicker());
        newPosition.setQuantity(position.getQuantity());
        repository.save(newPosition);
      }
    }
    return getOpenPositions(username);
  }
}
