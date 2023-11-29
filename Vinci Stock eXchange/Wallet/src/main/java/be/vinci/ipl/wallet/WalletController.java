package be.vinci.ipl.wallet;

import be.vinci.ipl.wallet.model.Position;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {
  private WalletService service;

  public WalletController(WalletService service){this.service = service;}

  @GetMapping("wallet/{username}/net-worth")
  public ResponseEntity<Double> getNetWorth(@PathVariable String username) {
    try {
      Double netWorth = service.getNetWorth(username);
      return ResponseEntity.ok(netWorth);
    } catch (Exception e) {
      // Gérer les erreurs appropriées, par exemple, utilisateur non trouvé, etc.
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
  @PostMapping("/wallet/{username}")
  public ResponseEntity<List<Position>> addPosition(@PathVariable String username, @RequestBody List<Position> positions) {
    List<Position> updatedPositions = service.addPositions(username, positions);
    return new ResponseEntity<>(updatedPositions, HttpStatus.OK);
  }
  @GetMapping("wallet/{username}")
  public ResponseEntity<List<Position>> getOpenPositions(@PathVariable String username) {
    try {
      List<Position> openPositions = service.getOpenPositions(username);
      return ResponseEntity.ok(openPositions);
    } catch (Exception e) {
      // Gérer les erreurs appropriées, par exemple, utilisateur non trouvé, etc.
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }


}
