package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<Integer, String> getUsersAndAccountId() {
        Map<Integer, String> users = new HashMap<>();
        String sql = "SELECT tu.username, a.account_id FROM tenmo_user tu JOIN account a USING (user_id);";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while(result.next()) {
            String username = result.getString("username");
            int accountId = result.getInt("account_id");
            users.put(accountId, username);
        }
        return users;
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
