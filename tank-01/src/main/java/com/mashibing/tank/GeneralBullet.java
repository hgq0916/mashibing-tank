package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * 子弹类
 */
public class GeneralBullet extends AbstractBullet{

  public static final int SPEED = PropertyMgrEnum.PROPERTY_MGR_INSTANCE.getInt("bulletSpeed");
  public static final int WIDTH =  ReourseMgr.bulletD.getWidth();
  public static final int HEIGHT = ReourseMgr.bulletD.getHeight();

  private Rectangle rectangle;

  public GeneralBullet(int x, int y, Dir dir,TankFrame tankFrame,Group group) {
    super(x,y,dir,tankFrame,group);
    rectangle = new Rectangle(x,y,WIDTH, HEIGHT);
  }

  @Override
  public int getWidth() {
    return WIDTH;
  }

  @Override
  public int getHeight() {
    return HEIGHT;
  }

  @Override
  public int speed() {
    return SPEED;
  }

  @Override
  public void paint(Graphics g) {

    if(!this.living) return;

    Color oldColor = g.getColor();
    BufferedImage image = null;
    switch (dir){
      case NORTHEAST:
        image = ReourseMgr.bulletRU;
        break;
      case NORTHWEST:
        image = ReourseMgr.bulletLU;
        break;
      case SOUTHEAST:
        image = ReourseMgr.bulletRD;
        break;
      case SOUTHWEST:
        image = ReourseMgr.bulletLD;
        break;
      case WEST:
        image = ReourseMgr.bulletL;
        break;
      case EAST:
        image = ReourseMgr.bulletR;
        break;
      case NORTH:
        image = ReourseMgr.bulletU;
        break;
      case SOUTH:
        image = ReourseMgr.bulletD;
        break;
    }
    g.drawImage(image,x,y,null);
    g.setColor(oldColor);

    move();
  }

  private void move() {
    switch (dir){
      case NORTHEAST:
        y -= SPEED;
        x += SPEED;
        break;
      case NORTHWEST:
        y -= SPEED;
        x -= SPEED;
        break;
      case SOUTHEAST:
        y += SPEED;
        x += SPEED;
        break;
      case SOUTHWEST:
        y += SPEED;
        x -= SPEED;
        break;
      case WEST:
        x -= SPEED;
        break;
      case EAST:
        x += SPEED;
        break;
      case NORTH:
        y -= SPEED;
        break;
      case SOUTH:
        y += SPEED;
        break;
    }

    if(x<0 || y<0 || x>TankFrame.WIN_WIDTH || y>TankFrame.WIN_HEIGHT){
      this.living = false;
    }

    //移动方块
    rectangle.x = x;
    rectangle.y = y;
  }

  @Override
  public Rectangle getRectangle(){
    return rectangle;
  }

  @Override
  public void collideWithTank(AbstractTank tank) {
    if(!tank.getGroup().equals(this.getGroup())){
      Rectangle bulletRectangle = this.getRectangle();
      Rectangle tankRectangle = tank.getRectangle();
      if(bulletRectangle.intersects(tankRectangle)){
        //发生碰撞
        this.die();
        tank.die();
        //产生爆炸
        AbstractExplode expolode = TankWarFactoryContextHolder.getTankWarFactory().createExplode(this.getX()+ GeneralBullet.WIDTH/2- BombExplode.WIDTH/2,this.getY()+
            GeneralBullet.HEIGHT/2- BombExplode.HEIGHT/2,this.tf);
        this.tf.explodes.add(expolode);
        new Thread(()->{
          Audio explodeAudio = new Audio("audio/explode.wav");
          explodeAudio.play();
        }).start();
      }
    }
  }

}
