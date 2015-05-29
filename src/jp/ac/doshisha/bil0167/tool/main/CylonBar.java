/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.doshisha.bil0167.tool.main;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author katsuya
 */
public class CylonBar implements Runnable{
 

    @Override
    public void run() {
        JFrame f = new JFrame();
        JProgressBar p = new JProgressBar();
       p.setIndeterminate(true);
//        JPanel p = new JPanel();
//        JLabel lb = new JLabel("しばらくお待ち下さい...");
//        p.add(lb);
        f.getContentPane().add(p);
        f.pack();
        f.setSize(200,200);
        f.setVisible(true);
    }
    
    
}
