package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * 子弹类
 */
public class Bullet {

  public static final int SPEED = 12;
  public static final int WIDTH =  ReourseMgr.bulletD.getWidth();
  public static final int HEIGHT = ReourseMgr.bulletD.getHeight();
  private final TankFrame tf;

  private Group group = Group.BAD;

  private Rectangle rectangle;

  private int x;
  private int y;
  private Dir dir;

  boolean living;

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

  public Bullet(int x, int y, Dir dir,TankFrame tankFrame,Group group) {
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.living =  true;
    this.tf = tankFrame;
    this.group = group;

    rectangle = new Rectangle(x,y,WIDTH, HEIGHT);
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

    if(!this.living) return;

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

    if(x<0 || y<0 || x>TankFrame.WIN_WIDTH || y>TankFrame.WIN_HEIGHT){
      this.living = false;
    }

    //移动方块
    rectangle.x = x;
    rectangle.y = y;
  }

  public Rectangle getRectangle(){
    return rectangle;
  }

  public void die() {
    this.living = false;
  }

}
