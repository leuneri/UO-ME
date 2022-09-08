package Persistence;

import model.RecordOfTransactions;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// inspired by https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNoLendersNoBorrowers() {
        try {
            RecordOfTransactions r = new RecordOfTransactions();
            JsonWriter writer = new JsonWriter("./data/testWriterNoLendersNoBorrowers.json");
            writer.open();
            writer.write(r);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoLendersNoBorrowers.json");
            r = reader.read();
            ArrayList<String> lenders = new ArrayList<>();
            ArrayList<String> borrowers = new ArrayList<>();
            assertEquals(lenders, r.lenderNames());
            assertEquals(borrowers, r.borrowerNames());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterOneLenderOneBorrower() {
        try {
            RecordOfTransactions r = new RecordOfTransactions();
            HashMap<String, Integer> lenders = new HashMap<>();
            lenders.put("Michael", -32);
            r.setLenders(lenders);
            HashMap<String, Integer> borrowers = new HashMap<>();
            borrowers.put("Joseph", 32);
            r.setBorrowers(borrowers);
            JsonWriter writer = new JsonWriter("./data/testWriterOneLenderOneBorrower.json");
            writer.open();
            writer.write(r);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterOneLenderOneBorrower.json");
            r = reader.read();
            ArrayList<String> lenderList = new ArrayList<>();
            ArrayList<String> borrowerList = new ArrayList<>();
            lenderList.add("Michael");
            borrowerList.add("Joseph");
            assertEquals(lenderList, r.lenderNames());
            assertEquals(borrowerList, r.borrowerNames());
            assertEquals(-32, r.owedOrLentAmount("Michael"));
            assertEquals(32, r.owedOrLentAmount("Joseph"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterOneLenderTwoBorrowers() {
        try {
            RecordOfTransactions r = new RecordOfTransactions();
            HashMap<String, Integer> lenders = new HashMap<>();
            lenders.put("Mike", -14);
            r.setLenders(lenders);
            HashMap<String, Integer> borrowers = new HashMap<>();
            borrowers.put("Josephine", 8);
            borrowers.put("Bob", 6);
            r.setBorrowers(borrowers);
            JsonWriter writer = new JsonWriter("./data/testWriterOneLenderTwoBorrowers.json");
            writer.open();
            writer.write(r);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterOneLenderTwoBorrowers.json");
            r = reader.read();
            ArrayList<String> lenderList = new ArrayList<>();
            ArrayList<String> borrowerList = new ArrayList<>();
            lenderList.add("Mike");
            borrowerList.add("Bob");
            borrowerList.add("Josephine");
            assertEquals(lenderList, r.lenderNames());
            assertEquals(borrowerList, r.borrowerNames());
            assertEquals(-14, r.owedOrLentAmount("Mike"));
            assertEquals(8, r.owedOrLentAmount("Josephine"));
            assertEquals(6, r.owedOrLentAmount("Bob"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterTwoLendersOneBorrower() {
        try {
            RecordOfTransactions r = new RecordOfTransactions();
            HashMap<String, Integer> lenders = new HashMap<>();
            lenders.put("Mike", -16);
            lenders.put("Bob", -2);
            r.setLenders(lenders);
            HashMap<String, Integer> borrowers = new HashMap<>();
            borrowers.put("Josephine", 18);
            r.setBorrowers(borrowers);
            JsonWriter writer = new JsonWriter("./data/testWriterTwoLendersOneBorrower.json");
            writer.open();
            writer.write(r);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTwoLendersOneBorrower.json");
            r = reader.read();
            ArrayList<String> lenderList = new ArrayList<>();
            ArrayList<String> borrowerList = new ArrayList<>();
            lenderList.add("Mike");
            lenderList.add("Bob");
            borrowerList.add("Josephine");
            assertEquals(lenderList, r.lenderNames());
            assertEquals(borrowerList, r.borrowerNames());
            assertEquals(-16, r.owedOrLentAmount("Mike"));
            assertEquals(-2, r.owedOrLentAmount("Bob"));
            assertEquals(18, r.owedOrLentAmount("Josephine"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
    @Test
    void testWriterTwoLendersTwoBorrowers() {
        try {
            RecordOfTransactions r = new RecordOfTransactions();
            HashMap<String, Integer> lenders = new HashMap<>();
            lenders.put("Aaron", -72);
            lenders.put("Ricky", -45);
            r.setLenders(lenders);
            HashMap<String, Integer> borrowers = new HashMap<>();
            borrowers.put("Kevin", 63);
            borrowers.put("Eric", 56);
            r.setBorrowers(borrowers);
            JsonWriter writer = new JsonWriter("./data/testWriterTwoLendersTwoBorrowers.json");
            writer.open();
            writer.write(r);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTwoLendersTwoBorrowers.json");
            r = reader.read();
            ArrayList<String> lenderList = new ArrayList<>();
            ArrayList<String> borrowerList = new ArrayList<>();
            lenderList.add("Aaron");
            lenderList.add("Ricky");
            borrowerList.add("Kevin");
            borrowerList.add("Eric");
            assertEquals(lenderList, r.lenderNames());
            assertEquals(borrowerList, r.borrowerNames());
            assertEquals(-72, r.owedOrLentAmount("Aaron"));
            assertEquals(-45, r.owedOrLentAmount("Ricky"));
            assertEquals(63, r.owedOrLentAmount("Kevin"));
            assertEquals(56, r.owedOrLentAmount("Eric"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}