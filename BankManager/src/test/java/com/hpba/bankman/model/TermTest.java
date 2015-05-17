package com.hpba.bankman.model;

import java.time.LocalDate;
import org.junit.*;
import static org.junit.Assert.*;

public class TermTest {
    
    Term instance;
    
    @Before
    public void setUp() {
        instance = new Term("00000000-00000001", 2000, 4.5);
    }

    /**
     * Test of update method, of class Term.
     */
    @Test
    public void testUpdate() {
        Term acc = new Term("00000000-00000001", 200, 4.5);
        acc.setLocked(true);
        assertEquals(false, instance.isLocked());
        instance.update(acc);
        assertEquals(true, instance.isLocked());
    }

    /**
     * Test of getBalance method, of class Term.
     */
    @Test
    public void testGetBalance() {
        double expResult = 2000.0;
        double result = instance.getBalance();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of lock method, of class Term.
     */
    @Test
    public void testLock() {
        int months = 4;
        boolean expResult = true;
        boolean result = instance.lock(months);
        assertEquals(expResult, result);
        expResult = false;
        result = instance.lock(months);
        assertEquals(expResult, result);
    }

    /**
     * Test of unlock method, of class Term.
     */
    @Test
    public void testUnlock() {
        instance.setLocked(true);
        assertEquals(true, instance.isLocked());
        instance.setLockStart(LocalDate.ofEpochDay(1200));
        instance.setLockEnd(LocalDate.ofEpochDay(2200));
        instance.unlock();
        assertEquals(false, instance.isLocked());
    }

    /**
     * Test of isLocked method, of class Term.
     */
    @Test
    public void testIsLocked() {
        boolean expResult = false;
        boolean result = instance.isLocked();
        assertEquals(expResult, result);
        expResult = true;
        instance.setLocked(true);
        result = instance.isLocked();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLocked method, of class Term.
     */
    @Test
    public void testSetLocked() {
        boolean isLocked = false;
        instance.setLocked(isLocked);
        assertEquals(false, instance.isLocked());
        isLocked = true;
        instance.setLocked(isLocked);
        assertEquals(true, instance.isLocked());
    }

    /**
     * Test of getLockStart method, of class Term.
     */
    @Test
    public void testGetLockStart() {
        instance.setLockStart(LocalDate.ofEpochDay(1200));
        LocalDate expResult = LocalDate.ofEpochDay(1200);
        LocalDate result = instance.getLockStart();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLockStart method, of class Term.
     */
    @Test
    public void testSetLockStart() {
        instance.setLockStart(LocalDate.ofEpochDay(1200));
        LocalDate expResult = LocalDate.ofEpochDay(1200);
        LocalDate result = instance.getLockStart();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLockEnd method, of class Term.
     */
    @Test
    public void testGetLockEnd() {
        instance.setLockEnd(LocalDate.ofEpochDay(1200));
        LocalDate expResult = LocalDate.ofEpochDay(1200);
        LocalDate result = instance.getLockEnd();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLockEnd method, of class Term.
     */
    @Test
    public void testSetLockEnd() {
        instance.setLockEnd(LocalDate.ofEpochDay(1200));
        LocalDate expResult = LocalDate.ofEpochDay(1200);
        LocalDate result = instance.getLockEnd();
        assertEquals(expResult, result);
    }
    
}
