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
import java.util.List;
import java.util.Random;

public class TankFrame extends Frame {

  public static final int WIN_WIDTH = 800;
  public static final int WIN_HEIGHT = 600;
  private Image image = null;

  Tank mytank = new Tank(200,400,Dir.SOUTH,this,Group.GOOD);
  List<Bullet> bullets = new ArrayList<>();
  List<Tank> enemyTanks = new ArrayList<>();
  List<Explode> explodes = new ArrayList<>();

  public TankFrame(){
    setSize(WIN_WIDTH,WIN_HEIGHT);
    setResizable(false);
    setTitle("mytank war");

    Dir[] values = Dir.values();
    Random random = new Random();
    for(int i=0;i<10;i++){

      Dir dir = values[random.nextInt(values.length)];
      Tank tank = new Tank(20+i*80,100,dir,this,Group.BAD);
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
    g.setColor(color);

    mytank.paint(g);

    for(int i=0;i<explodes.size();i++){
      explodes.get(i).paint(g);
    }

    for(int i=0;i<enemyTanks.size();i++){
      enemyTanks.get(i).paint(g);
    }

    for(int i=0;i<bullets.size();i++){
      bullets.get(i).paint(g);
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
            explodes.add(new Explode(bullet.getX(),bullet.getY(),this));
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
      }

      setMainTankDir();
    }
  }
}
