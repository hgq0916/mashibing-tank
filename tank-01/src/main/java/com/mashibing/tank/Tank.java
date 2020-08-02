package com.mashibing.tank;

import com.mashibing.tank.decorator.RectDecorator;
import com.mashibing.tank.decorator.TailDecorator;
import com.mashibing.tank.observer.FireEvent;
import com.mashibing.tank.observer.FireListener;
import com.mashibing.tank.observer.MyTankFireListener;
import com.mashibing.tank.singleton.PropertyMgrEnum;
import com.mashibing.tank.strategy.EightDirectionFireStrategy;
import com.mashibing.tank.strategy.FireStrategy;
import com.mashibing.tank.strategy.FourDirectionFireStrategy;
import com.mashibing.tank.strategy.NuclearBombFireStrategy;
import com.mashibing.tank.strategy.TriplePlayFireStrategy;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 坦克类
 */
public class Tank extends GameObject{

  public static final int TANK_WIDTH = ReourseMgr.goodtankD.getWidth();
  public static final int TANK_HEIGHT = ReourseMgr.goodtankD.getHeight();
 // private final TankFrame tf;

  //private GameModel gameModel;

  private List<FireListener> fireListeners = new ArrayList<>();

  {
    fireListeners.add(new MyTankFireListener());
  }

  private int oldX;
  private int oldY;

  private Dir dir = Dir.SOUTH;

  private Group group = Group.BAD;

  private Rectangle rectangle;

  private boolean moving = false;

  private static final int SPEED = PropertyMgrEnum.PROPERTY_MGR_INSTANCE.getInt("tankSpeed");

  private boolean living =true;

  private Random random = new Random();

  private FireStrategy fireStrategy;

  private FireStrategy  fourDirectionFireStrategy = FourDirectionFireStrategy.getInstance();
  private FireStrategy  eightDirectionFireStrategy = EightDirectionFireStrategy.getInstance();
  private FireStrategy  triplePlayFireStrategy = TriplePlayFireStrategy.getInstance();
  private FireStrategy  nuclearBombFireStrategy = NuclearBombFireStrategy.getInstance();

  public Tank(int x, int y, Dir dir,Group group) {
    this.x = x;
    this.y = y;
    this.dir = dir;
    //this.gameModel = gameModel;
    this.group = group;
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

/*  public GameModel getGameModel() {
    return gameModel;
  }*/

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

  public Dir getDir() {
    return dir;
  }

  public void setDir(Dir dir) {
    this.dir = dir;
  }

  public void paint(Graphics g) {

    if(!this.living){
      GameModel.getInstance().remove(this);
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

    oldX = x;
    oldY = y;

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

      Bullet bullet = new Bullet(this.x+TANK_WIDTH/2-Bullet.WIDTH/2,this.y+TANK_HEIGHT/2-Bullet.HEIGHT/2,this.dir,this.group);
      GameModel.getInstance().add(new TailDecorator(new RectDecorator(bullet)));
      if(Group.GOOD.equals(this.group)){
        new Thread(()->{
          new Audio("audio/tank_fire.wav").play();
        }).start();
      }

    FireEvent fireEvent = new FireEvent(this);
      for(FireListener fireListener :fireListeners){
        fireListener.fire(fireEvent);
      }
  }

  public void fire(FireStrategy fireStrategy){
    fireStrategy.fire(this);
    if(Group.GOOD.equals(this.group)){
      new Thread(()->{
        new Audio("audio/tank_fire.wav").play();
      }).start();
    }
  }

  public Rectangle getRectangle() {
    return rectangle;
  }

  public void die() {
    this.living = false;
  }



  private boolean BU = false;
  private boolean BD = false;
  private boolean BL = false;
  private boolean BR = false;

  @Override
  public void keyPressed(KeyEvent e) {

    if(!Group.GOOD.equals(this.group)) return;

    int keyCode = e.getKeyCode();

    switch (keyCode){
      case KeyEvent.VK_UP:
        BU = true;
        break;
      case KeyEvent.VK_DOWN:
        BD = true;
        break;
      case KeyEvent.VK_LEFT:
        BL = true;
        break;
      case KeyEvent.VK_RIGHT:
        BR = true;
        break;
    }

    setMainTankDir();
  }

  @Override
  public void keyReleased(KeyEvent e) {

    if(!Group.GOOD.equals(this.group)) return;

    int keyCode = e.getKeyCode();
    switch (keyCode){
      case KeyEvent.VK_UP:
        BU = false;
        break;
      case KeyEvent.VK_DOWN:
        BD = false;
        break;
      case KeyEvent.VK_LEFT:
        BL = false;
        break;
      case KeyEvent.VK_RIGHT:
        BR = false;
        break;
      case KeyEvent.VK_CONTROL:
        this.fire();
        break;
      case KeyEvent.VK_Z:
        this.fire(triplePlayFireStrategy);
        break;
      case KeyEvent.VK_SPACE:
        this.fire(nuclearBombFireStrategy);
        break;
      case KeyEvent.VK_ENTER:
        this.fire(eightDirectionFireStrategy);
        break;
    }

    setMainTankDir();
  }

  @Override
  public int getWidth() {
    return TANK_WIDTH;
  }

  @Override
  public int getHeight() {
    return TANK_HEIGHT;
  }

  private void setMainTankDir() {
    boolean bu = BU;
    boolean bd = BD;
    boolean bl = BL;
    boolean br = BR;

    if(bu && bd){
      bu = false;
      bd = false;
    }
    if(bl && br){
      bl = false;
      br = false;
    }

    if(!bu && !bd && !bl && !br){
      this.setMoving(false);
    } else {
      this.setMoving(true);

      if(bu && bl){
        this.setDir(Dir.NORTHWEST);
      }else if(bu && br){
        this.setDir(Dir.NORTHEAST);
      }else if(br && bd){
        this.setDir(Dir.SOUTHEAST);
      }else if(bl && bd){
        this.setDir(Dir.SOUTHWEST);
      }else if(bu){
        this.setDir(Dir.NORTH);
      }else if(bd){
        this.setDir(Dir.SOUTH);
      }else if(bl){
        this.setDir(Dir.WEST);
      }else if(br){
        this.setDir(Dir.EAST);
      }

    }

  }

  public void stop() {
    this.moving = false;
  }

  /**
   * 返回上一步
   */
  public void back() {
    this.x = oldX;
    this.y = oldY;
  }

}
