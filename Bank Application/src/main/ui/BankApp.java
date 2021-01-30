package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.WorkRoom;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


//Banking application
public class BankApp {
    private static final String SAVE_LOCATION = "./data/bankingdatabase.json";
    private UserAccountDatabase userAccountDatabase = new UserAccountDatabase();
    private JsonWriter writer;
    private JsonReader reader;
    private Scanner input;
    private UserAccount currentAccount;
    private BankAccount currentBankAccount;
    private WorkRoom workRoom;

    // Effects: run the banking application
    public BankApp() {
        workRoom = new WorkRoom("Banking Database");
        writer = new JsonWriter(SAVE_LOCATION);
        reader = new JsonReader(SAVE_LOCATION);
        runBankApp();
    }

    //Modifies: this
    //Effects:process user input
    private void runBankApp() {
        UserAccount testUser = new UserAccount("johnny", "johnny");
        ChequingBankAccount testChk = new ChequingBankAccount();
        testUser.addAccount(testChk);
        userAccountDatabase.addBankAccount(testUser);

        boolean keepAppRunning = true;
        String command;

        input = new Scanner(System.in);

        while (keepAppRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepAppRunning = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("Thank you for using the Banking System");
    }

    // modifies: this
    // Effect: process user command
    private void processCommand(String command) {
        if (command.equals("n")) {
            newAccount();
        } else if (command.equals("e")) {
            verifyExistingMemberAccount();
        } else if (command.equals("s")) {
            saveToDatabase();
        } else if (command.equals("l")) {
            loadFromDatabase();
        } else {
            System.out.println("Input Not Valid, Try Again");
        }
    }

    // Modifies: this
    // Effects: save current user information into a file that can be loaded
    private void saveToDatabase() {
        try {
            workRoom.setUserAccountDatabase(userAccountDatabase);
            writer.open();
            writer.write(workRoom);
            writer.close();
            System.out.println("Saved " + workRoom.getName() + " to " + SAVE_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to: " + SAVE_LOCATION);
        }
    }

    // Modifies: this
    // Effects: loads user information from banking file
    private void loadFromDatabase() {
        try {
            workRoom = reader.read();
            userAccountDatabase = workRoom.getAccountDatabase();
            System.out.println("Loaded " + workRoom.getName() + " from " + SAVE_LOCATION);
        } catch (IOException e) {
            System.out.println("Unable to read from: " + SAVE_LOCATION);
        }
    }


    // Effects: display main menu to user
    private void displayMenu() {
        System.out.println("\nYou are at the main page");
        System.out.println("\nSelect from:");
        System.out.println("\tn -> new account");
        System.out.println("\te -> existing member");
        System.out.println("\ts -> save banking database");
        System.out.println("\tl -> load banking database");
        System.out.println("\tq -> quit");
    }


    //Modifies: this
    //Effects: determine if a user information is valid to access user account.
    private void verifyExistingMemberAccount() {
        System.out.println("Enter Username");
        String userName = getInput();
        System.out.println("Enter Password");
        String password = getInput();
        boolean correctCredentials = userAccountDatabase.correctUserAccount(userName, password);
        if (correctCredentials) {
            currentAccount = userAccountDatabase.getUserAccounts(userName,password);
            System.out.println("account verified.");
            perfromUserActions(currentAccount);
        } else {
            System.out.println("Incorrect login credentials, try again.");
        }
    }

    // Modifies: this
    // Effects: presents navigation menu for user to select what tasks they would like to perform
    private void perfromUserActions(UserAccount user) {
        displayExistingUserMenu(user);
        boolean continueUserAction = true;

        while (continueUserAction) {
            String command = input.next();
            if (command.equals("q")) {
                continueUserAction = false;
            } else {
                processMemberCommand(command);
                displayExistingUserMenu(user);
            }
        }
    }

    // modifies: this
    // Effect: process user command
    private void processMemberCommand(String command) {
        if (command.equals("n")) {
            // create new bank account
            createNewBankAccount();
        } else if (command.equals("e")) {
            // access current accounts
            accessUserAccounts();
        } else {
            System.out.println("Incorrect input, try again.");
        }
    }

    //Requires: user input is either the account number or q
    //modifies: q
    //Effects: Access user account functions
    private void accessUserAccounts() {
        displayAccounts();
        boolean continueUserAction = true;

        while (continueUserAction) {
            String command = input.next();
            System.out.println(command);
            if (command.equals("q")) {
                continueUserAction = false;
            } else {
                accessUserBankAccount(command);
                displayAccounts();
            }
        }
    }

    // Requires: user input is an index for the account number shown by the displayAccounts method
    // Modifies: this
    // Effects: allows user to access their selected bank accounts select a function to perform
    private void accessUserBankAccount(String command) {
        int index = Integer.parseInt(command);
        currentBankAccount = currentAccount.userBankAccounts.get(index);
        displayBankAccountFunctions();
        boolean continueUserAction = true;

        while (continueUserAction) {
            String newCommand = input.next();
            System.out.println(newCommand);
            if (newCommand.equals("q")) {
                continueUserAction = false;
            } else {
                processUserBankAccountCommands(newCommand);
                displayBankAccountFunctions();
            }
        }

    }

    // Modifies: this
    // Effects: process user commands
    private void processUserBankAccountCommands(String newCommand) {
        if (newCommand.equals("d")) {
            depositMoney();
        } else if (newCommand.equals("w")) {
            withdrawMoney();
        } else if (newCommand.equals("t")) {
            displayTransactionHistory();
        }
            
            
    }

    // Effects: displays transaction history for the current bank account
    private void displayTransactionHistory() {
        System.out.println("\nHere is your transaction history");
        System.out.println("\nCurrency    Transaction Type          Transaction Amount");
        for (Transaction next: currentBankAccount.transactionHistory) {
            System.out.println("   " + next.currencyType + "         " + next.transactionType
                    + "                     " + next.transactionAmount);
        }

    }

    //Modifies: this
    //Effects: withdraw a user's input amount from the current bank account, ensuring they have enough balance
    // and >0 is the input
    private void withdrawMoney() {
        System.out.println("Enter amount to withdraw:");
        double d;
        do {
            d = input.nextDouble();
        } while (d < 0);
        if (currentBankAccount.checkBalance() < d) {
            System.out.println("\n Insufficient Funds");
        } else {
            currentBankAccount.withdraw(d);
            System.out.println("\n You have withdrawn $" + d);
            System.out.println("\n You current balance is $" + currentBankAccount.checkBalance());
        }

    }

    // Modifies: this
    // Effects: ensures that user input is correct (>0$) and deposits that amount the the account;
    private void depositMoney() {
        System.out.println("Enter amount to deposit:");
        double d;
        do {
            d = input.nextDouble();
        } while (d < 0);
        if (d < 0) {
            System.out.println("\n Cannot deposit negative amount of money");
        } else {
            currentBankAccount.deposit(d);
            System.out.println("\n You have deposited $" + d);
            System.out.println("\n You current balance is $" + currentBankAccount.checkBalance());
        }
    }

    // Effects: display function that users can do with their current bank account
    private void displayBankAccountFunctions() {
        System.out.println("\nYou are at the " + currentBankAccount.currency + " account page.");
        System.out.println("\nSelect one of the following:");
        System.out.println("\td -> deposit");
        System.out.println("\tw -> withdraw");
        System.out.println("\tt -> transaction history");
        System.out.println("\tq -> back");
    }


    // Modifies: this
    // Effects: process user input and determine what type of account user would like to make (between chequing or USD)
    private void addNewBankAccount(String command) {
        BankAccount accountToAdd;
        if (command.equals("CHK")) {
            accountToAdd = new ChequingBankAccount();
            currentAccount.addAccount(accountToAdd);
            System.out.println(command + " account added.");
        } else if (command.equals("USD")) {
            accountToAdd = new UsdBankAccount();
            currentAccount.addAccount(accountToAdd);
            System.out.println(command + " account added.");
        }
    }

    // Display current user accounts
    private void displayAccounts() {
        System.out.println("\nSelect from the following accounts:");
        System.out.println("\n acc#    currency    balance");
        for (int i = 0; i < currentAccount.userBankAccounts.size(); i++) {
            System.out.println("   " + i + "       " + currentAccount.userBankAccounts.get(i).currency
                    + "           " + currentAccount.userBankAccounts.get(i).checkBalance());
        }
        System.out.println("\n q to go back");
    }

    // Modifies: this
    // Effects: creates new account for user
    private void createNewBankAccount() {
        displayCreateNewBankAccount();
        boolean continueUserAction = true;

        while (continueUserAction) {
            String command = input.next();
            if (command.equals("q")) {
                continueUserAction = false;
            } else {
                addNewBankAccount(command);
                displayCreateNewBankAccount();
            }
        }
    }

    // Effects: displays the new accounts that can be made currently
    private void displayCreateNewBankAccount() {
        System.out.println("\nYou are at the add a new bank account page");
        System.out.println("\nSelect one of the following:");
        System.out.println("\tCHK -> new chequing account");
        System.out.println("\tUSD -> new USD account");
        System.out.println("\tq -> back");

    }

    // Effects: display existing user menu to user
    private void displayExistingUserMenu(UserAccount user) {
        System.out.println("\n Hello " + currentAccount.userName + ", you are the the main user page");
        System.out.println("\nSelect one of the following options:");
        System.out.println("\tn -> new bank account");
        System.out.println("\te -> access existing accounts");
        System.out.println("\tq -> back");
    }

    // Modifies: this
    // Effects: when user inputs valid username and password, a new UserAccounts will be created for the user allowing
    // users to create bank accounts.
    private void newAccount() {
        System.out.println("Enter Username");
        String userName = getInput();
        System.out.println("Enter Password");
        String password = getInput();
        UserAccount toAdd = new UserAccount(userName, password);
        if (userAccountDatabase.addBankAccount(toAdd)) {
            System.out.println("Your account has been created, please log in to access your account.");
        } else {
            System.out.println("Your have entered an invalid username, try again.");
        }
    }

    //Effects: gets a valid input from the user which is a string that are 6-10 characters longs
    private String getInput() {
        String userName;
        while (true) {
            System.out.println("Enter between 6 and 10 Characters");
            userName = input.next();
            if (userName.length() >= 6 && userName.length() <= 10) {
                System.out.println("Your entered " + userName);
                return userName;
            }
            System.out.println("Incorrect input, try again.");
        }
    }
}
