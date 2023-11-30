package be.vinci.ipl.gateway.models;

public class Order {

  private String guid;


  private String owner;

  private int timestamp;


  private String ticker;


  private int quantity;


  private OrderSide side;

  private OrderType type;

  private double limit;
  private int filled;
}
