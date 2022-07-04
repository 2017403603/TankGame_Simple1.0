package com.company.TankGame5;

import java.io.Serializable;

public class Bomb implements Serializable {
    //炸弹的坐标
    int x;
    int y;
    //炸弹的生命周期
    int life = 9;
    //炸弹是否存活
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //减少生命值
    public void lifeDown() {
        if (life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }
}
