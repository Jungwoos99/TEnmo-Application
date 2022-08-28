package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.exceptions.InsufficientFundsException;
import com.techelevator.tenmo.exceptions.InvalidTransactionAmountException;
import com.techelevator.tenmo.exceptions.InvalidTransferException;
import com.techelevator.tenmo.exceptions.TransferNotFoundException;

public interface TransferDao {

    Map<Integer, String> getUsersAndUserIds();

    Transfer sendTypeTransfer(BigDecimal amount, int fromUserId, int toUserId) throws InsufficientFundsException,
            InvalidTransferException, InvalidTransactionAmountException;

    List<Transfer> viewTransfers(int userId);

    Transfer viewTransferDetails(int userId, int transferId) throws TransferNotFoundException;

}


