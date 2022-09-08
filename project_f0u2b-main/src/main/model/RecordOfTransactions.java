package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Keeps records of transactions and determines who is a lender or borrower based on the amount they owe
public class RecordOfTransactions implements Writable {
    HashMap<String, Integer> lenders;
    HashMap<String, Integer> borrowers;

    public RecordOfTransactions() {
        this.lenders = new HashMap<>();
        this.borrowers = new HashMap<>();
    }

    public void setLenders(HashMap<String, Integer> jsonLenders) {
        this.lenders = jsonLenders;
    }

    public void setBorrowers(HashMap<String, Integer> jsonBorrowers) {
        this.borrowers = jsonBorrowers;
    }

    // REQUIRES: keys are all unique
    // MODIFIES: amount (int)
    // EFFECTS: gets the total amount that a person borrowed or lent from/to people
    public int owedOrLentAmount(String person) {
        int amount;
        if (lenders.containsKey(person)) {
            amount = lenders.get(person);
        } else {
            amount = borrowers.get(person);
        }
        return amount;
    }

    // EFFECTS: gets a list of borrowers (positive associated amount)
    public ArrayList<String> borrowerNames() {
        return new ArrayList<>(borrowers.keySet());
    }

    // EFFECTS: gets a list of lenders (negative associated amount)
    public ArrayList<String> lenderNames() {
        return new ArrayList<>(lenders.keySet());
    }

    // MODIFIES: this
    // EFFECTS: clears record of lenders and borrowers
    public void clearContents() {
        lenders.clear();
        borrowers.clear();
        EventLog.getInstance().clear();
    }

    // MODIFIES: everyone
    // EFFECTS: shows a list of all the lenders, then all the borrowers with their associated amount owed or lent
    public List<HashMap<String, Integer>> show() {
        ArrayList<HashMap<String, Integer>> everyone = new ArrayList<>();
        everyone.add(lenders);
        everyone.add(borrowers);
        return everyone;
    }

    // MODIFIES: this
    // EFFECTS: Houses different functions that allow for creating a single number (amount) associate with each person
    public void calculateFinalTransactions(Lender lender, Borrower borrower) {
        lendingFromLenders(lender);
        borrowingFromBorrowers(borrower);
        lendingFromBorrowers(lender);
        borrowingFromLenders(borrower);
        addingToLenders(lender);
        addingToBorrowers(borrower);
    }


    // MODIFIES: this
    // REQUIRES: if lender is in lenders group, then add the amount to the existing lender in lenders group
    private void lendingFromLenders(Lender lender) {
        if (lenders.containsKey(lender.getName())) {
            int amount = (lender.getAmount() + lenders.get(lender.getName()));
            lenders.replace(lender.getName(), lender.getAmount() + lenders.get(lender.getName()));
            EventLog.getInstance().logEvent(new Event(lender.getName()
                    + " lent out " + Math.abs(lender.getAmount()) + " more and now has a balance of "
                    + amount));
        }
    }

    // MODIFIES: this
    // REQUIRES: if borrower is in the borrowers group, then add the amount to the existing borrower in the borrowers
    private void borrowingFromBorrowers(Borrower borrower) {
        if (borrowers.containsKey(borrower.getName())) {
            int amount = (borrower.getAmount() + borrowers.get(borrower.getName()));
            borrowers.replace(borrower.getName(), borrower.getAmount() + borrowers.get(borrower.getName()));
            EventLog.getInstance().logEvent(new Event(borrower.getName()
                    + " borrowed " + borrower.getAmount() + "more and now has a balance of "
                    + amount));
        }
    }

