package com.mashibing.tank.cor;

import com.mashibing.tank.Bullet;
import com.mashibing.tank.GameObject;
import com.mashibing.tank.Wall;
import java.awt.Rectangle;

public class BulletWallCollider implements Collider {

  @Override
  public boolean collideWith(GameObject o1, GameObject o2) {
    if(o1 instanceof Wall && o2 instanceof Bullet){
      Wall wall = (Wall) o1;
      Bullet bullet = (Bullet) o2;
      Rectangle rectangle = wall.getRectangle();
      Rectangle rectangle1 = bullet.getRectangle();
      if(rectangle.intersects(rectangle1)){
        bullet.setLiving(false);
      }
    }else if(o1 instanceof Bullet && o2 instanceof Wall){
      return collideWith(o2,o1);
    }else {
    }

    return true;
  }
}
