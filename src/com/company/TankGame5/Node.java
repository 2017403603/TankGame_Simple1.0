package com.company.TankGame5;

import java.io.Serializable;

/**
 * @ClassName Node  //类名称
 * @Description: 一个node对象，用于表示敌人信息
 * @Author: 程哥哥    //作者
 * @CreateDate: 2022/2/10 11:17	//创建时间
 * @UpdateUser: 更新人
 * @UpdateDate: 2022/2/10 11:17	//更新时间
 * @UpdateRemark: 更新的信息
 * @Version: 1.0    //版本号
 */

public class Node implements Serializable {
    private int x;
    private int y;
    private int direct;

    public Node() {
    }

    public Node(int x, int y, int direct) {
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
}
