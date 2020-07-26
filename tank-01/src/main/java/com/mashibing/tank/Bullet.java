package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;

/**
 * 子弹类
 */
public class Bullet {

  public static final int SPEED = 10;
  public static final int WIDTH =  30;
  public static final int HEIGHT = 30;
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
    g.setColor(Color.RED);
    g.fillOval(x,y,WIDTH,HEIGHT);
    g.setColor(oldColor);

    move();
  }

  private void move() {
    switch (dir){
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
      }
    }
  }

}
