package com.mashibing.tank;

public class SuperTankWarFactory implements TankWarFactory{

  @Override
  public AbstractTank createTank(int x, int y, Dir dir, TankFrame tf, Group group) {
    return new HeavyTank(x,y,dir,tf,group);
  }

  @Override
  public AbstractBullet createBullet(int x, int y, Dir dir, TankFrame tankFrame, Group group) {
    return new NuclearBomb(x,y,dir,tankFrame,group);
  }

  @Override
  public AbstractBullet createBullet(BulletCreater bulletCreater) {
    AbstractBullet bullet = bulletCreater.createBullet(NuclearBomb.WIDTH, NuclearBomb.HEIGHT);
    return bullet;
  }

  @Override
  public AbstractExplode createExplode(int x, int y, TankFrame tf) {
    return new BombExplode(x,y,tf);
  }

}
