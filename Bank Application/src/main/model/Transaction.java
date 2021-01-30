package model;

import java.util.Date;

// Item of transaction including transaction amount, transaction type, and currency type
public class Transaction {
    public double transactionAmount;
    public String transactionType;
    public String currencyType;

    // Requires
    // Effects: records transaction type and transaction amount
    Transaction(String transactionType, double transactionAmount, String currencyType) {
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.currencyType = currencyType;

    }
}
