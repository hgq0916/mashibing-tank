package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 抽象坦克类
 */
public abstract class AbstractTank {

  protected final TankFrame tf;
  protected int x,y;
  protected Dir dir;

  protected Group group;

  protected boolean moving = false;

  protected boolean living =true;

  public AbstractTank(int x, int y, Dir dir,TankFrame tf,Group group) {
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.tf = tf;
    this.group = group;
  }

  public TankFrame getTf() {
    return tf;
  }

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

  public abstract void paint(Graphics g);

  public abstract void fire();

  public abstract void fire(FireStrategy fireStrategy);

  public abstract Rectangle getRectangle();

  public abstract int getWidth();

  public abstract int getHeight();

  public abstract int speed();

  public void die() {
    this.living = false;
  }

}
