package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDao {

    Map<Integer, String> getUsersAndAccountId();

    void sendBucks(int userId, int toUserId, double amount);

    List<Transfer> viewTransfers(int userId);

    Transfer viewTransferDetails(int transferId);

    List<Transfer> findAll();
}


