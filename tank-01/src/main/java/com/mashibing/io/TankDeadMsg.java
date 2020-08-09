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

public class TankDeadMsg extends Msg{

  private String id;

  public TankDeadMsg(){}

  public TankDeadMsg(Tank tank){
    this.id = tank.getId();
  }

  public TankDeadMsg(String id,int x, int y, Dir dir) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
  public String toString() {
    return "TankDeadMsg{" +
        "id='" + id + '\'' +
        '}';
  }

  @Override
  public void handle() {
    String tankId = this.getId();
    if(TankFrame.INSTANCE.getMainTank().getId().equals(tankId)){
      TankFrame.INSTANCE.getMainTank().die();
      return;
    }

    Tank tank = TankFrame.INSTANCE.getTankById(tankId);
    if(tank != null){
      //修改坦克的状态
      tank.die();
    }
  }

  @Override
  public MsgType getMsgType() {
    return MsgType.TANK_DEAD;
  }

  @Override
  public TankDeadMsg parse(byte[] data) {
    ByteArrayInputStream bis = null;
    DataInputStream dis = null;
    try {
      bis = new ByteArrayInputStream(data);
      dis = new DataInputStream(bis);
      long mostSigBits = dis.readLong();
      long leastSigBits = dis.readLong();
      this.setId(new UUID(mostSigBits,leastSigBits).toString());

    } catch (IOException e) {
      e.printStackTrace();
    }

    return this;
  }


}
