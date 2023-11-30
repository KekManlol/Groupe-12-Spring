package be.vinci.ipl.orders.models;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum OrderSide {
    BUY, SELL;
}
