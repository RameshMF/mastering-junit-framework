package net.javaguides.bankapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService accountService;

    @BeforeEach
    void setUp(){
        accountService = new AccountService();
    }

    @Test
    void testCreateAccount(){

        Account account = accountService.createAccount(123L, "Ramesh Fadatare", 500.0);

        assertNotNull(account);

        assertEquals(123L, account.getAccountNumber());

        assertEquals("Ramesh Fadatare", account.getHolderName());

        assertEquals(500.0, account.getBalance());
    }

    @Test
    void testUpdateAccount(){

        Account account = accountService.createAccount(123L, "Ramesh Fadatare", 500.0);

        Account updatedAccount = accountService.updateAccount(123L, "Ram Jadhav");

        assertEquals("Ram Jadhav", updatedAccount.getHolderName());

        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            accountService.updateAccount(1234L, "Ram Jadhav");
        });

        assertEquals("Account not found.", exception.getMessage());

    }

    @Test
    void testGetAccount(){

        Account account = accountService.createAccount(123L, "Ramesh Fadatare", 500.0);

        Account actualAccount = accountService.getAccount(123L);

        assertNotNull(actualAccount);

        assertEquals(123L, actualAccount.getAccountNumber());

        assertEquals("Ramesh Fadatare", actualAccount.getHolderName());

        assertEquals(500.0, actualAccount.getBalance());

        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            accountService.getAccount(1234L);
        });

        assertEquals("Account not found.", exception.getMessage());
    }

    @Test
    void testRemoveAccount(){

        Account account = accountService.createAccount(123L, "Ramesh Fadatare", 500.0);

        accountService.removeAccount(123L);

        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            accountService.removeAccount(123L);
        });

        assertEquals("Account not found.", exception.getMessage());

    }

    @Test
    void testDeposit(){

        Account account = accountService.createAccount(123L, "Ramesh Fadatare", 500.0);

        accountService.deposit(123L, 1500.0);

        Account updatedAccount = accountService.getAccount(123L);

        double actualBalance = updatedAccount.getBalance();

        assertEquals(2000.0, actualBalance);

    }

    @Test
    void testDeposit_NegativeAmount(){

        Account account = accountService.createAccount(123L, "Ramesh Fadatare", 500.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
           accountService.deposit(123L, -500.0);
        });

        assertNotNull(exception);
        assertEquals("Deposit amount must be greater than zero.", exception.getMessage());
    }


    @Nested
    class WithdrawTests {

        @Test
        void testWithdraw(){

            accountService.createAccount(123L, "Ramesh Fadatare", 1000.0);

            accountService.withdraw(123L, 500.0);

            Account updatedAccount = accountService.getAccount(123L);

            double actualBalance = updatedAccount.getBalance();

            assertEquals(500.0, actualBalance);

        }

        @Test
        void testWithdraw_InsufficientBalance(){

            accountService.createAccount(123L, "Ramesh Fadatare", 1000.0);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.withdraw(123L, 2000.0);
            });

            assertNotNull(exception);

            assertEquals("Insufficient balance.", exception.getMessage());
        }

        @Test
        void testWithdraw_NegativeAmount(){

            accountService.createAccount(123L, "Ramesh Fadatare", 1000.0);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.withdraw(123L, -500.0);
            });

            assertNotNull(exception);

            assertEquals("Withdraw amount must be greater than zero.", exception.getMessage());
        }

    }

    @Nested
    class TransferFundTests {
        @Test
        void testTransferFund(){

            Account fromAccount = accountService.createAccount(101L, "Ramesh Fadatare", 1000.0);
            Account toAccount = accountService.createAccount(102L, "Pramod Jadhav", 500.0);

            accountService.transferFund(101L, 102L, 500.0);

            Account updatedFromAccount = accountService.getAccount(101L);
            Account updatedToAccount = accountService.getAccount(102L);

            double actualFromAccountBalance = updatedFromAccount.getBalance();
            double actualToAccountBalance = updatedToAccount.getBalance();

            assertEquals(500.0, actualFromAccountBalance);

            assertEquals(1000.0, actualToAccountBalance);

        }

        @Test
        void testTransferFund_InsufficientBalance(){

            Account fromAccount = accountService.createAccount(101L, "Ramesh Fadatare", 1000.0);
            Account toAccount = accountService.createAccount(102L, "Pramod Jadhav", 500.0);

            assertThrows(IllegalArgumentException.class, () -> {
                accountService.transferFund(101L, 102L, 2000.0);
            });

        }

        @Test
        void testTransferFund_NegativeBalance(){

            Account fromAccount = accountService.createAccount(101L, "Ramesh Fadatare", 1000.0);
            Account toAccount = accountService.createAccount(102L, "Pramod Jadhav", 500.0);

            assertThrows(IllegalArgumentException.class, () -> {
                accountService.transferFund(101L, 102L, -500.0);
            });

        }
    }
}