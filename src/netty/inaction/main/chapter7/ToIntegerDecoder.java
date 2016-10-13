package netty.inaction.main.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Listing 7.1  of <i>Netty in Action</i>
 *
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
public class ToIntegerDecoder extends ByteToMessageDecoder { // 1 Implementation extends ByteToMessageDecode to decode bytes to messages

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= 4) { // 2 Check if there are at least 4 bytes readable
            out.add(in.readInt());  // 3 Read integer from inbound ByteBuf, add to the List of decodec messages
        }
        // Netty自带的解码包,io.netty.handler.codec里
        // 提供大量解码的类(如:LineBasedFrameDecoder按行划分数据)

    }
}

