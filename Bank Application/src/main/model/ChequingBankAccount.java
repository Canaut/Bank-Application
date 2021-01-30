package model;

//banking account that uses Canadian currency
public class ChequingBankAccount extends BankAccount {

    //Effects: creating bank account with "CAD" as the currency
    public ChequingBankAccount() {
        super();
        currency = "CAD";
    }

    //Effects: creating bank account with "CAD" as the currency and initialized with n as the balanced
    public ChequingBankAccount(double n) {
        super();
        currency = "CAD";
        this.deposit(n);
    }
}
