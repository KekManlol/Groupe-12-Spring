package be.vinci.ipl.order.models;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum OrderType {
    MARKET, LIMIT;
}
