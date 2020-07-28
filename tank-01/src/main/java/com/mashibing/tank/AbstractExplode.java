package com.mashibing.tank;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * 抽象爆炸
 */
public abstract class AbstractExplode {

  protected final TankFrame tf;
  protected int x;
  protected int y;

  protected boolean living = true;

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

  public boolean isLiving() {
    return living;
  }

  public void setLiving(boolean living) {
    this.living = living;
  }

  public AbstractExplode(int x,int y,TankFrame tf){
    this.x = x;
    this.y = y;
    this.tf = tf;
  }

  public abstract void paint(Graphics g);

  public abstract int getWidth();

  public abstract int getHeight();

}
