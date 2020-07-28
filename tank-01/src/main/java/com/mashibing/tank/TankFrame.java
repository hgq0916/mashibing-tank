package com.mashibing.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TankFrame extends Frame {

  public static final int WIN_WIDTH = PropertyMgrEnum.PROPERTY_MGR_INSTANCE.getInt("gameWidth");
  public static final int WIN_HEIGHT = PropertyMgrEnum.PROPERTY_MGR_INSTANCE.getInt("gameHeight");
  private Image image = null;

  private FireStrategy  fourDirectionFireStrategy = FourDirectionFireStrategy.getInstance();
  private FireStrategy  eightDirectionFireStrategy = EightDirectionFireStrategy.getInstance();
  private FireStrategy  triplePlayFireStrategy = TriplePlayFireStrategy.getInstance();
  private FireStrategy  nuclearBombFireStrategy = NuclearBombFireStrategy.getInstance();

  LightTank mytank = new LightTank(200,400,Dir.SOUTH,this,Group.GOOD);
  List<Bullet> bullets = new ArrayList<>();
  List<LightTank> enemyTanks = new ArrayList<>();
  List<Explode> explodes = new ArrayList<>();
  List<NuclearBomb> nuclearBombs = new ArrayList<>();

  public TankFrame(){
    setSize(WIN_WIDTH,WIN_HEIGHT);
    setResizable(false);
    setTitle("mytank war");

    Dir[] values = Dir.values();
    Random random = new Random();

    int initTankCount = PropertyMgrEnum.PROPERTY_MGR_INSTANCE.getInt("initTankCount");
    for(int i=0;i<initTankCount;i++){

      Dir dir = values[random.nextInt(values.length)];
      LightTank tank = new LightTank(20+i*80,100,dir,this,Group.BAD);
      tank.setMoving(true);
      enemyTanks.add(tank);
    }

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
    Iterator<LightTank> tankIterator = enemyTanks.iterator();
    while (tankIterator.hasNext()){
      LightTank tank = tankIterator.next();
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
        LightTank tank = enemyTanks.get(j);
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
        LightTank tank = enemyTanks.get(j);
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
        mytank.setMoving(true);

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
        case KeyEvent.VK_Z:
          mytank.fire(triplePlayFireStrategy);
          break;
        case KeyEvent.VK_SPACE:
          mytank.fire(nuclearBombFireStrategy);
          break;
        case KeyEvent.VK_ENTER:
          mytank.fire(eightDirectionFireStrategy);
          break;
      }

      setMainTankDir();
    }
  }
}
