package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "/view_users")
    public Map<Integer,String> viewUsers() {
        return transferDao.getUsersAndAccountId();
    }

}
