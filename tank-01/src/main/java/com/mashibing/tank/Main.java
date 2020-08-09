package com.mashibing.tank;

import com.mashibing.io.NettyClient;

public class Main {

  public static void main(String[] args) {
    TankFrame tankFrame = new TankFrame();

    new Thread(()->{
      NettyClient.INSTANCE.connect();
    }).start();

    new Thread(()->{
      new Audio("audio/war1.wav").loop();
    }).start();

    while (true){
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      tankFrame.repaint();
    }
  }

}
