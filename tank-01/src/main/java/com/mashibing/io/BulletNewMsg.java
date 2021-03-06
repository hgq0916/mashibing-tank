package com.mashibing.io;

import com.mashibing.tank.Bullet;
import com.mashibing.tank.Dir;
import com.mashibing.tank.Group;
import com.mashibing.tank.TankFrame;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class BulletNewMsg extends Msg {

  private String id;
  private String tankId;
  private int x;
  private int y;
  private Dir dir;
  private boolean living;
  private Group group;

  public BulletNewMsg(){}

  public BulletNewMsg(Bullet bullet) {
    this.x = bullet.getX();
    this.y = bullet.getY();
    this.dir = bullet.getDir();
    this.living = bullet.isLiving();
    this.group = bullet.getGroup();
    this.id = bullet.getId();
    this.tankId = bullet.getTankId();
  }

  public BulletNewMsg(String id, int x, int y, Dir dir, boolean living, Group group,String tankId) {
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.living = living;
    this.group = group;
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

  public Dir getDir() {
    return dir;
  }

  public void setDir(Dir dir) {
    this.dir = dir;
  }

  public boolean isLiving() {
    return living;
  }

  public void setLiving(boolean living) {
    this.living = living;
  }

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
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
      dos.writeInt(x);
      dos.writeInt(y);
      dos.writeInt(dir.ordinal());
      dos.writeInt(group.ordinal());
      dos.writeBoolean(living);
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
    if(!TankFrame.INSTANCE.existBulletById(this.id)){
      Bullet bullet = new Bullet(this.id,this.x,this.y,this.dir,this.group,this.tankId);
      TankFrame.INSTANCE.addBullet(bullet);
    }
  }

  @Override
  public MsgType getMsgType() {
    return MsgType.BULLET_NEW;
  }

  @Override
  public BulletNewMsg parse(byte[] data) {
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
      this.setGroup(Group.values()[dis.readInt()]);
      this.setLiving(dis.readBoolean());
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
    return "BulletNewMsg{" +
        "id='" + id + '\'' +
        ", tankId='" + tankId + '\'' +
        ", x=" + x +
        ", y=" + y +
        ", dir=" + dir +
        ", living=" + living +
        ", group=" + group +
        '}';
  }
}
