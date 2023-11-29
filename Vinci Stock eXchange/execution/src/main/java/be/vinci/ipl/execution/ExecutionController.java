package be.vinci.ipl.execution;

import be.vinci.ipl.execution.models.Order;
import be.vinci.ipl.execution.models.Position;
import be.vinci.ipl.execution.models.Transaction;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExecutionController {

  private final ExecutionService service;

  public ExecutionController(ExecutionService service){
    this.service = service;
  }

  @PostMapping("/transaction/{ticker}/{seller}/{buyer}")
  public ResponseEntity<Void> createOne(@PathVariable String ticker, @PathVariable String buyer, @PathVariable String seller, @RequestBody
      Transaction transaction){
    if (!Objects.equals(transaction.getTicker(), ticker) || !Objects.equals(transaction.getBuyer(), buyer) || !Objects.equals(transaction.getSeller(), seller)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (transaction.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    // Step 1 : add cash to seller
    Position positionCashSeller = new Position("CASH",
        (int) (transaction.getQuantity() * transaction.getPrice()), 1);
    service.updateCashWallet(seller, positionCashSeller);
    // Step 2 : remove cash from buyer
    Position positionCashBuyer = new Position("CASH",
        (int) (-transaction.getQuantity()* transaction.getPrice()), 1);
    service.updateCashWallet(buyer, positionCashBuyer);
    // Step 3 : remove ticker from seller
    Position positionTickerSeller = new Position(ticker, transaction.getQuantity(), transaction.getPrice());
    service.updateCashWallet(seller, positionTickerSeller);
    // Step 4 : add ticker to buyer
    Position positionTickerBuyer = new Position(ticker, -transaction.getQuantity(), transaction.getPrice());
    service.updateCashWallet(buyer, positionTickerBuyer);
    // Step 5 : indicate to price service the last price of ticker
    int newPrice = (int) transaction.getPrice();
    service.updatePrice(ticker, newPrice);
    // Step 6 : update seller order status
    Order sellerOrder = service.getOrder(transaction.getGUIDSellingOrder());
    sellerOrder.setFilled(sellerOrder.getFilled() - transaction.getQuantity());
    service.updateOrder(sellerOrder);
    // Step 7 : update buyer order status
    Order buyerOrder = service.getOrder(transaction.getGUIDBuyingOrder());
    buyerOrder.setFilled(buyerOrder.getFilled() - transaction.getQuantity());
    service.updateOrder(buyerOrder);

    return new ResponseEntity<>(HttpStatus.OK);
  }

}
