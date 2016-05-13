/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tsp.interfaces;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;

/**
 *
 * @author omistaja
 */
public class InputFrame implements Runnable {

    private final Controller controller;
    private JFrame frame;

    public InputFrame(Controller controller) {
        this.controller = controller;
    }

    public JFrame getFrame() {
        return frame;
    }
    
    

    @Override
    public void run() {
        this.frame = new JFrame();
        frame.setPreferredSize(new Dimension(1400, 800));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setComponents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void setComponents(Container c) {
        ButtonListener buttonListener = new ButtonListener(this.controller);
        c.add(this.controller.getRenderer());
        c.addMouseListener(this.controller.getMouse());
        JPanel panel = new JPanel();
        FlowLayout fl = new FlowLayout();
        panel.setLayout(fl);
        JButton simAnnealing = new JButton("Sim Annealing");
        simAnnealing.addActionListener(buttonListener);
        panel.add(simAnnealing);
        JButton bruteForce = new JButton("Brute Force");
        bruteForce.addActionListener(buttonListener);
        panel.add(bruteForce);
        JButton clarkeWright = new JButton("Clarke Wright");
        clarkeWright.addActionListener(buttonListener);
        panel.add(clarkeWright);
        c.add(panel, BorderLayout.SOUTH);
        JButton simAnnealingWithAnimation = new JButton("Sim Annealing With Animation");
        simAnnealingWithAnimation.addActionListener(buttonListener);
        panel.add(simAnnealingWithAnimation);
        JButton showBest = new JButton("Show best route");
        showBest.addActionListener(buttonListener);
        panel.add(showBest);
        JPanel valuepanel = new JPanel();
        FlowLayout fl2 = new FlowLayout();
        valuepanel.setLayout(fl2);
        JLabel al = new JLabel("alpha: ");
        JLabel sT = new JLabel("startTemp: ");
        JLabel fT = new JLabel("finalTemp: ");
        JTextField alpha = new JTextField("0.999");
        JTextField startTemp = new JTextField("10.0");
        JTextField finalTemp = new JTextField("0.01");
        alpha.setName("alpha");
        startTemp.setName("startTemp");
        finalTemp.setName("finalTemp");
        valuepanel.add(al);
        valuepanel.add(alpha);
        valuepanel.add(sT);
        valuepanel.add(startTemp);
        valuepanel.add(fT);
        valuepanel.add(finalTemp);
        panel.add(valuepanel);
    }

}
