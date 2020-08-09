package com.mashibing.io;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Group;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class DataPacket {

  public static final int DATA_PACKET_HEADER_LEN = 12;

  public int sign = 0x10101010;

  public MsgType msgType;

  public int len;

  public byte[] data;

  public byte[] toBytes() {
    ByteArrayOutputStream bos = null;
    DataOutputStream dos = null;
    try {
      bos = new ByteArrayOutputStream();
      dos = new DataOutputStream(bos);
      dos.writeInt(sign);
      dos.writeInt(msgType.ordinal());
      dos.writeInt(len);
      dos.write(data);
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

  public static class Builder {

    DataPacket dataPacket = new DataPacket();

    public Builder msgType(MsgType msgType){
      dataPacket.msgType = msgType;
      return this;
    }

    public Builder data(byte[] data){
      dataPacket.len = data.length;
      dataPacket.data = data;
      return this;
    }

    public DataPacket build(){
      return dataPacket;
    }

    public static DataPacket deserialize(byte[] header) {

      ByteArrayInputStream bis = null;
      DataInputStream dis = null;
      try {
        bis = new ByteArrayInputStream(header);
        dis = new DataInputStream(bis);
        DataPacket dataPacket = new DataPacket();
        dataPacket.sign = dis.readInt();
        dataPacket.msgType = MsgType.values()[dis.readInt()];
        dataPacket.len = dis.readInt();
        return dataPacket;
      } catch (IOException e) {
        e.printStackTrace();
      }

      return null;
    }

  }

}
