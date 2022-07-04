package com.company.TankGame4;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
    public EnemyTank() {
    }
    //存放射击对象
    Vector<Shot> shotVector = new Vector<>();
    //判断是否存活
    public EnemyTank(int x, int y, int direct, int speed) {
        super(x, y, direct, speed);
    }
    //敌人坦克多线程运动
    @Override
    public void run() {

        while (true){
            //如果shotvector集合size为0，则创建一个shot()并启动
            if(shotVector.size()<=3 && isLive){
                Shot s = null;
                switch (getDirect()){
                    case 0://向上
                        s = new Shot(getX()+20,getY(),0);
                        break;
                    case 1://向右
                        s = new Shot(getX()+60,getY()+20,1);
                        break;
                    case 2://向下
                        s = new Shot(getX()+20,getY()+60,2);
                        break;
                    case 3://向左
                        s = new Shot(getX(),getY()+20,3);
                        break;
                }

                //启动shot线程
                new Thread(s).start();
                //加入shot集合
                shotVector.add(s);
            }
            switch (getDirect()){ //获得坦克方向
                //根据方向进行相应移动,走30步才随机换一次方向
                case 0:
                    for (int i=0;i<30;i++){
                            moveUp();//向上
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    for (int i=0;i<30;i++){
                            moveRight();//向右
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for (int i=0;i<30;i++){
                            moveDown();//向下
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for (int i=0;i<30;i++){
                            moveLeft();//向左
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    System.out.println("没有这个方向");
                    break;
            }
            //休眠50ms
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //随机设置方向
            setDirect((int)(Math.random()*4));
            //setDirect(2);
            //如果敌人坦克已经被击中，则退出线程
            if (!isLive){
                break;
            }
        }
    }
}
