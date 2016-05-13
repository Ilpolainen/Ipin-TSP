/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.algorithms;

import tsp.citytools.CityHandler;
import tsp.citytools.Connection;
import tsp.citytools.RouteHandler;
import tsp.citytools.SavingsComparator;
import tsp.citytools.UnionFinder;
import tsp.datastructures.Heap;

/**
 * Valitsin tämän algoritmin sattumalta ja myöhemmin huomasin, että se onkin
 * tarkoitettu VRP-hen, joka on sukua TSP-lle. TSP:n ratkaisijaksi tämä ei ole
 * erityisen nopea tai tarkka. Jonkinlaisen järkevän tuloksen tällä saa
 * soveltamalla tätä "multihubina". Single hubissa valitaan yksi keskus (hub),
 * johon suhteessa lasketaan savings-matriisi. Matriisissa on aikkien kaupunkien
 * välisten yhteyksien pituuksien sijaan "säästöt", jotka saavutetaan kulkemalla
 * tämä yhteys sen sijaan että kuljettaisiin hubin kautta sama väli. Yhteydet
 * valitaan savingsien perusteella valitsemalla ensin yheys jolla säästöt ovat
 * suurimmat jne.. Valinnassa pitää pitää huolta myös siitä, että yhteys ei
 * kelpaa, mikäli se muodostaa kehän tai risteyksen. Lopuksi lisätään reittiin
 * vielä hub. Multihubissa valitaan paras näistä muodostuneista reiteistä.
 *
 * @author omistaja
 */
public class ClarkeWright implements Algorithm {

    private double[][][] savings;
    private CityHandler cityHandler;
    private RouteHandler routeHandler;
    private int[] route;
    private double routeLength;
    private int[] finalRoute;
    private double finalRouteLength;
    private boolean multiHub;
    private int userHub;
    private Connection[] connectionsRoute;

