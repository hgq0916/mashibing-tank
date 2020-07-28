package com.mashibing.tank;

/**
 * 坦克大战抽象工厂
 */
public interface TankWarFactory {

  /**
   * 生产坦克
   */
  AbstractTank createTank();


  /**
   * 生产子弹
   */
  AbstractBullet createBullet();

  /**
   * 爆炸
   */



}
