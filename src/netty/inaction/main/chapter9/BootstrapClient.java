package netty.inaction.main.chapter9;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
public class BootstrapClient {

    // 将在调用bind(...)或connect(...)后创建通道
    public void bootstrap() {
        // #1 Create a new bootstrap to create new client channels and connect them
        Bootstrap bootstrap = new Bootstrap();
        // #2 Specify the EventLoopGroup to get EventLoops from and register with the channels
        // 这是java的一种习惯写法,group,channel和handler都返回Bootstrap类型的变量
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)    //#3 指定channel类型
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                        System.out.println("Reveived data");
                    }
                });


        /*
        经验显示，相兼容的实现一般在同一个包下面，例如使用NioEventLoop，NioEventLoopGroup和NioServerSocketChannel在一起。
        请注意，这些都是前缀“Nio”，然后不会用这些代替另一个实现和另一个前缀，如“Oio”，
        也就是说OioEventLoopGroup和NioServerSocketChannel是不相容的。
         */

        // #3 Specify the channel class that will be used to instance
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("Connection established");
                } else {
                    System.err.println("Connection attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        BootstrapClient client = new BootstrapClient();
        client.bootstrap();
    }
}
