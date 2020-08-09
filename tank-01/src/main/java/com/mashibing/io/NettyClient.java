package com.mashibing.io;

import com.mashibing.tank.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
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
              pipeline.addLast(new MsgEncoder());
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

  public void write(Msg msg) {
    this.channel.writeAndFlush(msg);
  }
}

class ClientEventHandler extends SimpleChannelInboundHandler<Msg> {

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {

    TankJoinMsg tankJoinMsg = new TankJoinMsg(TankFrame.INSTANCE.getMainTank());
    ctx.writeAndFlush(tankJoinMsg);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    System.out.println("与服务端连接断开");
  }

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Msg msg)
      throws Exception {
    msg.handle();
  }

}

