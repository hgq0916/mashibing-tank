package com.mashibing.tank;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.Collider
 * @Description: 碰撞器
 * @date 2020/7/31 17:03
 */
public interface Collider {

  void collideWith(GameObject o1,GameObject o2);

}
