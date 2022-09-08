package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTests {
    private Lender lender1;
    private Lender lender2;
    private Lender lender3;
    private Lender lender4;
    private Lender lender5;
    private Lender lender6;
    private Lender lender7;
    private Lender lender8;
    private Borrower borrower1;
    private Borrower borrower2;
    private Borrower borrower3;
    private Borrower borrower4;
    private Borrower borrower5;
    private Borrower borrower6;
    private Borrower borrower7;
    private Borrower borrower8;
    private RecordOfTransactions transactions;
    private ArrayList<String> lenders;
    private ArrayList<String> borrowers;
    private HashMap<String, Integer> hashmap1;
    private HashMap<String, Integer> hashmap2;
    private ArrayList<HashMap<String, Integer>> hashMapList;

    @BeforeEach
    void setUp() {
        lender1 = new Lender("Eric", -33);
        lender2 = new Lender("Eric", -44);
        lender3 = new Lender("Ricky", -23);
        lender4 = new Lender("Joseph", -32);
        lender5 = new Lender("Joseph", -34);
        lender6 = new Lender("Zoe", -35);
        lender7 = new Lender("Jackson", -31);
        lender8 = new Lender("Johnson", -3);
        borrower1 = new Borrower("Joseph", 33);
        borrower2 = new Borrower("Andy", 44);
        borrower3 = new Borrower("Joseph", 23);
        borrower4 = new Borrower("William", 32);
        borrower5 = new Borrower("William", 34);
        borrower6 = new Borrower("Eric", 35);
        borrower7 = new Borrower("Eric", 31);
        borrower8 = new Borrower("Aaron", 3);
        transactions = new RecordOfTransactions();
        lenders = new ArrayList<>();
        borrowers = new ArrayList<>();
        hashmap1 = new HashMap<>();
        hashmap2 = new HashMap<>();
        hashMapList = new ArrayList<>();
    }

    @Test
    void testOneLenderOneBorrower() {
        transactions.calculateFinalTransactions(lender1, borrower1);
        assertEquals(-33, transactions.owedOrLentAmount("Eric"));
        assertEquals(33, transactions.owedOrLentAmount("Joseph"));
        lenders.add("Eric");
        borrowers.add("Joseph");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
        hashmap1.put("Eric", -33);
        hashmap2.put("Joseph", 33);
        hashMapList.add(hashmap1);
        hashMapList.add(hashmap2);
        assertEquals(hashMapList, transactions.show());
        transactions.clearContents();
        lenders.clear();
        borrowers.clear();
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
        hashMapList.clear();
        hashmap1.clear();
        hashmap2.clear();
        hashMapList.add(hashmap1);
        hashMapList.add(hashmap2);
        assertEquals(hashMapList, transactions.show());
    }

    @Test
    void testMultipleLendersAndBorrowers() {
        transactions.calculateFinalTransactions(lender1, borrower1);
        assertEquals(-33, transactions.owedOrLentAmount("Eric"));
        assertEquals(33, transactions.owedOrLentAmount("Joseph"));
        lenders.add("Eric");
        borrowers.add("Joseph");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
        transactions.calculateFinalTransactions(lender8, borrower8);
        assertEquals(3, transactions.owedOrLentAmount("Aaron"));
        assertEquals(-3, transactions.owedOrLentAmount("Johnson"));
        lenders.add("Johnson");
        lenders.remove("Eric");
        lenders.add("Eric");
        borrowers.add("Aaron");
        borrowers.remove("Joseph");
        borrowers.add("Joseph");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
        hashmap1.put("Eric", -33);
        hashmap1.put("Johnson", -3);
        hashmap2.put("Joseph", 33);
        hashmap2.put("Aaron", 3);
        hashMapList.add(hashmap1);
        hashMapList.add(hashmap2);
        assertEquals(hashMapList, transactions.show());
        transactions.clearContents();
        lenders.clear();
        borrowers.clear();
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
        hashMapList.clear();
        hashmap1.clear();
        hashmap2.clear();
        hashMapList.add(hashmap1);
        hashMapList.add(hashmap2);
        assertEquals(hashMapList, transactions.show());
    }

    @Test
    void testLendingFromLender() {
        transactions.calculateFinalTransactions(lender1, borrower1);
        assertEquals(-33, transactions.owedOrLentAmount("Eric"));
        assertEquals(33, transactions.owedOrLentAmount("Joseph"));
        lenders.add("Eric");
        borrowers.add("Joseph");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
        transactions.calculateFinalTransactions(lender2, borrower2);
        assertEquals(-77, transactions.owedOrLentAmount("Eric"));
        assertEquals(33, transactions.owedOrLentAmount("Joseph"));
        assertEquals(44, transactions.owedOrLentAmount("Andy"));
        borrowers.add("Andy");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
    }

    @Test
    void testBorrowingFromBorrower() {
        transactions.calculateFinalTransactions(lender1, borrower1);
        assertEquals(-33, transactions.owedOrLentAmount("Eric"));
        assertEquals(33, transactions.owedOrLentAmount("Joseph"));
        lenders.add("Eric");
        borrowers.add("Joseph");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
        transactions.calculateFinalTransactions(lender3, borrower3);
        assertEquals(-33, transactions.owedOrLentAmount("Eric"));
        assertEquals(56, transactions.owedOrLentAmount("Joseph"));
        assertEquals(-23, transactions.owedOrLentAmount("Ricky"));
        lenders.add("Ricky");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
    }

    @Test
    void testLendingFromBorrowerUnderAmount() {
        transactions.calculateFinalTransactions(lender1, borrower1);
        assertEquals(-33, transactions.owedOrLentAmount("Eric"));
        assertEquals(33, transactions.owedOrLentAmount("Joseph"));
        lenders.add("Eric");
        borrowers.add("Joseph");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
        transactions.calculateFinalTransactions(lender4, borrower4);
        assertEquals(-33, transactions.owedOrLentAmount("Eric"));
        assertEquals(1, transactions.owedOrLentAmount("Joseph"));
        assertEquals(32, transactions.owedOrLentAmount("William"));
        borrowers.add("William");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
    }

    @Test
    void testLendingFromBorrowerOverAmount() {
        transactions.calculateFinalTransactions(lender1, borrower1);
        assertEquals(-33, transactions.owedOrLentAmount("Eric"));
        assertEquals(33, transactions.owedOrLentAmount("Joseph"));
        lenders.add("Eric");
        borrowers.add("Joseph");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
        transactions.calculateFinalTransactions(lender5, borrower5);
        assertEquals(-33, transactions.owedOrLentAmount("Eric"));
        assertEquals(-1, transactions.owedOrLentAmount("Joseph"));
        assertEquals(34, transactions.owedOrLentAmount("William"));
        borrowers.add("William");
        borrowers.remove("Joseph");
        lenders.add("Joseph");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
    }

    @Test
    void testBorrowingFromLenderOverAmount() {
        transactions.calculateFinalTransactions(lender1, borrower1);
        assertEquals(-33, transactions.owedOrLentAmount("Eric"));
        assertEquals(33, transactions.owedOrLentAmount("Joseph"));
        lenders.add("Eric");
        borrowers.add("Joseph");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
        transactions.calculateFinalTransactions(lender6, borrower6);
        assertEquals(2, transactions.owedOrLentAmount("Eric"));
        assertEquals(33, transactions.owedOrLentAmount("Joseph"));
        assertEquals(-35, transactions.owedOrLentAmount("Zoe"));
        lenders.add("Zoe");
        lenders.remove("Eric");
        borrowers.add("Eric");
        borrowers.remove("Joseph");
        borrowers.add("Joseph");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
    }

    @Test
    void testBorrowingFromLenderUnderAmount() {
        transactions.calculateFinalTransactions(lender1, borrower1);
        assertEquals(-33, transactions.owedOrLentAmount("Eric"));
        assertEquals(33, transactions.owedOrLentAmount("Joseph"));
        lenders.add("Eric");
        borrowers.add("Joseph");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
        transactions.calculateFinalTransactions(lender7, borrower7);
        assertEquals(-2, transactions.owedOrLentAmount("Eric"));
        assertEquals(33, transactions.owedOrLentAmount("Joseph"));
        assertEquals(-31, transactions.owedOrLentAmount("Jackson"));
        lenders.add("Jackson");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
    }


    @Test
    void testMultipleFunctions() {
        transactions.calculateFinalTransactions(lender1, borrower1);
        transactions.calculateFinalTransactions(lender2, borrower2);
        transactions.calculateFinalTransactions(lender3, borrower3);
        transactions.calculateFinalTransactions(lender4, borrower4);
        transactions.calculateFinalTransactions(lender5, borrower5);
        transactions.calculateFinalTransactions(lender6, borrower6);
        transactions.calculateFinalTransactions(lender7, borrower7);
        transactions.calculateFinalTransactions(lender8, borrower8);
        assertEquals(-11, transactions.owedOrLentAmount("Eric"));
        assertEquals(-10, transactions.owedOrLentAmount("Joseph"));
        assertEquals(-31, transactions.owedOrLentAmount("Jackson"));
        assertEquals(-23, transactions.owedOrLentAmount("Ricky"));
        assertEquals(-35, transactions.owedOrLentAmount("Zoe"));
        assertEquals(-3, transactions.owedOrLentAmount("Johnson"));
        assertEquals(44, transactions.owedOrLentAmount("Andy"));
        assertEquals(66, transactions.owedOrLentAmount("William"));
        assertEquals(3, transactions.owedOrLentAmount("Aaron"));
        lenders.add("Zoe");
        lenders.add("Johnson");
        lenders.add("Eric");
        lenders.add("Joseph");
        lenders.add("Jackson");
        lenders.add("Ricky");
        borrowers.add("Aaron");
        borrowers.add("William");
        borrowers.add("Andy");
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
        transactions.clearContents();
        lenders.clear();
        borrowers.clear();
        assertEquals(lenders, transactions.lenderNames());
        assertEquals(borrowers, transactions.borrowerNames());
    }
}