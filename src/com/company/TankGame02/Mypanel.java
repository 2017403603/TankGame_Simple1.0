package com.company.TankGame02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class Mypanel extends JPanel implements KeyListener,Runnable {
    private HeroTank heroTank = null;

    private Vector<EnemyTank> enemyTanks = new Vector<>();
    private int EnemyTankSize = 3;
    public Mypanel(){
        heroTank = new HeroTank(100,100,0,5);
        for(int i=0;i<3;i++){
            enemyTanks.add(new EnemyTank(100*(i+1),0,2,2));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);
        //画出自己的坦克
        DrawTank(heroTank.getX(), heroTank.getY(),g,heroTank.getDirect(),1);
        //画出自己坦克射出的子弹
        if(heroTank.getShot() != null && heroTank.getShot().isIslive()==true){
            System.out.println("点被绘制");
            g.fill3DRect(heroTank.getShot().getX(),heroTank.getShot().getY(),3,3,false);
        }
        //画出敌人坦克
        for(int i=0;i<enemyTanks.size();i++){
            EnemyTank enemyTank = enemyTanks.get(i);
            DrawTank(enemyTank.getX(), enemyTank.getY(),g,enemyTank.getDirect(),0);
        }
    }

    public void DrawTank(int x,int y,Graphics g,int direct,int type){
        switch (type){
            case 0:
                g.setColor(Color.yellow);
                break;
            case 1:
                g.setColor(Color.cyan);
                break;
        }
        switch (direct){
            case 0: //向上
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.drawOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y);
                break;
            case 1: //向右
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.drawOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x+60,y+20);
                break;
            case 2: //向下
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.drawOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y+60);
                break;
            case 3: //向左
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.drawOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x,y+20);
                break;
            default:
                System.out.println("暂时没有处理");
                break;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_S){
            heroTank.setDirect(2);
            heroTank.moveDown();
        }else if(e.getKeyCode()==KeyEvent.VK_W){
            heroTank.setDirect(0);
            heroTank.moveUp();
        }else if(e.getKeyCode()==KeyEvent.VK_A){
            heroTank.setDirect(3);
            heroTank.moveLeft();
        }else if(e.getKeyCode()==KeyEvent.VK_D){
            heroTank.setDirect(1);
            heroTank.moveRight();
        }

        //当按下J键，坦克发射子弹
        if (e.getKeyCode()==KeyEvent.VK_J){
            System.out.println("用户按下了J，开始射击");
            heroTank.ShotEnemyTank();
        }

        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }
}
