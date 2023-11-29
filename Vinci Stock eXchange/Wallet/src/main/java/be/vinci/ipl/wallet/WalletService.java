package be.vinci.ipl.wallet;


import be.vinci.ipl.wallet.model.InvestorData;
import be.vinci.ipl.wallet.model.Position;
import be.vinci.ipl.wallet.model.Wallet;
import be.vinci.ipl.wallet.repositories.InvestorProxy;
import be.vinci.ipl.wallet.repositories.WalletRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
  private final WalletRepository repository;
  private final InvestorProxy investorProxy;



  public WalletService(WalletRepository repository, InvestorProxy investorProxy) {
    this.repository = repository;
    this.investorProxy = investorProxy;
  }
  public Double getNetWorth(String username) {
    List<Wallet> positions = repository.findByUsername(username);

    return positions.stream()
        .mapToDouble(position -> position.getQuantity() * position.getUnitValue())
        .sum();
  }
  public List<Position> getOpenPositions(String username) {
    List<Wallet> positions = repository.findByUsername(username);

    List<Position> openPositions = positions.stream()
        .map(entity -> {
          Position position = new Position();
          position.setTicker(entity.getTicker());
          position.setQuantity(entity.getQuantity());
          position.setUnitValue(entity.getUnitValue());
          return position;
        })
        .filter(position -> position.getQuantity() > 0)
        .collect(Collectors.toList());

    return openPositions;
  }

  public List<Position> addPositions(String username, List<Position> positions) {
    List<Wallet> existingPositions = repository.findByUsername(username);

    for (Position position : positions) {
      Wallet existingPosition = existingPositions.stream()
          .filter(p -> p.getTicker().equals(position.getTicker()))
          .findFirst()
          .orElse(null);

      if (existingPosition != null) {
        // Mettre à jour la quantité existante
        existingPosition.setQuantity(existingPosition.getQuantity() + position.getQuantity());
        if (position.getUnitValue() != 0.0) {
          existingPosition.setUnitValue(position.getUnitValue());
        }
        repository.save(existingPosition);
      } else {
        // Créer une nouvelle position si elle n'existe pas
        Wallet newPosition = new Wallet();
        newPosition.setUsername(username);
        newPosition.setTicker(position.getTicker());
        newPosition.setQuantity(position.getQuantity());
        newPosition.setUnitValue(position.getUnitValue());
        repository.save(newPosition);
      }
    }

    // Récupérer les positions mises à jour depuis la base de données
    List<Wallet> updatedPositions = repository.findByUsername(username);

    // Mapper les positions vers PositionDTO
    List<Position> updatedPositionDTOs = updatedPositions.stream()
        .map(entity -> {
          Position position = new Position();
          position.setTicker(entity.getTicker());
          position.setQuantity(entity.getQuantity());
          position.setUnitValue(entity.getUnitValue());
          return position;
        })
        .collect(Collectors.toList());

    return updatedPositionDTOs;
  }
}
