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

  static Properties properties = new Properties();

  static {
    try {
      properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Object get(String key){
    return properties == null?null:properties.get(key);
  }

  public static String getString(String key){
    return properties == null?null: (String) properties.get(key);
  }

  public static Integer getInt(String key){
    if(properties == null) return null;
    Object value = properties.get(key);
    return value == null?null:Integer.parseInt(value.toString());
  }

}
