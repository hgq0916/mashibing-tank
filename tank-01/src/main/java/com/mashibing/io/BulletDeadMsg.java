package com.mashibing.io;

import com.mashibing.tank.Audio;
import com.mashibing.tank.Bullet;
import com.mashibing.tank.Explode;
import com.mashibing.tank.TankFrame;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class BulletDeadMsg extends Msg {

  private String id;
  private String tankId;

  public BulletDeadMsg(){}

  public BulletDeadMsg(Bullet bullet) {
    this.id = bullet.getId();
    this.tankId = bullet.getTankId();
  }

  public BulletDeadMsg(String id, String tankId) {
    this.id = id;
    this.tankId = tankId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTankId() {
    return tankId;
  }

  public void setTankId(String tankId) {
    this.tankId = tankId;
  }

  @Override
  public byte[] toBytes() {
    ByteArrayOutputStream bos = null;
    DataOutputStream dos = null;
    try {
      bos = new ByteArrayOutputStream();
      dos = new DataOutputStream(bos);
      dos.writeLong(UUID.fromString(this.id).getMostSignificantBits());
      dos.writeLong(UUID.fromString(this.id).getLeastSignificantBits());
      dos.writeLong(UUID.fromString(tankId).getMostSignificantBits());
      dos.writeLong(UUID.fromString(tankId).getLeastSignificantBits());

      return bos.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      if(dos != null){
        try {
          dos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  @Override
  public void handle() {
    Bullet bullet = TankFrame.INSTANCE.getBulletById(this.id);
    if(bullet!= null){
      bullet.die();
      //产生爆炸
      TankFrame.INSTANCE.explodes.add(new Explode(bullet.getX()+Bullet.WIDTH/2-Explode.WIDTH/2,bullet.getY()+Bullet.HEIGHT/2-Explode.HEIGHT/2));
      new Thread(()->{
        Audio explodeAudio = new Audio("audio/explode.wav");
        explodeAudio.play();
      }).start();
    }
  }

  @Override
  public MsgType getMsgType() {
    return MsgType.BULLET_DEAD;
  }

  @Override
  public BulletDeadMsg parse(byte[] data) {
    ByteArrayInputStream bis = null;
    DataInputStream dis = null;
    try {
      bis = new ByteArrayInputStream(data);
      dis = new DataInputStream(bis);
      long mostSigBits = dis.readLong();
      long leastSigBits = dis.readLong();
      this.setId(new UUID(mostSigBits,leastSigBits).toString());
      long tMostSigBits = dis.readLong();
      long tMeastSigBits = dis.readLong();
      this.setTankId(new UUID(tMostSigBits,tMeastSigBits).toString());

    } catch (IOException e) {
      e.printStackTrace();
    }

    return this;
  }

  @Override
  public String toString() {
    return "BulletDeadMsg{" +
        "id='" + id + '\'' +
        ", tankId='" + tankId + '\'' +
        '}';
  }
}
