/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omistaja
 */
public class ButtonListener implements ActionListener {
    

    private final Controller controller;
    

    public ButtonListener(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.controller.getAlgorithm() != null) 
            if (this.controller.getAlgorithm().iterating()) {
            return;
        }
        if (e.getActionCommand().equals("Sim Annealing")) {           
            try {
                this.controller.simulatedAnnealing();
                this.controller.setAlgorithmExecuted(true);
            } catch (InterruptedException ex) {
                Logger.getLogger(ButtonListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (e.getActionCommand().equals("Sim Annealing With Animation")) {
            try {
                this.controller.simulatedAnnealingWithAnimation();
                this.controller.setAlgorithmExecuted(true);
            } catch (InterruptedException ex) {
                Logger.getLogger(ButtonListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getActionCommand().equals("Clarke Wright")) {
            try {
                this.controller.clarkeWrightMulti();
                this.controller.setAlgorithmExecuted(true);
            } catch (InterruptedException ex) {
                Logger.getLogger(ButtonListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (e.getActionCommand().equals("Brute Force")) {
            try {
                this.controller.bruteForce();
                this.controller.setAlgorithmExecuted(true);
            } catch (InterruptedException ex) {
                Logger.getLogger(ButtonListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getActionCommand().equals("Show best route") && this.controller.isAlgorithmExecuted() && this.controller.getCityHandler().getCityArray().length > 3) {
            try {
                this.controller.showOverallBest();
            } catch (InterruptedException ex) {
                Logger.getLogger(ButtonListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
