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
public class CityHubAngleComparator implements Comparator<City> {

    private City hub;
    
    @Override
    public int compare(City o1, City o2) {

        if (o1.getY() > hub.getY()) {
            
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
