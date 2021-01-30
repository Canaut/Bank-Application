package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

//Users of the banking system, they have a username and a password and a list of Bank accounts they own
public class UserAccount {
    private static final double EXCHANGE_RATE = 1.33;
    public String userName;
    public String userPassword;
    public List<BankAccount> userBankAccounts;


    // Requires: user have >0 length, password have >0 length
    // Effects: create bank account with username and password
    // Effects: initialize every user with a Chequing account (index 0) and a USD account (index 1)
    public UserAccount(String user, String password) {
        this.userName = user;
        this.userPassword = password;
        userBankAccounts = new LinkedList<>();
    }

    //Modifies: this
    //Effects: given string and balance, add new account to bank account
    public void addAccount(BankAccount newBankAccount) {
        userBankAccounts.add(newBankAccount);
    }

    //Modifies: this
    //Effects: if correct current password given, change current password to new password otherwise do nothing
    public boolean changePassword(String currentPW, String newPW) {
        if (this.userPassword.equals(currentPW)) {
            this.userPassword = newPW;
            return true;
        }
        return false;
    }

    //Requires: account must have enough money to be transferred
    //Modifies: this
    //Effects: exchanges money from CAD account to USD account or vice versa
    public void exchangeMoney(BankAccount from, BankAccount to, double n) {
        if (from.currency.equals("CAD")) {
            from.withdraw(n);
            to.deposit(n / EXCHANGE_RATE);
        } else {
            from.withdraw(n);
            to.deposit(n * EXCHANGE_RATE);
        }

    }

    //Modifies: nothing
    //Effects: finds a the bank account given currency type
    public BankAccount findBankAccount(String currency) {
        for (BankAccount next : this.userBankAccounts) {
            if (currency.equals(next.currency)) {
                return next;
            }
        }
        return null;
    }

    // returns the JSON object for this workRoom,
    // specifically, returns a JsonObject for UserAccounts
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", userName);
        json.put("password", userPassword);
        JSONArray jsonArray = new JSONArray();
        for (BankAccount b: this.userBankAccounts) {
            jsonArray.put(b.toJson());
        }
        json.put("accounts", jsonArray);

        return json;
    }
}
