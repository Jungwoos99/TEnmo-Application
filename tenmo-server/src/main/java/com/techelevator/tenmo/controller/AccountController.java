package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.AccountDTO;
import com.techelevator.tenmo.model.Transfer;

@RequestMapping("/account")
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
public class AccountController {

    private AccountDao accountDAO;
    private UserDao userDAO;
    private TransferDao transferDAO;

    public AccountController (AccountDao accountDAO, UserDao userDAO, TransferDao transferDAO) {
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
        this.transferDAO = transferDAO;
    }

    @GetMapping(path = "/balance")
    public BigDecimal getBalance (Principal principal) {
        int userId = userIdHelperMethod(principal);
        return accountDAO.getAccountBalance(userId);
    }

    @GetMapping(path = "/account_holders")
    public Map<Integer, Integer> accounts() {
        return accountDAO.getAccountAndUserIds();
    }

    /***Test method to return account id using user id***/
    @GetMapping(path = "/{id}")
    public int getAccountId(@PathVariable int id) {
        return accountDAO.getAccountIdWithUserId(id);
    }

    //Helper Method - non request mapping method

    public Integer userIdHelperMethod(Principal principal) {
        return userDAO.findIdByUsername(principal.getName());
    }

}
