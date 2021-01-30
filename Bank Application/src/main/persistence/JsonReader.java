package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// represents reader that reads a workroom from JSON data stored in data folder
public class JsonReader {
    private String source;

    // Effects constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //Effects: reads workroom from file and returns it;
    // throws IOException if there is an error reading data
    public WorkRoom read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject((jsonData));
        return parseWorkRoom(jsonObject);
    }

    // Effects read source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> stringBuilder.append(s));
        }

        return stringBuilder.toString();
    }

    // Effects: parses workroom from JSON object and returns it
    private WorkRoom parseWorkRoom(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        WorkRoom wr = new WorkRoom(name);
        addUserAccounts(wr, jsonObject);
        return wr;
    }

    // Modifiers: wr
    // Effects: parse UserAccounts from JSON object and add them to workroom
    private void addUserAccounts(WorkRoom wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("userAccounts");
        for (Object json : jsonArray) {
            JSONObject nextUserAccount = (JSONObject) json;
            addUserAccount(wr, nextUserAccount);
        }
    }

    // Modifies: wr
    // Effects: parse UserAccount from JSON object and adds it to workroom
    private void addUserAccount(WorkRoom wr, JSONObject jsonObject) {
        String name = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        UserAccount account = new UserAccount(name, password);
        JSONArray jsonArray = jsonObject.getJSONArray("accounts");
        for (Object json : jsonArray) {
            JSONObject nextBankAccount = (JSONObject) json;
            account.addAccount(addBankAccount(wr, nextBankAccount));
        }
        wr.addUserAccounts(account);
    }

    // returns a BankAccount given the JSONObject used to represent it
    private BankAccount addBankAccount(WorkRoom wr, JSONObject nextBankAccount) {
        String currency = nextBankAccount.getString("currency");
        Double balance = nextBankAccount.getDouble("balance");
        if (currency.equals("USD")) {
            return new UsdBankAccount(balance);
        } else {
            return new ChequingBankAccount(balance);
        }

    }


}
