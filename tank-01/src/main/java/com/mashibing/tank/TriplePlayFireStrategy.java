package com.mashibing.tank;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.TripleTapFireStrategy
 * @Description: 三连发射子弹
 * @date 2020/7/28 15:47
 */
public class TriplePlayFireStrategy implements FireStrategy{

  private TriplePlayFireStrategy(){}

  private static TriplePlayFireStrategy triplePlayFireStrategy = new TriplePlayFireStrategy();

  public static FireStrategy getInstance() {
    return triplePlayFireStrategy;
  }

  @Override
  public void fire(AbstractTank tank) {

    TankWarFactory tankWarFactory = TankWarFactoryContextHolder.getTankWarFactory();
    AbstractBullet bullet = tankWarFactory.createBullet(tank.getX()+ tank.getWidth()/2- GeneralBullet.WIDTH/2,tank.getY()
          + tank.getHeight()/2- GeneralBullet.HEIGHT/2,tank.getDir(),tank.getTf(),tank.getGroup());
      tank.getTf().bullets.add(bullet);

    AbstractBullet bullet1 = tankWarFactory.createBullet(tank.getX()+ tank.getWidth()/2- GeneralBullet.WIDTH/2-
        GeneralBullet.WIDTH/2,tank.getY()
        + tank.getHeight()/2- GeneralBullet.HEIGHT/2,tank.getDir(),tank.getTf(),tank.getGroup());
    tank.getTf().bullets.add(bullet1);

    AbstractBullet bullet2 = tankWarFactory.createBullet(tank.getX()+ tank.getWidth()/2-
        GeneralBullet.WIDTH/2+ GeneralBullet.WIDTH- GeneralBullet.WIDTH/2,tank.getY()
        + tank.getHeight()/2- GeneralBullet.HEIGHT/2,tank.getDir(),tank.getTf(),tank.getGroup());
    tank.getTf().bullets.add(bullet2);

  }

}
