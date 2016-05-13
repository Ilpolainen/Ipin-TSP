/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.datastructures;

import tsp.datastructures.GrowingArray;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author omistaja
 */
public class GrowingArrayTest {

    private GrowingArray GA;

    public GrowingArrayTest() {
    }

    @Before
    public void setUp() {
        GA = new GrowingArray();
    }

    @Test
    public void constructorCreatesNewArrayOfLengthHundredAndSetsSizeZero() {
        assertEquals(0, GA.size());
        assertEquals(100, GA.getArray().length);
    }

    @Test
    public void addingObjectWorksWhenArrayLimitNotExceeded() {
        GA.add('a');
        GA.add('b');
        assertEquals('a', GA.getArray()[0]);
        assertEquals('b', GA.getArray()[1]);
        assertEquals(2, GA.size());
    }

    @Test
    public void checkSizeWorks() {
        int a = GA.getArray().length;
        for (int i = 0; i < a; i++) {
            Integer x = i;
            GA.add(x);
        }
        GA.checkSize();
        assertEquals(a * 2, GA.getArray().length);
        for (int i = 0; i < a; i++) {
            assertEquals(i, GA.get(i));
        }
        for (int i = a; i < a * 2; i++) {
            Object x = GA.get(i);
            boolean truth = x == null;
            assertEquals(true, truth);
        }
    }

    @Test
    public void addWorksWhenStackSizeGoesOvererArraySize() {
        int a = GA.getArray().length;
        for (int i = 0; i <= a; i++) {
            Integer x = i;
            GA.add(x);
        }
        assertEquals(a * 2, GA.getArray().length);
        assertEquals(a + 1, GA.size());
        for (int i = 0; i < a; i++) {
            assertEquals(i, GA.getArray()[i]);
        }
    }

    @Test
    public void getWorksWithCorrectValues() {
        for (int i = 0; i < 10; i++) {
            Integer x = i;
            GA.add(x);
        }
        for (int i = 0; i < 10; i++) {
            int a = (int) GA.get(i);
            assertEquals(i, a);
        }
    }

    @Test
    public void getReturnsNullWithNegativeIndex() {
        GA.add('a');
        Object x = GA.get(-1);
        boolean truth = x == null;
        assertEquals(true, truth);
    }

    @Test
    public void getReturnsNullWithIndexBiggerThanArray() {
        GA.add('a');
        Object x = GA.get(200);
        boolean truth = x == null;
        assertEquals(true, truth);
    }

    @Test
    public void getReturnsNullWithIndexBiggerThanSize() {
        GA.add('a');
        Object x = GA.get(2);
        boolean truth = x == null;
        assertEquals(true, truth);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
