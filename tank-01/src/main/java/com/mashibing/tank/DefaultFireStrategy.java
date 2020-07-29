package com.mashibing.tank;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.TripleTapFireStrategy
 * @Description: 默认的发射子弹的策略
 * @date 2020/7/28 15:47
 */
public class DefaultFireStrategy implements FireStrategy{

  @Override
  public void fire(AbstractTank tank) {

    AbstractBullet bullet = TankWarFactoryContextHolder.getTankWarFactory().createBullet(
        (int width, int height)-> {
          return TankWarFactoryContextHolder.getTankWarFactory()
              .createBullet(tank.getX() + tank.getWidth() / 2 - width/ 2,
                  tank.getY() + tank.getHeight() / 2 -
                      height / 2, tank.getDir(), tank.getTf(), tank.getGroup());
        });

    tank.getTf().bullets.add(bullet);
  }

}
