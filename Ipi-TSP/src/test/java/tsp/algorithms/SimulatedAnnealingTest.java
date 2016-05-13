/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.algorithms;

import tsp.algorithms.BruteForce;
import tsp.algorithms.SimulatedAnnealing;
import tsp.citytools.CityHandler;
import tsp.citytools.RouteHandler;
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
public class SimulatedAnnealingTest {

    private CityHandler ch;
    private RouteHandler rh;
    private BruteForce bf;
    private SimulatedAnnealing presetAlgorithm;
    private SimulatedAnnealing alg;

    public SimulatedAnnealingTest() {
    }

    @Before
    public void setUp() {
        ch = new CityHandler();
        rh = new RouteHandler(ch);
        Random random = new Random();
        for (int i = 0; i < 13; i++) {
            ch.addCity(random.nextInt(300), random.nextInt(300));
        }
        ch.makeDistanceMatrix();
        rh.constructAllConnections();
        presetAlgorithm = new SimulatedAnnealing(rh);
        alg = new SimulatedAnnealing(100, 0.9997, 0.005, rh);
        bf = new BruteForce(rh, ch);
    }

    @After
    public void tearDown() {
    } 
    
    @Test
    public void resetWorks() {
        this.presetAlgorithm.run();
        this.presetAlgorithm.reset();
        assertEquals(true, presetAlgorithm.isCooling());
        assertEquals(true, presetAlgorithm.getCurrentTemperature() == 10);
        assertEquals(true ,presetAlgorithm.getCurrentTemperature() == presetAlgorithm.getStartingTemperature());
        assertEquals(true, (int) presetAlgorithm.getFinalLength() == (int) presetAlgorithm.getRouteLength());
    }

    @Test
    public void algortihmFindsRoute101PercentOfOptimalWith13CitiesAndPresetSettings() {
        presetAlgorithm.run();
        bf.run();
        double tulos = rh.countRouteLength(presetAlgorithm.getFinalRoute());
        double oikeaVastaus = bf.getBestLength();
        assertEquals(true, tulos / oikeaVastaus < 1.02);
    }

    @Test
    public void algorithmIsFastWithBetterSettingsAnd30Cities() {
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            ch.addCity(random.nextInt(300), random.nextInt(300));
        }
        ch.makeDistanceMatrix();
        rh.constructAllConnections();
        Long previousTime = System.nanoTime();
        alg.run();
        Long currentTime = System.nanoTime();
        assertEquals(true, previousTime-currentTime < 10000000);
    }
    
    @Test
    public void getInfoWorks() {
        presetAlgorithm.run();
        double finalLength = presetAlgorithm.getFinalLength();
        double routeLength = presetAlgorithm.getRouteLength();
        double currentTemperature = presetAlgorithm.getCurrentTemperature();
        String[] info1 = new String[2];
        info1[0] = ("Total length: " + finalLength);
        info1[1] = ("Temperature: " + currentTemperature);
        String[] info = presetAlgorithm.getInfoArray();
        for (int i = 0; i < 2; i++) {
            assertEquals(true, info[i].equals(info1[i]));
        }
        presetAlgorithm.setCooling(true);
        String[] info2 = new String[2];
        info2[0] = ("Total length: " + routeLength);
        info2[1] = ("Temperature: " + currentTemperature);
    }
    
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
