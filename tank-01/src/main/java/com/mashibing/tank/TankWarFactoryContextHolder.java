package com.mashibing.tank;

public class TankWarFactoryContextHolder {

  private TankWarFactoryContextHolder(){}

  public static TankWarFactory getTankWarFactory(){
    return TankWarFactoryContext.tankWarFactory;
  }

  private static class TankWarFactoryContext {

    private static TankWarFactory tankWarFactory;

    static {
      String tankFactoryClass = PropertyMgr.getInstance().getString("tankFactory");
      try {
        Class<?> aClass = Class.forName(tankFactoryClass);
        tankWarFactory = (TankWarFactory) aClass.newInstance();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

}
