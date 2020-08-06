package com.mashibing.tank;

import com.mashibing.tank.cor.ColliderChain;
import com.mashibing.tank.singleton.PropertyMgrEnum;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.GameObjectMgr
 * @Description: TODO
 * @date 2020/7/31 10:17
 */
public class GameModel {

  private static GameModel gameModel = new GameModel();

  public static GameModel getInstance(){
    return gameModel;
  }

  Tank mytank;

  ColliderChain colliderChain = new ColliderChain();

 List<GameObject> gameObjects = new ArrayList<>();

 public void add(GameObject gameObject){
   gameObjects.add(gameObject);
 }

 public void remove(GameObject gameObject){
   gameObjects.remove(gameObject);
 }


  private GameModel(){

    mytank = new Tank(200,400,Dir.SOUTH,Group.GOOD);

    add(mytank);

    Dir[] values = Dir.values();
    Random random = new Random();

    int initTankCount = PropertyMgrEnum.PROPERTY_MGR_INSTANCE.getInt("initTankCount");
    for(int i=0;i<initTankCount;i++){
      Dir dir = values[random.nextInt(values.length)];
      Tank tank = new Tank(20+i*80,100,dir,Group.BAD);
      tank.setMoving(true);
      add(tank);
    }

    Wall wall = new Wall(100,200,40,150);
    Wall wall1 = new Wall(800,300,40,200);
    //add(wall);
    //add(wall1);
  }

  public  void paint(Graphics g){
    //paint会先清屏，然后绘制
    Color color = g.getColor();

    g.setColor(Color.WHITE);
    /*g.drawString("子弹数量："+bullets.size(),60,60);
    g.drawString("坦克数量："+enemyTanks.size(),60,80);
    g.drawString("爆炸数量："+explodes.size(),60,100);*/
    g.setColor(color);

    for(int i=0;i<gameObjects.size();i++){
      GameObject gameObject = gameObjects.get(i);
      gameObject.paint(g);
    }

    //碰撞检测
    for(int i=0;i<gameObjects.size();i++){
      for(int j=i+1;j<gameObjects.size();j++){
        GameObject o1 = gameObjects.get(i);
        GameObject o2 = gameObjects.get(j);

        colliderChain.collideWith(o1,o2);
      }
    }
  }

  public void keyPressed(KeyEvent e) {

    int keyCode = e.getKeyCode();
    switch (keyCode){
      case KeyEvent.VK_S:
        save();
        break;
      case KeyEvent.VK_L:
        load();
        break;
    }

    mytank.keyPressed(e);
  }

  /**
   * 游戏加载到磁盘
   */
  private void load() {
    System.out.println("游戏加载到磁盘");
    ObjectInputStream ois = null;
    try {
      File file = new File("H:\\学习\\马士兵课程\\4-设计模式（坦克一期)\\project\\tank\\tank.data");
      FileInputStream fileInputStream = new FileInputStream(file);
      ois = new ObjectInputStream(fileInputStream);
      mytank = (Tank) ois.readObject();
      gameObjects = (List<GameObject>) ois.readObject();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }finally {
      if(ois != null){
        try {
          ois.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    System.out.println("游戏加载到磁盘完成");
  }

  /**
   * 游戏存盘
   */
  private void save() {
    System.out.println("游戏开始存盘");

    FileOutputStream fos = null;
    ObjectOutputStream oos = null;
    try {
      File file = new File("H:\\学习\\马士兵课程\\4-设计模式（坦克一期)\\project\\tank\\tank.data");
      fos = new FileOutputStream(file);
      oos = new ObjectOutputStream(fos);
      oos.writeObject(mytank);
      oos.writeObject(gameObjects);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      if(oos != null){
        try {
          oos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    System.out.println("游戏存盘结束");
  }

  public void keyReleased(KeyEvent e) {
    mytank.keyReleased(e);
  }

}
