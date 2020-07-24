package com.mashibing.tank;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {

 Tank mytank = new Tank(200,200,Dir.DOWN);

  public TankFrame(){
    setSize(800,600);
    setResizable(false);
    setTitle("mytank war");
    setVisible(true);
    this.addKeyListener(new MyKeyListener());
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  @Override
  public void paint(Graphics g) {
    //paint会先清屏，然后绘制
    ///System.out.println("paint");
    mytank.paint(g);
    //x +=10;
    //y +=10;
  }

  private class MyKeyListener extends KeyAdapter {

    boolean BU = false;
    boolean BD = false;
    boolean BL = false;
    boolean BR = false;

    @Override
    public void keyPressed(KeyEvent e) {
      System.out.println(e);

      int keyCode = e.getKeyCode();

      switch (keyCode){
        case KeyEvent.VK_UP:
          BU = true;
          break;
        case KeyEvent.VK_DOWN:
          BD = true;
          break;
        case KeyEvent.VK_LEFT:
          BL = true;
          break;
        case KeyEvent.VK_RIGHT:
          BR = true;
          break;
      }

      setMainTankDir();
    }

    private void setMainTankDir() {
      if(BU) mytank.setDir(Dir.UP);
      if(BD) mytank.setDir(Dir.DOWN);
      if(BL) mytank.setDir(Dir.LEFT);
      if(BR) mytank.setDir(Dir.RIGHT);
    }

    @Override
    public void keyReleased(KeyEvent e) {
      int keyCode = e.getKeyCode();
      switch (keyCode){
        case KeyEvent.VK_UP:
          BU = false;
          break;
        case KeyEvent.VK_DOWN:
          BD = false;
          break;
        case KeyEvent.VK_LEFT:
          BL = false;
          break;
        case KeyEvent.VK_RIGHT:
          BR = false;
          break;
      }

      setMainTankDir();
    }
  }
}
