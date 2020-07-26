package com.mashibing.tank;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 资源管理
 */
public class ReourseMgr {

  /**
   * 坦克图片
   */
  public static BufferedImage tankD,tankL,tankLD,tankLU,tankR,tankRD,tankRU,tankU;
  public static BufferedImage bulletD,bulletL,bulletLD,bulletLU,bulletR,bulletRD,bulletRU,bulletU;

  static {
    try {
      bulletD = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
      bulletL = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/bulletL.gif"));
      bulletR = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/bulletR.gif"));
      bulletU = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
      bulletLD = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/missileLD.gif"));
      bulletLU = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/missileLU.gif"));
      bulletRD = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/missileRD.gif"));
      bulletRU = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/missileRU.gif"));

      tankD = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/tankD.gif"));
      tankL = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/tankL.gif"));
      tankLD = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/tankLD.gif"));
      tankLU = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/tankLU.gif"));
      tankR = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/tankR.gif"));
      tankRD = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/tankRD.gif"));
      tankRU = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/tankRU.gif"));
      tankU = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/tankU.gif"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
