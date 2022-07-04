package com.company.TankGame02;

public class Shot implements Runnable{
    private int x;
    private int y;
    private int direct;
    private int speed=5;
    private boolean islive = true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
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

    public boolean isIslive() {
        return islive;
    }

    public void setIslive(boolean islive) {
        this.islive = islive;
    }

    @Override
    public void run() {

        while(true){

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (direct){
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
                default:
                    System.out.println("没这个方向");
                    break;
            }

            System.out.println("子弹x = "+ x + "，y = " + y);

            if(!(x >= 0 && x <= 1000 && y >= 0 &&y <= 750)){
                System.out.println("到达边界，子弹退出");
                islive = false;
                break;
            }
        }
    }
}
