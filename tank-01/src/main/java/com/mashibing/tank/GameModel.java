package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
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

  List<Bullet> bullets = new ArrayList<>();
  List<Tank> enemyTanks = new ArrayList<>();
  List<Explode> explodes = new ArrayList<>();
  List<NuclearBomb> nuclearBombs = new ArrayList<>();


  public GameModel(TankFrame tankFrame){
    this.tankFrame = tankFrame;

    mytank = new Tank(200,400,Dir.SOUTH,this,Group.GOOD);

    Dir[] values = Dir.values();
    Random random = new Random();

    int initTankCount = PropertyMgrEnum.PROPERTY_MGR_INSTANCE.getInt("initTankCount");
    for(int i=0;i<initTankCount;i++){
      Dir dir = values[random.nextInt(values.length)];
      Tank tank = new Tank(20+i*80,100,dir,this,Group.BAD);
      tank.setMoving(true);
      enemyTanks.add(tank);
    }

  }

  public  void paint(Graphics g){
    //paint会先清屏，然后绘制
    Color color = g.getColor();

    g.setColor(Color.WHITE);
    g.drawString("子弹数量："+bullets.size(),60,60);
    g.drawString("坦克数量："+enemyTanks.size(),60,80);
    g.drawString("爆炸数量："+explodes.size(),60,100);
    g.setColor(color);

    mytank.paint(g);

    Iterator<Explode> explodeIterator = explodes.iterator();
    while (explodeIterator.hasNext()){
      Explode explode = explodeIterator.next();
      if(!explode.isLiving()) {
        explodeIterator.remove();
        continue;
      }
      explode.paint(g);
    }

    int i =0;
    Iterator<Tank> tankIterator = enemyTanks.iterator();
    while (tankIterator.hasNext()){
      Tank tank = tankIterator.next();
      if(!tank.isLiving()) {
        tankIterator.remove();
        continue;
      }
      tank.paint(g);
    }

    Iterator<Bullet> bulletIterator = bullets.iterator();
    while (bulletIterator.hasNext()){
      Bullet bullet = bulletIterator.next();
      if(!bullet.isLiving()) {
        bulletIterator.remove();
        continue;
      }
      bullet.paint(g);
    }

    Iterator<NuclearBomb> nuclearBombIterator = nuclearBombs.iterator();
    while (nuclearBombIterator.hasNext()){
      NuclearBomb nuclearBomb = nuclearBombIterator.next();
      if(!nuclearBomb.isLiving()) {
        nuclearBombIterator.remove();
        continue;
      }
      nuclearBomb.paint(g);
    }

    //碰撞检测
    collisionDetection();
  }

  private void collisionDetection() {
    //让每一颗子弹和每一辆坦克相撞
    for(int i=0;i<bullets.size();i++){
      for(int j=0;j<enemyTanks.size();j++){
        Bullet bullet = bullets.get(i);
        Tank tank = enemyTanks.get(j);
        if(!tank.getGroup().equals(bullet.getGroup())){
          Rectangle bulletRectangle = bullet.getRectangle();
          Rectangle tankRectangle = tank.getRectangle();
          if(bulletRectangle.intersects(tankRectangle)){
            //发生碰撞
            bullet.die();
            tank.die();
            //产生爆炸
            explodes.add(new Explode(bullet.getX()+Bullet.WIDTH/2-Explode.WIDTH/2,bullet.getY()+Bullet.HEIGHT/2-Explode.HEIGHT/2,this));
            new Thread(()->{
              Audio explodeAudio = new Audio("audio/explode.wav");
              explodeAudio.play();
            }).start();
            break;
          }
        }
      }
    }

    for(int i=0;i<nuclearBombs.size();i++){
      for(int j=0;j<enemyTanks.size();j++){
        NuclearBomb nuclearBomb = nuclearBombs.get(i);
        Tank tank = enemyTanks.get(j);
        if(!tank.getGroup().equals(nuclearBomb.getGroup())){
          Rectangle nuclearBombRectangle = nuclearBomb.getRectangle();
          Rectangle tankRectangle = tank.getRectangle();
          if(nuclearBombRectangle.intersects(tankRectangle)){
            //发生碰撞
            nuclearBomb.die();
            tank.die();
            //产生爆炸
            explodes.add(new Explode(nuclearBomb.getX()+Bullet.WIDTH/2-Explode.WIDTH/2,nuclearBomb.getY()+Bullet.HEIGHT/2-Explode.HEIGHT/2,this));
            new Thread(()->{
              Audio explodeAudio = new Audio("audio/explode.wav");
              explodeAudio.play();
            }).start();
            break;
          }
        }
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
