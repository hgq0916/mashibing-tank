package com.mashibing.tank;

import java.util.ArrayList;
import java.util.List;

public class GameObjectMgr {

  List<GameObject> gameObjects = new ArrayList<>();

  public void addGameObject(GameObject gameObject){
    gameObjects.add(gameObject);
  }

  public void removeGameObject(GameObject gameObject){
    gameObjects.remove(gameObject);
  }

}
