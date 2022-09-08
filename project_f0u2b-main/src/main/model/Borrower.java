package model;

// Represents a lender with their name and amount that they owe (positive amount)
public class Borrower {
    private final String name;
    private final int amount;

    public Borrower(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

}
