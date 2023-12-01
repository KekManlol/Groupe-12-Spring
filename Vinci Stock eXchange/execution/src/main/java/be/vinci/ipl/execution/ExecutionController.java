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

  public ExecutionController(ExecutionService service) {
    this.service = service;
  }

  /**
   * @param ticker      the ticker's identifier.
   * @param buyer       the buyer's username.
   * @param seller      the seller's username.
   * @param transaction the transaction to execute.
   * @return an empty ResponseEntity.
   */
  @PostMapping("/execute/{ticker}/{seller}/{buyer}")
  public ResponseEntity<Void> createOne(@PathVariable String ticker, @PathVariable String buyer,
      @PathVariable String seller, @RequestBody Transaction transaction) {

    if (!Objects.equals(transaction.getTicker(), ticker) || !Objects.equals(transaction.getBuyer(),
        buyer) || !Objects.equals(transaction.getSeller(), seller)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (transaction.invalid()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Order sellerOrder = service.getOrder(transaction.getSellOrderGUID());
    Order buyerOrder = service.getOrder(transaction.getBuyOrderGUID());
    if (!Objects.equals(sellerOrder.getOwner(), seller) || !Objects.equals(buyerOrder.getOwner(), buyer)
        || !Objects.equals(buyerOrder.getTicker(), ticker) || !Objects.equals(sellerOrder.getTicker(), ticker)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    // Add cash to seller
    Position positionCashSeller = new Position("CASH",
        (int) (transaction.getQuantity() * transaction.getPrice()), 1);
    service.updateWallet(seller, positionCashSeller);
    // Substract cash from buyer
    Position positionCashBuyer = new Position("CASH",
        (int) (-transaction.getQuantity() * transaction.getPrice()), 1);
    service.updateWallet(buyer, positionCashBuyer);

    // Remove ticker from seller's wallet
    Position positionTickerSeller = new Position(ticker, transaction.getQuantity(),
        transaction.getPrice());
    service.updateWallet(seller, positionTickerSeller);
    // Add ticker to buyer's wallet
    Position positionTickerBuyer = new Position(ticker, -transaction.getQuantity(),
        transaction.getPrice());
    service.updateWallet(buyer, positionTickerBuyer);
    // Update price
    service.updatePrice(ticker, transaction.getPrice());
    // Update seller's order
    sellerOrder.setFilled(sellerOrder.getFilled() - transaction.getQuantity());
    service.updateOrder(sellerOrder);
    // Update buyer's order
    buyerOrder.setFilled(buyerOrder.getFilled() - transaction.getQuantity());
    service.updateOrder(buyerOrder);

    return new ResponseEntity<>(HttpStatus.OK);
  }

}
