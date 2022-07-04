package com.company.TankGame4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class Mypanel extends JPanel implements KeyListener,Runnable {
    //定义自己的坦克
    private HeroTank heroTank = null;
    //放敌人坦克的容器
    private Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个Vector用于存放炸弹
    //当敌人坦克被子弹击中，初始化一个bomb，并加入vector
    Vector<Bomb> bombvector = new Vector<>();
    //初始化敌人坦克数量
    private int EnemyTankSize = 3;
    //定义三张图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    //存储被击中爆炸的敌人坦克，如果子弹还在，则继续显示，不在则不显示
    private Vector<EnemyTank> havebombenemyTanks = new Vector<>();
    public Mypanel(){
        //新建自己坦克的对象
        heroTank = new HeroTank(500,100,0,5);

        for(int i=0;i<EnemyTankSize;i++){
            //新建敌人坦克对象
            EnemyTank enemyTank = new EnemyTank(100*(i+1),0,2,2);
            //启动敌人坦克线程
            new Thread(enemyTank).start();
            //每新建一个敌人坦克，新建一个射击对象
            Shot shot = new Shot(enemyTank.getX()+20,enemyTank.getY()+60,enemyTank.getDirect());
            //启动射击进程
            new Thread(shot).start();
            //加入射击容器
            enemyTank.shotVector.add(shot);
            //加入敌人坦克容器
            enemyTanks.add(enemyTank);
        }
        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Mypanel.class.getResource("image/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Mypanel.class.getResource("image/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Mypanel.class.getResource("image/bomb_3.gif"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);
        //画出自己的坦克
        if(heroTank.isLive && heroTank != null){
            DrawTank(heroTank.getX(), heroTank.getY(),g,heroTank.getDirect(),1);
        }

        //画出自己坦克射出的子弹,将hero中射击集合遍历取出
        for(int i=0;i<heroTank.shotVector.size();i++){
            Shot shot = heroTank.shotVector.get(i);
            if(shot != null && shot.isIslive()){
                //点被绘制
                g.setColor(Color.cyan);
                g.fill3DRect(shot.getX(),shot.getY(),3,3,false);
            }else { //如果shot对象已经无效，则移除
                heroTank.shotVector.remove(shot);
            }
        }
        //如果bombvector中有对象，则将其画出
        for(int i=0;i<bombvector.size();i++){
            //取出炸弹
            Bomb bomb = bombvector.get(i);
            //根据当前这个bomb对象的life值去画出对应图片
            if(bomb.life>6){
                g.drawImage(image1,bomb.x, bomb.y, 60 ,60 ,this);
            }else if(bomb.life>3){
                g.drawImage(image2,bomb.x, bomb.y, 60 ,60 ,this);
            }else {
                g.drawImage(image3,bomb.x, bomb.y, 60 ,60 ,this);
            }
            //每次显示后减少生命值
            bomb.lifeDown();
            //如果生命值显示为0，则从vector中删除掉
            if(bomb.life == 0){
                bombvector.remove(bomb);
            }
        }
        //画出已经销毁的敌人坦克的子弹.如果子弹仍然存活，则画出
        for(int i=0;i<havebombenemyTanks.size();i++){
            EnemyTank enemyTank = havebombenemyTanks.get(i);
            for (int j = 0; j < enemyTank.shotVector.size() ; j++){
                //获得敌人坦克射击对象
                Shot shot = enemyTank.shotVector.get(j);
                //判断射击对象是否存活
                if(shot.isIslive()){
                    //画出子弹
                    g.setColor(Color.yellow);
                    g.fill3DRect(shot.getX(), shot.getY(),3,3,false);
                }
                else {
                    //移除射击对象
                    enemyTank.shotVector.remove(shot);
                }
            }
        }
        //画出敌人坦克
        for(int i=0;i<enemyTanks.size();i++){
            EnemyTank enemyTank = enemyTanks.get(i);
            if(enemyTank.isLive){
                //画出敌人坦克
                DrawTank(enemyTank.getX(), enemyTank.getY(),g,enemyTank.getDirect(),0);
                for (int j = 0; j < enemyTank.shotVector.size() ; j++){
                    //获得敌人坦克射击对象
                    Shot shot = enemyTank.shotVector.get(j);
                    //判断射击对象是否存活
                    if(shot.isIslive()){
                        //画出子弹
                        g.setColor(Color.yellow);
                        g.fill3DRect(shot.getX(), shot.getY(),3,3,false);
                    }
                    else {
                        //移除射击对象
                        enemyTank.shotVector.remove(shot);
                    }
                }
            }
        }
    }

    public void DrawTank(int x,int y,Graphics g,int direct,int type){
        switch (type){
            case 0://敌人坦克
                g.setColor(Color.yellow);
                break;
            case 1://英雄坦克
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
    //编写方法，判断子弹是否击中坦克
    //type=0表示击中敌人情况判断，=1表示被敌人击中判断
    public void hitTank(Shot s , Tank tank ,int type){

        //判断s 击中坦克
        switch (tank.getDirect()){
            case 0://坦克向上
            case 2://坦克向下
                if(s.getX() > tank.getX() && s.getX() < tank.getX()+40 && s.getY() > tank.getY() && s.getY() < tank.getY()+60){
                    s.setIslive(false);
                    tank.isLive=false;
                    if(type==0){
                        //当子弹击中敌人坦克，将enemytank从vector中拿掉
                        enemyTanks.remove(tank);
                        //放入已经被击中且销毁的坦克vector中
                        havebombenemyTanks.add((EnemyTank) tank);
                        //已经击中，子弹销毁
                        heroTank.shotVector.remove(s);
                    }else if(type==1){
                        //当自己坦克被敌人击中，结束存活
                        heroTank.isLive=false;
                    }

                    //初始化一个新的炸弹
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombvector.add(bomb);

                }
                break;
            case 1: //坦克向右
            case 3: //坦克向左
                if(s.getX() > tank.getX() && s.getX() < tank.getX()+60 && s.getY() > tank.getY() && s.getY() < tank.getY()+40){
                    s.setIslive(false);
                    tank.isLive=false;
                    if(type==0){
                        //当子弹击中敌人坦克，将enemytank从vector中拿掉
                        enemyTanks.remove(tank);
                        //放入已经被击中且销毁的坦克vector中
                        havebombenemyTanks.add((EnemyTank) tank);
                        //已经击中，子弹销毁
                        heroTank.shotVector.remove(s);
                    }else if(type==1){
                        //当自己坦克被敌人击中，结束存活
                        heroTank.isLive=false;
                    }
                    //初始化一个新的炸弹
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombvector.add(bomb);
                }
                break;
        }
    }
    //判断所有子弹是否与敌人坦克碰撞，遍历子弹，遍历敌人
    public void HitEnemyTank(){
        for (int j=0; j < heroTank.shotVector.size();j++){
            Shot shot = heroTank.shotVector.get(j);
            //判断是否击中了敌人坦克
            //遍历是否击中敌人坦克
            for(int i=0;i< enemyTanks.size();i++){
                if(shot != null && shot.isIslive()){  //当我的子弹还存活
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //判断是否碰撞
                    hitTank(shot,enemyTank,0);
                }else { //若已经击中过，或者不存在，则直接结束这颗子弹的循环
                    break;
                }
            }
        }
    }
    //判断敌人的子弹是否击中我方坦克，遍历所有敌人坦克，遍历所有敌人坦克的子弹
    public void HitHeroTank(){
        //判断已经摧毁了的敌人坦克的子弹是否击中我方坦克
        for (int i=0; i < havebombenemyTanks.size();i++){
            EnemyTank enemyTank = havebombenemyTanks.get(i);
            //判断是否击中了自己坦克
            //遍历是否击中自己坦克
            for(int j=0;j< enemyTank.shotVector.size();j++){
                Shot shot = enemyTank.shotVector.get(j);
                if(shot != null && shot.isIslive() && heroTank.isLive){  //当敌人的子弹还存活
                    //判断是否碰撞
                    hitTank(shot, heroTank,1);
                } else { //若已经击中过，或者不存在，则直接结束这颗子弹的循环
                    break;
                }
            }
        }
        for (int i=0; i < enemyTanks.size();i++){
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断是否击中了自己坦克
            //遍历是否击中自己坦克
            for(int j=0;j< enemyTank.shotVector.size();j++){
                Shot shot = enemyTank.shotVector.get(j);
                if(shot != null && shot.isIslive() && heroTank.isLive){  //当敌人的子弹还存活
                    //判断是否碰撞
                    hitTank(shot, heroTank,1);
                } else { //若已经击中过，或者不存在，则直接结束这颗子弹的循环
                    break;
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //上下左右移动
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
        //多线程重新绘制
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断是否击中目标
            HitEnemyTank();
            //判断是否被击中
            HitHeroTank();
            this.repaint();
        }
    }
}
