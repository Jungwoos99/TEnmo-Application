package com.techelevator.tenmo.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.*;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

//@PreAuthorize("isAuthenticated()")
@RequestMapping("/account")
@RestController
public class AccountController {

    private AccountDao accountDAO;
    private UserDao userDAO;
    private TransferDao transferDAO;
    public AccountController (AccountDao accountDAO, UserDao userDAO, TransferDao transferDAO) {
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
        this.transferDAO = transferDAO;

    }
    @RequestMapping (path = "/{id}/balance", method = RequestMethod.GET)
    public BigDecimal getBalance (@PathVariable int id) {
        return accountDAO.getAccountBalance(id);
    }

    @RequestMapping (path = "/{id}/balanceCheck", method = RequestMethod.GET)
    public double getBalanceCheck (@PathVariable int id) {
        return accountDAO.getAccountBalanceCheck(id);
    }

    @RequestMapping (path = "/findUsers", method = RequestMethod.GET)
    public User[] getUsers (){
        return userDAO.findAll().toArray(new User[0]);
    }
    @RequestMapping (path = "/{id}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable int id) {
        //System.out.println("in getAccount");
        return accountDAO.getAccountById(id);
    }
    @RequestMapping (path = "/{id}/update", method = RequestMethod.PUT)
    public void updateAccountBalance(@PathVariable int id, @RequestBody AccountDTO balance) {
        accountDAO.updateAccountBalance(id, balance.getNumber());
    }

    @RequestMapping(path = "/{id}/transfer", method = RequestMethod.GET)
    public Transfer transferDetails(@PathVariable int id) {
        //System.out.println("in transfer details");
        return transferDAO.viewTransferDetails(id);
    }
    @RequestMapping(path = "{id}/allTransfers", method = RequestMethod.GET)
    public Transfer[] getTransferList (@PathVariable int id) {
        //System.out.println("in all transfers");
        return transferDAO.viewTransfers(id).toArray(new Transfer[0]);
    }
    @RequestMapping(path = "/transferwith", method = RequestMethod.POST)
    public void updateBalances(@RequestParam int fromUserId,
                               @RequestParam int toUserId, @RequestParam double amount) {
        transferDAO.sendBucks(fromUserId,toUserId,amount);
    }



}
