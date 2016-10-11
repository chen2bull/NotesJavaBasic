package netty.inaction.main.chapter6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/**
 *
 * Listing 6.11 of <i>Netty in Action</i>
 *
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
@ChannelHandler.Sharable
public class DiscardOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {  // 1 Extend ChannelOutboundHandlerAdapter
        ReferenceCountUtil.release(msg); //2 Release resource by using ReferenceCountUtil.release(...)
        promise.setSuccess();   //3 Notify ChannelPromise that data handled
        // 若ChannelPromise没有被通知,可能会导致其中一个ChannelFutureListener没有被通知去处理一个消息
        // Once the message is passed over to the actual Transport it will be released automatically
        // by it once the message was written or the Channel was closed.
    }

}

