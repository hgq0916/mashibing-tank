package com.mashibing.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TankFrame extends Frame {

  public static final int WIN_WIDTH = 800;
  public static final int WIN_HEIGHT = 600;
  private Image image = null;

  Tank mytank = new Tank(200,200,Dir.DOWN,this);
  List<Bullet> bullets = new ArrayList<>();

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
    if(image == null){
      image = this.createImage(WIN_WIDTH, WIN_HEIGHT);
    }
    Graphics graphics = image.getGraphics();
    Color c = graphics.getColor();
    graphics.setColor(Color.BLACK);
    graphics.fillRect(0,0,WIN_WIDTH,WIN_HEIGHT);
    graphics.setColor(c);
    paint(graphics);
    g.drawImage(image,0,0,null);
  }

  @Override
  public void paint(Graphics g) {
    //paint会先清屏，然后绘制
    Color color = g.getColor();

    g.setColor(Color.WHITE);
    g.drawString("子弹数量："+bullets.size(),60,60);
    g.setColor(color);

    mytank.paint(g);

    for(int i=0;i<bullets.size();i++){
      bullets.get(i).paint(g);
    }

    /*Iterator<Bullet> iterator = bullets.iterator();
    for(;iterator.hasNext();){
      Bullet bullet = iterator.next();
      if(!bullet.live){
        iterator.remove();
      }else {
        bullet.paint(g);
      }
    }*/

    //使用迭代器不允许在其他线程删除元素
    /*for(Bullet bullet: bullets){
      bullet.paint(g);
    }*/

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
        case KeyEvent.VK_CONTROL:
          mytank.fire();
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
