package persistence;

import model.BankAccount;
import model.ChequingBankAccount;
import model.UsdBankAccount;
import model.UserAccount;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkUserAccounts(String name, String pw, UserAccount userAccount) {
        assertEquals(name, userAccount.userName);
        assertEquals(pw, userAccount.userPassword);
    }

    protected List<UserAccount> getUserAccount() {
        List<UserAccount> userAccounts = new LinkedList<>();
        BankAccount chequingAccount = new ChequingBankAccount(500);
        BankAccount usdAccount = new UsdBankAccount(100.0);
        UserAccount ua1 = new UserAccount("user12", "pw1234");
        ua1.addAccount(chequingAccount);
        ua1.addAccount(usdAccount);
        UserAccount ua2 = new UserAccount("user23", "pw2345");
        ua2.addAccount(usdAccount);
        ua2.addAccount(chequingAccount);
        userAccounts.add(ua1);
        userAccounts.add(ua2);

        return userAccounts;

    }
}
