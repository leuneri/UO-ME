package persistence;

import model.RecordOfTransactions;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// inspired by https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriter {
    private PrintWriter writer;
    private final String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of RecordOfTransactions to file
    public void write(RecordOfTransactions r) {
//        JSONObject json = r.toJson();
        JSONObject json = r.toJson();
        saveToFile(json);
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(JSONObject json) {
        writer.print(json);
    }
}
