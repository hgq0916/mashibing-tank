package com.mashibing.tank;

import com.mashibing.io.BulletDeadMsg;
import com.mashibing.io.NettyClient;
import com.mashibing.io.TankDeadMsg;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.UUID;

/**
 * 子弹类
 */
public class Bullet {

  public static final int SPEED = PropertyMgrEnum.PROPERTY_MGR_INSTANCE.getInt("bulletSpeed");
  public static final int WIDTH =  ReourseMgr.bulletD.getWidth();
  public static final int HEIGHT = ReourseMgr.bulletD.getHeight();

  private Group group = Group.BAD;

  private Rectangle rectangle;

  private int x;
  private int y;
  private Dir dir;

  boolean living;

  private String id;
  private String tankId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTankId() {
    return tankId;
  }

  public void setTankId(String tankId) {
    this.tankId = tankId;
  }

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public boolean isLiving() {
    return living;
  }

  public void setLiving(boolean living) {
    this.living = living;
  }

  public Bullet(int x, int y, Dir dir,Group group,String tankId) {
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.living =  true;
    this.group = group;
    this.tankId = tankId;
    this.id = UUID.randomUUID().toString();

    rectangle = new Rectangle(x,y,WIDTH, HEIGHT);
  }

  public Bullet(String id,int x, int y, Dir dir,Group group,String tankId) {
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.living =  true;
    this.group = group;
    this.id = id;
    this.tankId = tankId;

    rectangle = new Rectangle(x,y,WIDTH, HEIGHT);
  }

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

  public Dir getDir() {
    return dir;
  }

  public void setDir(Dir dir) {
    this.dir = dir;
  }

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

  public Rectangle getRectangle(){
    return rectangle;
  }

  public void die() {
    this.living = false;
  }

  public void strikeWithTank(Tank tank) {
    if(!tank.getId().equals(this.getTankId())){
      Rectangle bulletRectangle = this.getRectangle();
      Rectangle tankRectangle = tank.getRectangle();
      if(bulletRectangle.intersects(tankRectangle)){
        //发生碰撞
        this.die();
        tank.die();
        NettyClient.INSTANCE.write(new BulletDeadMsg(this));
        NettyClient.INSTANCE.write(new TankDeadMsg(tank));
        //产生爆炸
        TankFrame.INSTANCE.explodes.add(new Explode(this.getX()+Bullet.WIDTH/2-Explode.WIDTH/2,this.getY()+Bullet.HEIGHT/2-Explode.HEIGHT/2));
        new Thread(()->{
          Audio explodeAudio = new Audio("audio/explode.wav");
          explodeAudio.play();
        }).start();
      }
    }
  }

}
