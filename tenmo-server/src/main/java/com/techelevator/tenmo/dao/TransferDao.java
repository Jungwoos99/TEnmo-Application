package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDao {



    void sendBucks(int userId, int toUserId, double amount);

    List<Transfer> viewTransfers(int userId);

    Transfer viewTransferDetails(int transferId);

    List<Transfer> findAll();
}


