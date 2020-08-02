package com.mashibing.tank.strategy;

import com.mashibing.tank.Bullet;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.Tank;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.TripleTapFireStrategy
 * @Description: 三连发射子弹
 * @date 2020/7/28 15:47
 */
public class TriplePlayFireStrategy implements FireStrategy {

  private TriplePlayFireStrategy(){}

  private static TriplePlayFireStrategy triplePlayFireStrategy = new TriplePlayFireStrategy();

  public static FireStrategy getInstance() {
    return triplePlayFireStrategy;
  }

  @Override
  public void fire(Tank tank) {

      Bullet bullet = new Bullet(tank.getX()+Tank.TANK_WIDTH/2-Bullet.WIDTH/2,tank.getY()
          +Tank.TANK_HEIGHT/2-Bullet.HEIGHT/2,tank.getDir(),tank.getGroup());
      GameModel.getInstance().add(bullet);

    Bullet bullet1 = new Bullet(tank.getX()+Tank.TANK_WIDTH/2-Bullet.WIDTH/2-Bullet.WIDTH/2,tank.getY()
        +Tank.TANK_HEIGHT/2-Bullet.HEIGHT/2,tank.getDir(),tank.getGroup());
    GameModel.getInstance().add(bullet1);

    Bullet bullet2 = new Bullet(tank.getX()+Tank.TANK_WIDTH/2-Bullet.WIDTH/2+Bullet.WIDTH-Bullet.WIDTH/2,tank.getY()
        +Tank.TANK_HEIGHT/2-Bullet.HEIGHT/2,tank.getDir(),tank.getGroup());
    GameModel.getInstance().add(bullet2);

  }

}
