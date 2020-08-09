package com.mashibing.io;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TankMsgEncoder extends MessageToByteEncoder<TankJoinMsg> {

  @Override
  protected void encode(ChannelHandlerContext ctx, TankJoinMsg msg, ByteBuf out) throws Exception {
    out.writeBytes(msg.toBytes());
  }

}
