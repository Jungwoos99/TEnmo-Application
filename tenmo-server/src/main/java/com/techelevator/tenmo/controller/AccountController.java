package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/account")
public class AccountController {

    private AccountDao accountDao;

    @RequestMapping(path = "/{id}/balance", method = RequestMethod.GET)
    public BigDecimal checkBalance(@PathVariable int id) {
        return accountDao.getBalance(id);
    }

    @GetMapping(path = "/{id}/details")
    public Account getAccount(@PathVariable int id) {
        return accountDao.getAccountByUserId(id);
    }

    @GetMapping(path = "/showAll")
    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    @GetMapping(path = "/{id}")
    public int getAccountId(@PathVariable int id) {
        return accountDao.getAccountId(id);
    }

}
