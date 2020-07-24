package com.mashibing.tank;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {

  int x=200,y=200;

  public TankFrame(){
    setSize(800,600);
    setResizable(false);
    setTitle("tank war");
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
    System.out.println("paint");
    g.fillRect(x,y,50,50);
    x +=10;
    //y +=10;
  }

  private class MyKeyListener extends KeyAdapter {


    @Override
    public void keyPressed(KeyEvent e) {
      x += 200;
      System.out.println("keyPressed");
      //repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
      System.out.println("keyReleased");
    }
  }
}
