/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.citytools;

import java.util.Comparator;

/**
 *
 * @author omistaja
 */
public class SavingsComparator implements Comparator<Connection> {

    public SavingsComparator() {
    }
    
    

    @Override
    public int compare(Connection o1, Connection o2) {
        if (o1.getSavingsWeight() < o2.getSavingsWeight()) {
            return 1;
        }
        if (o1.getSavingsWeight() == o2.getSavingsWeight()) {
            return 0;
        }
        return  -1; 
    }

    
}
