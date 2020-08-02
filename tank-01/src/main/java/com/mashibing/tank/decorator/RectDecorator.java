package com.mashibing.tank.decorator;

import com.mashibing.tank.GameObject;
import java.awt.Color;
import java.awt.Graphics;

public class RectDecorator extends GameObject {

  private GameObject gameObject;

  public RectDecorator(GameObject gameObject) {
    this.gameObject = gameObject;
    this.x = gameObject.getX();
    this.y = gameObject.getY();
  }

  @Override
  public void paint(Graphics g) {
    this.x = gameObject.getX();
    this.y = gameObject.getY();
    gameObject.paint(g);
    Color color = g.getColor();
    g.setColor(Color.YELLOW);
    g.drawRect(x,y,getWidth()+2,getHeight()+2);
    g.setColor(color);
  }

  @Override
  public int getWidth() {
    return gameObject.getWidth()+2;
  }

  @Override
  public int getHeight() {
    return gameObject.getHeight()+2;
  }

}