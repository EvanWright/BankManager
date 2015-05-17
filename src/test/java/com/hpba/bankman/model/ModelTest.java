package com.hpba.bankman.model;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The Model class tester.
 *
 * @author generalhpba
 */
public class ModelTest {

    final Model instance = new Model();
    final double eps = 0.001;

    @Before
    public void setUp() {
        instance.setClient(new Client("9"));
    }

    @After
    public void tearDown() {
        instance.reset();
    }

    /**
     * Test of transfer method, of class Model.
     */
    @Test
    public void testTransfer() {
        Term from = new Term("01234567-01234567", 500, 2.5);
        Term from1 = new Term("01234567-01234567", 500, 2.5);
        from1.setLocked(true);
        Savings from2 = new Savings("01234567-01234567", 500, 2.5);
        from2.setTransferLimit(5);
        from2.setTransfersThisMonth(5);
        Savings to = new Savings("12345678-12345678", 900, 3.5);
        double amount1 = 20, amount2 = 2000;
        String description = "Transfering money from acc A to acc B";
        int rSuccess = 0, rInsufficient = 1, rSavingsFail = 2,
                rTermFail = 3, rException = 5, rSame = 6, result;

        // test success
        result = instance.transfer(from, to, amount1, description);
        assertEquals(rSuccess, result);
        // test not enough money
        result = instance.transfer(from, to, amount2, description);
        assertEquals(rInsufficient, result);
        // test savings failed
        result = instance.transfer(from2, to, amount1, description);
        assertEquals(rSavingsFail, result);
        // test term failed
        result = instance.transfer(from1, to, amount1, description);
        assertEquals(rTermFail, result);
        // test exception
        result = instance.transfer(null, null, amount1, description);
        assertEquals(rException, result);
        // test same
        result = instance.transfer(from1, from1, amount1, description);
        assertEquals(rSame, result);
    }

    /**
     * Test of exchange method, of class Model.
     */
    @Test
    public void testExchange() {
        Term from = new Term("01234567-01234567", 500, 2.5);
        Savings to = new Savings("12345678-12345678", 900, 3.5);
        double amount = 100, exp1 = 400, exp2 = 1000;
        instance.exchange(from, to, amount);
        assertEquals(from.getBalance(), exp1, eps);
        assertEquals(to.getBalance(), exp2, eps);
    }

    /**
     * Test of addHistory method, of class Model.
     */
    @Test
    public void testAddHistory() {
        Term from = new Term("01234567-01234567", 500, 2.5);
        Savings to = new Savings("12345678-12345678", 900, 3.5);
        double amount = 100;
        String description = "Monthly Rent Fee";
        instance.addHistory(from, to, amount, description);
        Entry e = new Entry(from.getNumber(), to.getNumber(), "Sent",
                -amount, description);
        System.out.println(e);
        System.out.println(from.getLastEntry());
        assertEquals(e.toString(), from.getLastEntry().toString());
    }

    /**
     * Test of checkTerm method, of class Model.
     */
    @Test
    public void testCheckTerm() {
        Term acc1 = new Term("00000000-00000000", 2500, 1.2);
        Term acc2 = new Term("00000000-00000000", 2500, 1.2);
        acc2.setLocked(true);
        boolean expResult = true, result;

        // test not locked
        result = instance.checkTerm(acc1);
        assertEquals(expResult, result);
        // test locked
        result = instance.checkTerm(acc2);
        assertEquals(!expResult, result);
    }

    /**
     * Test of checkSavings method, of class Model.
     */
    @Test
    public void testCheckSavings() {
        Savings acc1 = new Savings("00000000-00000000", 2500, 1.2);
        Savings acc2 = new Savings("00000000-00000000", 2500, 1.2);
        acc1.setTransferLimit(5);
        acc1.setTransfersThisMonth(1);
        acc2.setTransferLimit(5);
        acc2.setTransfersThisMonth(5);
        boolean expResult = true, result;

        // test can transfer
        result = instance.checkSavings(acc1);
        assertEquals(expResult, result);
        // test no more transfers left
        result = instance.checkSavings(acc2);
        assertEquals(!expResult, result);
    }

