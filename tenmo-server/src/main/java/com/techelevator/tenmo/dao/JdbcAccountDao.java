package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(int id) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?;";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, id);
        BigDecimal balance = null;
        Account account = new Account();
        if(sqlRowSet.next()) {
            account = mapRowToAccount(sqlRowSet);
        }
        return account.getBalance();
    }

    @Override
    public Account getAccountByUserId(int id) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        Account account = new Account();
        if(rowSet.next()) {
            account = mapRowToAccount(rowSet);
        }
        return account;
    }

    @Override
    public int getAccountId(int id) {
        String sql = "SELECT account_id FROM account WHERE user_id = ?;";
        return jdbcTemplate.queryForObject(sql, Integer.class, id);
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, user_id, balance FROM account;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        if(result.next()) {
            Account account = mapRowToAccount(result);
            accounts.add(account);
        }
        return accounts;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }

}
