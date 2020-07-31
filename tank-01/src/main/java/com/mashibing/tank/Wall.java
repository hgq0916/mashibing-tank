package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * 墙
 */
public class Wall extends GameObject{

  public static final int WIDTH=40;
  public static final int HEIGHT=150;

  private int x;
  private int y;

  private Rectangle rectangle;

  private GameModel gameModel;

  public Wall(int x, int y, GameModel gameModel) {
    this.x = x;
    this.y = y;
    this.gameModel = gameModel;
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

  public GameModel getGameModel() {
    return gameModel;
  }

  public void setGameModel(GameModel gameModel) {
    this.gameModel = gameModel;
  }

  @Override
  public void paint(Graphics g) {
    Color color = g.getColor();
    g.setColor(Color.WHITE);
    g.fillRect(x,y,WIDTH,HEIGHT);
    g.setColor(color);
  }

  public Rectangle getRectangle(){
    return rectangle;
  }

}
