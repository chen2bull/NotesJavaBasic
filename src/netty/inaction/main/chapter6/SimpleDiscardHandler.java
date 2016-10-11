package netty.inaction.main.chapter6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * Listing 6.9 of <i>Netty in Action</i>
 *
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
@ChannelHandler.Sharable
public class SimpleDiscardHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        // 这里不需要调用ReferenceCountUtil.release方法来释放msg
        // 这种用法不能在吧msg保存起来,然后以后再用
        // 因为随后msg会被回收(SimpleChannelInboundHandler的autoRelease默认为true)
    }

}

