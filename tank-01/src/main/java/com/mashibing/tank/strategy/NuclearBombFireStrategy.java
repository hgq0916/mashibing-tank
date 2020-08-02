package com.mashibing.tank.strategy;

import com.mashibing.tank.GameModel;
import com.mashibing.tank.NuclearBomb;
import com.mashibing.tank.Tank;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.TripleTapFireStrategy
 * @Description: 发射核弹
 * @date 2020/7/28 15:47
 */
public class NuclearBombFireStrategy implements FireStrategy {

  private NuclearBombFireStrategy(){}

  private static NuclearBombFireStrategy nuclearBombFireStrategy = new NuclearBombFireStrategy();

  public static FireStrategy getInstance() {
    return nuclearBombFireStrategy;
  }

  @Override
  public void fire(Tank tank) {
    NuclearBomb nuclearBomb = new NuclearBomb(tank.getX()+Tank.TANK_WIDTH/2-NuclearBomb.WIDTH/2,tank.getY()
        +Tank.TANK_HEIGHT/2-NuclearBomb.HEIGHT/2,tank.getDir(),tank.getGroup());
    GameModel.getInstance().add(nuclearBomb);
  }

}
