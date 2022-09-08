package ui;

import model.Borrower;
import model.EventLog;
import model.Event;
import model.Lender;
import model.RecordOfTransactions;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class GUI implements ActionListener {
    private final JLabel label;
    private JLabel amountLabel;
    private final JFrame frame;
    private JFrame addFrame;
    private JFrame amountFrame;
    private final JPanel panel;
    private final JButton button0;
    private final JButton button1;
    private final JButton button2;
    private final JButton button3;
    private final JButton button4;
    private final JButton button5;
    private final JButton button6;
    private JTextField lenderText;
    private JTextField borrowerText;
    private JTextField amountText;
    private JTextField amountInput;
    private static final String JSON_STORE = "./data/Records.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private RecordOfTransactions records;
    private static final int BAR_WIDTH = 50;
    private static final int INCREMENT = 2;

    // Creates the GUI in the application
    public GUI() {
        frame = new JFrame();
        records = new RecordOfTransactions();
        button0 = new JButton("Add a transaction");
        button1 = new JButton("Get a list of the lenders names");
        button2 = new JButton("Get a list of the borrowers names");
        button3 = new JButton("Get the amount borrowed by a person (negative amount indicates amount lent)");
        button4 = new JButton("Show everyone categorized by lender or borrower by their final amount");
        button5 = new JButton("Clear all transactions");
        button6 = new JButton("Exit program");
        label = new JLabel("Click on one of the menu options!");
        panel = new JPanel();
        startLoad();
    }

    // MODIFIES: this
    // EFFECTS: gives the option to load from JSON files before starting main menu
    private void startLoad() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        int value;
        value = JOptionPane.showConfirmDialog(null,
                "Would you like to load the data from the previously saved transactions?",
                "Load data", JOptionPane.YES_NO_OPTION);
        if (value == JOptionPane.NO_OPTION) {
            mainMenu();
        } else if (value == JOptionPane.YES_NO_OPTION) {
            loadPeople();
            mainMenu();
        } else {
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads people from file
    private void loadPeople() {
        try {
            records = jsonReader.read();
            JOptionPane.showMessageDialog(null,
                    "The information from previous transactions are loaded!",
                    "Loaded!", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "The information from this file cannot be loaded, change the file source!",
                    "Not loaded!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // EFFECTS: creates main menu with different buttons each with their own abilities
    private void mainMenu() {
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(label);
        panel.add(button0);
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);
        panel.add(button6);
        addAction();
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Welcome to the transaction recorder!");
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: assigns ability to each button from main menu
    public void addAction() {
        button0.addActionListener(e -> addTransaction());
        button1.addActionListener(e -> lenderList());
        button2.addActionListener(e -> borrowerList());
        button3.addActionListener(e -> amountFromPerson());
        button4.addActionListener(e -> showEveryone());
        button5.addActionListener(e -> clearContents());
        button6.addActionListener(e -> saveContent());
    }

    // MODIFIES: this
    // EFFECTS: creates a box that allows for inputs that represents a transaction to be recorded
    private void addTransaction() {
        addFrame = new JFrame("Add a transaction");
        JLabel lenderLabel = new JLabel("Add the full name of the lender");
        JLabel borrowerLabel = new JLabel("Add the full name of the borrower");
        amountLabel = new JLabel("Add the amount borrowed from the lender");
        lenderText = new JTextField(20);
        borrowerText = new JTextField(20);
        amountText = new JTextField(20);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        JButton addButton = new JButton("Submit Transaction");
        JPanel addPanel = new JPanel();
        addPanel.add(lenderLabel);
        addPanel.add(lenderText);
        addPanel.add(borrowerLabel);
        addPanel.add(borrowerText);
        addPanel.add(amountLabel);
        addPanel.add(amountText);
        addPanel.add(addButton);
        addFrame.add(addPanel);
        addFrame.setSize(300, 300);
        addFrame.setVisible(true);
        addButton.addActionListener(this);
    }

    // EFFECTS: shows a list of lender names or sends a signal to add transactions if empty
    private void lenderList() {
        String lenderString = String.join(", ", records.lenderNames());
        if (lenderString.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Add some lenders!",
                    "The lenders", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, lenderString,
                    "The lenders", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // EFFECTS: shows a list of borrower names or sends a signal to add transactions if empty
    private void borrowerList() {
        String borrowerString = String.join(", ", records.borrowerNames());
        if (borrowerString.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Add some borrowers!",
                    "The borrowers", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, borrowerString,
                    "The borrowers", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // EFFECTS: shows a balance of the inputted name or sends a signal to add transactions if empty
    private void amountFromPerson() {
        JButton back = new JButton("Back");
        back.addActionListener(this);
        if (records.lenderNames().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Please add some transactions first!",
                    "Add transactions", JOptionPane.INFORMATION_MESSAGE);
        } else {
            amountFrame = new JFrame("Person Balance");
            amountInput = new JTextField(20);
            amountLabel = new JLabel("Who do you want to know the balance of?");
            JPanel amountPanel = new JPanel();
            JButton amountButton = new JButton("Submit");
            amountButton.addActionListener(this);
            amountPanel.add(amountLabel);
            amountPanel.add(amountInput);
            amountPanel.add(amountButton);
            amountPanel.add(back);
            amountFrame.add(amountPanel);
            amountFrame.setSize(300, 300);
            amountFrame.setVisible(true);
        }
    }

    // EFFECTS: creates and shows a histogram in ascending order by balance of the people in the transactions
    private void showEveryone() {
        LinkedHashMap<String, Integer> sorted;
        sorted = getSortedByNumbs();
        if (sorted.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Please add some transactions first!",
                    "Add transactions", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int extraArea = 2;
            int bottomArea = extraArea / 2;
            int topArea = extraArea / 2;
            int maxSize = maxSize(sorted) * INCREMENT;
            int minSize = (minSize(sorted) * -1) * INCREMENT;
            int height = maxSize + minSize;
            int width = (sorted.size() * BAR_WIDTH) + 25;
            Histogram histogram = new Histogram(width, sorted, height, minSize, topArea, bottomArea);
            JFrame everybodyFrame = new JFrame();
            everybodyFrame.setSize(width, height);
            everybodyFrame.add(histogram);
            everybodyFrame.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: sorts the people in the transactions by ascending order of their balances
    private LinkedHashMap<String, Integer> getSortedByNumbs() {
        ArrayList<String> lenderNames = records.lenderNames();
        ArrayList<String> borrowerNames = records.borrowerNames();
        ArrayList<String> allList = new ArrayList<>();
        LinkedHashMap<String, Integer> sorted = new LinkedHashMap<>();
        allList.addAll(lenderNames);
        allList.addAll(borrowerNames);
        HashMap<String, Integer> allTransactions = new HashMap<>();
        for (String person : allList) {
            allTransactions.put(person, records.owedOrLentAmount(person));
        }
        ArrayList<Integer> numbs = new ArrayList<>();
        for (HashMap.Entry<String, Integer> entry : allTransactions.entrySet()) {
            numbs.add(entry.getValue());
        }
        Collections.sort(numbs);
        for (int num : numbs) {
            for (Map.Entry<String, Integer> entry : allTransactions.entrySet()) {
                if (entry.getValue().equals(num)) {
                    sorted.put(entry.getKey(), num);
                }
            }
        }
        return sorted;
    }

    // EFFECTS: outputs the highest balance within the group of people in transactions
    private int maxSize(HashMap<String, Integer> m) {
        int max = 0;
        for (Integer num : m.values()) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    // EFFECTS outputs the lowest balance within the group of people in transactions
    private int minSize(HashMap<String, Integer> m) {
        int min = 0;
        for (Integer num : m.values()) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }

    // MODIFIES: this
    // EFFECTS: clears the transactions recorded and outputs a box that indicates that
    private void clearContents() {
        records.clearContents();
        JOptionPane.showMessageDialog(null,
                "Contents Cleared!",
                "Clearing Contents", JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: creates a box with the option of saving the information and saves the information if asked to
    private void saveContent() {
        int value;
        printEvents(EventLog.getInstance());
        value = JOptionPane.showConfirmDialog(null,
                "Would you like to save the data from the new transactions?",
                "Save data", JOptionPane.YES_NO_OPTION);
        if (value == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null,
                    "Thank you for using UO ME!",
                    "Thanks!", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else if (value == JOptionPane.YES_NO_OPTION) {
            savePeople();
            JOptionPane.showMessageDialog(null,
                    "Thank you for using UO ME!",
                    "Thanks!", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Thank you for using UO ME!",
                    "Thanks!", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    // EFFECTS: saves the people to file
    private void savePeople() {
        try {
            jsonWriter.open();
            jsonWriter.write(records);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null,
                    "Saved to " + JSON_STORE + "!",
                    "Saved Information", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to save data to " + JSON_STORE,
                    "Unable to save :(", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // EFFECTS: uses the method based on the button clicked on
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Submit Transaction")) {
            submitTransaction();
        } else if (e.getActionCommand().equals("Submit")) {
            submitForAmount();
        } else if (e.getActionCommand().equals("Back")) {
            dispose();
        }
    }

    // EFFECTS: dispose frame
    private void dispose() {
        amountFrame.dispose();
    }

    // EFFECTS: submits input to show balance of person
    private void submitForAmount() {
        String name = amountInput.getText();
        int amount = records.owedOrLentAmount(name);
        JOptionPane.showMessageDialog(null,
                name + " has a balance of " + amount,
                "Person balance", JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: submits the transaction to be calculated and be recorded
    private void submitTransaction() {
        String lenderName = lenderText.getText();
        int lenderAmount = Integer.parseInt(amountText.getText()) * -1;
        String borrowerName = borrowerText.getText();
        int borrowerAmount = Integer.parseInt(amountText.getText());
        Lender lender = new Lender(lenderName, lenderAmount);
        Borrower borrower = new Borrower(borrowerName, borrowerAmount);
        records.calculateFinalTransactions(lender, borrower);
        addFrame.dispose();
    }

    private void printEvents(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }

}
