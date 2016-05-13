/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp.interfaces;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author omistaja
 */
public class InputHandler {

    private double alpha;
    private double startTemp;
    private double finalTemp;

    public InputHandler() {
        this.alpha = 0.999;
        this.startTemp = 10.0;
        this.finalTemp = 0.01;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getFinalTemp() {
        return finalTemp;
    }

    public double getStartTemp() {
        return startTemp;
    }

    boolean checkIfValue(String value) {  
        String[] divided = value.split("[.]");
        if (divided.length > 2) {
            return false;
        }
        if (divided.length == 2) {
            String first = divided[0];
            String second = divided[1];
            if (!checkIfNumeral(first) || !checkIfNumeral(second)) {
                return false;
            }
        }
        if (divided.length == 1) {
            String first = divided[0];
            if (!this.checkIfNumeral(first)) {
                return false;
            }
        }
        return true;
    }

    boolean checkIfNumeral(String part) {
        for (int i = 0; i < part.length(); i++) {
            if (!checkIfNumber(part.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    boolean checkIfNumber(char c) {
        for (int i = 0; i < 10; i++) {
            if ((int) c - 48 == i) {
                return true;
            }
        }
        return false;
    }

    public void findValuesFromFrame(JFrame frame) {
        List<Component> comps = this.getAllComponents(frame.getContentPane());
        for (Component comp : comps) {
            if (comp.getName() == null) {
                continue;
            }
            if (comp.getName().equals("startTemp")) {
                JTextField sT = (JTextField) comp;
                String startT = sT.getText();
                if (this.checkIfValue(startT)) {
                    Double candidate = Double.parseDouble(startT);
                    if (candidate > finalTemp && candidate < 1000000) {
                        this.startTemp = candidate;
                    }
                }
            }
            if (comp.getName().equals("finalTemp")) {
                JTextField fT = (JTextField) comp;
                String finalT = fT.getText();
                if (this.checkIfValue(finalT)) {
                    Double candidate = Double.parseDouble(finalT);
                    if (candidate > 0 && candidate < this.startTemp) {
                        this.finalTemp = candidate;
                    }
                }
            }
            if (comp.getName().equals("alpha")) {
                JTextField a = (JTextField) comp;
                String al = a.getText();
                if (this.checkIfValue(al)) {
                    Double candidate = Double.parseDouble(al);
                    if (0 < candidate && candidate < 1) {
                        this.alpha = candidate;
                    }               
                }
            }
        }
    }

    public List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container) {
                compList.addAll(getAllComponents((Container) comp));
            }
        }
        return compList;
    }
}
