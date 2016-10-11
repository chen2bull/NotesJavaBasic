package netty.inaction.main.chapter6;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**
 *
 * Listing 6.2, 6.3 and 6.4 of <i>Netty in Action</i>
 *
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
public class Writes {

    /**
     *
     * Listing 6.2 of <i>Netty in Action</i>
     */
    public static void writeViaChannel(ChannelHandlerContext context) {
        ChannelHandlerContext ctx = context;
        Channel channel = ctx.channel();
        channel.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8)); // Write buffer via channel

    }

    /**
     *
     * Listing 6.3 of <i>Netty in Action</i>
     */
    public static void writeViaChannelPipeline(ChannelHandlerContext context) {
        ChannelHandlerContext ctx = context;
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));
        // Write buffer via ChannelPipeline 和上面的函数例子作用一样

    }

    /**
     *
     * Listing 6.4 of <i>Netty in Action</i>
     */
    public static void writeViaChannelHandlerContext(ChannelHandlerContext context) {
        ChannelHandlerContext ctx = context;
        ctx.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));
        // 和上面两个例子的意义不一样
        // 从指定的ChannelHandlerContext开始，跳过前面所有的ChannelHandler，使用ChannelHandlerContext操作是常见的模式，
        // 最常用的是从ChannelHanlder调用操作，也可以在外部使用ChannelHandlerContext，因为这是线程安全的。

    }
}
