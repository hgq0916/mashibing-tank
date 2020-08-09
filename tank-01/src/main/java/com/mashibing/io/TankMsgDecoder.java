package com.mashibing.io;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class TankMsgDecoder extends ByteToMessageDecoder {

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    if(in.readableBytes()>=34){
      byte[] data = new byte[34];
      in.readBytes(data);
      TankJoinMsg tankJoinMsg = TankJoinMsg.deserialize(data);
      out.add(tankJoinMsg);
    }
  }
}
