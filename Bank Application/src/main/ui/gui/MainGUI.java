package ui.gui;

import model.UserAccountDatabase;

import java.util.HashMap;
import java.util.Map;

// Contains all the user interface frames to switch between
public class MainGUI {
    protected Map<GuiType, GUI> interfaceMap;
    protected SaveAndLoadGUI saveAndLoadGUI;
    protected MainInterfaceUI mainInterfaceUI;
    protected UserAccountDatabase userAccountDatabase;
    protected SaveAndLoad saveAndLoad;

    public MainGUI() {
        interfaceMap = new HashMap<>();
        saveAndLoadGUI = new SaveAndLoadGUI(this);
        saveAndLoad = saveAndLoadGUI.saveAndLoad;
        this.userAccountDatabase = saveAndLoad.getUserAccountDatabase();
        mainInterfaceUI = new MainInterfaceUI(this);

    }

    //Modifies: this
    // Effects: adds the GUI to a interface used to navigate between different interfaces
    public void addNewGUI(GuiType key, GUI userInterface) {
        if (!interfaceMap.containsKey(key) && userInterface != interfaceMap.get(key)) {
            interfaceMap.put(key, userInterface);
        } else {
            interfaceMap.replace(key, interfaceMap.get(key), userInterface);
        }
    }

    // Effects: accesses the user interface of the given GuiType
    public void accessGui(GuiType guiType) {
        interfaceMap.get(guiType).frame.setVisible(true);
    }



}
