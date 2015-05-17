package com.hpba.bankman.model;

import org.junit.*;
import static org.junit.Assert.*;

public class InterestBearingTest {
    
    InterestBearingImpl instance;
    
    
    @Before
    public void setUp() {
        instance = new InterestBearingImpl();
    }

    /**
     * Test of setInterestRate method, of class InterestBearing.
     */
    @Test
    public void testSetInterestRate() {
        double interestRate = 3.0;
        instance.setInterestRate(interestRate);
        assertEquals(3.0, instance.getInterestRate(), 0.0001);
    }

    public class InterestBearingImpl extends InterestBearing {

        public InterestBearingImpl() {
            super("00010002-00030004", 2500);
        }
    }
    
}
