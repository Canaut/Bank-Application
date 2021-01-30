package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.WorkRoom;
import ui.gui.MainGUI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


//Banking application
public class BankAppUI {

    // Effects: run the banking application
    public BankAppUI() {
        runBankApp();
    }

    private void runBankApp() {
        new MainGUI();
    }





}