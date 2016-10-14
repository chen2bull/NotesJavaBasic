package netty.inaction.main.chapter8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileInputStream;

/**
 * 8.11
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
public class ChunkedWriteHandlerInitializer extends ChannelInitializer<Channel> {
    private final File file;

    public ChunkedWriteHandlerInitializer(File file) {
        this.file = file;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ChunkedWriteHandler());    // 1 Add ChunkedWriteHandler to handle ChunkedInput implementations
        pipeline.addLast(new WriteStreamHandler());     // 2 Add WriteStreamHandler to write a ChunkedInput
    }

    public final class WriteStreamHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            ctx.writeAndFlush(new ChunkedStream(new FileInputStream(file)));
            // 3 Write the content of the file via a ChunkedStream once the connection is established
            // (we use a FileInputStream only for demo purposes, any InputStream works)
        }
    }
}
