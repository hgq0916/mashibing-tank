package com.mashibing.tank;

public class TankWarFactoryContextHolder {

  private TankWarFactoryContextHolder(){}

  public static TankWarFactory getTankWarFactory(){
    return TankWarFactoryContext.tankWarFactory;
  }

  private static class TankWarFactoryContext {

    private static TankWarFactory tankWarFactory;

    static {
      tankWarFactory = new SuperTankWarFactory();
    }

  }

}
