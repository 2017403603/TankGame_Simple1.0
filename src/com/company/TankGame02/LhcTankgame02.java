package com.company.TankGame02;

import javax.swing.*;

public class LhcTankgame02 extends JFrame {
    private Mypanel mypanel = null;

    public static void main(String[] args) {
        new LhcTankgame02();
    }

    public LhcTankgame02(){
        mypanel = new Mypanel();
        new Thread(mypanel).start();
        this.add(mypanel);
        this.addKeyListener(mypanel);
        this.setSize(1000,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
