package be.vinci.ipl.matching;

import be.vinci.ipl.matching.data.ExecutionProxy;
import be.vinci.ipl.matching.data.OrderProxy;
import be.vinci.ipl.matching.models.Order;
import be.vinci.ipl.matching.models.OrderSide;
import be.vinci.ipl.matching.models.PatchDTO;
import be.vinci.ipl.matching.models.Transaction;
import org.springframework.stereotype.Service;

@Service
public class MatchingService {

  private ExecutionProxy executionProxy;
  private OrderProxy orderProxy;

  public MatchingService(ExecutionProxy executionProxy, OrderProxy orderProxy){
    this.executionProxy = executionProxy;
    this.orderProxy = orderProxy;
  }

  /**
   * Tries to find matches from orders based on the ticker
   * @param ticker The financial instrument's identifier
   */
  public void findMatches(String ticker) {

    Iterable<Order> sellOrders = orderProxy.findOrdersByTicker(ticker, OrderSide.SELL);
    Iterable<Order> buyOrders = orderProxy.findOrdersByTicker(ticker, OrderSide.BUY);

    Order chosenSellOrder = null;
    Order chosenBuyOrder = null;

    for (Order sellOrder : sellOrders) {
      for (Order buyOrder : buyOrders) {
        if (sellOrder.getType().name().equals("MATCH") || buyOrder.getType().name().equals("MATCH") ||
                sellOrder.getLimit() <= buyOrder.getLimit()){
          chosenSellOrder = sellOrder;
          chosenBuyOrder = buyOrder;
          break;
        }
      }
    }

    if (chosenSellOrder != null){

      int remainingSellOrderTitle = chosenSellOrder.getQuantity() - chosenSellOrder.getFilled();
      int remainingBuyOrderTitle = chosenBuyOrder.getQuantity() - chosenBuyOrder.getFilled();
      int titleQuantity = Math.min(remainingBuyOrderTitle, remainingSellOrderTitle);

      Transaction transaction = new Transaction(ticker,chosenSellOrder.getOwner(), chosenBuyOrder.getOwner(),
              chosenSellOrder.getGuid(), chosenBuyOrder.getGuid(), titleQuantity,0);

      executionProxy.executeOrder(ticker,chosenSellOrder.getOwner(), chosenBuyOrder.getOwner(), transaction);

      PatchDTO patchDTOSell = new PatchDTO(chosenSellOrder.getFilled() + titleQuantity);
      PatchDTO patchDTOBuy = new PatchDTO(chosenBuyOrder.getFilled() + titleQuantity);

      orderProxy.updateSharesQuantity(chosenSellOrder.getGuid(), patchDTOSell);
      orderProxy.updateSharesQuantity(chosenBuyOrder.getGuid(), patchDTOBuy);
    }
  }
}
