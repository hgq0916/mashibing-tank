package com.mashibing.io;

import com.mashibing.io.DataPacket.Builder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgEncoder extends MessageToByteEncoder<Msg> {

  @Override
  protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf out) throws Exception {
    byte[] data = msg.toBytes();
    MsgType msgType = msg.getMsgType();
    DataPacket dataPacket = new Builder()
        .data(data)
        .msgType(msgType)
        .build();
    byte[] packet = dataPacket.toBytes();
    out.writeBytes(packet);
  }

}
