package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * 墙
 */
public class Wall extends GameObject{

  private int w;
  private int h;

  private Rectangle rectangle;

  //private GameModel gameModel;

  public Wall(int x, int y,int w,int h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    //this.gameModel = gameModel;
    rectangle = new Rectangle(x,y,w,h);
  }

/*  public GameModel getGameModel() {
    return gameModel;
  }

  public void setGameModel(GameModel gameModel) {
    this.gameModel = gameModel;
  }*/

  @Override
  public void paint(Graphics g) {
    Color color = g.getColor();
    g.setColor(Color.WHITE);
    g.fillRect(x,y,w,h);
    g.setColor(color);
  }

  @Override
  public int getWidth() {
    return w;
  }

  @Override
  public int getHeight() {
    return h;
  }

  public Rectangle getRectangle(){
    return rectangle;
  }

}