    public ClarkeWright(CityHandler cityHandler, RouteHandler routeHandler, int hub) {
        int nOC = cityHandler.getCityArray().length;
        this.cityHandler = cityHandler;
        this.routeHandler = routeHandler;
        this.savings = new double[nOC][nOC][nOC];
        this.route = new int[nOC];
        if (hub < 0) {
            multiHub = true;
        } else if (hub >= nOC) {
            userHub = nOC;
        } else {
            userHub = hub;
        }
    }

  

    
    /**
     * Suoraviivainen n*n -matriisinmuodostus sisäkkäisillä luupeilla. Tätä
     * voisi optimoida, sillä meille riittäisi puolikas symmetrisyydestä
     * johtuen. O(n^2).
     *
     * @param hub
     */
    private void countSavingsMatrix(int hub) {
        double[][] distanceMatrix = this.cityHandler.getDistanceMatrix();
        int size = this.cityHandler.getCityArray().length;
        double[][] hubSavings = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != hub && j != hub && i != j) {
                    hubSavings[i][j] = distanceMatrix[i][hub] + distanceMatrix[hub][j] - distanceMatrix[i][j];
                }
            }
        }
        this.savings[hub] = hubSavings;
    }

    @Override
    public void run() {
        if (trivial()) {
            return;
        }
        if (multiHub == false) {
            this.runSingleHub(userHub);
        } else {
            this.runMultiHub();
        }
    }

    /**
     * Suoraviivainen tarkistus ja lasku niissä tapauksissa, että kaupunkeja
     * korkeintaan kaksi. Kolmaskin olisi tietenkin triviaali (Ehkä refactoroin
     * siksi). Periaatteessa O(1), sillä ainoata epätriviaalia metodia kutsutaan
     * vain jos n on 1 tai 2.
     *
     * @return palauttaa true, jos kaupunkeja korkeintaan kaksi.
     */
    private boolean trivial() {
        if (this.cityHandler.getCityList() == null) {
            return true;
        }
        if (this.cityHandler.getCityList().size() == 1) {
            this.route = this.routeHandler.getRandomRoute();
            this.finalRoute = route;
            this.routeLength = 0.0;
            this.finalRouteLength = 0.0;
            return true;
        }
        if (this.cityHandler.getCityList().size() == 2) {
            this.route = this.routeHandler.getRandomRoute();
            this.finalRoute = route;
            this.routeLength = this.routeHandler.countRouteLength(route);
            this.finalRouteLength = this.routeLength;
            return true;
        }
        return false;
    }

    /**
     * Suorittaa Clarke-Wrightin savings-algoritmin.
     * O(n^2*log(n)) + O(n^2) = O(n^2*log(n))
     * @param hub Annettu keskuskaupunki, johon suhteessa savings-painot lasketaan.
     */
    private void runSingleHub(int hub) {
        this.countSavingsMatrix(hub);
        this.runAlgorithm(hub);
    }

    
    /**
     * Ilmiselvästi kuten runSingleHub, mutta suoritetaan n-kertaa. => O(n^3*log(n))
     */
    private void runMultiHub() {
        for (int i = 0; i < this.cityHandler.getCityList().size(); i++) {
            this.countSavingsMatrix(i);
            this.runAlgorithm(i);
        }
    }

    /**
     * Suorittaa algoritmin ajassa O(n^2 * log(n)). Aluksi yksi getteri ja
     * Vakioaikaisia oliokonstruktoreita. ConstructHeap on O(n^2 * log(n)),
     * jonka analyysi itse metodin kommenttina.
     *
     * @param hub
     */
    private void runAlgorithm(int hub) {
        this.connectionsRoute = new Connection[this.savings.length];
        double[][] hubSavings = this.savings[hub];
        SavingsComparator comparator = new SavingsComparator();
        Heap heap = new Heap(hubSavings.length * hubSavings.length,comparator);
        this.constructMyHeap(heap, hubSavings, hub);
//        constructHeap(savingsOrder, hubSavings, hub);
//        this.constructShortCuts(connectionsRoute, savingsOrder, route.length, hub);
        this.constructMyShortCuts(connectionsRoute, heap, route.length, hub);
//        System.out.println("hub: " + hub);

//        this.routeHandler.tulostaYhteydet(connectionsRoute);
        this.route = routeHandler.convertConnectionsToRoute(connectionsRoute);
//        System.out.println("" + this.routeHandler.countRouteLength(route));
//        System.out.println("");
        this.routeLength = routeHandler.countRouteLength(route);
        if (this.finalRoute == null || this.routeLength < this.finalRouteLength) {
            this.finalRoute = route;
            this.finalRouteLength = this.routeLength;
        }
    }

    /**
     * Rakentaa maksimikeon Connection -oliosta käyttäen avaimena näiden
     * "savingsWeight:iä", eli säästöä, joka saavutetaan kulkemalla Connection
     * -olion kaupunkien väli sen sijaan, että kuljettaisiin keskuskaupungin eli
     * "hub":in kautta.
     *
     * O(n^2 * log(n)) Sisäkkäinen kaksinkertainen n-luuppi, jonka sisällä
     * kutsutaan O(1) -kostruktoria ja setteriä. Näiden lisäksi siellä kutsutaan
     * heap-inserttiä, joka on O(log n) (analyysi Heap-luokassa). Eli O(n^2 *
     * log(n))
     *
     * @param savingsOrder Maksimikeko, jota rakennetaan.
     * @param hubSavings taulukko joka sisältää kaikki "savingsWeight:it", jotka
     * on täsmennetty yllä.
     * @param hub Keskus, johon suhteessa "savingWeight:it" lasketaan.
     */
    
    
    public void constructMyHeap(Heap savingsOrder, double[][] hubSavings, int hub) {
        for (int i = 0; i < hubSavings.length; i++) {
            for (int j = i + 1; j < hubSavings.length; j++) {
                if (i != hub && j != hub && i != j) {
                    Connection con = new Connection(cityHandler.getCityArray()[i], cityHandler.getCityArray()[j]);
                    con.setSavingsWeight(hubSavings[i][j]);
                    savingsOrder.heapInsert(con);
                }
            }
        }
    }

    /**
     * Ottaa savings -keosta Connection -olioita ja lisää niitä reittiin, jos ne
     * eivät muodosta risteyksiä tai kehiä. O(n^2 *log(n)), missä n = kaupunkien
     * lukumäärä (savingsOrderin koko on noin n^2 kpl). SavingsOrder
     * tyhjennetään luupissa, joten sisällä oleva koodi suoritetaan n^2 kertaa.
     * Heap-delMax O(log(n)) on analysoitu omassa luokassaan ja sen jälkeen
     * kutsuttava unionfinder.formingCycle on käytännössä yhteensä O(1). Tämä
     * täsmennetään UnionFinder-luokassa. Sen jälkeen vielä List-Insert ja pari
     * muuta vakioaikaista. Luupin jälkeen addHubToConnections O(n).
     *
     * @param connectionsRoute Lista johon kerätään algoritmin hyväksymät
     * Connection -oliot.
     * @param savingsOrder Maksimikeko Connection -olioita avaimenaan "savings"
     * -arvo.
     * @param length Kaupunkien määrä.
     * @param hub
     */
    
    
    public void constructMyShortCuts(Connection[] connectionsRoute, Heap savingsOrder, int length, int hub) {
        UnionFinder unionFinder = new UnionFinder(length);
        int[] vertexDegree = new int[length];
        System.out.println("");
        System.out.println("hub: " + hub);
        int i = 0;
        while (!savingsOrder.isEmpty()) {
            Connection con = savingsOrder.heapDelmax();
            
            if (!checkVertexDegree(con, vertexDegree)) {
                continue;
            }
            if (!unionFinder.formingCycle(con)) {
                System.out.println("Route: (" + con.getStart().getNumber() + "," + con.getEnd().getNumber() + ")");
                System.out.println("Savings: " + con.getSavingsWeight());
                connectionsRoute[i] = con;
                i++;
                vertexDegree[con.getStart().getNumber()]++;
                vertexDegree[con.getEnd().getNumber()]++;
            }
        }
        this.addHubToConnections(connectionsRoute, vertexDegree, hub);
    }

    /**
     * Selkeä O(1), jossa pidetään huolta, ettei reittiin synny risteystä.
     *
     * @param con
     * @param vertexDegree
     * @return Palauttaa false, jos risteys syntyy.
     */
    public boolean checkVertexDegree(Connection con, int[] vertexDegree) {
        if (vertexDegree[con.getStart().getNumber()] >= 2 || vertexDegree[con.getEnd().getNumber()] >= 2) {
            return false;
        }
        return true;
    }

    /**
     * Lisää hubin reittiin. O(n). For-luuppi käy pahimmillaan läpi jo
     * muodostetun reitin kaupungit, jonka pituus on n-1.
     *
     * @param connectionsRoute listamuotoinen
     * @param vertexDegrees sisältää kaupunkien vertex-degree-arvot, jotka
     * kertovat sopivan paikan hubille (vertexDegree == 1)
     * @param hub
     */
    public void addHubToConnections(Connection[] connectionsRoute, int[] vertexDegrees, int hub) {
        int start = 0;
        int end = 0;
        boolean startFound = false;
        for (int i = 0; i < vertexDegrees.length; i++) {
            if (i != hub) {
                if (startFound) {
                    if (vertexDegrees[i] == 1) {
                        end = i;
                        break;
                    }
                } else {
                    if (vertexDegrees[i] == 1) {
                        start = i;
                        startFound = true;
                    }
                }
            }
        }
        connectionsRoute[connectionsRoute.length - 2] = new Connection(cityHandler.getCityArray()[start], cityHandler.getCityArray()[hub]);
        connectionsRoute[connectionsRoute.length - 1] = new Connection(cityHandler.getCityArray()[hub], cityHandler.getCityArray()[end]);
    }

    @Override
    public void iterate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean iterating() {
        return false; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int[] getRoute() {
        return this.route; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int[] getFinalRoute() {
        return this.route; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] getInfoArray() {
        String[] info = new String[1];
        info[0] = "" + this.finalRouteLength;
        return info;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setIterating(boolean b) {
        
    }

    @Override
    public Connection[] getConnectionsRoute() {
        return this.connectionsRoute; //To change body of generated methods, choose Tools | Templates.
    }

    

}
