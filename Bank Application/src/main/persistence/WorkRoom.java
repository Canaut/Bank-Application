package persistence;

import model.UserAccount;
import model.UserAccountDatabase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

public class WorkRoom {
    private String name;
    private UserAccountDatabase userAccountDatabase;

    //Effects: constructs workroom with a name and a new UserAccountDatabase
    public WorkRoom(String name) {
        this.name = name;
        userAccountDatabase = new UserAccountDatabase();
    }

    public String getName() {
        return this.name;
    }

    // Modifies this
    // Sets userAccountDatabase
    public void setUserAccountDatabase(UserAccountDatabase u) {
        userAccountDatabase = u;
    }

    // Modifies this
    // Effects: add a UserAccount to the workroom
    public void addUserAccounts(UserAccount userAccount) {
        userAccountDatabase.addBankAccount(userAccount);
    }

    //Effects: returns a unmodifiable list of of user accounts in this workroom
    public List<UserAccount> getUserAccounts() {
        return Collections.unmodifiableList(userAccountDatabase.userAccounts);
    }

    //Effects: return number of userAccounts in the userAccountDatabase
    public int numUserAccounts() {
        return userAccountDatabase.userAccounts.size();
    }

    // returns JSON in this workroom (specifically, the JSONObject representation
    // of a UserAccountDatabase
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("userAccounts", userAccountsToJson());
        return json;
    }

    //Effects: returns things in this workroom as a JSON array,
    // specifically in this case, creating the JSON array for the array of
    // UserAccounts.
    private JSONArray userAccountsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (UserAccount u : userAccountDatabase.userAccounts) {
            jsonArray.put(u.toJson());
        }
        return jsonArray;
    }


    public UserAccountDatabase getAccountDatabase() {
        return userAccountDatabase;
    }
}
