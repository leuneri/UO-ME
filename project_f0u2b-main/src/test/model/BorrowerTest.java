package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BorrowerTest {

    private Borrower borrower1;
    private Borrower borrower2;

    @BeforeEach
    void setUp() {
        borrower1 = new Borrower("Eric", 10);
        borrower2 = new Borrower("Jack", 20);
    }

    @Test
    void testOneLender() {
        assertEquals("Eric", borrower1.getName());
        assertEquals(20, borrower2.getAmount());
    }

    @Test
    void testMultipleLenders() {
        assertEquals("Eric", borrower1.getName());
        assertEquals(10, borrower1.getAmount());
        assertEquals("Jack", borrower2.getName());
        assertEquals(20, borrower2.getAmount());
    }
}
