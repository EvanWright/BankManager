package com.hpba.bankman.model;

import java.time.LocalDateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class EntryTest {
    
    Entry instance;
    
    @Before
    public void setUp() {
        instance = new Entry("00010002-00030004", "00050006-00070008", "Transfer", 
                2500, "Monthly rent pay", LocalDateTime.parse("2015-05-12T12:05"));
    }

    /**
     * Test of getNumber method, of class Entry.
     */
    @Test
    public void testGetNumber() {
        String expResult = "00010002-00030004";
        String result = instance.getNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOperation method, of class Entry.
     */
    @Test
    public void testGetOperation() {
        String expResult = "Transfer";
        String result = instance.getOperation();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStatement method, of class Entry.
     */
    @Test
    public void testGetStatement() {
        String expResult = "Monthly rent pay";
        String result = instance.getStatement();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDate method, of class Entry.
     */
    @Test
    public void testGetDate() {
        LocalDateTime expResult = LocalDateTime.parse("2015-05-12T12:05");
        LocalDateTime result = instance.getDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAmount method, of class Entry.
     */
    @Test
    public void testGetAmount() {
        double expResult = 2500.0;
        double result = instance.getAmount();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getParty method, of class Entry.
     */
    @Test
    public void testGetParty() {
        String expResult = "00050006-00070008";
        String result = instance.getParty();
        assertEquals(expResult, result);
    }
    
}
