package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import io.cucumber.java.bs.I;
import io.cucumber.java.sl.In;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void viewPastTransfers(Transfer[] transfers, int userId, Map<Integer, String> users, Map<Integer, Integer> accountHolders) {
        boolean hasTransfers = false;
        System.out.println("-------------------------------------------\n" + "Transfers");
        System.out.printf("|%-13s| %-14s|  %10s|  %n", "ID", "From/To", "Amount");
        System.out.println("-------------------------------------------\n");

        String account = new String();
        for(Map.Entry<Integer, Integer> entry : accountHolders.entrySet()) {
            if(entry.getValue().equals(userId)) {
                account = String.valueOf(entry.getKey());
                break;
            }
        }
            for (Transfer transfer : transfers) {
                if (transfer.getAccountTo() == Integer.valueOf(account)) {
                    hasTransfers = true;
                    System.out.printf("|%-13s| %-14s| %11s|%n", String.valueOf(transfer.getTransferId()), "From: " + users.get(String.valueOf(accountHolders.get(String.valueOf(transfer.getAccountFrom())))), "$"+String.valueOf(transfer.getAmount()));
                } else if (transfer.getAccountFrom() == Integer.valueOf(account)) {
                    hasTransfers = true;
                    System.out.printf("|%-13s| %-14s| %11s|%n", String.valueOf(transfer.getTransferId()), "To: " + users.get(String.valueOf(accountHolders.get(String.valueOf(transfer.getAccountTo())))), "$"+String.valueOf(transfer.getAmount()));
                }
            }
        if(!hasTransfers) {
            System.out.println("No transfers are associated with this account.");
        }
    }

    public void viewTransfer(Transfer[] transfers, int transferId, Map<Integer, String> users, Map<Integer, Integer> accountHolders) {
        Map<Integer, String> userList = users;
        boolean hasTransfer = false;
        for(Transfer transfer : transfers) {
            if(transfer.getTransferId() == transferId) {
                hasTransfer = true;
                System.out.println("\n----------------------------");
                System.out.println("Transfer Id: "+transfer.getTransferId());
                System.out.println("From: " + users.get(String.valueOf(accountHolders.get(String.valueOf(transfer.getAccountFrom())))));
                System.out.println("To: " + users.get(String.valueOf(accountHolders.get(String.valueOf(transfer.getAccountTo())))));
                System.out.println("Transfer Type: " + getTransferType(transfer.getTransferType()));
                System.out.println("Transfer Status: " + getTransferStatus(transfer.getTransferStatus()));
                System.out.println("Transfer Amount: $" + transfer.getAmount());
                System.out.println("----------------------------");
            }
        }
        if (!hasTransfer) {
            System.out.println("\nTransfer #" + transferId + " is not associated with this account.");
        }
    }

    public void showUsersAndUserIds(Map<Integer, String> users) {
        System.out.println("****************************************************************************************");
        System.out.printf("%43s %n", "Users");
        System.out.println("****************************************************************************************");
        for(Map.Entry<Integer, String> entry : users.entrySet()) {
            System.out.printf("|%-43s|%-43s|\n", "User ID: "+String.valueOf(entry.getKey()), "Username: " + entry.getValue());
        }
    }

    public int promptForIdInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter an id number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void showBalance(String prompt, BigDecimal balance) {
        System.out.println(prompt + balance);
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    private String getTransferType(int transferType) {
        String type = new String();
        if(transferType == 2) {
            type = "Send";
        } else if (transferType == 1) {
            type = "Request";
        }
        return type;
    }

    private String getTransferStatus(int transferStatus) {
        String status = new String();
        if(transferStatus == 2) {
            status = "Approved";
        } else if(transferStatus == 1) {
            status = "Pending";
        } else if(transferStatus == 3) {
            status = "Rejected";
        }
        return status;
    }

}
