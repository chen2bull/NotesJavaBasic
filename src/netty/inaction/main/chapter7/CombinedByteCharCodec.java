package netty.inaction.main.chapter7;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * Listing 7.10 of <i>Netty in Action</i>
 *
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
    public CombinedByteCharCodec() {
        // 2 Pass an instance of ByteToCharDecoder and CharToByteEncoder to the super constructor as it will
        // delegate calls to them to combine them
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}
