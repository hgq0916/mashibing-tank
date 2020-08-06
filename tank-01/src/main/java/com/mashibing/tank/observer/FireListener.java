package com.mashibing.tank.observer;

import java.io.Serializable;

public interface FireListener extends Serializable {

  void fire(FireEvent fireEvent);

}
