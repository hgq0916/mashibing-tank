package com.mashibing.tank.observer;

import com.mashibing.tank.Tank;

public class MyTankFireListener implements FireListener {

  @Override
  public void fire(FireEvent fireEvent) {
    Tank source = fireEvent.getSource();
    System.out.println("tank group:"+source.getGroup()+" fire...");
  }
}
