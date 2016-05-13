/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.datastructures;

import tsp.datastructures.MyStack;
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
public class MyStackTest {

    private MyStack stack;

    public MyStackTest() {
    }

    @Before
    public void setUp() {
        this.stack = new MyStack();
    }

    @Test
    public void constructorSetsSizeAndArraysizeCorrect() {
        assertEquals(0, stack.getSize());
        assertEquals(100, stack.getArraySize());
        assertEquals(100, stack.getStackArray().length);
    }

    @Test
    public void emptyRecognizesWhetherStackIsEmptyOrNot() {
        assertEquals(true, stack.empty());
        stack.push('e');
        assertEquals(false, stack.empty());
    }

    @Test
    public void pushUpdatesSizeAndAddsObjectToStackArray() {
        stack.push('a');
        assertEquals('a', stack.getStackArray()[0]);
        assertEquals(1, stack.getSize());
        assertEquals(100, stack.getArraySize());
    }

    @Test
    public void tenPushesWorks() {
        for (int i = 0; i < 10; i++) {
            Integer x = i;
            stack.push(x);
        }
        assertEquals(100, stack.getStackArray().length);
        assertEquals(10, stack.getSize());
        for (int i = 0; i < 10; i++) {
            assertEquals(i, stack.getStackArray()[i]);
        }
    }

    @Test
    public void pushWorksWhenStackSizeIsUnderArraySize() {
        for (int i = 0; i < 100; i++) {
            Integer x = i;
            stack.push(x);
        }
        assertEquals(100, stack.getStackArray().length);
        assertEquals(100, stack.getSize());
        for (int i = 0; i < 100; i++) {
            assertEquals(i, stack.getStackArray()[i]);
        }
    }

    @Test
    public void pushWorksWhenStackSizeGoesOvererArraySize() {
        int a = stack.getArraySize();
        for (int i = 0; i <= a; i++) {
            Integer x = i;
            stack.push(x);
        }
        assertEquals(a * 2, stack.getStackArray().length);
        assertEquals(a + 1, stack.getSize());
        for (int i = 0; i < a; i++) {
            assertEquals(i, stack.getStackArray()[i]);
        }
    }

    @Test
    public void popReturnsNullFromEmptyStack() {
        Object o = stack.pop();
        boolean truth = o == null;
        assertEquals(true, truth);
    }

    @Test
    public void popReturnsTheLastPushedObject() {
        for (int i = 0; i < 10; i++) {
            Integer x = i;
            stack.push(x);
        }
        assertEquals(9, stack.pop());
    }

    @Test
    public void popUpdatesStackSize() {
        for (int i = 0; i < 10; i++) {
            Integer x = i;
            stack.push(x);
        }
        int size = stack.getSize();
        stack.pop();
        assertEquals(size - 1, stack.getSize());
    }

    @Test
    public void popReturnsObjectInReversedOrder() {
        for (int i = 0; i < 50; i++) {
            Integer x = i;
            stack.push(x);
        }
        for (int i = 0; i < 50; i++) {
            assertEquals(49 - i, stack.pop());
        }
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
