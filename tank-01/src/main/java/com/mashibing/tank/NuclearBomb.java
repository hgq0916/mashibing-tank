package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.NuclearBomb
 * @Description: 核弹
 * @date 2020/7/28 16:47
 */
public class NuclearBomb extends GameObject{

  private int x;
  private int y;
  public static final int WIDTH = ReourseMgr.nuclearBombD.getWidth();
  public static final int HEIGHT = ReourseMgr.nuclearBombD.getHeight();

  public static final int SPEED = 10;

  private boolean living = true;

  private Dir dir;

  private Group group;

  private GameModel gameModel;

  private Rectangle rectangle;

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public NuclearBomb(int x, int y,Dir dir, GameModel gameModel,Group group) {
    this.x = x;
    this.y = y;
    this.dir =dir;
    this.gameModel = gameModel;
    this.group = group;

    rectangle = new Rectangle(x,y,WIDTH,HEIGHT);
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

  public boolean isLiving() {
    return living;
  }

  public void setLiving(boolean living) {
    this.living = living;
  }

  public Dir getDir() {
    return dir;
  }

  public void setDir(Dir dir) {
    this.dir = dir;
  }

  public GameModel getGameModel() {
    return gameModel;
  }

  public void paint(Graphics g){

    if(!this.living){
      this.gameModel.remove(this);
      return;
    }

    Color color = g.getColor();
    BufferedImage image = null;
    switch (dir){
      case NORTHEAST:
        image = ReourseMgr.nuclearBombRU;
        break;
      case NORTHWEST:
        image = ReourseMgr.nuclearBombLU;
        break;
      case SOUTHEAST:
        image = ReourseMgr.nuclearBombRD;
        break;
      case SOUTHWEST:
        image = ReourseMgr.nuclearBombLD;
        break;
      case WEST:
        image = ReourseMgr.nuclearBombL;
        break;
      case EAST:
        image = ReourseMgr.nuclearBombR;
        break;
      case NORTH:
        image = ReourseMgr.nuclearBombU;
        break;
      case SOUTH:
        image = ReourseMgr.nuclearBombD;
        break;
    }
    g.drawImage(image,x,y,null);
    g.setColor(color);

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

    if(x<0 || y<0 || x> gameModel.tankFrame.WIN_WIDTH || y> gameModel.tankFrame.WIN_HEIGHT){
      this.living = false;
    }

    rectangle.x = x;
    rectangle.y = y;

  }

  public Rectangle getRectangle() {
    return rectangle;
  }

  public void die() {
    this.living = false;
  }
}
