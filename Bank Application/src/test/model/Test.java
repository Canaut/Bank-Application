package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountDatabaseTest {

    private UserAccount testAccount;
    private UserAccountDatabase test = new UserAccountDatabase();


    @BeforeEach
    void beforeEach(){
        test = new UserAccountDatabase();
        testAccount = new UserAccount("testName", "testPassword");
        test.addBankAccount(testAccount);
    }

    @Test
    void testAddUSDAccountAndExchange() {
        UsdBankAccount usdAccount = new UsdBankAccount();
        testAccount.addAccount(usdAccount);
        assertEquals("USD", testAccount.userBankAccounts.get(0).currency);

        ChequingBankAccount chequingAccount = new ChequingBankAccount();
        chequingAccount.deposit(50);
        testAccount.addAccount(chequingAccount);

        testAccount.exchangeMoney(testAccount.userBankAccounts.get(1), testAccount.userBankAccounts.get(0), 50);
        assertEquals(0, testAccount.userBankAccounts.get(1).checkBalance());
        double num = 50 / 1.33;
        assertEquals(num, testAccount.userBankAccounts.get(0).checkBalance());
        testAccount.exchangeMoney(testAccount.userBankAccounts.get(0), testAccount.userBankAccounts.get(1), 10);
        assertEquals(10 * 1.33, testAccount.userBankAccounts.get(1).checkBalance());
        assertEquals(50 / 1.33 - 10, testAccount.userBankAccounts.get(0).checkBalance());
    }

    @Test
    void testChangePassword() {
        boolean test;
        test = testAccount.changePassword("hello", "newPW");
        assertFalse(test);
        test = testAccount.changePassword("testPassword", "newPW");
        assertTrue(test);
        assertEquals("newPW", testAccount.userPassword);
    }

    @Test
    void testDepositAndWithdrawNegativeAmount() {
        ChequingBankAccount chequingAccount = new ChequingBankAccount(0);
        testAccount.addAccount(chequingAccount);
        testAccount.userBankAccounts.get(0).deposit(-10);
        assertEquals(0, testAccount.userBankAccounts.get(0).checkBalance());
        testAccount.userBankAccounts.get(0).withdraw(-10);
        assertEquals(0, testAccount.userBankAccounts.get(0).checkBalance());

    }

    @Test
    void testDepositAndWithdrawPositiveAmount() {
        ChequingBankAccount chequingAccount = new ChequingBankAccount(0);
        testAccount.addAccount(chequingAccount);
        testAccount.userBankAccounts.get(0).deposit(10);
        assertEquals(10, testAccount.userBankAccounts.get(0).checkBalance());
        testAccount.userBankAccounts.get(0).withdraw(10);
        assertEquals(0, testAccount.userBankAccounts.get(0).checkBalance());

    }

    @Test
    void testTransactionHistory() {
        UsdBankAccount chequingAccount = new UsdBankAccount();
        testAccount.addAccount(chequingAccount);
        testAccount.userBankAccounts.get(0).deposit(50.0);
        Transaction testTransaction = testAccount.userBankAccounts.get(0).getTransaction(0);
        assertEquals("deposit", testTransaction.transactionType);
        assertEquals(50.0, testTransaction.transactionAmount);
        assertEquals("USD", testTransaction.currencyType);

        testAccount.userBankAccounts.get(0).withdraw(10);
        testTransaction = testAccount.userBankAccounts.get(0).getTransaction(1);
        assertEquals("withdraw", testTransaction.transactionType);
        assertEquals(-10, testTransaction.transactionAmount);
        assertEquals("USD", testTransaction.currencyType);

        boolean testPurchase = testAccount.userBankAccounts.get(0).makePurchase("restaurant", 5, "USD");
        testTransaction = testAccount.userBankAccounts.get(0).getTransaction(2);
        assertTrue(testPurchase);
        assertEquals("restaurant", testTransaction.transactionType);
        assertEquals(-5, testTransaction.transactionAmount);
        assertEquals("USD", testTransaction.currencyType);

        testPurchase = testAccount.userBankAccounts.get(0).makePurchase("restaurant", 5, "CAD");
        assertFalse(testPurchase);

    }


    @Test
    void testFindBankAccountNotFound(){
        BankAccount toAdd = new ChequingBankAccount(50);
        testAccount.addAccount(toAdd);
        testAccount.addAccount(toAdd);

        assertNull(testAccount.findBankAccount("USD"));

    }

    @Test
    void testFindBankAccountFound(){
        BankAccount toAdd = new UsdBankAccount(50);
        testAccount.addAccount(toAdd);

        BankAccount searchedBankAccount = testAccount.findBankAccount("USD");
        assertEquals(50, searchedBankAccount.checkBalance());
    }

    @Test
    void testAddAccountToDatabaseContainingAccountName(){
        ChequingBankAccount chequingAccount = new ChequingBankAccount();
        chequingAccount.deposit(50);
        test.userAccounts.get(0).addAccount(chequingAccount);

        testAccount = new UserAccount("testName", "testPassword");
        boolean accountAdded = test.addBankAccount(testAccount);
        assertFalse(accountAdded);

    }

    @Test
    void testAddAccountToDatabaseNewAccountName(){
        ChequingBankAccount chequingAccount = new ChequingBankAccount();
        chequingAccount.deposit(50);
        test.userAccounts.get(0).addAccount(chequingAccount);

        testAccount = new UserAccount("testName1", "testPassword1");
        boolean accountAdded = test.addBankAccount(testAccount);
        assertTrue(accountAdded);
    }

    @Test
    void testTransferMoneyBetweenTwoUserBankAccounts(){
        UsdBankAccount usdBankAccount = new UsdBankAccount(50);
        test.userAccounts.get(0).addAccount(usdBankAccount);

        UserAccount toAdd = new UserAccount("test12", "test12");
        UsdBankAccount usdBankAccount1 = new UsdBankAccount(40);
        toAdd.addAccount(usdBankAccount1);
        test.addBankAccount(toAdd);

        test.transferMoney("test12", "testName", 10, "USD");


        assertEquals(30, test.userAccounts.get(1).userBankAccounts.get(0).checkBalance());
        assertEquals(60, test.userAccounts.get(0).userBankAccounts.get(0).checkBalance());


    }

    @Test
    void testFindUserBankAccountNotFound() {
        UsdBankAccount usdBankAccount = new UsdBankAccount(50);
        test.userAccounts.get(0).addAccount(usdBankAccount);
        assertNull( test.findUser("testUser", "X"));
    }

    @Test
    void testFindUserAccountsFound(){
        boolean containsUser = test.correctUserAccount("testName", "testPassword");
        assertTrue(containsUser);

    }

    @Test
    void testFindUserAccountsNotFound() {
        boolean containsUser = test.correctUserAccount(" ", " ");
        assertFalse(containsUser);
    }

    @Test
    void testFindUserAccountsNotFoundIncorrectPassword() {
        boolean containsUser = test.correctUserAccount("testName", " ");
        assertFalse(containsUser);
    }

    @Test
    void testGetUserAccountsFound(){
        UserAccount user = test.getUserAccounts("testName", "testPassword");
        assertEquals("testName", user.userName);

    }

    @Test
    void testGetUserAccountsNotFound(){
        UserAccount user = test.getUserAccounts(" ", "testPassword");
        assertNull( user);

    }

    @Test
    void testGetUserAccountsNotFoundIncorrectPassword(){
        UserAccount user = test.getUserAccounts("testName", "pw");
        assertNull(user);

    }

    @Test
    void testInvalidAccount() {
        UserAccount userAccount = new UserAccount("1", "1");
        UserAccountDatabase userAccountDatabase = new UserAccountDatabase();
        Boolean validAccount = userAccountDatabase.validAccount(userAccount);
        Boolean accountAdded = userAccountDatabase.addBankAccount(userAccount);

        assertEquals(0, userAccountDatabase.userAccounts.size());
        assertFalse(accountAdded);
        assertFalse(validAccount);

        userAccount = new UserAccount("123456", "1");
        validAccount = userAccountDatabase.validAccount(userAccount);
        accountAdded = userAccountDatabase.addBankAccount(userAccount);

        assertEquals(0, userAccountDatabase.userAccounts.size());
        assertFalse(accountAdded);
        assertFalse(validAccount);

        userAccount = new UserAccount("1", "123456");
        validAccount = userAccountDatabase.validAccount(userAccount);
        accountAdded = userAccountDatabase.addBankAccount(userAccount);

        assertEquals(0, userAccountDatabase.userAccounts.size());
        assertFalse(accountAdded);
        assertFalse(validAccount);

    }



}


