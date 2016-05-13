/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.interfaces;

import tsp.citytools.City;
import tsp.citytools.Connection;
import tsp.datastructures.GrowingArray;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author omistaja
 */
public class Renderer extends JPanel {

    private GrowingArray cities;
    private Connection[] connections;
    private String[] info;
    private Graphics g;

    public Renderer() {
        super();
        this.cities = null;
        this.connections = null;
        this.info = null;
    }

    @Override
    public void repaint() {
        super.repaint();
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;
        if (connections != null) {
            paintRoutes();
        }
        if (cities != null) {
            paintCities();
        }
        if (info != null) {
            paintInfo();
        }
    }

    protected void paintInfo() {
        for (int i = 0; i < info.length; i++) {
            g.drawString(info[i], 20, 20 + 16 * i);
        }
    }

    public void upDate(GrowingArray cities, Connection[] connections, String[] info) throws InterruptedException {
        this.cities = cities;
        this.connections = connections;
        this.info = info;
        this.repaint();
    }

    protected void paintCities() {
        if (cities == null) {
            return;
        }
        for (int i = 0; i < cities.size(); i++) {
            paintPoint((City) cities.get(i));
            writeNumber((City) cities.get(i));
        }
    }

    protected void paintPoint(City c) {
        int x = c.getX();
        int y = c.getY();
        g.setColor(Color.red);
        g.fillOval(x, y, 7, 7);
        g.setColor(Color.black);
        g.drawOval(x, y, 7, 7);
    }

    protected void writeNumber(City c) {
        g.drawString("   " + c.getNumber(), c.getX(), c.getY());
    }

    protected void showLength(Connection con) {
        g.setColor(Color.BLUE);
        int length = (int) con.getLength();
        int positionY = con.getStart().getY() + (con.getEnd().getY() - con.getStart().getY()) / 2;
        int positionX = con.getStart().getX() + (con.getEnd().getX() - con.getStart().getX()) / 2;
        g.drawString("" + length, positionX, positionY);
    }
    
    protected void showSavings(Connection con) {
        g.setColor(Color.RED);
        int savings = (int) con.getSavingsWeight();
        int positionY = con.getStart().getY() + 10 + (con.getEnd().getY() - con.getStart().getY()) / 2;
        int positionX = con.getStart().getX() + (con.getEnd().getX() - con.getStart().getX()) / 2;
        g.drawString("" + savings, positionX, positionY);
    }

    protected void paintRoutes() {
        if (connections == null || connections.length <= 1) {
            return;
        }
        for (Connection con : connections) {
            paintRoute(con);
            showLength(con);
            showSavings(con);
        }
    }

    protected void paintRoute(Connection route) {
        g.setColor(Color.black);
        int startX = (int) route.getStart().getX();
        int startY = (int) route.getStart().getY();
        int endX = (int) route.getEnd().getX();
        int endY = (int) route.getEnd().getY();
        g.drawLine(startX + 3, startY + 4, endX + 3, endY + 4);
    }

    protected void paintRoute(City city1, City city2) {
        g.setColor(Color.black);
        g.drawLine(city1.getX() + 3, city1.getY() + 4, city2.getX() + 3, city2.getY() + 4);
    }
}
