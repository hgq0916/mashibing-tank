package com.mashibing.tank;

import java.io.IOException;
import java.util.Properties;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.PropertyMgr
 * @Description: 配置文件管理
 * @date 2020/7/27 14:29
 */
public class PropertyMgr {

  /*private  Properties properties = new Properties();

  //构造方法私有化
  private PropertyMgr(){
    try {
      properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }*/

  //方式一：饿汉模式
  //private static PropertyMgr propertyMgr = new PropertyMgr();

  /*public static PropertyMgr getInstance(){
    return propertyMgr;
  }*/

  //方式二： 懒汉模式
  /*private static PropertyMgr propertyMgr;

  public static PropertyMgr getInstance(){
    if(propertyMgr == null){
      propertyMgr = new PropertyMgr();
      System.out.println("propertyMgr init");
    }
    return propertyMgr;
  }*/

  //方式三：懒汉+线程安全,效率低
  /*private static PropertyMgr propertyMgr;

  public static synchronized PropertyMgr getInstance(){
    if(propertyMgr == null){
      propertyMgr = new PropertyMgr();
      System.out.println("propertyMgr init");
    }
    return propertyMgr;
  }*/

  //方式四：懒汉+双重锁检验 需要加volatile禁止指令重排序
  /*private static volatile PropertyMgr propertyMgr;

  public static  PropertyMgr getInstance(){
    if(propertyMgr == null){
      synchronized (PropertyMgr.class){
        if(propertyMgr == null){
          propertyMgr = new PropertyMgr();
          System.out.println("propertyMgr init");
        }
      }
    }
    return propertyMgr;
  }*/

  //方式五：使用静态内部类

  private  Properties properties = new Properties();

  private PropertyMgr(){
    try {
      properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static class PropertyHolder {
    private static PropertyMgr PROPERTYMGR = new PropertyMgr();

  }

  public static PropertyMgr getInstance(){
    return PropertyHolder.PROPERTYMGR;
  }

  //方式六：使用枚举

/*
  static {
    try {
      properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }*/

  public  Object get(String key){
    return properties == null?null:properties.get(key);
  }

  public  String getString(String key){
    return properties == null?null: (String) properties.get(key);
  }

  public  Integer getInt(String key){
    if(properties == null) return null;
    Object value = properties.get(key);
    return value == null?null:Integer.parseInt(value.toString());
  }

}
