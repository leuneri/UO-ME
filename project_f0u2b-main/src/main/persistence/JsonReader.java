package persistence;

import model.RecordOfTransactions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

// inspired by https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads RecordOfTransactions from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RecordOfTransactions read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRecordOfTransactions(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses records from JSON object and returns it
    private RecordOfTransactions parseRecordOfTransactions(JSONObject jsonObject) {
        RecordOfTransactions r = new RecordOfTransactions();
        HashMap<String, Integer> lenderHashMap = new HashMap<>();
        HashMap<String, Integer> borrowerHashMap = new HashMap<>();
        JSONArray lenderArray = jsonObject.getJSONArray("Lender");
        for (Object json : lenderArray) {
            JSONObject nextLender = (JSONObject) json;
            addLenderPerson(lenderHashMap, nextLender);
        }
        r.setLenders(lenderHashMap);
        JSONArray borrowerArray = jsonObject.getJSONArray("Borrower");
        for (Object json : borrowerArray) {
            JSONObject nextBorrower = (JSONObject) json;
            addBorrowerPerson(borrowerHashMap, nextBorrower);
        }
        r.setBorrowers(borrowerHashMap);
        return r;
    }

    // MODIFIES: r
    // EFFECTS: parses lenders from JSON object and adds them to records
    private void addLenderPerson(HashMap<String, Integer> lenderHashMap, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Integer amount = jsonObject.getInt("amount");
        lenderHashMap.put(name, amount);
    }

    // MODIFIES: r
    // EFFECTS: parses borrowers from JSON object and adds them to records
    private void addBorrowerPerson(HashMap<String, Integer> borrowerHashMap, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Integer amount = jsonObject.getInt("amount");
        borrowerHashMap.put(name, amount);
    }
}