    // MODIFIES: this
    // REQUIRES: if borrower is not in the borrowers group but in the lenders group, take the sum of the amount and
    // the associated number, then group only into lenders if <=0, group only into borrowers otherwise
    private void borrowingFromLenders(Borrower borrower) {
        if (!(borrowers.containsKey(borrower.getName())) & (lenders.containsKey(borrower.getName()))) {
            if ((lenders.get(borrower.getName()) + borrower.getAmount()) <= 0) {
                int amount = (lenders.get(borrower.getName()) + borrower.getAmount());
                lenders.replace(borrower.getName(), lenders.get(borrower.getName()) + borrower.getAmount());
                EventLog.getInstance().logEvent(new Event(borrower.getName()
                        + " borrowed " + borrower.getAmount() + " after lending a bit and now has a balance of "
                        + amount));
            } else {
                borrowers.put(borrower.getName(), lenders.get(borrower.getName()) + borrower.getAmount());
                int amount = (lenders.get(borrower.getName()) + borrower.getAmount());
                lenders.remove(borrower.getName());
                EventLog.getInstance().logEvent(new Event(borrower.getName()
                        + " borrowed " + borrower.getAmount()
                        + " and is now categorized as a borrower with a balance of "
                        + amount));
            }
        }
    }

    // MODIFIES: this
    // REQUIRES: if lender is not in lenders but in borrowers, add the associated number with the input
    // number, then group only into borrowers if >=0, group only into lenders otherwise
    private void lendingFromBorrowers(Lender lender) {
        if (!(lenders.containsKey(lender.getName())) & (borrowers.containsKey(lender.getName()))) {
            if ((borrowers.get(lender.getName()) + lender.getAmount()) >= 0) {
                int amount = borrowers.get(lender.getName()) + lender.getAmount();
                borrowers.replace(lender.getName(), borrowers.get(lender.getName()) + lender.getAmount());
                EventLog.getInstance().logEvent(new Event(lender.getName()
                        + " lent out " + Math.abs(lender.getAmount())
                        +  " after borrowing a bit and now has a balance of "
                        + amount));
            } else {
                lenders.put(lender.getName(), borrowers.get(lender.getName()) + lender.getAmount());
                int amount = borrowers.get(lender.getName()) + lender.getAmount();
                borrowers.remove(lender.getName());
                EventLog.getInstance().logEvent(new Event(lender.getName()
                        + " lent out " + Math.abs(lender.getAmount())
                        + " and is now categorized as a lender with a balance of "
                        + amount));
            }
        }
    }

    // MODIFIES: this
    // REQUIRES: if lender doesn't exist in lenders or borrowers, then add to lenders
    private void addingToLenders(Lender lender) {
        if (!(lenders.containsKey(lender.getName())) & !(borrowers.containsKey(lender.getName()))) {
            lenders.put(lender.getName(), lender.getAmount());
            EventLog.getInstance().logEvent(new Event(lender.getName()
                    + " has lent out money for the first time and has a balance of " + lender.getAmount()));
        }
    }

    // MODIFIES: this
    // EFFECTS: if borrower is not in lenders or borrowers, then add to borrowers
    private void addingToBorrowers(Borrower borrower) {
        if (!(lenders.containsKey(borrower.getName())) & !(borrowers.containsKey(borrower.getName()))) {
            borrowers.put(borrower.getName(), borrower.getAmount());
            EventLog.getInstance().logEvent(new Event(borrower.getName()
                    + " has borrowed money for the first time and has a balance of " + borrower.getAmount()));
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Lender", addLendersToJson());
        json.put("Borrower", addBorrowersToJson());
        return json;
    }

    // MODIFIES: this
    // EFFECTS: returns lender JSON object in this record as a JSON array
    private JSONArray addLendersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String lender: lenders.keySet()) {
            jsonArray.put(addLenderToJson(lender));
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: add lender name and amount to JSON object
    private JSONObject addLenderToJson(String name) {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("amount", lenders.get(name));
        return json;
    }

    // MODIFIES: this
    // EFFECTS: returns borrower JSON object in this record as a JSON array
    private JSONArray addBorrowersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String borrower: borrowers.keySet()) {
            jsonArray.put(addBorrowerToJson(borrower));
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: add borrower name and amount to JSON object
    private JSONObject addBorrowerToJson(String name) {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("amount", borrowers.get(name));
        return json;
    }

}




