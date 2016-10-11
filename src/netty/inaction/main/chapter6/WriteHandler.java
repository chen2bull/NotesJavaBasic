package netty.inaction.main.chapter6;


import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 *
 * Listing 6.5 of <i>Netty in Action</i>
 *
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
public class WriteHandler extends ChannelHandlerAdapter {

    private ChannelHandlerContext ctx;

    // 调用ChannelHandlerContext的pipeline()方法能访问ChannelPipeline
    // 能在运行时动态的增加、删除、替换ChannelPipeline中的ChannelHandler
    // 可以(在外部)保存ChannelHandlerContext供以后使用，甚至从一个不同的线程触发事件

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        this.ctx = ctx; // Store reference to ChannelHandlerContext for later use
    }

    public void send(String msg) {
        ctx.writeAndFlush(msg); // Send message using previously stored ChannelHandlerContext
    }
}

