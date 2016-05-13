/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.datastructures;

import tsp.datastructures.CityKnot;
import tsp.datastructures.WheelList;
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
public class WheelListTest {

    public WheelListTest() {
    }

    private WheelList empty;
    private WheelList wl;

    @Before
    public void setUp() {
        empty = new WheelList();
        wl = new WheelList(6);
    }

    @Test
    public void emptyConstrucorSetsHeadNull() {
        assertEquals(null, empty.getHead());
    }

    @Test
    public void emptyConstrucorSetsSizeZero() {
        assertEquals(0, empty.getSize());
    }

    @Test
    public void constructorSetsSizeRight() {
        assertEquals(6, wl.getSize());
    }

    @Test
    public void constructorTurnsNegativeSizesZero() {
        assertEquals(6, wl.getSize());
    }

    @Test
    public void insertWorksForEmptyWheel() {
        empty.insert(new CityKnot(0));
        assertEquals(0, empty.getHead().getNumber());
        assertEquals(0, empty.getHead().getPrev().getNumber());
        assertEquals(0, empty.getHead().getNext().getNumber());
        assertEquals(1, empty.getSize());
    }

    @Test
    public void insertWorksTwoTimes() {
        empty.insert(new CityKnot(100));
        empty.insert(new CityKnot(3));
        assertEquals(3, empty.getHead().getNumber());
        assertEquals(100, empty.getHead().getNext().getNumber());
        assertEquals(100, empty.getHead().getPrev().getNumber());
        assertEquals(2, empty.getSize());
    }

    @Test
    public void insertWorksManyTimes() {
        empty.insert(new CityKnot(100));
        empty.insert(new CityKnot(3));
        empty.insert(new CityKnot(4));
        assertEquals(4, empty.getHead().getNumber());
        assertEquals(100, empty.getHead().getNext().getNumber());
        assertEquals(3, empty.getHead().getPrev().getNumber());
        assertEquals(3, empty.getSize());
        empty.insert(new CityKnot(8));
        assertEquals(8, empty.getHead().getNumber());
        assertEquals(100, empty.getHead().getNext().getNumber());
        assertEquals(4, empty.getHead().getPrev().getNumber());
        assertEquals(4, empty.getSize());
    }

    @Test
    public void insertSetsRightOrder() {
        empty.insert(new CityKnot(100));
        empty.insert(new CityKnot(3));
        empty.insert(new CityKnot(4));
        empty.insert(new CityKnot(8));
        CityKnot knot = empty.getHead();
        assertEquals(8, knot.getNumber());
        knot = knot.getPrev();
        assertEquals(4, knot.getNumber());
        knot = knot.getPrev();
        assertEquals(3, knot.getNumber());
        knot = knot.getPrev();
        assertEquals(100, knot.getNumber());
    }

    @Test
    public void takeHeadWorksOnce() {
        CityKnot knot = wl.takeHead();
        assertEquals(0, knot.getNumber());
        assertEquals(1, wl.getHead().getNumber());
        assertEquals(2, wl.getHead().getPrev().getNumber());
        assertEquals(5, wl.getHead().getNext().getNumber());
        assertEquals(5, wl.getSize());
    }

    @Test
    public void takeHeadWorksTwice() {
        wl.takeHead();
        CityKnot knot = wl.takeHead();
        assertEquals(1, knot.getNumber());
        assertEquals(2, wl.getHead().getNumber());
        assertEquals(3, wl.getHead().getPrev().getNumber());
        assertEquals(5, wl.getHead().getNext().getNumber());
        assertEquals(4, wl.getSize());
    }

    @Test
    public void rotateWorks() {
        for (int i = 0; i < 6; i++) {
            assertEquals(i, wl.getHead().getNumber());
            assertEquals(6, wl.getSize());
            wl.rotate();
        }
    }

    @Test
    public void rotateDelInsertCombinationWorks() {
        CityKnot knot = wl.takeHead();
        assertEquals(0, knot.getNumber());
        assertEquals(1, wl.getHead().getNumber());
        wl.insert(knot);
        wl.rotate();
        assertEquals(1, wl.getHead().getNumber());
        assertEquals(0, wl.getHead().getNext().getNumber());
        knot = wl.takeHead();
        assertEquals(1, knot.getNumber());
        assertEquals(2, wl.getHead().getNumber());
        wl.insert(knot);
        assertEquals(1, wl.getHead().getNumber());
    }

    @Test
    public void constructorSetsHeadZeroAndNumbersInOrder() {
        CityKnot knot = wl.getHead();
        for (int i = 0; i < 6; i++) {
            assertEquals(i, knot.getNumber());
            knot = knot.getPrev();
        }
    }

    @Test
    public void takeHeadAntaaNullinTyhjastaListasta() {
        assertEquals(true, empty.takeHead() == null);
    }

    @Test
    public void emptyReturnsRightValues() {
        assertEquals(true, empty.empty());
        assertEquals(false, wl.empty());
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
