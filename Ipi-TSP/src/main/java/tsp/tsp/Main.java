/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.tsp;

import java.util.*;
import tsp.algorithms.BruteForce;
import tsp.algorithms.SimulatedAnnealing;
import tsp.citytools.City;
import tsp.citytools.CityHandler;
import tsp.citytools.RouteHandler;
import tsp.interfaces.Controller;

/**
 *
 * @author omistaja
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.run();
    }
}
