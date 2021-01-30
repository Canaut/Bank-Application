package model;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

//Represents a bank account
//Contains, account balance, account currency and account transactions
public abstract class BankAccount {
    private double balance;
    public String currency;
    public List<Transaction> transactionHistory;

    // Effects: create empty Bank account
    BankAccount() {
        balance = 0;
        currency = "";
        transactionHistory = new LinkedList<>();
    }

    // Modifies: this
    // Effects: take out n dollars from bank account and record on transaction history
    public void withdraw(double n) {
        if (n >= 0) {
            balance = balance - n;
            Transaction toAdd = new Transaction("withdraw", -n, this.currency);
            transactionHistory.add(toAdd);
        }
    }

    // Modifies: this
    // Effects: add n dollars from bank account and record on transaction history
    public void deposit(double n) {
        if (n >= 0) {
            balance = balance + n;
            Transaction toAdd;
            toAdd = new Transaction("deposit", n, this.currency);
            transactionHistory.add(toAdd);
        }
    }

    // Effects: return current balance
    public double checkBalance() {
        return balance;
    }

    //Requires: cost to be >= 0;
    //Modifies: this
    //Effects: subtract cost from account balance and record transaction type
    public boolean makePurchase(String purchaseType, double cost, String currencyType) {
        if (this.currency.equals(currencyType)) {
            Transaction transaction = new Transaction(purchaseType, -cost, currencyType);
            balance = balance - cost;
            transactionHistory.add(transaction);
            return true;
        }
        return false;
    }

    //requires: index >= 0 and less than size of transition history
    //Modifies: this
    //Effects: return the transaction history at a given index
    public Transaction getTransaction(int index) {
        return this.transactionHistory.get(index);
    }


    //Effects return JSON object derived from a BankAccount object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("currency", this.currency);
        json.put("balance", this.balance);
        return json;
    }
}
