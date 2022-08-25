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

    @RequestMapping (path = "/balance", method = RequestMethod.GET)
    public BigDecimal getBalance (Principal principal) {
        int userId = userIdHelperMethod(principal);
        return accountDAO.getAccountBalance(userId);
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

    //Helper Method - non request mapping method
    public Integer userIdHelperMethod(Principal principal) {
        return userDAO.findIdByUsername(principal.getName());
    }

}
