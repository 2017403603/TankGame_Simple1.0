package com.company.TankGame02;

public class HeroTank extends Tank {
    //定义一个Shot对象，表示一个射击线程
    private Shot shot = null;

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
    public void ShotEnemyTank(){
        switch (getDirect()){
            case 0://向上
                shot = new Shot(getX()+20,getY(),0);
                break;
            case 1://向右
                shot = new Shot(getX()+60,getY()+20,1);
                break;
            case 2://向下
                shot = new Shot(getX()+20,getY()+60,2);
                break;
            case 3://向左
                shot = new Shot(getX(),getY()+20,3);
                break;
        }
        //启动shot线程
        new Thread(shot).start();
    }
}
