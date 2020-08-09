package com.mashibing.io;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

  public static final NettyClient INSTANCE = new NettyClient();

  private NettyClient(){}

  private Channel channel;

  public void connect(){
    EventLoopGroup group = null;
    try{
      group = new NioEventLoopGroup(1);
      Bootstrap bootstrap = new Bootstrap();
      ChannelFuture channelFuture = bootstrap.group(group)
          .channel(NioSocketChannel.class)
          .handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
              ChannelPipeline pipeline = ch.pipeline();
              pipeline.addLast(new TankMsgDecoder());
              pipeline.addLast(new TankMsgEncoder());
              pipeline.addLast(new ClientEventHandler());
            }
          })
          .connect("localhost", 8888);

      channelFuture.addListener(new ChannelFutureListener(){

        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
          if(channelFuture.isSuccess()){
            channel = channelFuture.channel();
          }else {
            System.out.println("client connect failed");
          }
        }
      });
      ChannelFuture sync = channelFuture.sync();
      sync.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      if(group != null){
        group.shutdownGracefully();
      }
    }
  }

}

class ClientEventHandler extends SimpleChannelInboundHandler<TankMsg> {

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    TankMsg tankMsg = new TankMsg(100,50);
    ctx.writeAndFlush(tankMsg);
  }


  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, TankMsg tankMsg)
      throws Exception {
    System.out.println(tankMsg);
  }

}

