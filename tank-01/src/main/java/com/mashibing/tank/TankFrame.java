package com.mashibing.tank;

import com.mashibing.io.NettyClient;
import com.mashibing.io.TankMoveMsg;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TankFrame extends Frame {

  public static final TankFrame INSTANCE = new TankFrame();

  public static  int WIN_WIDTH;
  public static  int WIN_HEIGHT;

  private Image image = null;

  Tank mytank;
  Map<String,Bullet> bulletMap = new HashMap<>();
  List<Explode> explodes = new ArrayList<>();

  Map<String,Tank> enemyTankMap = new HashMap<>();

  private TankFrame(){
    WIN_WIDTH = PropertyMgrEnum.PROPERTY_MGR_INSTANCE.getInt("gameWidth");
    WIN_HEIGHT = PropertyMgrEnum.PROPERTY_MGR_INSTANCE.getInt("gameHeight");
    setSize(WIN_WIDTH,WIN_HEIGHT);
    setResizable(false);
    setTitle("mytank war");

    mytank = Tank.randomTank(Group.GOOD);

    setVisible(true);
    this.addKeyListener(new MyKeyListener());
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  @Override
  public void update(Graphics g) {
    if(image == null){
      image = this.createImage(WIN_WIDTH, WIN_HEIGHT);
    }
    Graphics graphics = image.getGraphics();
    Color c = graphics.getColor();
    graphics.setColor(Color.BLACK);
    graphics.fillRect(0,0,WIN_WIDTH,WIN_HEIGHT);
    graphics.setColor(c);
    paint(graphics);
    g.drawImage(image,0,0,null);
  }

  @Override
  public void paint(Graphics g) {
    //paint会先清屏，然后绘制
    Color color = g.getColor();

    g.setColor(Color.WHITE);
    g.drawString("子弹数量："+ bulletMap.values().size(),60,60);
    g.drawString("坦克数量："+enemyTankMap.values().size(),60,80);
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

    Iterator<Tank> tankIterator = enemyTankMap.values().iterator();
    while (tankIterator.hasNext()){
      Tank tank = tankIterator.next();
      if(!tank.isLiving()) {
        tankIterator.remove();
        continue;
      }
      tank.paint(g);
    }

    Iterator<Bullet> bulletIterator = bulletMap.values().iterator();
    while (bulletIterator.hasNext()){
      Bullet bullet = bulletIterator.next();
      if(!bullet.isLiving()) {
        bulletIterator.remove();
        continue;
      }
      bullet.paint(g);
      Iterator<Tank> tankIterator2 = enemyTankMap.values().iterator();
      while (tankIterator2.hasNext()){
        Tank tank = tankIterator2.next();
        bullet.strikeWithTank(tank);
      }
    }
  }

  public Tank getMainTank() {
    return mytank;
  }

  public boolean existsTankById(String tankId) {
    return enemyTankMap.containsKey(tankId);
  }

  public void addTank(Tank tank) {
    enemyTankMap.put(tank.getId(),tank);
  }

  public boolean existBulletById(String bulletId) {
    return bulletMap.containsKey(bulletId);
  }

  public void addBullet(Bullet bullet) {
    this.bulletMap.put(bullet.getId(),bullet);
  }

  public Tank getTankById(String tankId) {
    return enemyTankMap.get(tankId);
  }

  private class MyKeyListener extends KeyAdapter {

    boolean BU = false;
    boolean BD = false;
    boolean BL = false;
    boolean BR = false;

    @Override
    public void keyPressed(KeyEvent e) {

      int keyCode = e.getKeyCode();

      switch (keyCode){
        case KeyEvent.VK_UP:
          BU = true;
          break;
        case KeyEvent.VK_DOWN:
          BD = true;
          break;
        case KeyEvent.VK_LEFT:
          BL = true;
          break;
        case KeyEvent.VK_RIGHT:
          BR = true;
          break;
      }

      setMainTankDir();
    }

    private void setMainTankDir() {
      boolean bu = BU;
      boolean bd = BD;
      boolean bl = BL;
      boolean br = BR;

      if(bu && bd){
        bu = false;
        bd = false;
      }
      if(bl && br){
        bl = false;
        br = false;
      }

      if(!bu && !bd && !bl && !br){
        mytank.setMoving(false);
      } else {

        if(!mytank.isMoving()){
          NettyClient.INSTANCE.write(new TankMoveMsg(mytank));
          mytank.setMoving(true);
        }

        if(bu && bl){
          mytank.setDir(Dir.NORTHWEST);
        }else if(bu && br){
          mytank.setDir(Dir.NORTHEAST);
        }else if(br && bd){
          mytank.setDir(Dir.SOUTHEAST);
        }else if(bl && bd){
          mytank.setDir(Dir.SOUTHWEST);
        }else if(bu){
          mytank.setDir(Dir.NORTH);
        }else if(bd){
          mytank.setDir(Dir.SOUTH);
        }else if(bl){
          mytank.setDir(Dir.WEST);
        }else if(br){
          mytank.setDir(Dir.EAST);
        }

      }

    }

    @Override
    public void keyReleased(KeyEvent e) {
      int keyCode = e.getKeyCode();
      switch (keyCode){
        case KeyEvent.VK_UP:
          BU = false;
          break;
        case KeyEvent.VK_DOWN:
          BD = false;
          break;
        case KeyEvent.VK_LEFT:
          BL = false;
          break;
        case KeyEvent.VK_RIGHT:
          BR = false;
          break;
        case KeyEvent.VK_CONTROL:
          mytank.fire();
          break;
      }

      setMainTankDir();
    }
  }
}
