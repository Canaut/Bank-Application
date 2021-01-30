package ui.gui;

import model.UserAccountDatabase;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// User interface for saving and loading bank database
public class SaveAndLoadGUI extends GUI implements ActionListener {

    private JButton saveButton;
    private JButton loadButton;
    private JPanel saveAndLoadPanel;
    protected SaveAndLoad saveAndLoad;
    protected UserAccountDatabase userAccountDatabase;

    // Effects: Initialing tools needed to save and load userAccountDatabase
    SaveAndLoadGUI(MainGUI mainGUI) {
        super(mainGUI);
        userAccountDatabase = new UserAccountDatabase();
        saveAndLoad = new SaveAndLoad();
        createSaveAndLoadPanel();
        mainGUI.addNewGUI(GuiType.SAVEANDLOADINTERFACE, this);

    }

    // Modifies: this
    // Effects Creates the panel which where user can access the save and load functions
    private void createSaveAndLoadPanel() {
        saveAndLoadPanel = createPanel();
        createSaveAndLoadButton();
        frame.add(saveAndLoadPanel, BorderLayout.CENTER);
    }

    // Modifies: this
    // Effects: creates the buttons where users press to save and load bank databases
    private void createSaveAndLoadButton() {
        saveButton = createButton("Save");
        loadButton = createButton("Load");
        saveAndLoadPanel.add(saveButton);
        saveAndLoadPanel.add(loadButton);
    }

    // Modifies: this;
    // Effects: If save button is pressed, BankDatabase is saved, if load button is pressed, BankDatabase info is loaded
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (saveButton.equals(source)) {
            saveAndLoad.saveToDatabase();
            playSound("./data/success.wav");
            JOptionPane.showMessageDialog(null, "Saved Successfully");
            returnToMainInterface();
        } else if (loadButton.equals(source)) {
            saveAndLoad.loadFromDatabase();
            userAccountDatabase = saveAndLoad.getUserAccountDatabase();
            mainGUI.userAccountDatabase = userAccountDatabase;
            playSound("./data/success.wav");
            JOptionPane.showMessageDialog(null, "Loaded Successfully");
            returnToMainInterface();
        }

    }

    // Effects: plays the sound file in file path, if no sound exist, throw exceptions
    //          (UnsupportedAudioFileException, IOException, LineUnavailableException)
    public void playSound(String soundName) {
        try {
            AudioInputStream audio;
            audio = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error Playing Sound");
        }
    }
}
