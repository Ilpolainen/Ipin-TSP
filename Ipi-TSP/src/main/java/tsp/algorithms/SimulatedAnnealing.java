/*
 *
 */
package tsp.algorithms;

import tsp.citytools.Connection;
import tsp.citytools.RouteHandler;
import tsp.datastructures.MyStack;
import java.util.Random;

/**
 * Tämä luokka suorittaa nimensä mukaisen TSP-algoritmin syötteelle, joka
 * saadaan RouteHandlerilta.
 */
public class SimulatedAnnealing implements Algorithm {

    private final RouteHandler routeHandler;
    private double coolingRatio;
    private double finalTemperature;
    private double currentTemperature;
    private double startingTemperature;
    private final Random random;
    private int[] route;
    private double routeLength;
    private int[] finalRoute;
    private double finalLength;
    private boolean cooling;
    private MyStack stack;

    /**
     * Alustaa algoritmin presettimuuttujilla.
     *
     *
     * @param RouteHandler Gives tools and input for the algorithm.
     */
    public SimulatedAnnealing(RouteHandler routeHandler) {
        this.stack = new MyStack();
        this.random = new Random();
        this.currentTemperature = 10;
        this.startingTemperature = 10;
        this.coolingRatio = 0.999;
        this.finalTemperature = 0.01;
        this.routeHandler = routeHandler;
        this.route = routeHandler.getRandomRoute();
        this.routeLength = routeHandler.countRouteLength(route);
        this.finalRoute = route;
        this.finalLength = routeHandler.countRouteLength(finalRoute);
        this.cooling = true;
    }

    /**
     * Alustaa algoritmin parametrit construktorille annetuilla arvoilla. O(n).
     * Analyysi: RouteHandlerin metodit getRandomRoute O(n) + countRouteLength
     * O(1) + Objectin metodi clone O(n), jossa n edustaa kaupunkien lkm.
     *
     * @param temperature starting temperature
     * @param ratio annealing-ratio
     * @param finalTemperature target temperature
     * @param routeHandler Gives tools and input for the algorithm.
     */
    public SimulatedAnnealing(double temperature, double ratio, double finalTemperature, RouteHandler routeHandler) {
        this.stack = new MyStack();
        this.random = new Random();
        this.currentTemperature = temperature;
        this.startingTemperature = temperature;
        this.coolingRatio = ratio;
        this.finalTemperature = finalTemperature;
        this.routeHandler = routeHandler;
        this.route = routeHandler.getRandomRoute();
        this.routeLength = routeHandler.countRouteLength(route);
        this.finalRoute = route.clone();
        this.finalLength = this.routeLength;
        this.cooling = true;
    }

    public void setCooling(boolean cooling) {
        this.cooling = cooling;
    }

