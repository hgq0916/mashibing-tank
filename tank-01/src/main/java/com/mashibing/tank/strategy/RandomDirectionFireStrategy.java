package com.mashibing.tank.strategy;

import com.mashibing.tank.Bullet;
import com.mashibing.tank.Dir;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.Tank;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.TripleTapFireStrategy
 * @Description: 往随机方向发射子弹
 * @date 2020/7/28 15:47
 */
public class RandomDirectionFireStrategy implements FireStrategy {

  @Override
  public void fire(Tank tank) {

    Bullet bullet = new Bullet(tank.getX()+Tank.TANK_WIDTH/2-Bullet.WIDTH/2,tank.getY()
        +Tank.TANK_HEIGHT/2-Bullet.HEIGHT/2, Dir.randomDir(),tank.getGroup());

    GameModel.getInstance().add(bullet);
  }

}
