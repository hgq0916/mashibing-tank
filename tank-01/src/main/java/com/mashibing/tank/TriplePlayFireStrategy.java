package com.mashibing.tank;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.TripleTapFireStrategy
 * @Description: 三连发射子弹
 * @date 2020/7/28 15:47
 */
public class TriplePlayFireStrategy implements FireStrategy{

  @Override
  public void fire(Tank tank) {

      Bullet bullet = new Bullet(tank.getX()+Tank.TANK_WIDTH/2-Bullet.WIDTH/2,tank.getY()
          +Tank.TANK_HEIGHT/2-Bullet.HEIGHT/2,tank.getDir(),tank.getTf(),tank.getGroup());
      tank.getTf().bullets.add(bullet);

    Bullet bullet1 = new Bullet(tank.getX()+Tank.TANK_WIDTH/2-Bullet.WIDTH/2-Bullet.WIDTH/2,tank.getY()
        +Tank.TANK_HEIGHT/2-Bullet.HEIGHT/2,tank.getDir(),tank.getTf(),tank.getGroup());
    tank.getTf().bullets.add(bullet1);

    Bullet bullet2 = new Bullet(tank.getX()+Tank.TANK_WIDTH/2-Bullet.WIDTH/2+Bullet.WIDTH-Bullet.WIDTH/2,tank.getY()
        +Tank.TANK_HEIGHT/2-Bullet.HEIGHT/2,tank.getDir(),tank.getTf(),tank.getGroup());
    tank.getTf().bullets.add(bullet2);

  }

}
