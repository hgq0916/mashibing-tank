package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;

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
      case LEFT:
        x -= SPEED;
        break;
      case RIGHT:
        x += SPEED;
        break;
      case UP:
        y -= SPEED;
        break;
      case DOWN:
        y += SPEED;
        break;
    }

    if(x<0 || y<0 || x>TankFrame.WIN_WIDTH || y>TankFrame.WIN_HEIGHT){
      this.live = false;
    }
  }
}
