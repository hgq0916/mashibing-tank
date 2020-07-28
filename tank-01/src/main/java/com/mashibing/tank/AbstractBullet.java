package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * 抽象子弹类
 */
public abstract class AbstractBullet {

  protected final TankFrame tf;

  protected Group group;

  protected int x;
  protected int y;
  protected Dir dir;

  protected boolean living;

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public boolean isLiving() {
    return living;
  }

  public void setLiving(boolean living) {
    this.living = living;
  }

  public AbstractBullet(int x, int y, Dir dir,TankFrame tankFrame,Group group) {
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.living =  true;
    this.tf = tankFrame;
    this.group = group;
  }

  public abstract int getWidth();

  public abstract int getHeight();

  public abstract int speed();

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

  public abstract void paint(Graphics g);

  public abstract Rectangle getRectangle();

  public void die() {
    this.living = false;
  }

}
