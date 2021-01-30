package persistence;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONObject;

import java.io.*;

// represents a writer that writes JSON representation of workroom to file
public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    //Effects: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //Modifies: this
    //Effects: opens writer; throws FileNotFoundException if destination file cannot be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //Modifies: this
    //Effects: writes JSON representation of workroom to file
    public void write(WorkRoom wr) {
        JSONObject json = wr.toJson();
        saveToFile(json.toString(TAB));
    }

    //Modifies: this
    //Effects: closes writer
    public void close() {
        writer.close();
    }

    // Modifies: this
    // Effects: writes string to file;
    private void saveToFile(String json) {
        writer.print(json);
    }


}
