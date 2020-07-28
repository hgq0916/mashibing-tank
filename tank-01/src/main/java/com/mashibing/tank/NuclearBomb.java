package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.NuclearBomb
 * @Description: 核弹
 * @date 2020/7/28 16:47
 */
public class NuclearBomb extends AbstractBullet{

  public static final int WIDTH = ReourseMgr.nuclearBombD.getWidth();
  public static final int HEIGHT = ReourseMgr.nuclearBombD.getHeight();

  public static final int SPEED = 10;

  private boolean living = true;

  private Rectangle rectangle;

  public NuclearBomb(int x, int y,Dir dir, TankFrame tankFrame,Group group) {

    super(x,y,dir,tankFrame,group);
    rectangle = new Rectangle(x,y,WIDTH,HEIGHT);
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
  public void paint(Graphics g){

    if(!this.living) return;

    Color color = g.getColor();
    BufferedImage image = null;
    switch (dir){
      case NORTHEAST:
        image = ReourseMgr.nuclearBombRU;
        break;
      case NORTHWEST:
        image = ReourseMgr.nuclearBombLU;
        break;
      case SOUTHEAST:
        image = ReourseMgr.nuclearBombRD;
        break;
      case SOUTHWEST:
        image = ReourseMgr.nuclearBombLD;
        break;
      case WEST:
        image = ReourseMgr.nuclearBombL;
        break;
      case EAST:
        image = ReourseMgr.nuclearBombR;
        break;
      case NORTH:
        image = ReourseMgr.nuclearBombU;
        break;
      case SOUTH:
        image = ReourseMgr.nuclearBombD;
        break;
    }
    g.drawImage(image,x,y,null);
    g.setColor(color);

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

    if(x<0 || y<0 || x>tf.WIN_WIDTH || y>tf.WIN_HEIGHT){
      this.living = false;
    }

    rectangle.x = x;
    rectangle.y = y;

  }

  @Override
  public Rectangle getRectangle() {
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
        //产生爆炸new BombExplode()
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
