package be.vinci.ipl.wallet;


import be.vinci.ipl.wallet.model.InvestorData;
import be.vinci.ipl.wallet.model.Position;
import be.vinci.ipl.wallet.repositories.InvestorProxy;
import be.vinci.ipl.wallet.repositories.PositionRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
  private final PositionRepository positionRepository;
  private final InvestorProxy investorProxy;



  public WalletService(PositionRepository positionRepository, InvestorProxy investorProxy) {
    this.positionRepository = positionRepository;
    this.investorProxy = investorProxy;

  }
  public List<Position> createPositions(String username, List<Position> newPositions) {
    InvestorData investorData = investorProxy.readOne(username);
    List<Position> existingPositions = investorData.getPositions();

    for (Position newPosition : newPositions) {
      String ticker = newPosition.getTicker();
      int newQuantity = newPosition.getQuantity();
      double unitValue = newPosition.getUnitValue();

      boolean positionExists = false;

      for (Position existingPosition : existingPositions) {
        if (existingPosition.getTicker().equals(ticker)) {
          // Update existing position
          existingPosition.setQuantity(existingPosition.getQuantity() + newQuantity);
          positionExists = true;
          break;
        }
      }

      if (!positionExists) {
        // Add new position
        Position newPositionObject = new Position();
        newPositionObject.setTicker(ticker);
        newPositionObject.setQuantity(newQuantity);
        newPositionObject.setUnitValue(unitValue);
        existingPositions.add(newPositionObject);
      }
    }

    // Update the investorData object with the modified positions
    investorData.setPositions(existingPositions);

    // Print the updated positions
    System.out.println(existingPositions);

    return existingPositions;
  }


}
