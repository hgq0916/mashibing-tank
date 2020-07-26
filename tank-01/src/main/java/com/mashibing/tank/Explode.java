package com.mashibing.tank;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * 爆炸
 */
public class Explode {

  private final TankFrame tf;
  private int x;
  private int y;

  private boolean living = true;

  public Explode(int x,int y,TankFrame tf){
    this.x = x;
    this.y = y;
    this.tf = tf;
  }

  private int step = 0;

  public void paint(Graphics g){

    if(!this.living){
      this.tf.explodes.remove(this);
      return;
    }

    BufferedImage bufferedImage = ReourseMgr.explodes[step++];
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    int startX = x - width/2;
    int startY = y - height/2;

    g.drawImage(bufferedImage,startX,startY,null);

    if(step>=ReourseMgr.explodes.length){
      this.living = false;
    }

  }

}
