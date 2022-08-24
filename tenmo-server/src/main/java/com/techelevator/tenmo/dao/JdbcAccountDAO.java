package com.techelevator.tenmo.dao;



import java.math.BigDecimal;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

@Component
public class JdbcAccountDAO implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getAccountBalance (int id) {

        String sql = "SELECT balance FROM accounts WHERE user_id = ?";
        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class,id);

        return balance;

    }
    @Override
    public double getAccountBalanceCheck (int id) {

        String sql = "SELECT balance FROM accounts WHERE user_id = ?";
        double balance = jdbcTemplate.queryForObject(sql, double.class,id);

        return balance;

    }
    @Override
    public Account getAccountById (int user_id) {

        Account account = new Account();
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, user_id);

        if(result.next()) {
            account = mapRowToAccount(result);
        }
        return account;

    }
    @Override
    public void updateAccountBalance(int user_id, double balance) {
        String sql = "UPDATE accounts SET balance = ? WHERE user_id = ?"; // this may not work lol
        jdbcTemplate.update(sql, balance, user_id);
    }


    private Account mapRowToAccount(SqlRowSet result) {
        Account account = new Account();
        account.setBalance(result.getBigDecimal("balance"));
        account.setAccountId(result.getInt("account_id"));
        account.setUserId(result.getInt("user_id"));
        return account;

    }
}