    public boolean isCooling() {
        return cooling;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public double getStartingTemperature() {
        return startingTemperature;
    }

    public double getFinalLength() {
        return finalLength;
    }

    public double getRouteLength() {
        return routeLength;
    }

    @Override
    public int[] getRoute() {
        return route;
    }

    @Override
    public int[] getFinalRoute() {
        return this.finalRoute;
    }

    /**
     * Laskee algoritmin tämänhetkisen lämpötilan perusteella pidemmän
     * reittivaihtoehdon hyväksymis-todennäköisyyden. Selvä O(1).
     *
     * @param result the length of new route-candidate
     * @return the acceptance-propability
     */
    public double countLimit(double result) {
        double limit = 1 / Math.exp((Math.abs(result - routeLength)) / currentTemperature);
        return limit;
    }

    /**
     * Arpoo luvun 0-1 väliltä ja katsoo onko se alle vaaditun
     * todennäköisyysrajan. Jos on, palauttaa true
     *
     * @param result the length of a new routecandidate
     * @return boolean telling the algorithm, wether the new (worse) route will
     * be accepeted as current route. O(1). Analysis: random-operation O(1) +
     * countLimit O(1) = O(1)
     */
    public boolean accepted(double result) {
        return random.nextDouble() < countLimit(result);
    }

    /**
     * Suorittaa algoritmin. n = kaupunkien lkm., cR = jäähtymissuhde, fT =
     * lämpötila, jonka alle päästäessä algoritmi pysähtyy, cT = tämänhetkinen
     * lämpötila, sT = lähtölämpötila. O(n*log_{cR}(fT/sT)).
     *
     * Analyysi: Algoritmin iterointi pysähtyy kun lämpötila alittaa fT:n.
     * Jokaisella iteraatiolla lämpötilaa kerrotaan cR-llä. Jos i on
     * iteraatioiden lukumäärä, saamme seuraavan yhtälön. cT = sT*cR^i = fT <=>
     * i = log_{cR}(fT/sT). Jokainen iteraatio on pahimmassa tapauksessa O(n).
     * -> O(n)* O(log_{cR}(fT/sT)) = O(n*log_{cR}(fT/sT)). Syvempi analyysi
     * voisi osoittaa mielenkiintoisia tuloksia, mutta kykyni loppuvat tähän.
     *
     *
     */
    @Override
    public void run() {
        this.cooling = true;
        while (this.currentTemperature > this.finalTemperature) {
            iterate();
        }
        this.cooling = false;
    }

    /**
     * Vaihtaa kahden kaupungin järjestyksen reitillä ja kulkee näiden välisen
     * reitin takaperoisessa järjestyksessä. Laskee sitten uuden reitin pituuden
     * ja katsoo onko ehdokas edellistä parempi. Parempi reitti hyväksytään
     * aina, muta huonompikin saatetaan hyväksyä, lämpötilasta riippuvalla
     * todennäköisyydellä (accepted-metodi). Aikavaativuus O(n). Analyysi:
     * Ainoastaan triviaaleja O(1)-operaatioita lukuunottamatta swapcities(),
     * joka saatetaan suorittaa kahteen kertaan ja jotka ovat O(n)
     * -operaatioita. O(n). -> 2*O(n) = O(n).
     */
    @Override
    public void iterate() {
        if (currentTemperature < this.finalTemperature) {
            this.cooling = false;
            return;
        }
        int a = random.nextInt(route.length - 1);
        int b = random.nextInt(route.length - 1);
        swapCities(a, b);
        double newLength = routeHandler.countRouteLength(this.route);
        if (newLength > this.routeLength && !accepted(newLength)) {
            swapCities(a, b);
        } else {
            this.routeLength = newLength;
            if (newLength < this.finalLength) {
                this.finalLength = newLength;
                this.finalRoute = this.route;
            }
        }
        currentTemperature = currentTemperature * coolingRatio;
    }

    /**
     * Vaihtaa kahden kaupungin järjestyksen reitillä ja kulkee näiden välisen
     * reitin takaperoisessa järjestyksessä. Pino-operaatiot ovat O(1).
     * Pahimmassa tapauksessa a ja b ovat ensimmäinen ja viimeinen kaupunki,
     * jolloin for-luupit ovat noin n-mittaiset.
     *
     * @param a cityNumber one
     * @param b cituNumber two
     */
    private void swapCities(int a, int b) {
        if (a == b) {
            return;
        }
        int first = Math.min(a, b);
        int second = Math.max(a, b);
        for (int i = first; i <= second; i++) {
            stack.push(this.route[i]);
        }
        for (int i = first; i <= second; i++) {
            this.route[i] = (int) stack.pop();
        }
    }

    public int[] giveRoute() {
        return route;
    }

    @Override
    public boolean iterating() {
        return this.cooling;
    }

    /**
     * Renderöintiin tarvittava infon getteri. Selvä O(1).
     *
     * @return current (or the final if iteration is stopped) routeLength and
     * current algorithm temperature
     */
    @Override
    public String[] getInfoArray() {
        String length = "";
        if (this.cooling) {
            length = length + routeLength;
        } else {
            length = length + finalLength;
        }
        String[] info = new String[2];
        info[0] = ("Total length: " + length);
        info[1] = ("Temperature: " + currentTemperature);
        return info;
    }

    @Override
    public void setIterating(boolean b) {
        this.cooling = b;
    }

    @Override
    public void reset() {
        this.route = routeHandler.getRandomRoute();
        this.routeLength = routeHandler.countRouteLength(route);
        this.finalRoute = route.clone();
        this.finalLength = routeHandler.countRouteLength(finalRoute);
        this.cooling = true;
        this.currentTemperature = this.startingTemperature;
    }

    @Override
    public Connection[] getConnectionsRoute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
