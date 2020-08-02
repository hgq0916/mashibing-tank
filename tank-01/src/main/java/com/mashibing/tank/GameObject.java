package com.mashibing.tank;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.GameObject
 * @Description: 游戏对象
 * @date 2020/7/31 10:13
 */
public abstract class GameObject {

  protected int x;
  protected int y;

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

  public abstract void paint(Graphics g);

  public void keyPressed(KeyEvent e){

  }

  public void keyReleased(KeyEvent e){

  }

  public abstract int getWidth();

  public abstract int getHeight();

}
