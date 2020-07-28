package com.mashibing.tank;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.TripleTapFireStrategy
 * @Description: 往随机方向发射子弹
 * @date 2020/7/28 15:47
 */
public class RandomDirectionFireStrategy implements FireStrategy{

  @Override
  public void fire(AbstractTank tank) {

    GeneralBullet bullet = new GeneralBullet(tank.getX()+ tank.getWidth()/2- GeneralBullet.WIDTH/2,tank.getY()
        + tank.getHeight()/2- GeneralBullet.HEIGHT/2,Dir.randomDir(),tank.getTf(),tank.getGroup());

    tank.getTf().bullets.add(bullet);
  }

}
