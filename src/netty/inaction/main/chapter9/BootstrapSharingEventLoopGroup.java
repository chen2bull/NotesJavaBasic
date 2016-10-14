package netty.inaction.main.chapter9;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
public class BootstrapSharingEventLoopGroup {

    public void bootstrap() {
        // 1 Create a new ServerBootstrap to create new SocketChannel channels and bind them
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 2 Specify the EventLoopGroups that will be used to get EventLoops from and register
        // with the ServerChannel and the accepted channels
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup());
        // 3 Specify the channel class that will be used to instance
        bootstrap.channel(NioServerSocketChannel.class);
        // 4 Set a child handler which will handle I/O and data for the accepted channels
        bootstrap.childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
            ChannelFuture connectFuture;

            @Override
            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.channel(NioSocketChannel.class)
                        .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
                                System.out.println("Reveived data");
                            }
                        });
                bootstrap.group(ctx.channel().eventLoop());
                connectFuture = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
            }

            @Override
            protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                if (connectFuture.isDone()) {
                    // do something with the data
                }
            }
        });
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.err.println("Bound attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }
}
