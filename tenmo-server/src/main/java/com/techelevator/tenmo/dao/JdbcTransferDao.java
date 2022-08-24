package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void sendTransfer(int accountFrom, int accountTo, BigDecimal amount, int transferStatus) {
        if(checkSufficientFunds(accountFrom, amount) == 200 && accountTo != accountFrom) {
            String fromSql = "UPDATE account SET balance = balance - ?" +
                    " WHERE account_id = ?;";
            jdbcTemplate.update(fromSql, amount, accountFrom);
            String toSql = "UPDATE account SET balance = balance + ?" +
                    " WHERE account_id = ?";
            jdbcTemplate.update(toSql, amount, accountTo);
        }
    }

    public int checkSufficientFunds(int accountId, BigDecimal amount) {
        String sql = "SELECT balance FROM account WHERE account_id = ?;";
        BigDecimal accountBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);
        int status = 0;
        int comparison = accountBalance.compareTo(amount);
        if(comparison == 1) {
            status =  200;
        } else {
            status = 0;
        }
        return status;
    }

    @Override
    public void sendBucks(int userId, int toUserId, double amount) {

    }

    @Override
    public List<Transfer> viewTransfers(int userId) {
        return null;
    }

    @Override
    public Transfer viewTransferDetails(int transferId) {
        return null;
    }

    @Override
    public List<Transfer> findAll() {
        return null;
    }
}
