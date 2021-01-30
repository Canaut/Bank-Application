package model;

import java.util.LinkedList;
import java.util.List;

// represents a database of UserAccounts
// Stores information of different Users in the banking system
public class UserAccountDatabase {
    public List<UserAccount> userAccounts;

    public UserAccountDatabase() {
        userAccounts = new LinkedList<>();
    }

    //Modifies: this
    //Effects: add bank account to bankAccountDatabase if account doesnt exist
    public boolean addBankAccount(UserAccount account) {
        if (!checkDuplicates(account) && validAccount(account)) {
            userAccounts.add(account);
            return true;
        }
        return false;
    }

    //requires: account is not null
    //effects: if account username is >= 4 characters or password is >= 6 char, return true, otherwise return false.
    public boolean validAccount(UserAccount account) {
        if (account.userName.length() >= 6 && account.userPassword.length() >= 6) {
            return true;
        } else {
            return false;
        }
    }

    //Modifies: nothing
    //Effects: determines if a username is already in the account database
    private boolean checkDuplicates(UserAccount account) {
        for (UserAccount next : userAccounts) {
            if (next.userName.equals(account.userName)) {
                return true;
            }
        }
        return false;
    }

    //Requires: account names are valid and are in the database containing enough money
    //Modifies: this
    //Effects: transfers money between from one account to another
    public void transferMoney(String userFrom, String userTo, double transferred, String currency) {
        BankAccount from = findUser(userFrom, currency);
        BankAccount to = findUser(userTo, currency);
        from.withdraw(transferred);
        to.deposit(transferred);
    }


    // Modifies: this
    // Effects: finds the User's bank account of a given currency
    public BankAccount findUser(String user, String currency) {
        for (UserAccount next: userAccounts) {
            if (next.userName.equals(user)) {
                return next.findBankAccount(currency);
            }
        }
        return null;
    }

    //Modifies: this
    //Effects: returns true if a UserAccount can be found with the given username and database
    public boolean correctUserAccount(String username, String password) {
        for (UserAccount next: this.userAccounts) {
            if (next.userName.equals(username) && next.userPassword.equals(password)) {
                return true;
            }
        }
        return false;
    }

    //Modifies: this
    //Effects: returns the UserAccount if a UserAccount can be found with the given username and database
    public UserAccount getUserAccounts(String username, String password) {
        for (UserAccount next: this.userAccounts) {
            if (next.userName.equals(username) && next.userPassword.equals(password)) {
                return next;
            }
        }
        return null;
    }


}

