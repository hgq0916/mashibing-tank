package com.mashibing.tank.singleton;

import com.mashibing.tank.PropertyMgr;
import java.io.IOException;
import java.util.Properties;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.tank.PropertyMgrEnum
 * @Description: 单例模式：枚举，可以防止反序列化
 * @date 2020/7/27 15:33
 */
public enum PropertyMgrEnum {

  PROPERTY_MGR_INSTANCE;

  private  Properties properties = new Properties();

  private PropertyMgrEnum(){
    try {
      properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

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
