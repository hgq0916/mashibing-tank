package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 重型坦克类
 */
public class HeavyTank extends AbstractTank{

  public static final int TANK_WIDTH = ReourseMgr.goodtankD.getWidth();
  public static final int TANK_HEIGHT = ReourseMgr.goodtankD.getHeight();

  private Rectangle rectangle;

  private static final int SPEED = PropertyMgrEnum.PROPERTY_MGR_INSTANCE.getInt("tankSpeed");

  private Random random = new Random();

  private FireStrategy fireStrategy;

  public HeavyTank(int x, int y, Dir dir,TankFrame tf,Group group) {

    super(x,y,dir,tf,group);

    rectangle = new Rectangle(x,y,TANK_WIDTH,TANK_HEIGHT);

    try {
      Class<?> fireStrategyClass = Class
          .forName(PropertyMgr.getInstance().getString("fireStrategyClass"));
      fireStrategy = (FireStrategy) fireStrategyClass.newInstance();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void paint(Graphics g) {

    if(!this.living){
      return;
    }

    Color color = g.getColor();
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
    if(x+ HeavyTank.TANK_WIDTH>TankFrame.WIN_WIDTH-2) x = TankFrame.WIN_WIDTH- HeavyTank.TANK_WIDTH-2;
    if(y+ HeavyTank.TANK_HEIGHT>TankFrame.WIN_HEIGHT-2) y = TankFrame.WIN_HEIGHT- HeavyTank.TANK_HEIGHT-2;
  }

  @Override
  public void fire() {
    this.fireStrategy.fire(this);
  }

  @Override
  public void fire(FireStrategy fireStrategy){
    fireStrategy.fire(this);
  }

  @Override
  public Rectangle getRectangle() {
    return rectangle;
  }

  @Override
  public int getWidth() {
    return TANK_WIDTH;
  }

  @Override
  public int getHeight() {
    return TANK_HEIGHT;
  }

  @Override
  public int speed() {
    return SPEED;
  }

}
