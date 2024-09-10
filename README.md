# mastering-junit-framework

# Write JUnit test for create account feature
## Create Account Class
```java
package net.javaguides.bank;

public class Account {
    private long accountNumber;
    private String holderName;
    private double balance;

    public Account(long accountNumber, String holderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialBalance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
```
## create BankManager with createAccount method
```java
package net.javaguides.bank;

import java.util.HashMap;
import java.util.Map;

public class BankManager {
    private Map<Long, Account> accounts = new HashMap<>();

    public Account createAccount(long accountNumber, String holderName, double initialBalance) {
        if (accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException("Account with the same number already exists.");
        }
        Account account = new Account(accountNumber, holderName, initialBalance);
        accounts.put(accountNumber, account);
        return account;
    }
}
```

## JUnit test for createAccount method
```java
package net.javaguides.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankManagerTest {

    private BankManager bankManager;

    @BeforeEach
    void setUp() {
        bankManager = new BankManager();
       // bankManager.createAccount(123L, "Ramesh Fadatare", 1000.0);
    }

    @Test
    void testCreateAccount() {
        Account account = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

        assertNotNull(account, "Account should not be null after creation");
        assertEquals(123L, account.getAccountNumber());
        assertEquals("Ramesh Fadatare", account.getHolderName());
        assertEquals(500.0, account.getBalance());
    }

}
```

# Update Account Feature
## Create AccountNotFoundException
```java
package net.javaguides.bank;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
```
## Add updateAccount() method to BankManager Class
```java
    public Account updateAccount(long accountNumber, String newHolderName) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found.");
        }
        account.setHolderName(newHolderName);
        return account;
    }
```

## JUnit test for updateAccount() method
```java
    @Test
    void testUpdateAccount() {

        Account account = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

        Account updatedAccount = bankManager.updateAccount(123L, "Ram Fadatare");
        assertEquals("Ram Fadatare", updatedAccount.getHolderName());
    }
```
# Get Account Feature
## Add getAccount() method to BankManager class
```java
    public Account getAccount(long accountNumber){
        if (!accounts.containsKey(accountNumber)) {
            throw new AccountNotFoundException("Account not found.");
        }
        return accounts.get(accountNumber);
    }
```
## Junit test to test getAccount() method
```java
    @Test
    void testGetAccount(){
        Account account = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

        Account newAccount = bankManager.getAccount(account.getAccountNumber());

        // Assert that the returned account is not null
        assertNotNull(account, "Account should not be null");
        assertEquals(account.getAccountNumber(), newAccount.getAccountNumber());
        assertEquals(account.getHolderName(), newAccount.getHolderName());
        assertEquals(account.getBalance(), newAccount.getBalance());
    }
```
# Remove Account Feature
## Add removeAccount() method to BankManager class
```java
  public void removeAccount(long accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            throw new AccountNotFoundException("Account not found.");
        }
        accounts.remove(accountNumber);
    }
```
## Junit test to test removeAccount() method
```java
    @Test
    void testRemoveAccount() {

        Account account = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

        bankManager.removeAccount(123L);

        // Using assertThrows to check exception on second removal
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            bankManager.removeAccount(123L);
        });

        // Using assertEquals to check the exception message
        assertEquals("Account not found.", exception.getMessage(), "Exception message should match");
    }
```

# Deposit Amount Feature
## add deposit() method to BankManager class
```
    public void deposit(long accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        account.setBalance(account.getBalance() + amount);
    }
```
## JUnit test for deposit() method
```java
@Test
    void testDeposit() {

        Account newAccount = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

        bankManager.deposit(123L, 500.0);
        Account account = bankManager.getAccount(123L);

        // Using assertEquals
        assertEquals(1000.0, account.getBalance(), "Balance should be updated after deposit");
    }

    @Test
    void testDeposit_NegativeAmount() {
        Account newAccount = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bankManager.deposit(123L, -500.0);
        });

        // Using assertNotNull and assertEquals
        assertNotNull(exception, "Exception should not be null for negative deposit");
        assertEquals("Deposit amount must be greater than zero.", exception.getMessage(), "Error message should match");
    }
```
