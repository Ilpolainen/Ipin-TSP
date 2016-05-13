/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.algorithms;

import tsp.citytools.CityHandler;
import tsp.citytools.Connection;
import tsp.citytools.RouteHandler;
import tsp.datastructures.CityKnot;
import tsp.datastructures.WheelList;

/**
 * Tämä algoritmi ratkaisee lyhimmän mahdollisen Hamiltonin kierroksen
 * annettujen koordinaattien välillä. Tärkeä optimointi, joka katkaisee
 * syvyyshaun oksia, jos ne osoittautuvat kelvottomiksi, löytyy kommentoidusta
 * kohdasta. Ongelma on NP-täydellinen ja BruteForcen aikavaativuus on O(n!).
 * HUOMATTAVAA ON, ETTÄ SIMULATED ANNEALING LÖYTÄÄ HYVIN HELPOSTI PARHAAN
 * MAHDOLLISEN REITIN TÄMÄN ALGORITMIN JÄRKEVÄÄN SUORITTAMISEEN KELPAAVILLA
 * SYÖTTEILLÄ (Ainakin omalla koneellani.). MUUTAMAN SUORITUSKERRAN - JOISTA
 * YKSI KESTÄÄ SILMÄNRÄPÄYKSEN - JÄLKEEN OPTIMAALINEN - REITTI YLEENSÄ LÖYTYY.
 * TÄMÄ TEKEE TUUNATUN BRUTE FORCEN KÄYTÄNNÖSSÄ HYÖDYTTÖMÄKSI MUUHUN KUIN
 * TESTAAMISEEN.
 *
 * @author omistaja
 */
public class BruteForce implements Algorithm {

    private RouteHandler RH;
    private CityHandler CH;
    private double bestLength;
    private int[] routeAsList;
    private int[] bestRouteAsList;
    private int[] fRoute;
    private boolean iterating;

    /**
     * O(n) tulee Routehandlerin makeSimpleRoute:sta ja uuden n-arrayn
     * luomisesta.
     *
     * @param RH
     * @param CH
     */
    public BruteForce(RouteHandler RH, CityHandler CH) {
        this.RH = RH;
        this.CH = CH;
        RH.makeSimpleRoute();
        routeAsList = new int[CH.getCityArray().length];
        this.bestRouteAsList = this.routeAsList.clone();
        fRoute = RH.getRoute();
        bestLength = RH.countRouteLength(fRoute);
    }

    public double getBestLength() {
        return bestLength;
    }