    /**
     * Test of reset method, of class Model.
     */
    @Test
    public void testReset() {
        instance.addAccount(new Account("00000000-00000000", 2500));
        instance.getClient().add("00000000-00000000");
        assertEquals(1, instance.getAccountCount());
        instance.reset();
        assertEquals(0, instance.getAccountCount());
    }

    /**
     * Test of calcInterest method, of class Model.
     */
    @Test
    public void testCalcInterest() {
        Savings acc = new Savings("00000000-00000000", 1000, 5.0);
        double exp = 50;
        assertEquals(exp, Model.calcInterest(acc, 1), eps);
    }

    /**
     * Test of calcInterestPerMonth method, of class Model.
     */
    @Test
    public void testCalcInterestPerMonth() {
        Savings acc = new Savings("00000000-00000000", 1000, 5.0);
        double exp = 50;
        assertEquals(exp, Model.calcInterestPerMonth(acc), eps);
        exp = 100;
        assertEquals(exp, Model.calcInterestPerMonth(acc, 2000), eps);
    }

    /**
     * Test of getNumber method, of class Model.
     */
    @Test
    public void testGetNumber() {
        String result = instance.getNumber();
        assertEquals(null, result);
        instance.setAccount(new Account("01234567-01234567", 2500));
        result = instance.getNumber();
        String expResult = "01234567-01234567";
        assertEquals(expResult, result);
    }

    /**
     * Test of getBalance method, of class Model.
     */
    @Test
    public void testGetBalance() {
        String result = instance.getBalance();
        assertEquals(null, result);
        Account acc = new Account("01234567-01234567", 2500);
        instance.setAccount(acc);
        result = instance.getBalance();
        double expResult = 2500;
        assertEquals(expResult, Double.valueOf(result), eps);
    }

    /**
     * Test of getAccount method, of class Model.
     */
    @Test
    public void testGetAccount() {
        Account acc = new Account("01234567-01234567", 2500);
        instance.addAccount(acc);
        instance.addAccount(acc);
        Account result = instance.getAccount("01234567-01234567");
        assertEquals(acc, result);
        instance.setAccount(acc);
        result = instance.getAccount();
        assertEquals(acc, result);
    }

    /**
     * Test of updateAccountInfo method, of class Model.
     */
    @Test
    public void testUpdateAccountInfo() {
        Account acc = new Account("01234567-01234567", 2500);
        instance.addAccount(acc);
        Account acc2 = new Account("01234567-01234567", 9000);
        instance.updateAccountInfo(acc2);
        double expected = 9000;
        assertEquals(expected, acc.getBalance(), eps);
    }

    /**
     * Test of addAccount method, of class Model.
     */
    @Test
    public void testAddAccount() {
        int expected = 0;
        assertEquals(expected, instance.getAccountCount());
        Account acc = new Account("01234567-01234567", 2500);
        instance.getClient().add("01234567-01234567");
        instance.addAccount(acc);
        expected = 1;
        assertEquals(expected, instance.getAccountCount());
    }

    /**
     * Test of getClient method, of class Model.
     */
    @Test
    public void testGetClient_0args() {
        Client client = new Client("3", "testClient");
        instance.setClient(client);
        Client expResult = client;
        Client result = instance.getClient();
        assertEquals(expResult, result);
    }

