package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

import com.techelevator.tenmo.model.Account;

public interface AccountDao {


    BigDecimal getAccountBalance(int id);

    double getAccountBalanceCheck(int id);

    Account getAccountById(int user_id);

    void updateAccountBalance(int user_id, double balance);
}

