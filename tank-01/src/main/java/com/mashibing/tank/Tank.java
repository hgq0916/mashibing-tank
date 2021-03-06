package com.mashibing.tank;

import com.mashibing.io.BulletNewMsg;
import com.mashibing.io.NettyClient;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.UUID;

/**
 * 坦克类
 */
public class Tank {

  private static final int TANK_WIDTH = ReourseMgr.goodtankD.getWidth();
  private static final int TANK_HEIGHT = ReourseMgr.goodtankD.getHeight();

  private int x,y;
  private Dir dir = Dir.SOUTH;

  private String id;

  private Group group = Group.BAD;

  private Rectangle rectangle;

  private boolean moving = false;

  private static final int SPEED = PropertyMgrEnum.PROPERTY_MGR_INSTANCE.getInt("tankSpeed");

  private boolean living =true;

  private Random random = new Random();

  public Tank(int x, int y, Dir dir,Group group) {
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.group = group;
    this.id =  UUID.randomUUID().toString();
    rectangle = new Rectangle(x,y,TANK_WIDTH,TANK_HEIGHT);
  }

  public Tank(String id,int x, int y, Dir dir,Group group) {
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.group = group;
    rectangle = new Rectangle(x,y,TANK_WIDTH,TANK_HEIGHT);
    this.id =  id;
  }

  public static Tank randomTank(Group group) {
    Random r = new Random();
    int x = r.nextInt(TankFrame.WIN_WIDTH - TANK_WIDTH);
    int y = r.nextInt(TankFrame.WIN_HEIGHT-TANK_HEIGHT-28)+28;
    Dir dir = Dir.randomDir();
    Tank tank = new Tank(x,y,dir,group);
    return tank;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public boolean isMoving() {
    return moving;
  }

  public void setMoving(boolean moving) {
    this.moving = moving;
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

    if(!this.living){
      return;
    }

    Color color = g.getColor();
    g.setColor(Color.GREEN);
    g.drawString(this.id,x,y-10);
    BufferedImage image = null;

    if(Group.GOOD.equals(group)){
      switch (dir){
        case NORTHEAST:
          image = ReourseMgr.goodtankRU;
          break;
        case NORTHWEST:
          image = ReourseMgr.goodtankLU;
          break;
        case SOUTHEAST:
          image = ReourseMgr.goodtankRD;
          break;
        case SOUTHWEST:
          image = ReourseMgr.goodtankLD;
          break;
        case WEST:
          image = ReourseMgr.goodtankL;
          break;
        case EAST:
          image = ReourseMgr.goodtankR;
          break;
        case NORTH:
          image = ReourseMgr.goodtankU;
          break;
        case SOUTH:
          image = ReourseMgr.goodtankD;
          break;
      }
    }else {
      switch (dir){
        case NORTHEAST:
          image = ReourseMgr.badtankRU;
          break;
        case NORTHWEST:
          image = ReourseMgr.badtankLU;
          break;
        case SOUTHEAST:
          image = ReourseMgr.badtankRD;
          break;
        case SOUTHWEST:
          image = ReourseMgr.badtankLD;
          break;
        case WEST:
          image = ReourseMgr.badtankL;
          break;
        case EAST:
          image = ReourseMgr.badtankR;
          break;
        case NORTH:
          image = ReourseMgr.badtankU;
          break;
        case SOUTH:
          image = ReourseMgr.badtankD;
          break;
      }
    }

    g.drawImage(image,x,y,null);
    g.setColor(color);
    move();
  }

  private void move() {

    if(moving){
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
    }

    //边界检测
    boundaryDetect();

    if(Group.BAD.equals(group)){
      if(random.nextInt(30)>28){
        this.dir = Dir.randomDir();
      }
      if(random.nextInt(20)>18){
        this.fire();
      }
    }

    //移动方块
    rectangle.x = x;
    rectangle.y = y;
  }

  private void boundaryDetect() {
    if(x<2) this.x = 2;
    if(y<32) this.y = 32;
    if(x+Tank.TANK_WIDTH>TankFrame.WIN_WIDTH-2) x = TankFrame.WIN_WIDTH-Tank.TANK_WIDTH-2;
    if(y+Tank.TANK_HEIGHT>TankFrame.WIN_HEIGHT-2) y = TankFrame.WIN_HEIGHT-Tank.TANK_HEIGHT-2;
  }

  public void fire() {
    if(!this.living) return;
    Bullet bullet = new Bullet(this.x+TANK_WIDTH/2-Bullet.WIDTH/2,this.y+TANK_HEIGHT/2-Bullet.HEIGHT/2,this.dir,this.group,this.id);
    TankFrame.INSTANCE.bulletMap.put(bullet.getId(),bullet);
    //发送子弹消息
    NettyClient.INSTANCE.write(new BulletNewMsg(bullet));
  }

  public Rectangle getRectangle() {
    return rectangle;
  }

  public void die() {
    this.living = false;
  }

  @Override
  public String toString() {
    return "Tank{" +
        "x=" + x +
        ", y=" + y +
        ", dir=" + dir +
        ", id='" + id + '\'' +
        ", group=" + group +
        ", rectangle=" + rectangle +
        ", moving=" + moving +
        ", living=" + living +
        ", random=" + random +
        '}';
  }
}
