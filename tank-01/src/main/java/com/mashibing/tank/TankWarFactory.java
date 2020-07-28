package com.mashibing.tank;

/**
 * 坦克大战抽象工厂
 */
public interface TankWarFactory {

  /**
   * 生产坦克
   */
  AbstractTank createTank(int x, int y, Dir dir,TankFrame tf,Group group);


  /**
   * 生产子弹
   */
  AbstractBullet createBullet(int x, int y, Dir dir,TankFrame tankFrame,Group group);

  /**
   * 爆炸
   */
  AbstractExplode createExplode(int x,int y,TankFrame tf);


}
