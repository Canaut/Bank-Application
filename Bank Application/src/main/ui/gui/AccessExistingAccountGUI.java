package ui.gui;

import model.UserAccount;

import javax.swing.*;
import java.awt.event.ActionEvent;


// interface for accessing existing accounts
public class AccessExistingAccountGUI extends LoginGUI {
    private UserAccountUI userAccountUI;
    protected JButton returnToMainButton;

    // Effects: creates the user interface for accessing existing accounts. specifically for logging into an account
    public AccessExistingAccountGUI(MainGUI mainGUI) {
        super(mainGUI);
        returnToMainButton = createButton("Return to Main Menu");
        panel.add(returnToMainButton);
        panel.validate();
    }

    // Effects: if the return to main button is pressed, then it results to the main interface
    //          else inputted information is verified
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (returnToMainButton.equals(source)) {
            returnToMainInterface();
        } else {
            verifyExistingMemberAccount();
        }
    }

    // Modifies: this
    // Effects: Ensures that the information inputted by user matches a corresponding bank account;
    private void verifyExistingMemberAccount() {
        String userName = userTextField.getText();
        String password = passTextField.getText();
        boolean correctCredentials = mainGUI.userAccountDatabase.correctUserAccount(userName, password);
        if (correctCredentials) {
            UserAccount accountToAccess = mainGUI.userAccountDatabase.getUserAccounts(userName,password);
            userAccountUI = new UserAccountUI(accountToAccess, mainGUI);
            mainGUI.addNewGUI(GuiType.ACCOUNTINTERFACE, userAccountUI);
            frame.setVisible(false);
            mainGUI.accessGui(GuiType.ACCOUNTINTERFACE);
        } else {
            frame.setSize(200,250);
            failure.setText("Incorrect Credentials");
        }
    }
}
