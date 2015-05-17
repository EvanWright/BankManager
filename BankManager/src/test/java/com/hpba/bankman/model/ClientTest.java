package com.hpba.bankman.model;

import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {
    
    Client instance;
    
    @Before
    public void setUp() {
        instance = new Client("12", "test_user");
    }

    /**
     * Test of setUsername method, of class Client.
     */
    @Test
    public void testSetUsername() {
        String username = "other_username";
        instance.setUsername(username);
        assertEquals(username, instance.getUsername());
    }

    /**
     * Test of setId method, of class Client.
     */
    @Test
    public void testSetId() {
        String id = "6";
        instance.setId(id);
        assertEquals(id, instance.getId());
    }
    
}
