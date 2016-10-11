package netty.inaction.main.chapter6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Listing 6.8 of <i>Netty in Action</i>
 *
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
@ChannelHandler.Sharable
public class DiscardHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ReferenceCountUtil.release(msg);    // Discard received message by pass it to ReferenceCountUtil.release()
        // 如果缺少release方法的调用,会导致资源泄露
        // 自动释放资源的使用方法,见本例中的SimpleDiscardHandler是如何使用SimpleChannelInboundHandler<T>的
    }

}

