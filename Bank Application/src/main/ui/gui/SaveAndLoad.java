package ui.gui;

import model.ChequingBankAccount;
import model.UserAccount;
import model.UserAccountDatabase;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.WorkRoom;

import java.io.FileNotFoundException;
import java.io.IOException;

// Class that handles saving and loading of UserAccountInterfaces
public class SaveAndLoad {
    private static final String SAVE_LOCATION = "./data/bankingdatabase.json";
    protected UserAccountDatabase userAccountDatabase;
    private JsonWriter writer;
    private JsonReader reader;
    private WorkRoom workRoom;

    // Initializes the tools needed to save and load Bank Database
    public SaveAndLoad() {
        userAccountDatabase = new UserAccountDatabase();
        workRoom = new WorkRoom("Banking Database");

        writer = new JsonWriter(SAVE_LOCATION);
        reader = new JsonReader(SAVE_LOCATION);
    }

    // Modifies: this
    // Effects: save current user information into a file that can be loaded
    public void saveToDatabase() {
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
    public void loadFromDatabase() {
        try {
            workRoom = reader.read();
            userAccountDatabase = workRoom.getAccountDatabase();
            System.out.println("Loaded " + workRoom.getName() + " from " + SAVE_LOCATION);
        } catch (IOException e) {
            System.out.println("Unable to read from: " + SAVE_LOCATION);
        }
    }

    public UserAccountDatabase getUserAccountDatabase() {
        return userAccountDatabase;
    }


}
