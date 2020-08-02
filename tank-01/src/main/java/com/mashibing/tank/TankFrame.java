package com.mashibing.tank;

import com.mashibing.tank.singleton.PropertyMgrEnum;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {

  public static final int WIN_WIDTH = PropertyMgrEnum.PROPERTY_MGR_INSTANCE.getInt("gameWidth");
  public static final int WIN_HEIGHT = PropertyMgrEnum.PROPERTY_MGR_INSTANCE.getInt("gameHeight");
  private Image image = null;

  final GameModel gameModel = GameModel.getInstance();

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
    gameModel.paint(g);
  }

  private class MyKeyListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
      gameModel.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
      gameModel.keyReleased(e);
    }
  }
}
