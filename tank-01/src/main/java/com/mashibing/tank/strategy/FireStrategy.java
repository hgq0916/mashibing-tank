package com.mashibing.tank.strategy;

import com.mashibing.tank.Tank;
import java.io.Serializable;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.FireStrategy
 * @Description: 开火策略
 * @date 2020/7/28 15:46
 */
public interface FireStrategy extends Serializable {

  void fire(Tank tank);

}
