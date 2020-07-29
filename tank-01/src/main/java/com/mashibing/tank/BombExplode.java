package com.mashibing.tank;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * 爆炸
 */
public class BombExplode extends AbstractExplode{

  public static final int WIDTH = ReourseMgr.explodes1[0].getWidth();
  public static final int HEIGHT = ReourseMgr.explodes1[0].getHeight();

  public BombExplode(int x,int y,TankFrame tf){
    super(x,y,tf);
  }

  private int step = 0;

  public void paint(Graphics g){

    if(!this.living) return;

    BufferedImage bufferedImage = ReourseMgr.explodes1[step++];

    g.drawImage(bufferedImage,x,y,null);

    if(step>=ReourseMgr.explodes1.length){
      this.living = false;
    }

  }

  @Override
  public int getWidth() {
    return WIDTH;
  }

  @Override
  public int getHeight() {
    return HEIGHT;
  }

}
