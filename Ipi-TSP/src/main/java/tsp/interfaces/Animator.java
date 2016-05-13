/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.interfaces;

import tsp.algorithms.Algorithm;
import tsp.citytools.CityHandler;
import tsp.citytools.RouteHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;



/**
 *
 * @author omistaja
 */
public class Animator extends Timer implements ActionListener {
    
    private Algorithm algorithm;
    private Renderer renderer;
    private RouteHandler routeHandler;
    private CityHandler cityHandler;
    private Controller controller;

    public Animator (Controller controller) {
        super(5, null);
        this.controller = controller;
        this.algorithm = controller.getAlgorithm();
        this.renderer = controller.getRenderer();
        this.routeHandler = controller.getRouteHandler();
        this.cityHandler = controller.getCityHandler();
        this.addActionListener(this);
        
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (algorithm==null) {
            return;
        }       
        algorithm.iterate();
        try {
            this.renderer.upDate(this.cityHandler.getCityList(), this.routeHandler.convertRouteToConnections(this.algorithm.getRoute()), algorithm.getInfoArray());
        } catch (InterruptedException ex) {
            Logger.getLogger(Animator.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!algorithm.iterating()) {
            controller.checkBest();
            try {
                this.renderer.upDate(this.cityHandler.getCityList(), this.routeHandler.convertRouteToConnections(this.algorithm.getRoute()), algorithm.getInfoArray());
            } catch (InterruptedException ex) {
                Logger.getLogger(Animator.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.stop();
        }
    }
    
    
}
