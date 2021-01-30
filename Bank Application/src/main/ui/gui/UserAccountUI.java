package ui.gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

//user interface for user accounts.
public class UserAccountUI extends GUI implements ActionListener {
    private UserAccount userAccount;
    private BankAccount currentBankAccount;

    private List<JButton> backButtons = new LinkedList<>();
    private JPanel currentPanel;
    private Map<JPanel, JPanel> panelMap;

    private JPanel mainUserPanel;
    private JButton newBankAccountButton;
    private JButton currentBankAccountButton;

    private JPanel createBankAccountPanel;
    private JButton chequingAccountButton;
    private JButton usdAccountButton;

    private JPanel existingAccountPanel;
    private List<JButton> existingAccountPanelButtons;

    private JPanel bankAccountFunctionPanel;
    private List<JButton> bankAccountFunctionButtons;

    private JPanel addOrRemovePanel;
    private JLabel successLabel;
    private JLabel errorLabel;
    private JLabel amountLabel;
    private JButton addButton;
    private JButton removeButton;
    private JTextField amountTextField;

    private JPanel transactionHistoryPanel;
    private JTable transactionHistoryTable;
    private JButton returnToMainInterfaceButton;


    //Effects: Creates majority of panels and buttons for the user interace
    public UserAccountUI(UserAccount accountToAccess, MainGUI mainGUI) {
        super(mainGUI);
        this.userAccount = accountToAccess;
        createAddRemovePanel();
        createBankAccountFunctionPanel();
        createNewBankAccountPanel();
        createMainUserPanel();
        createPanelMap();
        mainUserPanel.setVisible(true);
        frame.setVisible(true);
    }

    // Modifies: this
    // Effects: creates a map used to keep track of the current directory and the previous directory
    private void createPanelMap() {
        panelMap = new HashMap<>();
        panelMap.put(addOrRemovePanel, bankAccountFunctionPanel);
        panelMap.put(transactionHistoryPanel, bankAccountFunctionPanel);
        panelMap.put(bankAccountFunctionPanel, mainUserPanel);
        panelMap.put(createBankAccountPanel, mainUserPanel);
    }

    // Modifies: this
    // Effects: Creates BankAccountFunctionpanel and its corresponding buttons (withdraw, deposit, transaction history)
    private void createBankAccountFunctionPanel() {
        bankAccountFunctionPanel = new JPanel();
        createBankAccountButtons();

    }

    // Modifies: this
    // Effects: creates and adds buttons for the BankAccountFunctionpanel
    private void createBankAccountButtons() {
        bankAccountFunctionButtons = new LinkedList<>();
        bankAccountFunctionButtons.add(createButton("Deposit"));
        bankAccountFunctionButtons.add(createButton("Withdraw"));
        bankAccountFunctionButtons.add(createButton("Transaction History"));
        bankAccountFunctionButtons.add(createBackButton());
        for (JButton button : bankAccountFunctionButtons) {
            bankAccountFunctionPanel.add(button);
        }
    }

    // Modifies: this
    // Effects: creates the panel where user can create bank account and access their old accounts
    private void createMainUserPanel() {
        mainUserPanel = panel;
        addMainUserAccountButtons();
        frame.add(mainUserPanel, BorderLayout.CENTER);

    }

    // Modifies: this
    // Effects: creates buttons for the mainUserPanel (creat account, access old account, return to main interface)
    private void addMainUserAccountButtons() {
        newBankAccountButton = createButton("Create Account");
        currentBankAccountButton = createButton("Existing Accounts");
        returnToMainInterfaceButton = createButton("Return to Main Interface");
        mainUserPanel.add(newBankAccountButton);
        mainUserPanel.add(currentBankAccountButton);
        mainUserPanel.add(returnToMainInterfaceButton);
    }

    // Modifies: this
    // Effects: Creates the interface where user can withdraw and deposit money.
    private void createAddRemovePanel() {
        addOrRemovePanel = createPanel();
        createAddRemovePanelElements();
    }

    // Modifies: this
    // Effects: creates and adds the elements required for user to despot and remove money
    private void createAddRemovePanelElements() {
        addButton = createButton("Add");
        removeButton = createButton("Remove");
        amountTextField = new JTextField(15);
        amountTextField.setBounds(100,40,65,25);
        amountLabel = new JLabel("Enter amount to Add/Remove");
        amountLabel.setBounds(10,20, 80, 25);
        errorLabel = new JLabel("Wrong Input, Try again");
        successLabel = new JLabel("");
        addOrRemovePanel.add(amountLabel);
        addOrRemovePanel.add(amountTextField);
        addOrRemovePanel.add(addButton);
        addOrRemovePanel.add(removeButton);
        addOrRemovePanel.add(createBackButton());
        addOrRemovePanel.add(successLabel);
        successLabel.setVisible(false);
        addOrRemovePanel.add(errorLabel);
        errorLabel.setVisible(false);
    }


