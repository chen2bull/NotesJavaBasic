package netty.inaction.main.chapter7;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Listing 7.3 of <i>Netty in Action</i>
 *
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer> {
    // 1 Implementation extends MessageToMessageDecoder
    // 参数化的类型就是decode方法第二个参数的类型

    @Override
    public void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        out.add(String.valueOf(msg));   // 转换成目标的类型
    }
    // io.netty.handler.codec.http 里有一些复杂的类型
}

