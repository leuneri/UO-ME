package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LenderTest {
    private Lender lender1;
    private Lender lender2;

    @BeforeEach
    void setUp() {
        lender1 = new Lender("Eric", -33);
        lender2 = new Lender("Jack", -44);
    }

    @Test
    void testOneLender() {
        assertEquals("Eric", lender1.getName());
        assertEquals(-33, lender1.getAmount());
    }

    @Test
    void testMultipleLenders() {
        assertEquals("Eric", lender1.getName());
        assertEquals(-33, lender1.getAmount());
        assertEquals("Jack", lender2.getName());
        assertEquals(-44, lender2.getAmount());
    }

}
