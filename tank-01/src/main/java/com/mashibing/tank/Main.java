package com.mashibing.tank;

public class Main {

  public static void main(String[] args) {
    TankFrame tankFrame = new TankFrame();

    new Thread(()->{
      //new Audio("audio/war1.wav").loop();
    }).start();

    while (true){
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      tankFrame.repaint();
    }
  }

}
