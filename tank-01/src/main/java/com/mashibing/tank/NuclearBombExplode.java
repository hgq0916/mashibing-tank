package com.mashibing.tank;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * 核弹爆炸
 */
public class NuclearBombExplode extends AbstractExplode{

  public static final int WIDTH = ReourseMgr.explodes[0].getWidth();
  public static final int HEIGHT = ReourseMgr.explodes[0].getHeight();

  public NuclearBombExplode(int x,int y,TankFrame tf){
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

  @Override
  public int getWidth() {
    return WIDTH;
  }

  @Override
  public int getHeight() {
    return HEIGHT;
  }

}
