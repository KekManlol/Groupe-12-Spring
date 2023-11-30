package be.vinci.ipl.wallet;

import be.vinci.ipl.wallet.model.Position;
import be.vinci.ipl.wallet.model.PositionWithUsername;
import be.vinci.ipl.wallet.repositories.PriceProxy;
import be.vinci.ipl.wallet.repositories.WalletRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
  private final WalletRepository repository;
  private final PriceProxy priceProxy;

  public WalletService(WalletRepository repository, PriceProxy priceProxy) {
    this.repository = repository;
    this.priceProxy = priceProxy;
  }
  public float getNetWorth(String username) {
    List<PositionWithUsername> positions = repository.findByUsername(username);
    if (positions == null || positions.isEmpty()) return 0;
    float netWorth = 0;
    for (PositionWithUsername position : positions) {
      netWorth += position.getQuantity() * priceProxy.getPriceByTicker(position.getTicker()).getPrice();
    }
    return netWorth;
  }
  public List<Position> getOpenPositions(String username) {
    List<PositionWithUsername> positions = repository.findByUsername(username);
    if (positions == null || positions.isEmpty()) return null;

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
    if (existingPositions == null) return null;

    for (Position position : newPositions) {
      PositionWithUsername existingPosition = existingPositions.stream()
          .filter(p -> p.getTicker().equals(position.getTicker()))
          .findFirst()
          .orElse(null);
      if (existingPosition != null) {
        existingPosition.setQuantity(existingPosition.getQuantity() + position.getQuantity());
        repository.save(existingPosition);
      }

    }
    return getOpenPositions(username);
  }
}
