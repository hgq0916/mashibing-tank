package com.mashibing.tank.cor;

import com.mashibing.tank.Audio;
import com.mashibing.tank.Bullet;
import com.mashibing.tank.Explode;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.GameObject;
import com.mashibing.tank.Group;
import com.mashibing.tank.Tank;
import java.awt.Rectangle;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.BulletTankCollider
 * @Description: 子弹和坦克碰撞
 * @date 2020/7/31 17:04
 */
public class BulletTankCollider implements Collider {

  @Override
  public boolean collideWith(GameObject o1, GameObject o2) {
    if(o1 instanceof Bullet && o2 instanceof Tank){
      Bullet bullet = (Bullet) o1;
      Tank tank = (Tank) o2;

      if(Group.GOOD.equals(tank.getGroup()) || bullet.getGroup().equals(tank.getGroup())) return true;

      Rectangle bulletRectangle = bullet.getRectangle();
      Rectangle tankRectangle = tank.getRectangle();
      if(bulletRectangle.intersects(tankRectangle)){
        //发生碰撞
        bullet.die();
        tank.die();
        //产生爆炸
        GameModel.getInstance().add(new Explode(bullet.getX()+Bullet.WIDTH/2-Explode.WIDTH/2,bullet.getY()+Bullet.HEIGHT/2-Explode.HEIGHT/2));
        new Thread(()->{
          Audio explodeAudio = new Audio("audio/explode.wav");
          explodeAudio.play();
        }).start();

        return false;
      }
    }else if(o1 instanceof Tank && o2 instanceof Bullet){
      return collideWith(o2,o1);
    }else {
      return true;
    }
    return true;
  }
}