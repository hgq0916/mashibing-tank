package com.mashibing.tank.observer;

import com.mashibing.tank.Tank;

public class FireEvent implements Event<Tank> {

  private Tank source;

  public FireEvent(Tank source){
    this.source = source;
  }

  @Override
  public Tank getSource() {
    return source;
  }

}
