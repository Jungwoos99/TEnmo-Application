package com.techelevator.tenmo.dao;



import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

@Component
public class JdbcAccountDAO implements AccountDao {

    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;

    public JdbcAccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getAccountBalance (int id) {

        String sql = "SELECT balance FROM account WHERE user_id = ?";
        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, id);

        return balance;
    }

    @Override
    public void updateAccountBalance(int user_id, double balance) {
        String sql = "UPDATE account SET balance = ? WHERE user_id = ?"; // this may not work lol
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
