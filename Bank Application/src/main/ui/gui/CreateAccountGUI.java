package ui.gui;

import model.UserAccount;
import model.UserAccountDatabase;

import java.awt.event.ActionEvent;

// User interface for creating an account
public class CreateAccountGUI extends LoginGUI {
    public CreateAccountGUI(MainGUI mainGUI) {
        super(mainGUI);
        button.setText("Create Account");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        newAccount();
        frame.setSize(310,200);
    }

    // Modifies: this
    // Effects: if the user input for user and pass are greater than 6 characters,
    //          a new user account is added to database and returns to the main interface
    //          otherwise the system prompts the user to try again
    private void newAccount() {
        UserAccount toAdd = new UserAccount(userTextField.getText(), passTextField.getText());
        if (mainGUI.userAccountDatabase.addBankAccount(toAdd)) {
            System.out.println("Your account has been created, please log in to access your account.");
            returnToMainInterface();
        } else {
            failure.setText("You have entered invalid information, try again.");
        }
    }
}
