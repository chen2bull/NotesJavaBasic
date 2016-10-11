package netty.inaction.main.chapter6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * Listing 6.6 of <i>Netty in Action</i>
 * ChannelHandler如果带有@Sharable注解,则可以被添加到多个ChannelPipeline。
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
@ChannelHandler.Sharable
public class SharableHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("channel read message " + msg);
        ctx.fireChannelRead(msg);
    }
}

