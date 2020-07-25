package com.mashibing.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {

  private static final int WIN_WIDTH = 800;
  private static final int WIN_HEIGHT = 600;
  Tank mytank = new Tank(200,200,Dir.DOWN);
 Bullet b = new Bullet(300,300,Dir.DOWN);

  public TankFrame(){
    setSize(WIN_WIDTH,WIN_HEIGHT);
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
  public void update(Graphics g) {
    Image image = this.createImage(WIN_WIDTH, WIN_HEIGHT);
    Graphics graphics = image.getGraphics();
    graphics.setColor(Color.BLACK);
    paint(graphics);
    g.drawImage(image,0,0,null);
  }

  @Override
  public void paint(Graphics g) {
    //paint会先清屏，然后绘制
    Color color = g.getColor();
    mytank.paint(g);
    b.paint(g);
    g.setColor(color);
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

      if(!BU && !BD && !BL && !BR){
        mytank.setMoving(false);
      } else {
        mytank.setMoving(true);
        if(BU) mytank.setDir(Dir.UP);
        if(BD) mytank.setDir(Dir.DOWN);
        if(BL) mytank.setDir(Dir.LEFT);
        if(BR) mytank.setDir(Dir.RIGHT);
      }

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
