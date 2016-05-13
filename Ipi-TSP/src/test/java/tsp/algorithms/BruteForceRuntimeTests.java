/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.algorithms;

import java.util.ArrayList;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tsp.citytools.City;
import tsp.citytools.CityHandler;
import tsp.citytools.RouteHandler;

/**
 *
 * @author vipohjol
 */
public class BruteForceRuntimeTests {

    
    private ArrayList<City> citylist;

    public BruteForceRuntimeTests() {
    }

     @Before
    public void setUp() {
        this.citylist = new ArrayList();
        konstruoiKaupungit();
//        this.chS = new CityHandler();
        
//        syotaKaupungit(chS, 4);
   
        teeEtaisyysMatriisit();
//        rhS = new RouteHandler(chS);
        
        konstruoiYhteydet();
    }

    private void konstruoiYhteydet() {
//        rhS.constructAllConnections();
//        
    }

    public void konstruoiKaupungit() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                City c = new City(i * 4 + j, i, j);
                citylist.add(c);
            }
        }
        citylist.remove(citylist.size() - 1);
        citylist.remove(citylist.size() - 1);
    }

    public void teeEtaisyysMatriisit() {
//        chS.makeDistanceMatrix();
        
    }

    public void syotaKaupungit(CityHandler ch, int size) {
        for (int i = 0; i < size; i++) {
            ch.addCity(citylist.get(i).getX(), citylist.get(i).getY());
        }
    }

    @After
    public void tearDown() {
    }

   
    @Test
    public void algoritmiSuorittaaKahdellatoistaRandomKaupungillaLaskennanAlleSekuntiin() {
        CityHandler ch = new CityHandler();
        RouteHandler rh = new RouteHandler(ch);
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            ch.addCity(random.nextInt(300), random.nextInt(300));
        }
        ch.makeDistanceMatrix();
        rh.constructAllConnections();
        BruteForce bf = new BruteForce(rh, ch);
        Long previousTime = System.nanoTime();
        bf.run();
        Long currentTime = System.nanoTime();
        assertEquals(true, previousTime-currentTime < 1000000000);
    }
}