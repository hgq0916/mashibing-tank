package com.mashibing.tank.decorator;

import com.mashibing.tank.GameObject;
import java.awt.Color;
import java.awt.Graphics;

public class RectDecorator extends GameObject {

  private GameObject gameObject;

  public RectDecorator(GameObject gameObject) {
    this.gameObject = gameObject;
  }

  @Override
  public void paint(Graphics g) {
    gameObject.paint(g);
    Color color = g.getColor();
    g.setColor(Color.YELLOW);
    g.drawRect(gameObject.getX(),gameObject.getY(),gameObject.getWidth()+2,gameObject.getHeight()+2);
    g.setColor(color);
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public int getHeight() {
    return 0;
  }

}
