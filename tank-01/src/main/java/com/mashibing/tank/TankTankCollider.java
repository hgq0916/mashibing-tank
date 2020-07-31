package com.mashibing.tank;

import java.awt.Rectangle;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.TankTankCollider
 * @Description: 坦克和坦克碰撞器
 * @date 2020/7/31 17:16
 */
public class TankTankCollider implements Collider {

  @Override
  public boolean collideWith(GameObject o1, GameObject o2) {
    if(o1 instanceof Tank && o2 instanceof Tank){
      Tank tank1 = (Tank) o1;
      Tank tank2 = (Tank) o2;
      Rectangle rectangle = tank1.getRectangle();
      Rectangle rectangle1 = tank2.getRectangle();
      if(rectangle.intersects(rectangle1)){
        //回到上一次的位置
        tank1.backToPrevious();
        tank2.backToPrevious();
      }
    }else {
    }
    return true;
  }
}
