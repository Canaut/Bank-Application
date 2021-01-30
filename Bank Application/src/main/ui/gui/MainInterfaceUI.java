package ui.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// main interface where user can create new account or access new account
public class MainInterfaceUI extends GUI implements ActionListener {

    private JButton newAccountButton;
    private JButton accessAccountButton;
    private JButton saveAndLoadButton;

    // Creates the main interface which allow users to create new account, access old account, and save and load.
    public MainInterfaceUI(MainGUI mainGUI) {
        super(mainGUI);
        addButtons();
        frame.setVisible(true);
    }

    // Effects: creates and adds buttons for user to make account, access old account, and save and load.
    private void addButtons() {
        newAccountButton = createButton("New Account");
        accessAccountButton = createButton("Access Existing Account");
        saveAndLoadButton = createButton("Save and Load");
        panel.add(newAccountButton);
        panel.add(accessAccountButton);
        panel.add(saveAndLoadButton);
    }

    // Effects: corresponding actions for the buttons pressed. If newAccountButton is pressed, user enters the
    //          interface to create new account, if accessAccountButton is pressed, user is prompted to enter login info
    //          if saveAndLoadButton is entered, user is prompted to save/load progress
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (newAccountButton.equals(source)) {
            createAccountUI();
        } else if (accessAccountButton.equals(source)) {
            accessExistingAccountUI();
        } else if (saveAndLoadButton.equals(source)) {
            returnToSaveLoadInterface();
        }
    }

    //Effects: navigates user to create account interface
    private void createAccountUI() {
        frame.setVisible(false);
        mainGUI.addNewGUI(GuiType.CREATEACCOUNT, new CreateAccountGUI(mainGUI));
    }

    //Effects: navigates user to access existing account interface
    private void accessExistingAccountUI() {
        frame.setVisible(false);
        mainGUI.addNewGUI(GuiType.ACCESSACCOUNT, new AccessExistingAccountGUI(mainGUI));
    }
}
