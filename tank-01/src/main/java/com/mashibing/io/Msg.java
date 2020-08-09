package com.mashibing.io;

public abstract class Msg {

  public abstract byte[] toBytes();

  public abstract void handle();

  public abstract MsgType getMsgType();

  public abstract Msg parse(byte[] data);

}
