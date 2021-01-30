package ui.gui;

import model.UserAccountDatabase;

import javax.swing.*;
import java.awt.event.*;

// The abstract interface which includes two textboxes where users can input their name and password.
public abstract class LoginGUI extends GUI implements ActionListener {

    private int count = 0;
    private JLabel userLabel;
    private JLabel userPassword;
    protected JTextField userTextField;
    protected JPasswordField passTextField;
    protected JButton button;
    protected JLabel failure;

    // Effects: initializes the frame for LoginGUI
    LoginGUI(MainGUI mainGUI) {
        super(mainGUI);
        createUserField();
        createPassWordField();
        createLoginButton();
        createFailure();

        frame.setVisible(true);

    }


    // Effects: creates a failure button that will be modified when user inputs inproper inputs
    private void createFailure() {
        failure = new JLabel("");
        failure.setBounds(10,110,300,25);
        panel.add(failure);
    }


    // Modifies: this
    // Effects: creates a field where users can input their password.
    private void createPassWordField() {
        userPassword = new JLabel("Password");
        userPassword.setBounds(10,50,80,25);
        panel.add(userPassword);
        passTextField = new JPasswordField(15);
        passTextField.setBounds(100,50,65,25);
        panel.add(passTextField);
    }

    // Modifies: this
    // Effects: creates a field where users can input their username
    private void createUserField() {
        userLabel = new JLabel("Username");
        userLabel.setBounds(10,20, 80, 25);
        panel.add(userLabel);
        userTextField = new JTextField(15);
        userTextField.setBounds(100,20,65,25);
        panel.add(userTextField);
    }

    // Modifies: this
    // Effects: creates a button where users can use retrieve information from the text boxes
    private void createLoginButton() {
        button = createButton("Login");
        button.setBounds(10, 80, 80, 25);
        panel.add(button);
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);
}
