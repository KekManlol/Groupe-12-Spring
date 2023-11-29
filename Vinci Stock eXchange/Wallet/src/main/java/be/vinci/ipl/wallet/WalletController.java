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


  @PostMapping("/wallet/{username}")
  public ResponseEntity<List<Position>> addPosition(@PathVariable String username, @RequestBody List<Position> positions) {
    List<Position> updatedPositions = service.createPositions(username, positions);
    return new ResponseEntity<>(updatedPositions, HttpStatus.OK);
  }


}
