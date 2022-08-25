package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getAccountBalance(int id);

    int getAccountIdWithUserId(int userId);

}

