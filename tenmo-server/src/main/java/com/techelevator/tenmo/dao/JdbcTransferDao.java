package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.exceptions.InsufficientFundsException;
import com.techelevator.tenmo.exceptions.InvalidTransactionAmountException;
import com.techelevator.tenmo.exceptions.InvalidTransferException;
import com.techelevator.tenmo.exceptions.TransferNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    private AccountDao accountDao;

    public JdbcTransferDao (JdbcTemplate jdbcTemplate, AccountDao accountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;
    }

    @Override
    public Map<Integer, String> getUsersAndUserIds() {
        Map<Integer, String> users = new HashMap<>();
        String sql = "SELECT tu.username, a.user_id FROM tenmo_user tu JOIN account a USING (user_id);";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while(result.next()) {
            String username = result.getString("username");
            int userId = result.getInt("user_id");
            users.put(userId, username);
        }
        return users;
    }

    @Override
    public Transfer sendTypeTransfer(BigDecimal amount, int fromUserId, int toUserId) throws InsufficientFundsException, InvalidTransferException, InvalidTransactionAmountException {
        Transfer transfer = new Transfer();
        boolean validFormat = getNumberOfDecimalPlaces(amount) <= 2;
        boolean sufficientFunds = checkForSufficientFunds(fromUserId, amount);
        boolean validTransaction = fromUserId != toUserId;
        boolean validAmount = amount.compareTo(new BigDecimal(0)) == 1;
        if(sufficientFunds && validTransaction && validAmount && validFormat) {
            String sendSql = "UPDATE account SET balance = balance - ? " +
                    "WHERE user_id = ?;";
            jdbcTemplate.update(sendSql, amount, fromUserId);
            String depositSql = "UPDATE account SET balance = balance + ? " +
                    "WHERE user_id = ?;";
            jdbcTemplate.update(depositSql, amount, toUserId);
            transfer = makeSendTransfer(fromUserId, toUserId, amount);
            return transfer;
        } else if(!sufficientFunds){
            throw new InsufficientFundsException("Insufficient Funds");
        } else if(!validTransaction){
            throw new InvalidTransferException("Transactions may only occur between different accounts.");
        } else if(!validAmount){
            throw new InvalidTransactionAmountException("Transaction amounts must be greater than $0");
        } else {
            throw new InvalidTransactionAmountException("Transaction amount should follow standard USD format");
        }
    }

    @Override
    public List<Transfer> viewTransfers(int userId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer " +
                "WHERE account_from = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountDao.getAccountIdWithUserId(userId));
        while(result.next()) {
            Transfer transfer = mapRowToTransfer(result);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public Transfer viewTransferDetails(int userId, int transferId) throws TransferNotFoundException {
        List<Transfer> transfers = viewTransfers(userId);
        for(Transfer transfer: transfers) {
            if(transfer.getTransfer_id() == transferId) {
                return transfer;
            }
        }
        throw new TransferNotFoundException("Transfer is not associated with your account");
    }

    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(results.getInt("transfer_id"));
        transfer.setTransfer_type(results.getInt("transfer_type_id"));
        transfer.setTransfer_status_id(results.getInt("transfer_status_id"));
        transfer.setAccount_from(results.getInt("account_from"));
        transfer.setAccount_to(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));
        return transfer;
    }

    /**Helper Methods**/

    private Transfer makeSendTransfer(int userFromId, int userToId, BigDecimal amount) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_type(retrieveSendType());
        transfer.setTransfer_status_id(retrieveApprovedStatus());
        transfer.setAccount_from(accountDao.getAccountIdWithUserId(userFromId));
        transfer.setAccount_to(accountDao.getAccountIdWithUserId(userToId));
        transfer.setAmount(amount);
        transfer.setTransfer_id(insertNewTransfer(transfer));
        return transfer;
    }

    private int insertNewTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, " +
                "account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "RETURNING transfer_id;";
        int transferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransfer_type(),
                transfer.getTransfer_status_id(), transfer.getAccount_from(),
                transfer.getAccount_to(), transfer.getAmount());
        return transferId;
    }

    private int retrieveSendType() {
        String sql = "SELECT transfer_type_id FROM transfer_type " +
                "WHERE transfer_type_desc = 'Send';";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    private int retrieveApprovedStatus() {
        String sql = "SELECT transfer_status_id FROM transfer_status " +
                "WHERE transfer_status_desc = 'Approved';";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    private int getNumberOfDecimalPlaces(BigDecimal amount) {
        return Math.max(0, amount.stripTrailingZeros().scale());
    }

    //Helper method which compares the balance of an account to a desired withdrawal amount
    private Boolean checkForSufficientFunds(int userId, BigDecimal amount) {
        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
        if(balance.compareTo(amount) >= 0) {
            return true;
        } else {
            return false;
        }
    }

}
