/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.citytools;

import tsp.datastructures.WheelList;
import java.util.ArrayList;
import java.util.Random;

/**
 * RouteHandler antaa erilaisia työkaluja TSP-algoritmeille.
 *
 * @author omistaja
 */
public class RouteHandler {

    private final CityHandler cityHandler;
    private int[] route;
    private Connection[] routeConnections;
    private Connection[][] allConnections;
    private final Random random;
    private RouteOrganizer organizer;

    /**
     * Tekee uuden RouteHandlerin annetulla CityHandlerilla. Ilmeinen O(1).
     *
     * @param cityHandler CityHandler is a tool to give input, and handle the
     * vectors on the route.
     */
    public RouteHandler(CityHandler cityHandler) {
        this.cityHandler = cityHandler;
        this.random = new Random();
        this.organizer = new RouteOrganizer();
    }

    public int[] getRoute() {
        return route;
    }

    public Connection[] getRouteConnections() {
        return routeConnections;
    }

    /**
     * Luo kaupunkien välille kaikki mahdolliset Connection -oliot. O(n^2) tulee
     * kahdesta sisäkkäisestä luupista. Analyysi: Luo uuden n*n -matriisit
     * O(n^2) + sisäkkäiset n-lupit O(n^2) joissa O(1)-operaatio +
     * makeSimpleRoute O(n).
     */
    public void constructAllConnections() {
        City[] cities = this.cityHandler.getCityArray();
        this.allConnections = new Connection[cities.length][cities.length];
        for (int i = 0; i < cities.length; i++) {
            for (int j = 0; j < cities.length; j++) {
                this.allConnections[i][j] = new Connection(cities[i], cities[j]);
            }
        }
        this.makeSimpleRoute();
    }

    /**
     * Luo numerojärjestyksessä kulkevan reitin Connection-oliomuodossa. O(n)
     * Analyysi: n-taulukon luonti O(n) + for-loop O(n).
     *
     */
    public void makeSimpleRoute() {

        City[] cities = cityHandler.getCityArray();
        if (cities == null) {
            return;
        }
        int length = cities.length;
        route = new int[length];
        routeConnections = new Connection[length];
        for (int i = 0; i < length - 1; i++) {
            routeConnections[i] = new Connection(cities[i], cities[i + 1]);
            route[i] = i + 1;
        }
    }

    /**
     * Luo random Hamiltonin kierroksen muodossa: route[i] = a, route[i+1] = b
     * stands for a->b. O(n^2). Analyysi: Array-konstruktori O(n) +
     * WheelList-construktori O(n) + takeHead O(1), sisäkkäiset luupit
     * (Ulkoluuppi n-1 kertaa, sisäluuppi random kertaa, missä
     * worstcase-scenario on n) Sisällä rengaslistan rotate O(1). Ulko luuppi
     * sisältää myös Array-Insertin. -> O(n) + O(n) + O(1) + O((n-1)*(n-1)) =
     * O(n^2)
     *
     * @return Random route through every city once ending to the starting city
     * in form of route where route[i] = a , route[i + 1] = b stands for a->b.
     */
    public int[] getRandomRoute() {
        int length = this.cityHandler.getCityArray().length;
        int[] randomRoute = new int[length];
        WheelList cityList = new WheelList(length);
        cityList.takeHead();
        randomRoute[randomRoute.length - 1] = 0;
        for (int i = 0; i < randomRoute.length - 1; i++) {
            for (int j = 0; j < this.random.nextInt(length - 1); j++) {
                cityList.rotate();
            }
            randomRoute[i] = cityList.takeHead().getNumber();
        }
        return randomRoute;
    }

    /**
     * Laskee Hamiltonin kierroksen pituuden: route[i] = a ja route[i + 1] = b
     * tarkoittaa väliä a->b. O(n) Analyysi: Length-arvo saadaan suoraan indexin
     * perusteella -> O(1). n-luuppi, joka sisältää O(1)-operaation = O(n).
     *
     * @param route
     * @return routeLengthF
     */
    public double countRouteLength(int[] route) {
        double[][] matrix = this.cityHandler.getDistanceMatrix();
        double length = 0;
        for (int i = 0; i < route.length - 1; i++) {
            length += matrix[route[i]][route[i + 1]];
        }
        length += matrix[route[route.length - 1]][route[0]];
        return length;
    }

    /**
     * Konvertoi reitin Connection-muodosta int[] muotoon, joka on edellä
     * esitelty. O(n). Analyysi: n-luuppi joka sisältää indeksihaun O(1) = O(n).
     *
     * @param route Route presented as: route[i] = a and route[i+1] stands for
     * a->b.
     * @return route presented as Connection-objects Connection[a] = Connection
     * starting from route[a] to route[a+1] (or route[0], if the last one).
     */
    public Connection[] convertRouteToConnections(int[] route) {
        for (int i = 0; i < route.length - 1; i++) {
            this.routeConnections[i] = this.allConnections[route[i]][route[i + 1]];
        }
        this.routeConnections[route.length - 1] = this.allConnections[route[route.length - 1]][route[0]];
        return this.routeConnections;
    }

    /**
     * Edellisen käänteinen operaatio, joka saa syötteenä järjestämättömän
     * joukon Connection-olioita, mikä mutkistaa prosessia. Jos annettu syöte ei
     * muodosta Hamiltonin kierrosta, käyttäytyy metodi ennakoimattomasti. O(n).
     * Analyysi RouteOrganizer -luokassa.
     *
     * @param routeConnections route presented as: Connection-objects in no
     * particular order
     * @return route presented as: route[i] = a and route[i + 1] = b stands for
     * a->b.
     */
    public int[] convertConnectionsToRoute(Connection[] routeConnections) {
        int[] converted = organizer.convertConnectionsToRoute(routeConnections);
        return converted;
    }

    /**
     * to be erased
     *
     * @param route
     */
    public void tulostaReitti(int[] route) {
        for (int i = 0; i < route.length - 1; i++) {
            System.out.println("(" + route[i] + "," + route[i + 1] + ")");
        }
        System.out.println("(" + 0 + "," + route[0] + ")");
        System.out.println("");
    }

    /**
     * to be erased
     *
     * @param connections
     */
    public void tulostaYhteydet(ArrayList<Connection> connections) {
        for (Connection con : connections) {
            System.out.println("from " + con.getStart().getNumber() + " to " + con.getEnd().getNumber());
        }
        System.out.println("");
    }

    public void tulostaYhteydet2(Connection[] cons) {
        for (Connection con : cons) {
            System.out.println("from " + con.getStart().getNumber() + " to " + con.getEnd().getNumber());
        }
        System.out.println("");
    }
}
