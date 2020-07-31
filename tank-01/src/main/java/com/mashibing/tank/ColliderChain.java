package com.mashibing.tank;

import java.util.LinkedList;
import java.util.List;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.ColliderChain
 * @Description: 碰撞链
 * @date 2020/7/31 17:35
 */
public class ColliderChain implements Collider{

  private List<Collider> colliders = new LinkedList<>();

  public ColliderChain(){

    String colliderClass = PropertyMgr.getInstance().getString("colliderClass");
    String[] colliderClasses = colliderClass.split(",");
    for(int i=0;i<colliderClasses.length;i++){
      try {
        Class<?> aClass = Class.forName(colliderClasses[i]);
        Object newInstance = aClass.newInstance();
        colliders.add((Collider) newInstance);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public ColliderChain add(Collider collider){
    colliders.add(collider);
    return this;
  }

  public void remove(Collider collider){
    colliders.remove(collider);
  }

  public boolean collideWith(GameObject o1,GameObject o2){

    for(int i=0;i<colliders.size();i++){
      Collider collider = colliders.get(i);
      if(!collider.collideWith(o1,o2)){
        return false;
      }
    }

    return true;
  }


}
