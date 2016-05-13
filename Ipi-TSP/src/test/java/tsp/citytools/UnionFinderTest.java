/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.citytools;

import tsp.citytools.City;
import tsp.citytools.UnionFinder;
import tsp.citytools.Connection;
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
public class UnionFinderTest {

    private UnionFinder UF;
    private Connection[] connections;
    private City[] cities;

    public UnionFinderTest() {
    }

    @Before
    public void setUp() {
        connections = new Connection[8];
        cities = new City[8];
        UF = new UnionFinder(8);
        for (int i = 0; i < 8; i++) {
            City c = new City(i, i, i);
            cities[i] = c;
        }
        for (int i = 0; i < 7; i++) {
            Connection con = new Connection(cities[i], cities[i + 1]);
            connections[i] = con;
        }
        Connection con = new Connection(cities[7], cities[0]);
        connections[7] = con;
    }

    @Test
    public void findWorksWithoutUnion() {
        for (int i = 0; i < 8; i++) {
            assertEquals(i, UF.find(i));
        }
        UF.getParents()[0] = 5;
        UF.getParents()[7] = 5;
        boolean truth1 = UF.find(0) == UF.find(7);
        boolean truth2 = UF.find(7) == UF.find(5);
        boolean truth3 = UF.find(7) == UF.find(3);
        assertEquals(true, truth1);
        assertEquals(true, truth2);
        assertEquals(false, truth3);
    }
    
    @Test
    public void unionWorks() {
        UF.union(0, 1);
        assertEquals(1, UF.getParents()[1]);
        assertEquals(1, UF.getParents()[0]);
        assertEquals(1, UF.getHeights()[1]);
        UF.union(2, 1);
        assertEquals(1, UF.getParents()[2]);
        assertEquals(1, UF.getHeights()[1]);
        UF.union(3, 1);
        assertEquals(1, UF.getParents()[2]);
        assertEquals(1, UF.getHeights()[1]);
        UF.union(4, 5);
        assertEquals(5, UF.getParents()[4]);
        assertEquals(1, UF.getHeights()[5]);
        UF.union(5, 1);
        assertEquals(1, UF.getParents()[5]);
        assertEquals(2, UF.getHeights()[1]);
    }
    

    @Test
    public void formingCycleWorksAndUnionFindDoesTheJob() {
        for (int i = 0; i < 7; i++) {
            boolean truth = UF.formingCycle(connections[i]);
            assertEquals(false, truth);
        }
        boolean truth = UF.formingCycle(connections[7]);
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
