package com.company.TankGame5;

import java.io.*;
import java.util.Vector;

/**
 * @ClassName Recorder  //类名称
 * @Description: 该类用于记录相关信息和文件交互
 * @Author: 程哥哥    //作者
 * @CreateDate: 2022/2/9 22:20	//创建时间
 * @UpdateUser: 更新人
 * @UpdateDate: 2022/2/9 22:20	//更新时间
 * @UpdateRemark: 更新的信息
 * @Version: 1.0    //版本号
 */

public class Recorder implements Serializable {
    //定义变量，记录我方击毁敌方坦克数量
    private static int AllEnemyTankNum = 0;
    //定义IO对象，将数据写入文件
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    //序列化和反序列化
    private static ObjectInputStream ois = null;
    private static ObjectOutputStream oos = null;
    //文件路径:上一局的坦克数据
    private static String RecordPath = "D:\\工程师之路\\韩顺平学java\\TankGame002\\src\\com\\company\\TankGame5\\RecordData\\myRecord.data";
    //文件路径:上一局的被击毁坦克数据
    private static String RecordPath_HasHitted = "D:\\工程师之路\\韩顺平学java\\TankGame002\\src\\com\\company\\TankGame5\\RecordData\\HasHitted.dat";
    //文件路径:上一局的被击毁的英雄坦克数据
    private static String RecordPath_HeroTank = "D:\\工程师之路\\韩顺平学java\\TankGame002\\src\\com\\company\\TankGame5\\RecordData\\HeroTank.dat";
    //定义Vector ,指向 MyPanel 对象的 敌人坦克Vector
    private static Vector<EnemyTank> enemyTanks = null;
    //定义Vector ,指向 MyPanel 对象的被击毁的敌人坦克Vector
    private static Vector<EnemyTank> havebombenemyTanks = null;
    //定义英雄坦克，用于存储上一局结束的英雄坦克
    private static HeroTank heroTank = null;

    //序列化上一局退出时的英雄的坦克
    public static void Serializable_HeroTank() {
        try {
            oos = new ObjectOutputStream(new FileOutputStream(RecordPath_HeroTank));
            oos.writeObject(heroTank);
            //System.out.println("herotank:"+heroTank);
            //System.out.println("保存成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //序列化上一局退出时存活的坦克
    public static void Serializable_Live_EnenmyTank() {
        try {
            oos = new ObjectOutputStream(new FileOutputStream(RecordPath));
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                oos.writeObject(enemyTank);
            }
            oos.writeObject(null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //序列化已经击毁的敌方坦克
    public static void Serializable_HasHitted_EnenmyTank() {
        try {
            oos = new ObjectOutputStream(new FileOutputStream(RecordPath_HasHitted));
            for (int i = 0; i < havebombenemyTanks.size(); i++) {
                EnemyTank enemyTank = havebombenemyTanks.get(i);
                oos.writeObject(enemyTank);
            }
            oos.writeObject(null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //反序列化已经击毁的敌方坦克
    public static Vector<EnemyTank> Deserialize_HasHitted_EnenmyTank() {
        try {
            ois = new ObjectInputStream(new FileInputStream(RecordPath_HasHitted));
            EnemyTank enemyTank = null;
            Object object = null;
            while ((object = ois.readObject()) != null) {
                enemyTank = (EnemyTank) object;
                //启动子弹线程
                for (int j = 0; j < enemyTank.shotVector.size(); j++) {
                    Shot shot = enemyTank.shotVector.get(j);
                    if (shot.isIslive()) {
                        new Thread(shot).start();
                    }
                }
                havebombenemyTanks.add(enemyTank);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return havebombenemyTanks;
    }

    //反序列化上一局存活的敌方坦克
    public static Vector<EnemyTank> Deserialize_Live_EnenmyTank() {
        try {
            ois = new ObjectInputStream(new FileInputStream(RecordPath));
            EnemyTank enemyTank = null;
            Object object = null;
            while ((object = ois.readObject()) != null) {
                enemyTank = (EnemyTank) object;
                //启动子弹线程
                for (int j = 0; j < enemyTank.shotVector.size(); j++) {
                    Shot shot = enemyTank.shotVector.get(j);
                    if (shot.isIslive()) {
                        new Thread(shot).start();
                    }
                }
                enemyTanks.add(enemyTank);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return enemyTanks;
    }

    //反序列化上一局英雄坦克
    public static HeroTank Deserialize_HeroTank() {
        try {
            ois = new ObjectInputStream(new FileInputStream(RecordPath_HeroTank));
            Object object = null;
            object = ois.readObject();
            if (object != null) {
                heroTank = (HeroTank) object;
                for (int j = 0; j < heroTank.shotVector.size(); j++) {
                    Shot shot = heroTank.shotVector.get(j);
                    if (shot.isIslive()) {
                        new Thread(shot).start();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return heroTank;
    }

    //设置指向 MyPanel 对象的 敌人坦克Vector
    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //设置指向 MyPanel 对象的被击毁的敌人坦克Vector
    public static void setHavebombenemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.havebombenemyTanks = enemyTanks;
    }

    //清空文件并且进行初始化
    public static void InitFile() {

        try {
            //1敌方已经摧毁坦克
            File file1 = new File(RecordPath_HasHitted);
            if (file1.exists()) {
                file1.delete();
                file1.createNewFile();
            }
            oos = new ObjectOutputStream(new FileOutputStream(RecordPath_HasHitted));
            oos.writeObject(null);
            oos.close();
            //2敌方存活坦克
            File file2 = new File(RecordPath);
            if (file2.exists()) {
                file2.delete();
                file2.createNewFile();
            }
            oos = new ObjectOutputStream(new FileOutputStream(RecordPath));
            oos.writeObject(null);
            oos.close();
            //3我方英雄坦克
            File file3 = new File(RecordPath_HeroTank);
            if (file3.exists()) {
                file3.delete();
                file3.createNewFile();
            }
            oos = new ObjectOutputStream(new FileOutputStream(RecordPath_HeroTank));
            oos.writeObject(null);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getAllEnemyTankNum() {
        return AllEnemyTankNum;
    }

    public static void setHeroTank(HeroTank heroTank) {
        Recorder.heroTank = heroTank;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.AllEnemyTankNum = allEnemyTankNum;
    }

    //当我方坦克击毁一个敌人坦克，就应当 allEnemyTankNum++
    public static void addAllEnemyTankNum() {
        Recorder.AllEnemyTankNum++;
    }
}
