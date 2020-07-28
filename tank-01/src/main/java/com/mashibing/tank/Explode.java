package com.mashibing.tank;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * 爆炸
 */
public class Explode extends AbstractExplode{

  public static final int WIDTH = ReourseMgr.explodes[0].getWidth();
  public static final int HEIGHT = ReourseMgr.explodes[0].getHeight();

  public Explode(int x,int y,TankFrame tf){
    super(x,y,tf);
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
