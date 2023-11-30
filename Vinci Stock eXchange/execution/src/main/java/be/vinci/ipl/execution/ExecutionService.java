package be.vinci.ipl.execution;

import be.vinci.ipl.execution.models.Order;
import be.vinci.ipl.execution.models.Position;
import be.vinci.ipl.execution.repositories.OrderProxy;
import be.vinci.ipl.execution.repositories.PriceProxy;
import be.vinci.ipl.execution.repositories.WalletProxy;
import org.springframework.stereotype.Service;

@Service
public class ExecutionService {
  private final WalletProxy walletProxy;
  private final PriceProxy priceProxy;
  private final OrderProxy orderProxy;

  public ExecutionService( WalletProxy walletProxy, PriceProxy priceProxy, OrderProxy orderProxy){
    this.walletProxy = walletProxy;
    this.priceProxy = priceProxy;
    this.orderProxy = orderProxy;
  }


  /**
   * @param username Username of the user.
   * @param position The position to add to the user's wallet.
   *
   */
  public void updateWallet(String username, Position position){
    walletProxy.updateOne(username, position);
  }

  /**
   * @param guid the order's guid.
   * @return an Order.
   */
  public Order getOrder(String guid){
    return orderProxy.getOne(guid);
  }

  /**
   * @param newOrder the new order to update.
   */
  public void updateOrder(Order newOrder){
    orderProxy.updateOne(newOrder.getGuid(), newOrder);
  }

  /**
   * @param ticker the ticker's identifier.
   * @param newPrice the ticker's new price.
   */
  public void updatePrice(String ticker, float newPrice){
    priceProxy.updateOne(ticker, String.valueOf(newPrice));
  }

}
