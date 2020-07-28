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
  public void fire(Tank tank) {

    Bullet bullet = new Bullet(tank.getX()+Tank.TANK_WIDTH/2-Bullet.WIDTH/2,tank.getY()
        +Tank.TANK_HEIGHT/2-Bullet.HEIGHT/2,Dir.NORTH,tank.getTf(),tank.getGroup());

    Bullet bullet1 = new Bullet(tank.getX()+Tank.TANK_WIDTH/2-Bullet.WIDTH/2,tank.getY()
        +Tank.TANK_HEIGHT/2-Bullet.HEIGHT/2,Dir.EAST,tank.getTf(),tank.getGroup());

    Bullet bullet2 = new Bullet(tank.getX()+Tank.TANK_WIDTH/2-Bullet.WIDTH/2,tank.getY()
        +Tank.TANK_HEIGHT/2-Bullet.HEIGHT/2,Dir.SOUTH,tank.getTf(),tank.getGroup());

    Bullet bullet3 = new Bullet(tank.getX()+Tank.TANK_WIDTH/2-Bullet.WIDTH/2,tank.getY()
        +Tank.TANK_HEIGHT/2-Bullet.HEIGHT/2,Dir.WEST,tank.getTf(),tank.getGroup());

    tank.getTf().bullets.add(bullet);
    tank.getTf().bullets.add(bullet1);
    tank.getTf().bullets.add(bullet2);
    tank.getTf().bullets.add(bullet3);

  }

}
