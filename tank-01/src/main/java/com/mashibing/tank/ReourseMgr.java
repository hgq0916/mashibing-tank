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

  public static BufferedImage goodtankD,goodtankL,goodtankLD,goodtankLU,goodtankR,goodtankRD,goodtankRU,goodtankU;
  public static BufferedImage badtankD,badtankL,badtankLD,badtankLU,badtankR,badtankRD,badtankRU,badtankU;
  public static BufferedImage bulletD,bulletL,bulletLD,bulletLU,bulletR,bulletRD,bulletRU,bulletU;
  public static BufferedImage explodes[];

  static {
    try {

      BufferedImage bullet = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));

      bulletD = ImageUtil.rotateImage(bullet,180);
      bulletL = ImageUtil.rotateImage(bullet,-90);
      bulletLD =ImageUtil.rotateImage(bullet,-135);
      bulletLU =ImageUtil.rotateImage(bullet,-45);
      bulletR = ImageUtil.rotateImage(bullet,90);
      bulletRD =ImageUtil.rotateImage(bullet,135);
      bulletRU =ImageUtil.rotateImage(bullet,45);
      bulletU = bullet;

      BufferedImage goodtank= ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/goodTank2.png"));
      BufferedImage badtank= ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/badTank1.png"));

      goodtankD = ImageUtil.rotateImage(goodtank,180);
      goodtankL = ImageUtil.rotateImage(goodtank,-90);
      goodtankLD =ImageUtil.rotateImage(goodtank,-135);
      goodtankLU =ImageUtil.rotateImage(goodtank,-45);
      goodtankR = ImageUtil.rotateImage(goodtank,90);
      goodtankRD =ImageUtil.rotateImage(goodtank,135);
      goodtankRU =ImageUtil.rotateImage(goodtank,45);
      goodtankU = goodtank;

      badtankD = ImageUtil.rotateImage(badtank,180);
      badtankL = ImageUtil.rotateImage(badtank,-90);
      badtankLD =ImageUtil.rotateImage(badtank,-135);
      badtankLU =ImageUtil.rotateImage(badtank,-45);
      badtankR = ImageUtil.rotateImage(badtank,90);
      badtankRD =ImageUtil.rotateImage(badtank,135);
      badtankRU =ImageUtil.rotateImage(badtank,45);
      badtankU = badtank;

      explodes = new BufferedImage[16];
      for(int i=0;i<explodes.length;i++){
        explodes[i] = ImageIO.read(ReourseMgr.class.getClassLoader().getResourceAsStream("images/e"+(i+1)+".gif"));
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
