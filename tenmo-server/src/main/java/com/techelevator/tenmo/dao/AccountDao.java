package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.Map;

public interface AccountDao {

    BigDecimal getAccountBalance(int id);

    int getAccountIdWithUserId(int userId);

    Map<Integer, Integer> getAccountAndUserIds();
}

