package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.techelevator.tenmo.model.Account;

public interface AccountDao {


    BigDecimal getAccountBalance(int id);

    void updateAccountBalance(int user_id, double balance);



}

