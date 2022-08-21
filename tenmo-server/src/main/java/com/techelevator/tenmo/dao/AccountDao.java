package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    //Check balance

    //Update Account

    //

    BigDecimal getBalance(int id);

    Account getAccountByUserId(int id);

    int getAccountId(int id);

    List<Account> getAllAccounts();


}
