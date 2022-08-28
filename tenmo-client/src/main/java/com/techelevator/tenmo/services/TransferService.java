package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class TransferService {

    private String BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();

    public TransferService (String url) {
        this.BASE_URL = url;
    }

    public Transfer getTransferDetails(AuthenticatedUser authenticatedUser, int transferId) {
        HttpEntity entity = makeEntity(authenticatedUser);
        String id = String.valueOf(transferId);
        Transfer transfer = null;
        try{
            ResponseEntity<Transfer> response =
                    restTemplate.exchange(BASE_URL+"/transfer/view/" + id, HttpMethod.GET,
                            entity, Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    public Transfer sendBucks(AuthenticatedUser authenticatedUser, int amount) {
        HttpEntity entity = makeEntity(authenticatedUser);

    }

    public Map<Integer, String> getListOfUsers(AuthenticatedUser authenticatedUser) {
        HttpEntity entity = makeEntity(authenticatedUser);
        Map<Integer, String> users = null;
        try {
            ResponseEntity<Map> response =
                    restTemplate.exchange(BASE_URL+"/transfer/view/users", HttpMethod.GET, entity, Map.class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        } return users;
    }

    public Transfer[] getTransfers(AuthenticatedUser authenticatedUser) {
        HttpEntity entity = makeEntity(authenticatedUser);
        Transfer[] transfers = null;
        try{
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(BASE_URL+"/transfer/view/all", HttpMethod.GET,
                            entity, Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    HttpEntity<AuthenticatedUser> makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(authenticatedUser, headers);
    }

}
