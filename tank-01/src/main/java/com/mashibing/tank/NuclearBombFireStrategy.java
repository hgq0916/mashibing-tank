package com.mashibing.tank;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.TripleTapFireStrategy
 * @Description: 发射核弹
 * @date 2020/7/28 15:47
 */
public class NuclearBombFireStrategy implements FireStrategy{

  @Override
  public void fire(Tank tank) {
    NuclearBomb nuclearBomb = new NuclearBomb(tank.getX()+Tank.TANK_WIDTH/2-NuclearBomb.WIDTH/2,tank.getY()
        +Tank.TANK_HEIGHT/2-NuclearBomb.HEIGHT/2,tank.getDir(),tank.getTf(),tank.getGroup());
    tank.getTf().nuclearBombs.add(nuclearBomb);
  }

}
