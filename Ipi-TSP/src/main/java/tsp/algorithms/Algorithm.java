/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.algorithms;

import tsp.citytools.Connection;

/**
 *
 * @author omistaja
 */
public interface Algorithm {
    
    public void run();
    public void iterate();
    public boolean iterating();
    public void setIterating(boolean b);
    public int[] getRoute();
    public int[] getFinalRoute();
    public String[] getInfoArray();
    public void reset();
    public Connection[] getConnectionsRoute();
}
