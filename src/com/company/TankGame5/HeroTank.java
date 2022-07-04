package com.company.TankGame5;

import java.io.Serializable;
import java.util.Vector;

public class HeroTank extends Tank implements Serializable {
    //定义一个Shot对象，表示一个射击线程
    private Shot shot = null;
    //定义一个包含shot的集合
    Vector<Shot> shotVector = new Vector<>();

    public HeroTank() {
    }

    public HeroTank(int x, int y, int direct, int speed) {
        super(x, y, direct, speed);
    }

    public Shot getShot() {
        return shot;
    }

    public void setShot(Shot shot) {
        this.shot = shot;
    }

    //发射子弹
    public void ShotEnemyTank() {
        if (!isLive) {
            return;
        }
        //最多只能发射7颗子弹
        if (shotVector.size() > 7) {
            return;
        }
        switch (getDirect()) {
            case 0://向上
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1://向右
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2://向下
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3://向左
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }
        //加入shot集合
        shotVector.add(shot);
        //启动shot线程
        new Thread(shot).start();
    }
}
