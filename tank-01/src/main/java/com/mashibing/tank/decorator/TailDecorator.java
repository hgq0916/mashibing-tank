package com.mashibing.tank.decorator;

import com.mashibing.tank.GameObject;
import java.awt.Color;
import java.awt.Graphics;

public class TailDecorator extends GameObject {

  private GameObject gameObject;

  public TailDecorator(GameObject gameObject){
    this.gameObject = gameObject;
  }

  @Override
  public void paint(Graphics g) {
    gameObject.paint(g);
    Color color = g.getColor();
    g.setColor(Color.RED);
    g.drawLine(gameObject.getX(),gameObject.getY(),gameObject.getX()+getWidth(),gameObject.getY()+getHeight());
    g.setColor(color);
  }

  @Override
  public int getWidth() {
    return gameObject.getWidth();
  }

  @Override
  public int getHeight() {
    return gameObject.getHeight();
  }
}