    // Effects: processes user button presses and performs the corresponding functions
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (backButtons.contains(source)) {
            returnToPreviousPanel();
        } else if (returnToMainInterfaceButton.equals(source)) {
            returnToMainInterface();
        } else if (newBankAccountButton.equals(source) || currentBankAccountButton.equals(source)) {
            mainUserPanelActions(source);
        } else if (chequingAccountButton.equals(source) || usdAccountButton.equals(source)) {
            newBankAccountButtonActions(source);
        } else if (isBankAccountFunctionButton(source)) {
            bankAccountFunctionButtonActions(source);
        } else if (isAddRemoveButton(source)) {
            addOrRemoveButtonActions(source);
        } else {
            bankAccountButtonsActions(source);
        }
    }

    // Effects: uses the panelMap and currentPanel to nagivate back to the previous frame
    private void returnToPreviousPanel() {
        JPanel previousPanel = panelMap.get(currentPanel);
        currentPanel.setVisible(false);
        previousPanel.setVisible(true);
        frame.add(previousPanel);
        currentPanel = previousPanel;
    }

    // Modifies: this
    // Effects: performs the actions for the add and remove buttom. If improper value is added, user is promoted
    //          with an error message to try again. If user input is less than 0, the value is converted to 0
    //          and nothing is done.
    private void addOrRemoveButtonActions(Object source) {
        String amount = amountTextField.getText();
        Double value;
        try {
            value = Double.parseDouble(amount);
            if (value < 0) {
                value = 0.0;
            }
            if (addButton.equals(source)) {
                currentBankAccount.deposit(Double.parseDouble(amount));
                successLabel.setText("Successfully deposited " + value + " $");
            } else if (removeButton.equals(source)) {
                currentBankAccount.withdraw(Double.parseDouble(amount));
                successLabel.setText("Successfully withdrew " + value + " $");
            }
            successLabel.setVisible(true);
            errorLabel.setVisible(false);
            addOrRemovePanel.validate();
        } catch (NumberFormatException e) {
            successLabel.setVisible(false);
            errorLabel.setVisible(true);
            addOrRemovePanel.validate();
        }



    }

    // Effects: checks if the button pressed is the add or remove button
    private boolean isAddRemoveButton(Object source) {
        if (addButton.equals(source) || removeButton.equals(source)) {
            return true;
        }
        return false;
    }


    // Effects: checks for the button that the user pressed and perfroms the corresponding action
    private void bankAccountFunctionButtonActions(Object source) {
        for (JButton button : bankAccountFunctionButtons) {
            if (button.equals(source)) {
                String buttonPressed = button.getText();
                performBankAccountFunctionButtonActions(buttonPressed);
                return;
            }
        }
    }

    // Effects: converts the button press into its correspodning function
    private void performBankAccountFunctionButtonActions(String string) {
        if (string.equals("Deposit") || string.equals("Withdraw")) {
            accessAddRemovePanel();
        } else if (string.equals("Transaction History")) {
            createTransactionHistoryPanel();
            accessTransactionHistoryPanel();
        }
    }

    // Effects: access teh transcation history of the current bank account
    private void accessTransactionHistoryPanel() {
        bankAccountFunctionPanel.setVisible(false);
        frame.add(transactionHistoryPanel, BorderLayout.CENTER);
        frame.setSize(300, 500);
        currentPanel = transactionHistoryPanel;
    }

    // Effects: access the panel where user can deposit and withdraw money.
    private void accessAddRemovePanel() {
        bankAccountFunctionPanel.setVisible(false);
        frame.add(addOrRemovePanel, BorderLayout.CENTER);
        currentPanel = addOrRemovePanel;
        addOrRemovePanel.setVisible(true);
    }


    // Modifies: this
    // Effects: creates the transaction history panel where user transaction history is displayed
    private void createTransactionHistoryPanel() {
        transactionHistoryPanel = createPanel();
        createTransactionHistoryPanelElements();
        frame.add(transactionHistoryPanel);
        panelMap.put(transactionHistoryPanel, bankAccountFunctionPanel);
    }

    // Effects: creates the elements (transaction history table, and back button) for the transaction history panel
    private void createTransactionHistoryPanelElements() {
        createTransactionTable();
        transactionHistoryPanel.add(createBackButton());
    }

    // Modifies: this
    // Effects:Creates the transaction history table for the transaction history panel
    private void createTransactionTable() {
        String[] columnNames = {"Amount", "Transaction Type", "Currency"};
        String[][] transactionList = createTransactionList();
        transactionHistoryTable = new JTable(transactionList, columnNames);
        transactionHistoryPanel.add(transactionHistoryTable);
    }

    // Modifies: this
    // Effects: creates the array used to convert to the transaction history table for bank account history
    private String[][] createTransactionList() {
        int size = currentBankAccount.transactionHistory.size() + 1;
        String[][] result = new String[size][3];
        result[0][0] = "Amount";
        result[0][1] = "Transacton Type";
        result[0][2] = "Currency";
        for (int index = 1; index < size; index++) {
            Transaction transaction = currentBankAccount.transactionHistory.get(index - 1);
            result[index][0] = String.valueOf(transaction.transactionAmount);
            result[index][1] = transaction.transactionType;
            result[index][2] = transaction.currencyType;
        }
        return result;
    }


    // Effects: returns true if the button pressed is one of the BankAccountFunctions, false otherwise
    private boolean isBankAccountFunctionButton(Object source) {
        for (JButton button : bankAccountFunctionButtons) {
            if (button.equals(source)) {
                return true;
            }
        }
        return false;

    }

    // Modfies: this
    // Effects: acesses the bank account that the user has selected
    private void bankAccountButtonsActions(Object source) {
        int counter = 0;
        for (JButton button : existingAccountPanelButtons) {
            if (button.equals(source)) {
                currentBankAccount = userAccount.userBankAccounts.get(counter);
                accessBankAccountPanel();
                return;
            } else {
                counter += 1;
            }
        }

    }


    // Effects: access the bankAccountFunctionPanel
    private void accessBankAccountPanel() {
        existingAccountPanel.setVisible(false);
        frame.add(bankAccountFunctionPanel, BorderLayout.CENTER);
        currentPanel = bankAccountFunctionPanel;
        bankAccountFunctionPanel.setVisible(true);

    }

    // Modifies: this
    // Effects: creates a bank account for the current user based on the bank account buttons pressed
    private void newBankAccountButtonActions(Object source) {
        if (chequingAccountButton.equals(source)) {
            addNewBankAccount("CHK");
            returnToExistingAccountPanel();
        } else if (usdAccountButton.equals(source)) {
            addNewBankAccount("USD");
            returnToExistingAccountPanel();
        }
    }

    // Effects: determines which of the mainUserPanel buttons are pressed and perform the corresponding function
    private void mainUserPanelActions(Object source) {
        if (newBankAccountButton.equals(source)) {
            accessNewBankAccountMenu();
        } else if (currentBankAccountButton.equals(source)) {
            accessExistingBankAccountPanel();
        }
    }

    // Effects: access the menu where user can create new bank accounts
    private void accessNewBankAccountMenu() {
        mainUserPanel.setVisible(false);
        frame.add(createBankAccountPanel, BorderLayout.CENTER);
        currentPanel = createBankAccountPanel;
        createBankAccountPanel.setVisible(true);
    }

    // Effects: returns to the main userPanel from the creating bank Account panel
    private void returnToExistingAccountPanel() {
        createBankAccountPanel.setVisible(false);
        mainUserPanel.setVisible(true);
    }

    // Modifies: this
    // Effects: process the string to determine the type of account the user wants to create and create it
    private void addNewBankAccount(String command) {
        BankAccount accountToAdd;
        if (command.equals("CHK")) {
            accountToAdd = new ChequingBankAccount();
            userAccount.addAccount(accountToAdd);
            System.out.println(command + " account added.");
        } else if (command.equals("USD")) {
            accountToAdd = new UsdBankAccount();
            userAccount.addAccount(accountToAdd);
            System.out.println(command + " account added.");
        }
    }

    // Effects: access the panel where user can access their existing accounts
    private void accessExistingBankAccountPanel() {
        mainUserPanel.setVisible(false);
        createExistingBankAccountPanel();
        frame.add(existingAccountPanel, BorderLayout.CENTER);
        currentPanel = existingAccountPanel;
        existingAccountPanel.setVisible(true);
    }

    // Modifies: this
    // Effects: creates the panels where user can navigate to their designated bank account;
    private void createExistingBankAccountPanel() {
        existingAccountPanel = new JPanel();
        addBankAccountButtons();
        panelMap.put(existingAccountPanel, mainUserPanel);
    }

    // Modifies: this
    // Effects: creates a button for every bank account this user has
    private void addBankAccountButtons() {
        existingAccountPanelButtons = new LinkedList<>();
        for (BankAccount account : userAccount.userBankAccounts) {
            JButton buttonToAdd = createButton(account.currency + " Account");
            existingAccountPanelButtons.add(buttonToAdd);
            existingAccountPanel.add(buttonToAdd);
        }
        existingAccountPanel.add(createBackButton());
    }

    // Modifies: this
    // Effects: creates the panel where user can create new bank accounts
    private void createNewAccountPanel() {
        createBankAccountPanel = createPanel();
        chequingAccountButton = createButton("Chequing Account");
        usdAccountButton = createButton("USD Account");
        createBankAccountPanel.add(chequingAccountButton);
        createBankAccountPanel.add(usdAccountButton);
        createBankAccountPanel.add(createBackButton());
    }

    // Modifies: this
    // Effects: creates a back button which can be pressed to return to the previous interface
    private JButton createBackButton() {
        JButton buttonToAdd = createButton("Back");
        backButtons.add(buttonToAdd);
        return buttonToAdd;
    }

    // Modifies: this
    // Effects: creates the panel where user create new bank accounts;
    private void createNewBankAccountPanel() {
        createNewAccountPanel();
        frame.add(createBankAccountPanel, BorderLayout.CENTER);
        createBankAccountPanel.setVisible(false);
    }
}
