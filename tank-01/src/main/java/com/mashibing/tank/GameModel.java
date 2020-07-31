package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
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

  public final TankFrame tankFrame;

  Tank mytank;

 /* List<Bullet> bullets = new ArrayList<>();
  List<Tank> enemyTanks = new ArrayList<>();
  List<Explode> explodes = new ArrayList<>();
  List<NuclearBomb> nuclearBombs = new ArrayList<>();*/

  ColliderChain colliderChain = new ColliderChain();

 List<GameObject> gameObjects = new ArrayList<>();

 public void add(GameObject gameObject){
   gameObjects.add(gameObject);
 }

 public void remove(GameObject gameObject){
   gameObjects.remove(gameObject);
 }


  public GameModel(TankFrame tankFrame){
    this.tankFrame = tankFrame;

    mytank = new Tank(200,400,Dir.SOUTH,this,Group.GOOD);

    add(mytank);

    Dir[] values = Dir.values();
    Random random = new Random();

    int initTankCount = PropertyMgrEnum.PROPERTY_MGR_INSTANCE.getInt("initTankCount");
    for(int i=0;i<initTankCount;i++){
      Dir dir = values[random.nextInt(values.length)];
      Tank tank = new Tank(20+i*80,100,dir,this,Group.BAD);
      tank.setMoving(true);
      add(tank);
    }

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
    mytank.keyPressed(e);
  }

  public void keyReleased(KeyEvent e) {
    mytank.keyReleased(e);
  }

}
