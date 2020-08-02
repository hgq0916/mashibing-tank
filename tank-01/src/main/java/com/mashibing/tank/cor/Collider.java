package com.mashibing.tank.cor;

import com.mashibing.tank.GameObject;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.Collider
 * @Description: 碰撞器
 * @date 2020/7/31 17:03
 */
public interface Collider {

  boolean collideWith(GameObject o1,GameObject o2);

}
