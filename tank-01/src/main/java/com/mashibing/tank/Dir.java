package com.mashibing.tank;

import java.util.Random;

/**
 * 方向类
 */
public enum Dir {
  WEST,NORTHWEST,NORTH,NORTHEAST,SOUTHEAST,EAST,SOUTH,SOUTHWEST;

  private static Random random = new Random();

  public static Dir randomDir(){
    Dir[] values = Dir.values();
    int index = random.nextInt(values.length);
    return values[index];
  }

}
