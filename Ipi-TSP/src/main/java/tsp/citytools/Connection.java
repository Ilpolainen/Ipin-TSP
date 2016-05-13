/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.citytools;

/**
 * Esittää yhteyden reitillä oliona, joka sisältää "alku-" ja "loppukaupunkien"
 * numerot, yhteyden pituuden sekä savingsWeightin Clarke-Wrightin kekoa varten.
 *
 * @author omistaja
 */
public class Connection {

    private City start;
    private City end;
    private final double length;
    private double savingsWeight;

    public Connection(City start, City end) {
        this.start = start;
        this.end = end;
        length = length();
        this.savingsWeight = 0.0;
    }

    public City getStart() {
        return start;
    }

    public City getEnd() {
        return end;
    }

    public void setStart(City start) {
        this.start = start;
    }

    public void setEnd(City end) {
        this.end = end;
    }

    public double getLength() {
        return length;
    }

    public double getSavingsWeight() {
        return savingsWeight;
    }

    public void setSavingsWeight(double savingsWeight) {
        this.savingsWeight = savingsWeight;
    }

    public void reverse() {
//        System.out.println("start: " + start.getNumber() + ", " + "end: " + end.getNumber() );
//        System.out.println("reversing: ");
        City temp = start;
//        System.out.println("temp: " + temp.getNumber());
        start = end;
//        System.out.println("start: " + start.getNumber());
        end = temp;
//        System.out.println("end: " + end.getNumber());
//        System.out.println("");
    }

    private double length() {
        double xSquare = 1.0 * (end.getX() - start.getX()) * (end.getX() - start.getX());
        double ySquare = 1.0 * (end.getY() - start.getY()) * (end.getY() - start.getY());
        return Math.sqrt(ySquare + xSquare);
    }

    @Override
    public String toString() {
        return "Connection: " + "(" + start.getNumber() + "," + end.getNumber() + ")"; //To change body of generated methods, choose Tools | Templates.
    }

}
