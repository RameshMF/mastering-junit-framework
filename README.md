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
# Withdraw Amount feature
## Add withdraw() method to BankManager class
```java
    public void withdraw(long accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be greater than zero.");
        }
        if (amount > account.getBalance()) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        account.setBalance(account.getBalance() - amount);
    }
```
## JUnit tests for  withdraw() method
```java
@Test
    void testWithdraw() {

        Account newAccount = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

        bankManager.withdraw(123L, 200.0);
        Account account = bankManager.getAccount(123L); // assuming getAccount method exists

        // Using assertEquals
        assertEquals(300.0, account.getBalance(), "Balance should be reduced by withdrawal amount");
    }

    @Test
    void testWithdraw_InsufficientBalance() {

        Account newAccount = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bankManager.withdraw(123L, 2000.0);
        });

        // Using assertNotNull and assertEquals
        assertNotNull(exception, "Exception should not be null when withdrawing more than balance");
        assertEquals("Insufficient balance.", exception.getMessage(), "Error message should match");
    }

    @Test
    void testWithdraw_NegativeAmount() {

        Account newAccount = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bankManager.withdraw(123L, -200.0);
        });

        // Using assertNotNull and assertEquals
        assertNotNull(exception, "Exception should not be null for negative withdrawal");
        assertEquals("Withdraw amount must be greater than zero.", exception.getMessage(), "Error message should match");
    }
```
## Nested test class demo:
```java
@Nested
    class WithDrawTests {
        @Test
        void testWithdraw() {

            Account newAccount = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

            bankManager.withdraw(123L, 200.0);
            Account account = bankManager.getAccount(123L); // assuming getAccount method exists

            // Using assertEquals
            assertEquals(300.0, account.getBalance(), "Balance should be reduced by withdrawal amount");
        }

        @Test
        void testWithdraw_InsufficientBalance() {

            Account newAccount = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                bankManager.withdraw(123L, 2000.0);
            });

            // Using assertNotNull and assertEquals
            assertNotNull(exception, "Exception should not be null when withdrawing more than balance");
            assertEquals("Insufficient balance.", exception.getMessage(), "Error message should match");
        }

        @Test
        void testWithdraw_NegativeAmount() {

            Account newAccount = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                bankManager.withdraw(123L, -200.0);
            });

            // Using assertNotNull and assertEquals
            assertNotNull(exception, "Exception should not be null for negative withdrawal");
            assertEquals("Withdraw amount must be greater than zero.", exception.getMessage(), "Error message should match");
        }
    }
```

# Transfer Fund Feature
## Add transferFund() method to BankManager class
```java
    public void transferFund(long fromAccountNumber, long toAccountNumber, double amount) {
        Account fromAccount = accounts.get(fromAccountNumber);
        Account toAccount = accounts.get(toAccountNumber);
        if (fromAccount == null || toAccount == null) {
            throw new AccountNotFoundException("One or both accounts not found.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }
        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
    }
```
## JUnit tests for transferFund() method
```java
@Test
    void testTransferFund() {
        Account account1 = bankManager.createAccount(101L, "Ramesh Fadatare", 500.0);
        Account account2 = bankManager.createAccount(102L, "Umesh Fadatare", 500.0);
        bankManager.transferFund(101L, 102L, 300.0);

        Account fromAccount = bankManager.getAccount(101L);
        Account toAccount = bankManager.getAccount(102L);

        // Using assertEquals for both accounts
        assertEquals(200.0, fromAccount.getBalance(), "Sender's balance should be reduced");
        assertEquals(800.0, toAccount.getBalance(), "Receiver's balance should be increased");
    }

    @Test
    void testTransferFund_InsufficientBalance() {
        Account account1 = bankManager.createAccount(101L, "Ramesh Fadatare", 500.0);
        Account account2 = bankManager.createAccount(102L, "Umesh Fadatare", 500.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bankManager.transferFund(101L, 102L, 2000.0);
        });

        // Using assertNotNull and assertEquals
        assertNotNull(exception, "Exception should not be null for insufficient funds transfer");
        assertEquals("Insufficient balance.", exception.getMessage(), "Error message should match");
    }

    @Test
    void testTransferFund_NegativeAmount() {
        Account account1 = bankManager.createAccount(101L, "Ramesh Fadatare", 500.0);
        Account account2 = bankManager.createAccount(102L, "Umesh Fadatare", 500.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bankManager.transferFund(101L, 102L, -100.0);
        });

        // Using assertNotNull and assertEquals
        assertNotNull(exception, "Exception should not be null for negative transfer amount");
        assertEquals("Transfer amount must be greater than zero.", exception.getMessage(), "Error message should match");
    }
```

