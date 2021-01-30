package model;

// Bank account which uses US as the currency
public class UsdBankAccount extends BankAccount {

    //Effects: create bank account with USD as the currency
    public UsdBankAccount() {
        super();
        this.currency = "USD";
    }

    //Effects: create bank account with USD as the currency initialized with n as the balance
    public UsdBankAccount(double n) {
        super();
        this.currency = "USD";
        this.deposit(n);
    }
}
