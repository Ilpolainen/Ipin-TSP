/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.citytools;

import tsp.datastructures.GrowingArray;

/**
 * Gives tools to this programs algorithms for handling vectors (cities), like
 * adding them in a list and count their distance etc..
 *
 * @author omistaja
 */
public class CityHandler {

    private GrowingArray cities;
    private double[][] distanceMatrix;

    /**
     * O(1)
     */
    public CityHandler() {
        this.cities = new GrowingArray();
        this.distanceMatrix = null;
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public GrowingArray getCityList() {
        return cities;
    }

    /**
     * Returns the objects current cityList as an array. O(n) (n stands for
     * number of cities). Analysis: new n-array (O(n)) + n-sized for-loop
     * containing (O(1)-operations).
     *
     * @return The objects current cityList as an array.
     */
    public City[] getCityArray() {
        City[] cityArray = new City[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            cityArray[i] = (City) cities.get(i);
        }
        return cityArray;
    }

    public void setCities(GrowingArray cities) {
        this.cities = cities;
    }

    /**
     * Luo City-olion annetuilla koordinaateilla ja nimeää sen cityListin koon
     * mukaan, ja lisää sen cityListiin. Selvä O(1).
     *
     * @param x the x-coordinate of the City-object to be added
     * @param y the y-coordinate of the City-object to be added
     */
    public void addCity(int x, int y) {
        int cityNro = cities.size();
        cities.add(new City(cityNro, x, y));
    }

    /**
     * Laskee kahden City -olion välisen euklidisan etäisyyden. O(1)
     *
     * @param city1 one City-object
     * @param city2 the other City-object
     * @return the euclidean distance between the two City-objects' coordinates
     */
    public double distanceBetween(City city1, City city2) {
        double xSquare = 1.0 * (city2.getX() - city1.getX()) * (city2.getX() - city1.getX());
        double ySquare = 1.0 * (city2.getY() - city1.getY()) * (city2.getY() - city1.getY());
        return Math.sqrt(ySquare + xSquare);
    }

    public double distanceBetween(int a, int b) {
        if (a > this.cities.size() - 1 || b > this.cities.size() - 1 || a < 0 || b < 0) {
            return Integer.MAX_VALUE;
        }
        return this.distanceMatrix[a][b];
    }

    /**
     * Luo liukuluku-arvoisen n*n matriisin, jossa indeksit viittaavat
     * City-numbereihin ja arvot kahden kaupunkin välisiin etäisyyksiin
     * ilmeisellä tavalla.
     *  Tila- ja aikavaativuus ovat O(n^2). Analyysi: Uuden n*n -taulukon luonti
     * (O(n^2)) + sisäkkäiset n-luupit jotka sisältävät O(1)-operation.
     * (O(n^2)). -> 2*O(n^2) = O(n^2).
     */
    public void makeDistanceMatrix() {
        City[] cityArray = this.getCityArray();
        int size = cities.size();
        this.distanceMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.distanceMatrix[i][j] = distanceBetween(cityArray[i], cityArray[j]);
            }
        }
    }
}
