package com.mashibing.tank;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Assert;
import org.junit.Test;

public class TestImage {

  @Test
  public void test01(){
    try {
     /* BufferedImage image = ImageIO.read(new File(
          "H:\\学习\\马士兵课程\\4-设计模式（坦克一期)\\project\\tank\\tank-01\\src\\main\\resources\\images\\4.gif"));*/
      BufferedImage image = ImageIO
          .read(TestImage.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
      Assert.assertNotNull(image);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
