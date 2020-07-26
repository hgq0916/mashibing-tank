package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * 坦克类
 */
public class Tank {

  private static final int TANK_WIDTH = 50;
  private static final int TANK_HEIGHT = 50;
  private final TankFrame tf;
  private int x,y;
  private Dir dir = Dir.SOUTH;

  private boolean good;

  private boolean moving = false;

  private static final int SPEED = 10;

  public Tank(int x, int y, Dir dir,TankFrame tf,boolean good) {
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.tf = tf;
    this.good = good;
  }

  public boolean isMoving() {
    return moving;
  }

  public void setMoving(boolean moving) {
    this.moving = moving;
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
    Color color = g.getColor();
    if(good){
      g.setColor(Color.ORANGE);
    }else {
      g.setColor(Color.BLUE);
    }

    g.fillRect(x,y,TANK_WIDTH,TANK_HEIGHT);
    g.setColor(color);
    move();
  }

  private void move() {
    if(moving){
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
    }
  }

  public void fire() {
    Bullet bullet = new Bullet(this.x+TANK_WIDTH/2-Bullet.WIDTH/2,this.y+TANK_HEIGHT/2-Bullet.HEIGHT/2,this.dir,this.tf);
    this.tf.bullets.add(bullet);
  }

  public Rectangle getRectangle() {
    return new Rectangle(x,y,TANK_WIDTH,TANK_HEIGHT);
  }

}
