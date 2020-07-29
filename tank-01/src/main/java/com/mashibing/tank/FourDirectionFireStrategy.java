package com.mashibing.tank;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.TripleTapFireStrategy
 * @Description: 往四个方向发射子弹
 * @date 2020/7/28 15:47
 */
public class FourDirectionFireStrategy implements FireStrategy{

  private static FourDirectionFireStrategy fourDirectionFireStrategy = new FourDirectionFireStrategy();

  private FourDirectionFireStrategy(){}

  public static FourDirectionFireStrategy getInstance(){
    return fourDirectionFireStrategy;
  }

  @Override
  public void fire(AbstractTank tank) {

    TankWarFactory tankWarFactory = TankWarFactoryContextHolder.getTankWarFactory();
    AbstractBullet bullet = tankWarFactory.createBullet(tank.getX()+ tank.getWidth()/2- GeneralBullet.WIDTH/2,tank.getY()
        + tank.getHeight()/2- GeneralBullet.HEIGHT/2,Dir.NORTH,tank.getTf(),tank.getGroup());

    AbstractBullet bullet1 = tankWarFactory.createBullet(tank.getX()+ tank.getWidth()/2- GeneralBullet.WIDTH/2,tank.getY()
        + tank.getHeight()/2- GeneralBullet.HEIGHT/2,Dir.EAST,tank.getTf(),tank.getGroup());

    AbstractBullet bullet2 = tankWarFactory.createBullet(tank.getX()+ tank.getWidth()/2- GeneralBullet.WIDTH/2,tank.getY()
        +tank.getHeight()/2- GeneralBullet.HEIGHT/2,Dir.SOUTH,tank.getTf(),tank.getGroup());

    AbstractBullet bullet3 = tankWarFactory.createBullet(tank.getX()+ tank.getWidth()/2- GeneralBullet.WIDTH/2,tank.getY()
        + tank.getHeight()/2- GeneralBullet.HEIGHT/2,Dir.WEST,tank.getTf(),tank.getGroup());

    tank.getTf().bullets.add(bullet);
    tank.getTf().bullets.add(bullet1);
    tank.getTf().bullets.add(bullet2);
    tank.getTf().bullets.add(bullet3);

  }

}
