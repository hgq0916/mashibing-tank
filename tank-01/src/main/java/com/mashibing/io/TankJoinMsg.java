package com.mashibing.io;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Group;
import com.mashibing.tank.Tank;
import com.mashibing.tank.TankFrame;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class TankJoinMsg extends Msg{

  private String id;

  private int x;

  private int y;

  private Dir dir;

  private Group group;

  private boolean moving;

  private boolean living;

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

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public boolean isMoving() {
    return moving;
  }

  public void setMoving(boolean moving) {
    this.moving = moving;
  }

  public boolean isLiving() {
    return living;
  }

  public void setLiving(boolean living) {
    this.living = living;
  }

  private TankJoinMsg(){}

  public TankJoinMsg(Tank tank){
    this.x = tank.getX();
    this.y = tank.getY();
    this.dir = tank.getDir();
    this.group = tank.getGroup();
    this.moving = tank.isMoving();
    this.id = tank.getId();
    this.living = tank.isLiving();
  }

  public TankJoinMsg(String id,int x, int y, Dir dir, Group group, boolean moving, boolean living) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.group = group;
    this.moving = moving;
    this.living = living;
  }

  public static TankJoinMsg deserialize(byte[] data) {

    ByteArrayInputStream bis = null;
    DataInputStream dis = null;
    try {
      bis = new ByteArrayInputStream(data);
      dis = new DataInputStream(bis);
      TankJoinMsg tankJoinMsg = new TankJoinMsg();
      long mostSigBits = dis.readLong();
      long leastSigBits = dis.readLong();
      tankJoinMsg.setId(new UUID(mostSigBits,leastSigBits).toString());
      tankJoinMsg.setX(dis.readInt());
      tankJoinMsg.setY(dis.readInt());
      tankJoinMsg.setDir(Dir.values()[dis.readInt()]);
      tankJoinMsg.setGroup(Group.values()[dis.readInt()]);
      tankJoinMsg.setMoving(dis.readBoolean());
      tankJoinMsg.setLiving(dis.readBoolean());

      return tankJoinMsg;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
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
      dos.writeInt(group.ordinal());
      dos.writeBoolean(moving);
      dos.writeBoolean(living);

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
        ", group=" + group +
        ", moving=" + moving +
        ", living=" + living +
        '}';
  }

  public Tank createTank() {
    return new Tank(this.id,this.x,this.y,this.dir, this.group);
  }

  @Override
  public void handle() {
    String tankId = this.getId();
    if(!TankFrame.INSTANCE.getMainTank().getId().equals(tankId) && !TankFrame.INSTANCE.existsTankById(tankId)){
      //不存在该坦克
      Tank tank = this.createTank();
      TankFrame.INSTANCE.addTank(tank);

      NettyClient.INSTANCE.write(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
    }
  }

  @Override
  public MsgType getMsgType() {
    return MsgType.TANK_JOIN_MSG;
  }

}