    /**
     * Käynnistää algoritmin. O((n-1)!), josta analyysi runAlgorithm:in
     * kohdalla.
     */
    @Override
    public void run() {
        runAlgorithm(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Rakentaa rengaslistan ja kutsuu rekursiota. . O((n)!). Rengaslistan
     * konstruktori on O(n) (n on kaupunkien määrä), wl.takehead() on O(1)
     * (Analyysit omassa luokissaan). Rekursio O((n)!) goToNextCity:ssä.
     */
    public void runAlgorithm() {
        WheelList wl = new WheelList(CH.getCityArray().length);
        wl.takeHead();
        this.bestRouteAsList[0] = 0;
        goToNextCity(wl, 0, 0, 1);
    }

    /**
     * Rekursiometodi sisältää muuten ainoastaan O(1) -operaatioita (omissa
     * luokissaan analyysit), mutta clone() on O(n)-operaatio ja luuppi kutsuu
     * rekursiota yhtä monta kertaa, kuin on kaupunkeja vielä käymättä. Jos
     * pidettäisiin kirjaa ainoastaan reitin pituudesta, päästäisiin O((n-1)!),
     * silä tällöin ei tarvitsisi kutsua metodia save(). Ensimmäisessä vaiheessa
     * n-1 kertaa, toisessa n-2 kertaa jne. Lopulta kutsuja tulee siis (n-1)!.
     * Metodi sisältää tärkeän optimoinnin, joka estää menemästä reitillä enää
     * pitemmälle, jos sen mitta on jo pidempi kuin tähänastinen paras.
     * Tilavaativuus on O(n) rekursiokutsuista johtuen. Jostain syystä
     * manuaalinen for-luuppikopionti ei toimi. Tämä pitäisin tilavaativuuden
     * O(n):ssä.
     *
     * @param wl Kaksisuuntainen rengaslista, jota käytetään pitämään kirjaa
     * vielä käymättömistä kaupungeista.
     * @param currentLength tähän asti kuljetun reitin pituus
     * @param prev reitissä tätä edeltävän kaupungin numero
     * @param listNumber numero, joka kertoo monesko kaupunki on valittavana
     */
    public void goToNextCity(WheelList wl, double currentLength, int prev, int listNumber) {
        if (listNumber == this.bestRouteAsList.length) {
            if (currentLength + CH.distanceBetween(prev, 0) <= bestLength) {
                bestLength = currentLength + CH.distanceBetween(prev, 0);
                save();
                return;
            } else {
                return;
            }
        }
        for (int i = 0; i < this.routeAsList.length - listNumber; i++) {
            CityKnot knot = wl.takeHead();
            this.routeAsList[listNumber] = knot.getNumber();
            double newLength = currentLength + CH.distanceBetween(knot.getNumber(), prev);
            //TÄRKEÄ OPTIMOINTI JOKA MAHDOLLISTAA 10 SEKUNNIN RATKAISUN YLEENSÄ JOPA 14:LLÄ KAUPUNGILLA 12:DEN SIJAAN.
            //PAHIMMASSA TAPAUKSESSA AIKA EI PARANE, MUTTA LÄHES AINA AIKA LYHENEE HUOMATTAVASTI. 
//            Testattuna ilman renderöintiä, tällä sai vielä 17 kaupunkiakin ihan jees menemään, jossa ilman tätä menis noin puol kuukautta.
            if (newLength + CH.distanceBetween(knot.getNumber(), 0) > bestLength) {
                wl.insert(knot);
                wl.rotate();
                continue;
            }
            goToNextCity(wl, newLength, knot.getNumber(), listNumber + 1);
            wl.insert(knot);
            wl.rotate();
        }
    }

    @Override
    public void iterate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean iterating() {
        return iterating;
    }

    @Override
    public void setIterating(boolean b) {
        this.iterating = b;
    }

    @Override
    public int[] getRoute() {
        return this.getFinalRoute(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Palauttaa lopullisen reitin muodossa: a->b <=> b = a+1; Selvä O(n).
     *
     * @return
     */
    @Override
    public int[] getFinalRoute() {
        for (int i = 0; i < fRoute.length - 1; i++) {
            fRoute[i] = this.bestRouteAsList[i + 1];
        }
        fRoute[fRoute.length - 1] = 0;
        return fRoute; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] getInfoArray() {
        String[] info = new String[2];
        String length = "length: " + this.bestLength;
        String flength = "froute length: " + RH.countRouteLength(fRoute);
        info[0] = length;
        info[1] = flength;
        return info;//To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Ei kovinkaan olennainen metodi. Aikavaativuus: O(n) tulee RouteHandlerin
     * metodeista makeSimpleList ja countRouteLength, sekä taulukon luonnista
     * (analyysit omissa luokissaan).
     */
    @Override
    public void reset() {
        RH.makeSimpleRoute();
        routeAsList = new int[CH.getCityArray().length];
        this.bestRouteAsList = this.routeAsList;
        fRoute = RH.getRoute();
        bestLength = RH.countRouteLength(fRoute);
    }

    /**
     * Kopioi tämänhetkisen routeAsListin bestRouteAsListiksi ilman, että
     * tarvitsee viedä lisätilaa clone-kopiolla. Selvä aikavaativuus selvä O(n).
     * Tilavaativuus on O(0);
     */
    public void save() {
        for (int i = 0; i < this.fRoute.length; i++) {
            this.bestRouteAsList[i] = this.routeAsList[i];
        }
    }

    @Override
    public Connection[] getConnectionsRoute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
