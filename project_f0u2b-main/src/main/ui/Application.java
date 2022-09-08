package ui;

import model.Borrower;
import model.Lender;
import model.RecordOfTransactions;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


// Input application for recording transactions
public class Application {
    private static final String JSON_STORE = "./data/Records.json";
    private Scanner personName;
    Lender lender;
    Borrower borrower;
    RecordOfTransactions records = new RecordOfTransactions(); // add lenders, users, everyone instead of user class
    private boolean stopper;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Runs the application and starts the operations
    public Application() {
        processOperations();
    }

    // REQUIRES: input between 0 and 6
    // EFFECTS: Processes user input (0 to 6)
    public void processOperations() {
        Scanner input = new Scanner(System.in);
        personName = new Scanner(System.in);
        stopper = true;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        printLoadCommands();
        int order1 = input.nextInt();
        loadOrNot(order1);

        while (stopper) {
            printAvailableCommands();
            int order2 = input.nextInt();
            commands(order2);
        }

        printSaveCommands();
        int order3 = input.nextInt();
        saveOrNot(order3);
        System.out.println("Thank you!");
    }

    private void loadOrNot(int order) {
        switch (order) {
            case 0:
                loadPeople();
                break;
            case 1:
                break;
        }
    }

    private void saveOrNot(int order) {
        switch (order) {
            case 0:
                savePeople();
                break;
            case 1:
                break;
        }
    }

    // EFFECTS: holds switch case for list of commands
    private void commands(int order) {
        switch (order) {
            case 0:
                createTransaction();
                break;
            case 1:
                nameOfLenders();
                break;
            case 2:
                nameOfBorrowers();
                break;
            case 3:
                personAmount();
                break;
            case 4:
                lendersAndBorrowers();
                break;
            case 5:
                clearingContents();
                break;
            case 6:
                stopper = false;
        }
    }

    // EFFECTS: represents operation when user inputs 1; creates a list of lender names
    private void nameOfLenders() {
        String lenderNames = records.lenderNames().toString();
        System.out.println(lenderNames);
    }

    // EFFECTS: represents operation when user inputs 2; creates a list of borrower names
    private void nameOfBorrowers() {
        String borrowerNames = records.borrowerNames().toString();
        System.out.println(borrowerNames);
    }

    // REQUIRES: inputted name is in the records
    // EFFECTS: represents operation when user inputs 3; input a name and get the amount of which they owe
    private void personAmount() {
        System.out.println("Please enter the name of the person");
        String name = personName.next(); // Enter name of person
        String amount = String.valueOf(records.owedOrLentAmount(name));
        System.out.println(amount);
    }

    // EFFECTS: represents operation when user inputs 4; shows a list of lenders' and borrowers' names and owed amount
    private void lendersAndBorrowers() {
        System.out.println("Lenders: " + records.show().get(0));
        System.out.println("Borrowers: " + records.show().get(1));
    }

    // EFFECTS: represents operation when user inputs 6; clears the lenders and borrowers in the records
    private void clearingContents() {
        records.clearContents();
        System.out.println("Contents are cleared!");
    }

    // EFFECTS: saves the people to file
    private void savePeople() {
        try {
            jsonWriter.open();
            jsonWriter.write(records);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads people from file
    private void loadPeople() {
        try {
            records = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void printLoadCommands() {
        System.out.println("Please enter the number associated with your request:");
        System.out.println("0 - Load information from previous record of transactions");
        System.out.println("1 - Skip");
    }

    private void printSaveCommands() {
        System.out.println("Please enter the number associated with your request:");
        System.out.println("0 - Save your transactions into the records");
        System.out.println("1 - Skip");
    }

    // EFFECTS: prints out operation commands
    private void printAvailableCommands() {
        System.out.println("Please enter the number associated with your request:");
        System.out.println("0 - Add a transaction");
        System.out.println("1 - Get a list of the lenders");
        System.out.println("2 - Get a list of the borrowers");
        System.out.println("3 - Get the amount borrowed by a person (negative amount indicates amount lent)");
        System.out.println("4 - Show everyone categorized by lender or borrower by their final amount");
        System.out.println("5 - Clear all transactions");
        System.out.println("6 - Exit program");
    }

    // EFFECTS: Inputted names of lender, borrower, and amount lent will be added to the records and simplified such
    // that each person has only one number associated with it. Enter -1 at the beginning of any transaction to quit
    // creating transactions
    private void createTransaction() {
        String lenderName;
        String borrowerName;
        int owedAmount;

        while (true) {
            System.out.println("Please input the full name of the lender or enter -1 to stop adding transactions");
            Scanner creator = new Scanner(System.in);
            lenderName = creator.next();
            if (lenderName.equals("-1")) {
                break;
            }
            System.out.println("Please input the full name of the borrower");
            borrowerName =  creator.next();
            System.out.println("Please input the amount which the borrower owes in cents");
            owedAmount =  creator.nextInt();
            lender = new Lender(lenderName, owedAmount * -1);
            borrower = new Borrower(borrowerName, owedAmount);
            records.calculateFinalTransactions(lender, borrower);
        }
    }
}

