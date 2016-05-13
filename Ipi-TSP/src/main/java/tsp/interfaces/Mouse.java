/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.interfaces;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omistaja
 */
public class Mouse implements MouseListener {

    private final Controller controller;

    public Mouse(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.controller.setAlgorithmExecuted(false);
        if (this.controller.getAlgorithm() != null) 
            if (this.controller.getAlgorithm().iterating()) {
            return;
        }
        int x = e.getX();
        int y = e.getY();
        try {
            this.controller.addCity(x, y);
            this.controller.setOverallBestInfo(null);
            this.controller.setOverAllBestRoute(null);
            this.controller.getRenderer().upDate(controller.getCityHandler().getCityList(), null, null);
        } catch (InterruptedException ex) {
            Logger.getLogger(Mouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
