package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transfer_id;
    private int transfer_type;
    private int transfer_status_id;
    private int account_from;
    private int account_to;
    private BigDecimal amount;

    public Transfer(){}

    public Transfer(int transferType, int transferStatus, int fromAccountId, int toAccountId, BigDecimal amount) {
        this.transfer_type = transferType;
        this.transfer_status_id = transferStatus;
        this.account_from = fromAccountId;
        this.account_to = toAccountId;
        this.amount = amount;
    }

    public int getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public int getTransfer_type() {
        return transfer_type;
    }

    public void setTransfer_type(int transfer_type) {
        this.transfer_type = transfer_type;
    }

    public int getTransfer_status_id() {
        return transfer_status_id;
    }

    public void setTransfer_status_id(int transfer_status_id) {
        this.transfer_status_id = transfer_status_id;
    }

    public int getAccount_from() {
        return account_from;
    }

    public void setAccount_from(int account_from) {
        this.account_from = account_from;
    }

    public int getAccount_to() {
        return account_to;
    }

    public void setAccount_to(int account_to) {
        this.account_to = account_to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transfer_id +
                ", transferType=" + transfer_type +
                ", transferStatus=" + transfer_status_id +
                ", fromAccountId=" + account_from +
                ", toAccountId=" + account_to +
                ", amount=" + amount +
                '}';
    }
}
