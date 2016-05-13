/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.algorithms;

import tsp.algorithms.BruteForce;
import tsp.citytools.City;
import tsp.citytools.CityHandler;
import tsp.citytools.RouteHandler;
import java.util.ArrayList;
import java.util.Random;
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
public class BruteForceTest {

    private CityHandler chS;
    private CityHandler chM;
    private CityHandler chL;
    private RouteHandler rhS;
    private RouteHandler rhM;
    private RouteHandler rhL;
    private ArrayList<City> citylist;
    private BruteForce algorithm;

    public BruteForceTest() {

    }

    @Before
    public void setUp() {
        this.citylist = new ArrayList();
        konstruoiKaupungit();
        this.chS = new CityHandler();
        this.chM = new CityHandler();
        this.chL = new CityHandler();
        syotaKaupungit(chS, 4);
        syotaKaupungit(chM, 8);
        syotaKaupungit(chL, 12);
        teeEtaisyysMatriisit();
        rhS = new RouteHandler(chS);
        rhM = new RouteHandler(chM);
        rhL = new RouteHandler(chL);
        konstruoiYhteydet();
    }

    private void konstruoiYhteydet() {
        rhS.constructAllConnections();
        rhM.constructAllConnections();
        rhL.constructAllConnections();
    }

    public void konstruoiKaupungit() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                City c = new City(i * 4 + j, i, j);
                citylist.add(c);
            }
        }
    }

    public void teeEtaisyysMatriisit() {
        chS.makeDistanceMatrix();
        chM.makeDistanceMatrix();
        chL.makeDistanceMatrix();
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
    public void algoritmiAntaaLyhimmanPolunNeljallaKaupungilla() {
        this.algorithm = new BruteForce(rhS, chS);
        algorithm.run();
        int pituus = (int) algorithm.getBestLength();
        int oikeaVastaus = 6;
        int[] route = algorithm.getFinalRoute();
        assertEquals(oikeaVastaus, pituus);
        assertEquals(oikeaVastaus, (int) rhS.countRouteLength(route));
    }

    @Test
    public void algoritmiAntaaLyhimmanPolunKahdeksallaKaupungilla() {
        this.algorithm = new BruteForce(rhM, chM);
        algorithm.run();
        int pituus = (int) algorithm.getBestLength();
        int oikeaVastaus = 8;
        int[] route = algorithm.getFinalRoute();
        assertEquals(oikeaVastaus, pituus);
        assertEquals(oikeaVastaus, (int) rhM.countRouteLength(route));
    }

    @Test
    public void algoritmiAntaaLyhimmanPolunKahdellatoistaKaupungilla() {
        this.algorithm = new BruteForce(rhL, chL);
        algorithm.run();
        int pituus = (int) algorithm.getBestLength();
        int oikeaVastaus = 12;
        int[] route = algorithm.getFinalRoute();
        assertEquals(oikeaVastaus, pituus);
        assertEquals(oikeaVastaus, (int) rhL.countRouteLength(route));
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
