package com.hpba.bankman.model;

import org.junit.*;
import static org.junit.Assert.*;

public class SavingsTest {

    Savings instance;

    @Before
    public void setUp() {
        instance = new Savings("00000000-00000001", 2500, 4.5);
    }

    /**
     * Test of update method, of class Savings.
     */
    @Test
    public void testUpdate() {
        Savings acc = new Savings("00000000-00000001", 2500, 5.5);
        acc.setTransfersThisMonth(5);
        acc.setTransferLimit(10);
        assertEquals(4.5, instance.getInterestRate(), 0.0001);
        assertEquals(0, instance.getTransfersThisMonth());
        assertEquals(3, instance.getTransferLimit());
        instance.update(acc);
        assertEquals(5.5, instance.getInterestRate(), 0.0001);
        assertEquals(5, instance.getTransfersThisMonth());
        assertEquals(10, instance.getTransferLimit());
    }

}
