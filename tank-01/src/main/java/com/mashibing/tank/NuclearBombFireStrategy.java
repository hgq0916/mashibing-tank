package com.mashibing.tank;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.TripleTapFireStrategy
 * @Description: 发射核弹
 * @date 2020/7/28 15:47
 */
public class NuclearBombFireStrategy implements FireStrategy{

  private NuclearBombFireStrategy(){}

  private static NuclearBombFireStrategy nuclearBombFireStrategy = new NuclearBombFireStrategy();

  public static FireStrategy getInstance() {
    return nuclearBombFireStrategy;
  }

  @Override
  public void fire(AbstractTank tank) {
    NuclearBomb nuclearBomb = new NuclearBomb(tank.getX()+tank.getWidth()/2-NuclearBomb.WIDTH/2,tank.getY()
        + tank.getHeight()/2-NuclearBomb.HEIGHT/2,tank.getDir(),tank.getTf(),tank.getGroup());
    tank.getTf().nuclearBombs.add(nuclearBomb);
  }

}
