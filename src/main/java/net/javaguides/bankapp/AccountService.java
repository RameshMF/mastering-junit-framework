package net.javaguides.bankapp;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private Map<Long, Account> accounts = new HashMap<>();

    public Account createAccount(long accountNumber,
                                 String holderName,
                                 double balance){

        if(accounts.containsKey(accountNumber)){
            throw new IllegalArgumentException("Account with same number is already exists.");
        }
        Account account = new Account(accountNumber, holderName, balance);
        accounts.put(accountNumber, account);
        return account;
    }

    public Account updateAccount(long accountNumber,
                                 String holderName){

        Account account = accounts.get(accountNumber);

        if(account == null){
            throw new AccountNotFoundException("Account not found.");
        }
        account.setHolderName(holderName);
        return account;
    }

    public Account getAccount(long accountNumber){

        Account account = accounts.get(accountNumber);

        if(account == null){
            throw new AccountNotFoundException("Account not found.");
        }

        return account;
    }

    public void removeAccount(long accountNumber){

        Account account = accounts.get(accountNumber);

        if(account == null){
            throw new AccountNotFoundException("Account not found.");
        }

        accounts.remove(accountNumber);
    }

    public void deposit(long accountNumber, double amount){

        Account account = accounts.get(accountNumber);

        if(account == null){
            throw new AccountNotFoundException("Account not found.");
        }

        if(amount <= 0){
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }

        double totalAmount = account.getBalance() + amount;
        account.setBalance(totalAmount);
    }

    public void withdraw(long accountNumber, double amount){

        Account account = accounts.get(accountNumber);

        if(account == null){
            throw new AccountNotFoundException("Account not found.");
        }

        if(amount <= 0){
            throw new IllegalArgumentException("Withdraw amount must be greater than zero.");
        }

        if(amount > account.getBalance()){
            throw new IllegalArgumentException("Insufficient balance.");
        }

        double totalBalance = account.getBalance() - amount;
        account.setBalance(totalBalance);
    }

    public void transferFund(long fromAccountNumber,
                             long toAccountNumber,
                             double amount){

        Account fromAccount = accounts.get(fromAccountNumber);
        Account toAccount = accounts.get(toAccountNumber);

        if(fromAccount == null || toAccount == null){
            throw new AccountNotFoundException("One or Both accounts not found.");
        }

        if(amount <= 0){
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }

        if(fromAccount.getBalance() < amount){
            throw new IllegalArgumentException("Insufficient balance.");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
    }
}