    /**
     * Test of getClientId method, of class Model.
     */
    @Test
    public void testGetClientId() {
        String expResult = "9";
        String result = instance.getClientId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setClient method, of class Model.
     */
    @Test
    public void testSetClient() {
        Client client = new Client("2");
        instance.setClient(client);
        assertEquals(client, instance.getClient());
    }

    /**
     * Test of getCurrentClient method, of class Model.
     */
    @Test
    public void testGetCurrentClient() {
        String expResult = "9";
        String result = instance.getCurrentClient();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCurrentClient method, of class Model.
     */
    @Test
    public void testSetCurrentClient() {
        String currentClient = "5";
        instance.setCurrentClient(currentClient);
        assertEquals(currentClient, instance.getCurrentClient());
    }

    /**
     * Test of getCurrentAccount method, of class Model.
     */
    @Test
    public void testGetCurrentAccount() {
        String expResult = "00000000-00000000";
        instance.setCurrentAccount("00000000-00000000");
        String result = instance.getCurrentAccount();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCurrentAccount method, of class Model.
     */
    @Test
    public void testSetCurrentAccount() {
        String expResult = "00000000-00000000";
        instance.setCurrentAccount("00000000-00000000");
        String result = instance.getCurrentAccount();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAccountCount method, of class Model.
     */
    @Test
    public void testGetAccountCount() {
        int expResult = 0;
        int result = instance.getAccountCount();
        assertEquals(expResult, result);
        expResult = 2;
        instance.addAccount(new Account("00000000-00000000", 1500));
        instance.addAccount(new Account("00000000-00000001", 1500));
        instance.getClient().add("00000000-00000000");
        instance.getClient().add("00000000-00000001");
        result = instance.getAccountCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTransactionalCount method, of class Model.
     */
    @Test
    public void testGetTransactionalCount() {
        int expResult = 0;
        int result = instance.getTransactionalCount();
        assertEquals(expResult, result);
        expResult = 1;
        instance.addAccount(new Savings("00000000-00000000", 1500, 1.2));
        instance.addAccount(new Transactional("00000000-00000001", 1500));
        instance.getClient().add("00000000-00000000");
        instance.getClient().add("00000000-00000001");
        result = instance.getTransactionalCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSavingsCount method, of class Model.
     */
    @Test
    public void testGetSavingsCount() {
        int expResult = 0;
        int result = instance.getSavingsCount();
        assertEquals(expResult, result);
        expResult = 1;
        instance.addAccount(new Term("00000000-00000000", 1500, 1.2));
        instance.addAccount(new Savings("00000000-00000001", 1500, 1.2));
        instance.getClient().add("00000000-00000000");
        instance.getClient().add("00000000-00000001");
        result = instance.getSavingsCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTermCount method, of class Model.
     */
    @Test
    public void testGetTermCount() {
        int expResult = 0;
        int result = instance.getTermCount();
        assertEquals(expResult, result);
        expResult = 1;
        instance.addAccount(new Savings("00000000-00000000", 1500, 1.2));
        instance.addAccount(new Term("00000000-00000001", 1500, 1.2));
        instance.getClient().add("00000000-00000000");
        instance.getClient().add("00000000-00000001");
        result = instance.getTermCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBalanceSum method, of class Model.
     */
    @Test
    public void testGetBalanceSum() {
        double expResult = 0.0;
        double result = instance.getBalanceSum();
        assertEquals(expResult, result, 0.0);
        instance.addAccount(new Account("00000000-00000000", 1500));
        instance.addAccount(new Term("00000000-00000001", 1500, 1.2));
        instance.getClient().add("00000000-00000000");
        instance.getClient().add("00000000-00000001");
        expResult = 3000;
        result = instance.getBalanceSum();
        assertEquals(expResult, result, eps);
    }

    /**
     * Test of getUsername method, of class Model.
     */
    @Test
    public void testGetUsername() {
        Client client = new Client("12", "testClient");
        String expResult = client.getUsername();
        instance.setClient(client);
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAccount method, of class Model.
     */
    @Test
    public void testGetAccount_String() {
        Account expResult = new Account("00000000-00000000", 2500);
        instance.addAccount(expResult);
        Account result = instance.getAccount("00000000-00000000");
        assertEquals(expResult, result);
    }

    /**
     * Test of getAccount method, of class Model.
     */
    @Test
    public void testGetAccount_0args() {
        Account expResult = new Account("00000000-00000000", 2500);
        instance.setAccount(expResult);
        Account result = instance.getAccount();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAccount method, of class Model.
     */
    @Test
    public void testSetAccount() {
        Account expResult = new Account("00000000-00000000", 2500);
        instance.setAccount(expResult);
        Account result = instance.getAccount();
        assertEquals(expResult, result);
    }

}
