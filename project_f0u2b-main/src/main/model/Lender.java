package model;

// Represents a lender with their name and amount that they owe (negative amount because they are to receive money back)
public class Lender {
    private final String name;
    private final int amount;

    public Lender(String name, int amount) {
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
