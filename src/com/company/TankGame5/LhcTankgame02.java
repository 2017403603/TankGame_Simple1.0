package com.company.TankGame5;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class LhcTankgame02 extends JFrame {
    //画布
    private Mypanel mypanel = null;

    //用于输入是否继续游戏
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new LhcTankgame02();
    }

    public LhcTankgame02() {
        setTitle("坦克大战简化版1.0");
        System.out.println("请输入选择 1: 新游戏 2: 继续上局");
        String key = scanner.next();
        mypanel = new Mypanel(key);
        //画布线程启动
        new Thread(mypanel).start();
        //将画布加入面板
        this.add(mypanel);
        //加入按键监听;
        this.addKeyListener(mypanel);
        //设置尺寸大小
        this.setSize(1300, 785);
        //设置默认关闭操作
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置为可见
        this.setVisible(true);

        //在JFrame 中增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //序列化英雄坦克
                Recorder.Serializable_HeroTank();
                //序列化上一局敌方坦克
                Recorder.Serializable_Live_EnenmyTank();
                //序列化上一局已经被击毁坦克
                Recorder.Serializable_HasHitted_EnenmyTank();
                System.exit(0);
            }
        });
    }
}
