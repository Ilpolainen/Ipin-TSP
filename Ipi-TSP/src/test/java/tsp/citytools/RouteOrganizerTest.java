/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.citytools;

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
public class RouteOrganizerTest {
    
    private RouteOrganizer RO;
    private Connection[] cons;
    private City[] cities;
     
    public RouteOrganizerTest() {
    }
  
    @Before
    public void setUp() {
        RO = new RouteOrganizer();
        cons = new Connection[9];
        cities = new City[9];
        for (int i = 0; i < 9; i++) {
            cities[i] = new City(i, i, i*i);
        }
        
        for (int i = 0; i < 5; i++) {
            Connection con = new Connection(cities[i], cities[i+1]);
            cons[i] = con;
        }
        for (int i = 8; i > 5; i--) {
            Connection con = new Connection(cities[i], cities[i-1]);
            cons[i-1] = con;
        }
        cons[8] = new Connection(cities[8], cities[0]);
    }
    
    @Test
    public void saatavaReittiOnNollastaKahdeksaan() {
        int[] route = RO.convertConnectionsToRoute(cons);
        assertEquals(0, route[8]);
        for (int i = 0; i < 8; i++) {
            assertEquals(i + 1, route[i]);
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
