/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.interfaces;

import tsp.algorithms.Algorithm;
import tsp.algorithms.BruteForce;
import tsp.algorithms.ClarkeWright;
import tsp.algorithms.SimulatedAnnealing;
import tsp.citytools.CityHandler;
import tsp.citytools.Connection;
import tsp.citytools.RouteHandler;

/**
 *
 * @author omistaja
 */
public class Controller {

    private CityHandler cityHandler;
    private RouteHandler routeHandler;
    private InputHandler checker;
    private final Renderer renderer;
    private Algorithm algorithm;
    private Animator animator;
    private final Mouse mouse;
    private final InputFrame frame;
    private final ButtonListener buttonListener;
    private int[] overAllBestRoute;
    private String[] overallBestInfo;
    private boolean algorithmExecuted;

    public Controller() {
        this.checker = new InputHandler();
        this.cityHandler = new CityHandler();
        this.routeHandler = new RouteHandler(this.cityHandler);
        this.renderer = new Renderer();
        this.mouse = new Mouse(this);
        this.frame = new InputFrame(this);
        this.buttonListener = new ButtonListener(this);
        this.algorithmExecuted = false;
    }

    public boolean isAlgorithmExecuted() {
        return algorithmExecuted;
    }

    public void setAlgorithmExecuted(boolean algorithmExecuted) {
        this.algorithmExecuted = algorithmExecuted;
    }
    
    

    public void run() {
        this.frame.run();
    }

    public CityHandler getCityHandler() {
        return cityHandler;
    }

    public Animator getAnimator() {
        return animator;
    }

    public String[] getOverallBestInfo() {
        return overallBestInfo;
    }

    public void setOverAllBestRoute(int[] overAllBestRoute) {
        this.overAllBestRoute = overAllBestRoute;
    }

    public void setOverallBestInfo(String[] overallBestInfo) {
        this.overallBestInfo = overallBestInfo;
    }

    public ButtonListener getButtonListener() {
        return buttonListener;
    }

    public int[] getOverAllBestRoute() {
        return overAllBestRoute;
    }

    public InputFrame getFrame() {
        return frame;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public RouteHandler getRouteHandler() {
        return routeHandler;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void addCity(int x, int y) throws InterruptedException {
        cityHandler.addCity(x, y);
        renderer.upDate(cityHandler.getCityList(), null, null);
    }

    public void simulatedAnnealingWithAnimation() throws InterruptedException {
        if (cityHandler.getCityArray() == null || cityHandler.getCityArray().length < 2) {
            return;
        }
        checker.findValuesFromFrame(frame.getFrame());
        routeHandler.constructAllConnections();
        cityHandler.makeDistanceMatrix();
        algorithm = new SimulatedAnnealing(checker.getStartTemp(), checker.getAlpha(), checker.getFinalTemp() ,routeHandler);
        if (animator == null) {
            this.animator = new Animator(this);
            animator.start();
        } else {
            animator.setAlgorithm(algorithm);
            animator.restart();
        }
        checkBest();
    }

    public void simulatedAnnealing() throws InterruptedException {
        if (cityHandler.getCityArray() == null || cityHandler.getCityArray().length < 2) {
            return;
        }
        checker.findValuesFromFrame(frame.getFrame());
        routeHandler.constructAllConnections();
        cityHandler.makeDistanceMatrix();
        algorithm = new SimulatedAnnealing(checker.getStartTemp(), checker.getAlpha(), checker.getFinalTemp() ,routeHandler);
        algorithm.run();
        checkBest();
        renderer.upDate(cityHandler.getCityList(), routeHandler.convertRouteToConnections(algorithm.getFinalRoute()), algorithm.getInfoArray());
    }

    public void bruteForce() throws InterruptedException {
        if (cityHandler.getCityArray() == null || cityHandler.getCityArray().length < 2) {
            return;
        }
        routeHandler.constructAllConnections();
        cityHandler.makeDistanceMatrix();
        this.algorithm = new BruteForce(routeHandler, cityHandler);
        algorithm.run();
        int[] route = algorithm.getFinalRoute();
        routeHandler.tulostaReitti(route);
        Connection[] cons = routeHandler.convertRouteToConnections(route);
        routeHandler.tulostaYhteydet2(cons);
        renderer.upDate(cityHandler.getCityList(), routeHandler.convertRouteToConnections(route), algorithm.getInfoArray());
    }

    public void clarkeWrightSingle() throws InterruptedException {
        if (cityHandler.getCityArray() == null || cityHandler.getCityArray().length < 2) {
            return;
        }
        routeHandler.constructAllConnections();
        cityHandler.makeDistanceMatrix();
        this.algorithm = new ClarkeWright(this.cityHandler, this.routeHandler, 0);
        algorithm.run();
        renderer.upDate(cityHandler.getCityList(), algorithm.getConnectionsRoute(), algorithm.getInfoArray());
    }

    public void clarkeWrightMulti() throws InterruptedException {
        if (cityHandler.getCityArray() == null || cityHandler.getCityArray().length < 2) {
            return;
        }
        routeHandler.constructAllConnections();
        cityHandler.makeDistanceMatrix();
        this.algorithm = new ClarkeWright(this.cityHandler, this.routeHandler, -1);
        algorithm.run();
        int[] route = algorithm.getFinalRoute();
//        routeHandler.tulostaReitti(route);
        renderer.upDate(cityHandler.getCityList(), routeHandler.convertRouteToConnections(route), algorithm.getInfoArray());
    }

    public void checkBest() {
        if (overAllBestRoute == null || routeHandler.countRouteLength(overAllBestRoute) > routeHandler.countRouteLength(algorithm.getFinalRoute())) {
            overAllBestRoute = algorithm.getFinalRoute();
            overallBestInfo = algorithm.getInfoArray();
        }
    }

    public void showOverallBest() throws InterruptedException {
        renderer.upDate(cityHandler.getCityList(), routeHandler.convertRouteToConnections(this.overAllBestRoute), this.overallBestInfo);
    }

}
