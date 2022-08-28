package com.techelevator.tenmo.model;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

public class Amount {

    private BigDecimal amount;

    public Amount(){}

    public Amount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
