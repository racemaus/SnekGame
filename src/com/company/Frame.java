package com.company;

import javax.swing.JFrame;

public class Frame extends JFrame{

    //Game frame
    Frame(){

        this.add(new Panel());
        this.setTitle("Snek");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
