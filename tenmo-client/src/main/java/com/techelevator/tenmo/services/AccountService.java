package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private final String BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();

    public AccountService (String url) {this.BASE_URL = url;}

    public BigDecimal getBalance(User user) {

    }




}
