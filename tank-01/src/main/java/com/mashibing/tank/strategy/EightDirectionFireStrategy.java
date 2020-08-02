package com.mashibing.tank.strategy;

import com.mashibing.tank.Bullet;
import com.mashibing.tank.Dir;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.Tank;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.TripleTapFireStrategy
 * @Description: 往八个方向发射子弹
 * @date 2020/7/28 15:47
 */
public class EightDirectionFireStrategy implements FireStrategy {

  private EightDirectionFireStrategy(){}

  private static EightDirectionFireStrategy eightDirectionFireStrategy = new EightDirectionFireStrategy();

  public static FireStrategy getInstance() {
    return eightDirectionFireStrategy;
  }

  @Override
  public void fire(Tank tank) {

    Dir[] values = Dir.values();
    for(int i=0;i<values.length;i++){
      Bullet bullet = new Bullet(tank.getX()+Tank.TANK_WIDTH/2-Bullet.WIDTH/2,tank.getY()
          +Tank.TANK_HEIGHT/2-Bullet.HEIGHT/2,values[i],tank.getGroup());
      GameModel.getInstance().add(bullet);
    }

  }

}
