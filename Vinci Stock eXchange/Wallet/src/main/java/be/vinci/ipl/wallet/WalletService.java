package be.vinci.ipl.wallet;

import be.vinci.ipl.wallet.model.Position;
import be.vinci.ipl.wallet.model.PositionWithUsername;
import be.vinci.ipl.wallet.repositories.InvestorProxy;
import be.vinci.ipl.wallet.repositories.PriceProxy;
import be.vinci.ipl.wallet.repositories.WalletRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
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
  /**
   * get the total value of the investor's wallet
   * @param username the username of the investor
   * @return the investor's wallet net worth, or -1 if the investor couldn't be found
   */
  public float getNetWorth(String username) {
    Iterable<PositionWithUsername> positions = repository.findByUsername(username);
    if (investorProxy.readOne(username) == null) return -1;
    float netWorth = 0;
    for (PositionWithUsername position : positions) {
      netWorth += position.getQuantity() * priceProxy.getPriceByTicker(position.getTicker()).getPrice();
    }
    return netWorth;
  }

  /**
   * get open positions of an investor
   * @param username the username of the investor
   * @return open positions, or null if the investor couldn't be found
   */
  public Iterable<Position> getOpenPositions(String username) {
    Iterable<PositionWithUsername> positions = repository.findByUsername(username);
    if (investorProxy.readOne(username) == null) return null;

    return StreamSupport.stream(positions.spliterator(), false)
        .filter(position -> position.getQuantity() > 0)
        .peek(position -> position.setUnitValue(priceProxy.getPriceByTicker(position.getTicker()).getPrice()))
        .map(positionWithUsername -> {
          Position position = new Position();
          position.setTicker(positionWithUsername.getTicker());
          position.setQuantity(positionWithUsername.getQuantity());
          position.setUnitValue(positionWithUsername.getUnitValue());
          return position;
        })
        .collect(Collectors.toList());
  }
  /**
   * add position(s) to investor's wallet
   * @param username the username of the investor
   * @param newPositions positions to add
   * @return update open positions, or null if the investor couldn't be found
   */
  public Iterable<Position> addPositions(String username, List<Position> newPositions) {
    Iterable<PositionWithUsername> existingPositions = repository.findByUsername(username);

    if (investorProxy.readOne(username) == null) return null;

    for (Position position : newPositions) {
      PositionWithUsername existingPosition = StreamSupport.stream(existingPositions.spliterator(), false)
          .filter(p -> p.getTicker().equals(position.getTicker()))
          .findFirst()
          .orElse(null);
      if (existingPosition != null) {
        existingPosition.setQuantity(existingPosition.getQuantity() + position.getQuantity());

        repository.save(existingPosition);
      }
      else {
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
