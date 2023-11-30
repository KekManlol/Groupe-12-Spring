package be.vinci.ipl.wallet;

import be.vinci.ipl.wallet.model.Position;
import be.vinci.ipl.wallet.model.Wallet;
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

  @GetMapping("/wallet/{username}/net-worth")
  public ResponseEntity<Float> getNetWorth(@PathVariable String username) {
    float netWorth = service.getNetWorth(username);
    if (netWorth == -1) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(netWorth, HttpStatus.OK);

  }
  @PostMapping("/wallet/{username}")
  public ResponseEntity<List<Position>> addPosition(@PathVariable String username, @RequestBody List<Position> positions) {
    List<Position> updatedPositions = service.addPositions(username, positions);
    if (updatedPositions == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(updatedPositions, HttpStatus.OK);
  }
  @GetMapping("/wallet/{username}")
  public ResponseEntity<List<Position>> getOpenPositions(@PathVariable String username) {
    List<Position> openPositions = service.getOpenPositions(username);
    if (openPositions == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(openPositions, HttpStatus.OK);


  }


}