## Nested Tests Demo
```java
@Nested
    @DisplayName("Transfer Fund Tests")
    class TransferFundTest {
        @Test
        void testTransferFund() {
            Account account1 = bankManager.createAccount(101L, "Ramesh Fadatare", 500.0);
            Account account2 = bankManager.createAccount(102L, "Umesh Fadatare", 500.0);
            bankManager.transferFund(101L, 102L, 300.0);

            Account fromAccount = bankManager.getAccount(101L);
            Account toAccount = bankManager.getAccount(102L);

            // Using assertEquals for both accounts
            assertEquals(200.0, fromAccount.getBalance(), "Sender's balance should be reduced");
            assertEquals(800.0, toAccount.getBalance(), "Receiver's balance should be increased");
        }

        @Test
        void testTransferFund_InsufficientBalance() {
            Account account1 = bankManager.createAccount(101L, "Ramesh Fadatare", 500.0);
            Account account2 = bankManager.createAccount(102L, "Umesh Fadatare", 500.0);
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                bankManager.transferFund(101L, 102L, 2000.0);
            });

            // Using assertNotNull and assertEquals
            assertNotNull(exception, "Exception should not be null for insufficient funds transfer");
            assertEquals("Insufficient balance.", exception.getMessage(), "Error message should match");
        }

        @Test
        void testTransferFund_NegativeAmount() {
            Account account1 = bankManager.createAccount(101L, "Ramesh Fadatare", 500.0);
            Account account2 = bankManager.createAccount(102L, "Umesh Fadatare", 500.0);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                bankManager.transferFund(101L, 102L, -100.0);
            });

            // Using assertNotNull and assertEquals
            assertNotNull(exception, "Exception should not be null for negative transfer amount");
            assertEquals("Transfer amount must be greater than zero.", exception.getMessage(), "Error message should match");
        }
    }
```
# Complete code 
## BankManager
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

    public Account updateAccount(long accountNumber, String newHolderName) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found.");
        }
        account.setHolderName(newHolderName);
        return account;
    }

    public Account getAccount(long accountNumber){
        if (!accounts.containsKey(accountNumber)) {
            throw new AccountNotFoundException("Account not found.");
        }
        return accounts.get(accountNumber);
    }

    public void removeAccount(long accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            throw new AccountNotFoundException("Account not found.");
        }
        accounts.remove(accountNumber);
    }

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

    public void withdraw(long accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be greater than zero.");
        }
        if (amount > account.getBalance()) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        account.setBalance(account.getBalance() - amount);
    }

    public void transferFund(long fromAccountNumber, long toAccountNumber, double amount) {
        Account fromAccount = accounts.get(fromAccountNumber);
        Account toAccount = accounts.get(toAccountNumber);
        if (fromAccount == null || toAccount == null) {
            throw new AccountNotFoundException("One or both accounts not found.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }
        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
    }
}
```
## BankManagerTest
```java
package net.javaguides.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @Test
    void testUpdateAccount() {

        Account account = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

        Account updatedAccount = bankManager.updateAccount(123L, "Ram Fadatare");
        assertEquals("Ram Fadatare", updatedAccount.getHolderName());
    }

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

    @Nested
    class WithDrawTests {
        @Test
        void testWithdraw() {

            Account newAccount = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

            bankManager.withdraw(123L, 200.0);
            Account account = bankManager.getAccount(123L); // assuming getAccount method exists

            // Using assertEquals
            assertEquals(300.0, account.getBalance(), "Balance should be reduced by withdrawal amount");
        }

        @Test
        void testWithdraw_InsufficientBalance() {

            Account newAccount = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                bankManager.withdraw(123L, 2000.0);
            });

            // Using assertNotNull and assertEquals
            assertNotNull(exception, "Exception should not be null when withdrawing more than balance");
            assertEquals("Insufficient balance.", exception.getMessage(), "Error message should match");
        }

        @Test
        void testWithdraw_NegativeAmount() {

            Account newAccount = bankManager.createAccount(123L, "Ramesh Fadatare", 500.0);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                bankManager.withdraw(123L, -200.0);
            });

            // Using assertNotNull and assertEquals
            assertNotNull(exception, "Exception should not be null for negative withdrawal");
            assertEquals("Withdraw amount must be greater than zero.", exception.getMessage(), "Error message should match");
        }
    }

    @Nested
    @DisplayName("Transfer Fund Tests")
    class TransferFundTest {
        @Test
        void testTransferFund() {
            Account account1 = bankManager.createAccount(101L, "Ramesh Fadatare", 500.0);
            Account account2 = bankManager.createAccount(102L, "Umesh Fadatare", 500.0);
            bankManager.transferFund(101L, 102L, 300.0);

            Account fromAccount = bankManager.getAccount(101L);
            Account toAccount = bankManager.getAccount(102L);

            // Using assertEquals for both accounts
            assertEquals(200.0, fromAccount.getBalance(), "Sender's balance should be reduced");
            assertEquals(800.0, toAccount.getBalance(), "Receiver's balance should be increased");
        }

        @Test
        void testTransferFund_InsufficientBalance() {
            Account account1 = bankManager.createAccount(101L, "Ramesh Fadatare", 500.0);
            Account account2 = bankManager.createAccount(102L, "Umesh Fadatare", 500.0);
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                bankManager.transferFund(101L, 102L, 2000.0);
            });

            // Using assertNotNull and assertEquals
            assertNotNull(exception, "Exception should not be null for insufficient funds transfer");
            assertEquals("Insufficient balance.", exception.getMessage(), "Error message should match");
        }

        @Test
        void testTransferFund_NegativeAmount() {
            Account account1 = bankManager.createAccount(101L, "Ramesh Fadatare", 500.0);
            Account account2 = bankManager.createAccount(102L, "Umesh Fadatare", 500.0);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                bankManager.transferFund(101L, 102L, -100.0);
            });

            // Using assertNotNull and assertEquals
            assertNotNull(exception, "Exception should not be null for negative transfer amount");
            assertEquals("Transfer amount must be greater than zero.", exception.getMessage(), "Error message should match");
        }
    }
}
```
