package com.mashibing.io;

import com.mashibing.io.DataPacket.Builder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    if(in.readableBytes()>=DataPacket.DATA_PACKET_HEADER_LEN){
      byte[] header = new byte[DataPacket.DATA_PACKET_HEADER_LEN];
      in.getBytes(in.readerIndex(),header);
      DataPacket dataPacket = Builder.deserialize(header);
      int len = dataPacket.len;
      if((DataPacket.DATA_PACKET_HEADER_LEN+len)>=in.readableBytes()){
        in.skipBytes(DataPacket.DATA_PACKET_HEADER_LEN);
        byte[] data = new byte[len];
        in.readBytes(data);
        dataPacket.data = data;
        MsgType msgType = dataPacket.msgType;
        Msg msg = null;
        switch (msgType){
          case BULLET_NEW:
            msg = new BulletMsg().parse(data);
            break;
          case TANK_JOIN:
            msg = new TankJoinMsg().parse(data);
            break;
          case TANK_MOVING:
            msg = new TankMoveMsg().parse(data);
            break;
            default:
              throw new IllegalStateException("不支持的类型");
        }
        out.add(msg);
      }
    }
  }
}
