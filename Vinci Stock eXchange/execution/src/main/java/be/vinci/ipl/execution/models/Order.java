package be.vinci.ipl.execution.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    public enum Side {
        BUY, SELL;
    }

    public enum Type {
        MARKET, LIMIT;
    }

    private String guid;
    
    private String owner;

    private String timestamp;

    private String ticker;
    private int quantity;
    private Side side;
    private Type type;

    private double limit;
    private int filled;
}
