package com.mashibing.io;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Tank;
import com.mashibing.tank.TankFrame;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class TankDirectionChangeMsg extends Msg{

  private String id;

  private int x;

  private int y;

  private Dir dir;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Dir getDir() {
    return dir;
  }

  public void setDir(Dir dir) {
    this.dir = dir;
  }

  public TankDirectionChangeMsg(){}

  public TankDirectionChangeMsg(Tank tank){
    this.x = tank.getX();
    this.y = tank.getY();
    this.dir = tank.getDir();
    this.id = tank.getId();
  }

  public TankDirectionChangeMsg(String id,int x, int y, Dir dir) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.dir = dir;
  }

  @Override
  public byte[] toBytes(){
    ByteArrayOutputStream bos = null;
    DataOutputStream dos = null;
    try {
      bos = new ByteArrayOutputStream();
      dos = new DataOutputStream(bos);
      dos.writeLong(UUID.fromString(this.id).getMostSignificantBits());
      dos.writeLong(UUID.fromString(this.id).getLeastSignificantBits());
      dos.writeInt(x);
      dos.writeInt(y);
      dos.writeInt(dir.ordinal());

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

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  @Override
  public String toString() {
    return "TankJoinMsg{" +
        "id='" + id + '\'' +
        ", x=" + x +
        ", y=" + y +
        ", dir=" + dir +
        '}';
  }

  @Override
  public void handle() {
    String tankId = this.getId();
    if(TankFrame.INSTANCE.getMainTank().getId().equals(tankId)) return;

    Tank tank = TankFrame.INSTANCE.getTankById(tankId);
    if(tank != null){
      //修改坦克的状态
      tank.setX(this.x);
      tank.setY(this.y);
      tank.setDir(this.dir);
    }
  }

  @Override
  public MsgType getMsgType() {
    return MsgType.TANK_CHANGE_DIR;
  }

  @Override
  public TankDirectionChangeMsg parse(byte[] data) {
    ByteArrayInputStream bis = null;
    DataInputStream dis = null;
    try {
      bis = new ByteArrayInputStream(data);
      dis = new DataInputStream(bis);
      long mostSigBits = dis.readLong();
      long leastSigBits = dis.readLong();
      this.setId(new UUID(mostSigBits,leastSigBits).toString());
      this.setX(dis.readInt());
      this.setY(dis.readInt());
      this.setDir(Dir.values()[dis.readInt()]);

    } catch (IOException e) {
      e.printStackTrace();
    }

    return this;
  }


}
