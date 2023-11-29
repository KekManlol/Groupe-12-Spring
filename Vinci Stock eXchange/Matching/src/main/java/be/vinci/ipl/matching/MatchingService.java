package be.vinci.ipl.matching;

import be.vinci.ipl.matching.data.ExecutionProxy;
import be.vinci.ipl.matching.data.OrdersProxy;
import be.vinci.ipl.matching.models.Order;
import be.vinci.ipl.matching.models.Transaction;
import org.springframework.stereotype.Service;

@Service
public class MatchingService {

  private ExecutionProxy executionProxy;
  private OrdersProxy ordersProxy;

  public MatchingService(ExecutionProxy executionProxy, OrdersProxy ordersProxy){
    this.executionProxy = executionProxy;
    this.ordersProxy = ordersProxy;
  }

  public void findMatches(String ticker) {

    Iterable<Order> sellOrders = ordersProxy.findOrdersByTicker(ticker, "SELL");
    Iterable<Order> buyOrders = ordersProxy.findOrdersByTicker(ticker, "BUY");

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
      Transaction transaction = new Transaction(ticker,chosenSellOrder.getOwner(), chosenBuyOrder.getOwner(),
              chosenSellOrder.getGuid(), chosenBuyOrder.getGuid(), 0,0);

      executionProxy.executeOrder(ticker,chosenSellOrder.getOwner(), chosenBuyOrder.getOwner(), transaction);
    }
  }
}
