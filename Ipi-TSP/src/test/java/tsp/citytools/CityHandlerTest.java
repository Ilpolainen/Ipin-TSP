/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.citytools;

import tsp.citytools.City;
import tsp.citytools.CityHandler;
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
public class CityHandlerTest {

    private CityHandler CH;

    public CityHandlerTest() {
    }

    @Before
    public void setUp() {
        this.CH = new CityHandler();
    }

    @Test
    public void distanceBetweenGivesCorrectDistance() {
        City c1 = new City(1, 30, 0);
        City c2 = new City(2, 0, 40);
        City c3 = new City(3, 0, 0);
        double a = CH.distanceBetween(c1, c2);
        double b = CH.distanceBetween(c2, c3);
        double c = CH.distanceBetween(c3, c1);
        assertEquals(50, (int) a);
        assertEquals(40, (int) b);
        assertEquals(30, (int) c);
    }
    
    
    @Test
    public void makeDistanceMatrixWorks() {
        CH.addCity(30, 0);
        CH.addCity(0, 40);
        CH.addCity(0, 0);
        CH.makeDistanceMatrix();
        for (int i = 0; i < 3; i++) {
            assertEquals(0, (int) CH.getDistanceMatrix()[i][i]);
        }
        assertEquals(50, (int) CH.getDistanceMatrix()[0][1]);
        assertEquals(50, (int) CH.getDistanceMatrix()[1][0]);
        assertEquals(40, (int) CH.getDistanceMatrix()[1][2]);
        assertEquals(40, (int) CH.getDistanceMatrix()[2][1]);
        assertEquals(30, (int) CH.getDistanceMatrix()[0][2]);
        assertEquals(30, (int) CH.getDistanceMatrix()[2][0]);
    }
    
    @Test
    public void distanceBetweenABGivesCorrectDistance() {
        CH.addCity(30, 0);
        CH.addCity(0, 40);
        CH.addCity(0, 0);
        CH.makeDistanceMatrix();
        double a = CH.distanceBetween(0, 1);
        double b = CH.distanceBetween(1, 2);
        double c = CH.distanceBetween(2, 0);
        assertEquals(50, (int) a);
        assertEquals(40, (int) b);
        assertEquals(30, (int) c);
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
