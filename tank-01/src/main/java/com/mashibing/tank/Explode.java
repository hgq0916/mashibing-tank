package com.mashibing.tank;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * 爆炸
 */
public class Explode {

  public static final int WIDTH = ReourseMgr.explodes[0].getWidth();
  public static final int HEIGHT = ReourseMgr.explodes[0].getHeight();
  private final TankFrame tf;
  private int x;
  private int y;

  private boolean living = true;

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

  public Explode(int x,int y,TankFrame tf){
    this.x = x;
    this.y = y;
    this.tf = tf;
  }

  private int step = 0;

  public void paint(Graphics g){

    if(!this.living) return;

    BufferedImage bufferedImage = ReourseMgr.explodes[step++];

    g.drawImage(bufferedImage,x,y,null);

    if(step>=ReourseMgr.explodes.length){
      this.living = false;
    }

  }

}
