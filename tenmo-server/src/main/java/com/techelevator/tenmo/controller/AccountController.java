package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountDao accountDao;

    @RequestMapping(path = "/{id}/balance", method = RequestMethod.GET)
    public BigDecimal checkBalance(@PathVariable int id) {
        return accountDao.getBalance(id);
    }


}
