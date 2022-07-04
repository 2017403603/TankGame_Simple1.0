package com.company.TankGame4;

import javax.swing.*;

public class LhcTankgame02 extends JFrame {
    //画布
    private Mypanel mypanel = null;

    public static void main(String[] args) {
        new LhcTankgame02();
    }

    public LhcTankgame02(){
        mypanel = new Mypanel();
        //画布线程启动
        new Thread(mypanel).start();
        //将画布加入面板
        this.add(mypanel);
        //加入按键监听;
        this.addKeyListener(mypanel);
        //设置尺寸大小
        this.setSize(1000,750);
        //设置默认关闭操作
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置为可见
        this.setVisible(true);
    }
}
