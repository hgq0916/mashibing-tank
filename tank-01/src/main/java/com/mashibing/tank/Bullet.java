package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Iterator;

/**
 * 子弹类
 */
public class Bullet {

  public static final int SPEED = 15;
  public static final int WIDTH =  ReourseMgr.bulletD.getWidth();
  public static final int HEIGHT = ReourseMgr.bulletD.getHeight();
  private final TankFrame tf;

  private int x;
  private int y;
  private Dir dir;

  boolean live;

  public Bullet(int x, int y, Dir dir,TankFrame tankFrame) {
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.live =  true;
    this.tf = tankFrame;
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

  public Dir getDir() {
    return dir;
  }

  public void setDir(Dir dir) {
    this.dir = dir;
  }

  public void paint(Graphics g) {

    if(!this.live) this.tf.bullets.remove(this);

    Color oldColor = g.getColor();
    BufferedImage image = null;
    switch (dir){
      case NORTHEAST:
        image = ReourseMgr.bulletRU;
        break;
      case NORTHWEST:
        image = ReourseMgr.bulletLU;
        break;
      case SOUTHEAST:
        image = ReourseMgr.bulletRD;
        break;
      case SOUTHWEST:
        image = ReourseMgr.bulletLD;
        break;
      case WEST:
        image = ReourseMgr.bulletL;
        break;
      case EAST:
        image = ReourseMgr.bulletR;
        break;
      case NORTH:
        image = ReourseMgr.bulletU;
        break;
      case SOUTH:
        image = ReourseMgr.bulletD;
        break;
    }
    g.drawImage(image,x,y,null);
    g.setColor(oldColor);

    move();
  }

  private void move() {
    switch (dir){
      case NORTHEAST:
        y -= SPEED;
        x += SPEED;
        break;
      case NORTHWEST:
        y -= SPEED;
        x -= SPEED;
        break;
      case SOUTHEAST:
        y += SPEED;
        x += SPEED;
        break;
      case SOUTHWEST:
        y += SPEED;
        x -= SPEED;
        break;
      case WEST:
        x -= SPEED;
        break;
      case EAST:
        x += SPEED;
        break;
      case NORTH:
        y -= SPEED;
        break;
      case SOUTH:
        y += SPEED;
        break;
    }

    //碰撞检测
    collisionDetection();

    if(x<0 || y<0 || x>TankFrame.WIN_WIDTH || y>TankFrame.WIN_HEIGHT){
      this.live = false;
    }
  }

  public Rectangle getRectangle(){
    return new Rectangle(x,y,WIDTH,HEIGHT);
  }

  public boolean intersection(Tank tank){
    Rectangle tankRectangle = tank.getRectangle();
    Rectangle bulletRectangle = this.getRectangle();
    //判断两个矩形是否相交
    return tankRectangle.intersects(bulletRectangle);
  }

  private void collisionDetection() {
    Iterator<Tank> iterator = tf.enemyTanks.iterator();
    while (iterator.hasNext()){
      Tank tank = iterator.next();
      if(this.intersection(tank)){
        iterator.remove();
        this.live = false;
      }
    }
  }

}
