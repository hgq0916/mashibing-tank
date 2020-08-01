package com.mashibing.tank;

import java.awt.Rectangle;

public class TankWallCollider implements Collider {

  @Override
  public boolean collideWith(GameObject o1, GameObject o2) {
    if(o1 instanceof Wall && o2 instanceof Tank){
      Wall wall = (Wall) o1;
      Tank tank = (Tank) o2;
      Rectangle rectangle = wall.getRectangle();
      Rectangle rectangle1 = tank.getRectangle();
      if(rectangle.intersects(rectangle1)){
        if(tank.getGroup().equals(Group.BAD)){
          tank.back();
        }
      }
    }else if(o1 instanceof Tank && o2 instanceof Wall){
      return collideWith(o2,o1);
    }else {
    }

    return true;
  }
}
