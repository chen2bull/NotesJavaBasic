package netty.inaction.main.chapter4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Listing 4.4  of <i>Netty in Action</i>
 * 用Netty作为网络框架编写的一个非阻塞IO例子
 *
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
public class NettyNioServer {

    public void server(int port) throws Exception {
        final ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
        // 事件循环组
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            // 用来引导服务器配置
            ServerBootstrap b = new ServerBootstrap();

            // 使用NIO异步模式
            b.group(new NioEventLoopGroup(), new NioEventLoopGroup()).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
                    // 指定ChannelInitializer初始化handlers
                    // An ChannelInitializer is also itself a ChannelHandler which automatically removes itself
                    // from the ChannelPipeline after it has added the other handlers.
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch)
                        throws Exception {
                    // 添加一个“入站”handler到ChannelPipeline
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 连接后，写消息到客户端，写完后便关闭连接
                            ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                                }
                            });
                        }
                    });
            // 绑定服务器接受连接
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            // 释放所有资源
            group.shutdownGracefully().sync();
        }
    }
}

