package Persistence;

import model.RecordOfTransactions;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// inspired by https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderNoLendersNoBorrowers.json");
        try {
            RecordOfTransactions r = reader.read();
            ArrayList<String> lenders = new ArrayList<>();
            ArrayList<String> borrowers = new ArrayList<>();
            assertEquals(lenders, r.lenderNames());
            assertEquals(borrowers, r.borrowerNames());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderOneLenderOneBorrower() {
        JsonReader reader = new JsonReader("./data/testReaderOneLenderOneBorrower.json");
        try {
            RecordOfTransactions r = reader.read();
            ArrayList<String> lenders = new ArrayList<>();
            ArrayList<String> borrowers = new ArrayList<>();
            lenders.add("Jack");
            borrowers.add("Eric");
            assertEquals(lenders, r.lenderNames());
            assertEquals(borrowers, r.borrowerNames());
            assertEquals(-30, r.owedOrLentAmount("Jack"));
            assertEquals(30, r.owedOrLentAmount("Eric"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderOneLenderTwoBorrower() {
        JsonReader reader = new JsonReader("./data/testReaderOneLenderTwoBorrowers.json");
        try {
            RecordOfTransactions r = reader.read();
            ArrayList<String> lenders = new ArrayList<>();
            ArrayList<String> borrowers = new ArrayList<>();
            lenders.add("Jack");
            borrowers.add("Johnson");
            borrowers.add("John");
            assertEquals(lenders, r.lenderNames());
            assertEquals(borrowers, r.borrowerNames());
            assertEquals(-70, r.owedOrLentAmount("Jack"));
            assertEquals(40, r.owedOrLentAmount("John"));
            assertEquals(30, r.owedOrLentAmount("Johnson"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderTwoLendersOneBorrower() {
        JsonReader reader = new JsonReader("./data/testReaderTwoLendersOneBorrower.json");
        try {
            RecordOfTransactions r = reader.read();
            ArrayList<String> lenders = new ArrayList<>();
            ArrayList<String> borrowers = new ArrayList<>();
            borrowers.add("Ricky");
            lenders.add("Aaron");
            lenders.add("Kevin");
            assertEquals(lenders, r.lenderNames());
            assertEquals(borrowers, r.borrowerNames());
            assertEquals(40, r.owedOrLentAmount("Ricky"));
            assertEquals(-30, r.owedOrLentAmount("Aaron"));
            assertEquals(-10, r.owedOrLentAmount("Kevin"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderTwoLendersTwoBorrowers() {
        JsonReader reader = new JsonReader("./data/testReaderTwoLendersTwoBorrowers.json");
        try {
            RecordOfTransactions r = reader.read();
            ArrayList<String> lenders = new ArrayList<>();
            ArrayList<String> borrowers = new ArrayList<>();
            borrowers.add("Bill");
            borrowers.add("Josh");
            lenders.add("William");
            lenders.add("Andy");
            assertEquals(lenders, r.lenderNames());
            assertEquals(borrowers, r.borrowerNames());
            assertEquals(25, r.owedOrLentAmount("Bill"));
            assertEquals(24, r.owedOrLentAmount("Josh"));
            assertEquals(-32, r.owedOrLentAmount("Andy"));
            assertEquals(-17, r.owedOrLentAmount("William"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
