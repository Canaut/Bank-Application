package ui.gui;

import model.UserAccountDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class GUI implements ActionListener {
    protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    protected JFrame frame;
    protected JPanel panel;
    protected MainGUI mainGUI;

    GUI(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        this.frame = createFrame("Banking Application");
        this.panel = createPanel();
        frame.add(panel, BorderLayout.CENTER);
        createPanel();
    }

    //Effects: returns a new JPanel object;
    protected JPanel createPanel() {
        JPanel panel = new JPanel();
        return panel;

    }

    // Modifies: this, frame
    // Effects: creates a frame
    protected JFrame createFrame(String name) {
        JFrame frame = new JFrame();
        frame.setSize(200,200);
        frame.setBounds((screenSize.width / 2) - frame.getWidth() / 2,
                (screenSize.height / 2) - frame.getHeight() / 2,
                frame.getWidth(),
                frame.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(name);
        return frame;
    }

    // Effects: returns to the main interface
    protected void returnToMainInterface() {
        frame.setVisible(false);
        mainGUI.mainInterfaceUI.frame.setVisible(true);
    }

    // Effects: returns to the save and load interface
    protected void returnToSaveLoadInterface() {
        frame.setVisible(false);
        mainGUI.accessGui(GuiType.SAVEANDLOADINTERFACE);
    }

    // Effects: creates a button with an action listener
    protected JButton createButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(this);

        return button;
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);
}
