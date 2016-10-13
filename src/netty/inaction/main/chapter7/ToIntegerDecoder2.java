package netty.inaction.main.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Listing 7.2  of <i>Netty in Action</i>
 *
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
public class ToIntegerDecoder2 extends ReplayingDecoder<Void> {

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(in.readInt());
        // 扩展ByteToMessageDecoder的话,每次读取数据前都要检查是否有足够的字节
        // 用ReplayingDecoder就无需自己检查,
        // 当从接收的数据ByteBuf读取integer，若没有足够的字节可读，decode(...)会停止解码，
        // When reading the integer from the inbound ByteBuf, if not enough bytes are readable, it will
        //throw a signal which will be cached so the decode() method will be called later, once more
        // data is ready. Otherwise, add it to the List


        // ReplayingDecoder缺点:
        // 1.不是所有的操作都被ByteBuf支持，如果调用一个不支持的操作会抛出DecoderException。
        // 2.ByteBuf.readableBytes()大部分时间不会返回期望值


    }
}

