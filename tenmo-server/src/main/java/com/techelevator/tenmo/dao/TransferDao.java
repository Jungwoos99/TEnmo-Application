package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.security.InsufficientFundsException;
import com.techelevator.tenmo.security.InvalidTransactionAmount;
import com.techelevator.tenmo.security.InvalidTransferException;
import com.techelevator.tenmo.security.TransferNotFoundException;

public interface TransferDao {

    Map<Integer, String> getUsersAndUserIds();

    Transfer sendTypeTransfer(int fromUserId, int toUserId, BigDecimal amount) throws InsufficientFundsException,
            InvalidTransferException, InvalidTransactionAmount;

    List<Transfer> viewTransfers(int userId);

    Transfer viewTransferDetails(int userId, int transferId) throws TransferNotFoundException;

}


