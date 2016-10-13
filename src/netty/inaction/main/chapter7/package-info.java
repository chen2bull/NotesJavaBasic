/**
 * Contains code examples for chapter 7 of <i>Netty in Action</i>
 *
 * Netty中提供的ByteToMessageDecoder可以将字节消息解码成POJO对象
 * Listing 7.1:     ToIntegerDecoder.java
 *
 * 不需要每次都检查是否有足够的字节(ReplayingDecoder<T>)
 * Listing 7.2:     ToIntegerDecoder2.java
 *
 * decode from one message to another message (forexample, POJO to POJO)
 * 而不是bytes和其他类型之间的转换(MessageToMessageDecoder<T>)
 * Listing 7.3:     IntegerToStringDecoder.java
 *
 * 安全地Decode要检查数据大小(书本没有这个例子,可能新版的书才有这个例子)
 * Listing 7.4:     SafeByteToMessageDecoder.java
 *
 * message转成Bytes,扩展MessageToByteEncoder<T>
 * Listing 7.5:     ShortToByteEncoder.java
 *
 * message转成message(比如两个类之间的转换)
 * Decoding is for inbound data and encoding for outbound data.
 * Listing 7.6:     IntegerToStringEncoder.java
 *
 *
 * 强制同时Encoder和Decoder
 * 编解码器MessageToMessageCodec的使用(convert a message from one API to another API)
 * Using a codec will force you to have both the decoder and encoder in the ChannelPipeline or none of these.
 * Listing 7.7:     WebSocketConvertHandler.java
 *
 *
 * 像编解码器一样可以组合使用Encoder和Decoder,但也可以只使用两个中的一个
 * Although CombinedChannelDuplexHandler isn't part of the codec API itself, it's often used to build up a codec.
 * Listing 7.8:     ByteToCharDecoder.java
 * Listing 7.9:     CharToByteEncoder.java
 * Listinh 7.10:    CombinedByteCharCodec.java
 *
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
package netty.inaction.main.chapter7;
