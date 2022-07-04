package com.company.TankGame4;

public class Tank {
    //x,y表示坐标
    private int x;
    private int y;
    //表示方向和速度
    private int direct;
    private int speed=1;
    //判断是否存活
    boolean isLive = true;
    public Tank() {
    }

    public Tank(int x, int y, int direct, int speed) {
        this.x = x;
        this.y = y;
        this.direct = direct;
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //上下左右移动
    public void moveUp(){
        if (y>0){
            y-=speed;
        }
    }
    public void moveRight(){
        if(x+60<1000){
            x+=speed;
        }
    }
    public void moveDown(){
        if(y+60<750){
            y+=speed;
        }
    }
    public void moveLeft(){
        if(x>0){
            x-=speed;
        }
    }

    @Override
    public String toString() {
        return "Tank{" +
                "x=" + x +
                ", y=" + y +
                ", direct=" + direct +
                ", speed=" + speed +
                ", isLive=" + isLive +
                '}';
    }
}
