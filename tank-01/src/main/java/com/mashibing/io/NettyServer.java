package com.mashibing.io;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NettyServer {

  public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
  private Channel channel;

  public void start(){
    EventLoopGroup bossGroup = null;
    EventLoopGroup workerGroup = null;
    try{
      //线程数默认为cpu的核心数
      bossGroup = new NioEventLoopGroup(1);
      workerGroup = new NioEventLoopGroup(2);
      ServerBootstrap bootstrap = new ServerBootstrap();
      ChannelFuture channelFuture = bootstrap.group(bossGroup, workerGroup)
          .childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
              ChannelPipeline pipeline = ch.pipeline();
              pipeline.addLast(new MsgDecoder());
              pipeline.addLast(new MsgEncoder());
              pipeline.addLast(new ServerReadHanler());
            }
          })
          .channel(NioServerSocketChannel.class)
          .bind("localhost", 8888);
      channel = channelFuture.sync().channel();
      ServerFrame.INSTANCE.updateServerMsg("server started");
      channel.closeFuture().sync();//closeFuture.sync在调用close后方法才返回
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      if(bossGroup != null){
        bossGroup.shutdownGracefully();
      }
      if(workerGroup != null){
        //关闭线程池
        workerGroup.shutdownGracefully();
      }
    }
  }

}

class ServerReadHanler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    //将客户端加入channel组
    ServerFrame.INSTANCE.updateServerMsg("一个新的客户端连接上了");
    NettyServer.clients.add(ctx.channel());
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    ServerFrame.INSTANCE.updateClientMsg(msg.toString());
    //向所有的客户端转发消息
    NettyServer.clients.writeAndFlush(msg);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ServerFrame.INSTANCE.updateServerMsg("client exit");
    NettyServer.clients.remove(ctx.channel());
    ctx.close();
  }
}
