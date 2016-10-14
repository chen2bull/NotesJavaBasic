/**
 * Contains code examples for chapter 8 of <i>Netty in Action</i>
 *
 * ssl的编解码器
 * Listing 8.1:     SslChannelInitializer.java
 *
 * http的编解码器
 * 需要处理请求的不同部分,如果需要访问到完整的请求和响应的话看Listing8.3
 * Listing 8.2:     HttpPipelineInitializer.java
 *
 * http消息聚合
 * 能访问到完整的请求/响应对象FullHttpResponse and FullHttpRequest
 * Listing 8.3:     HttpAggregatorInitializer.java
 *
 * http消息压缩
 * 利用压缩来减少网络上传输的字节
 * 使用HttpContentDecompressor和HttpContentDecompressor
 * Listing 8.4:     HttpCompressionInitializer.java
 *
 * https
 * 前面加ssl编解码器即可
 * Listing 8.5:     HttpsCodecInitializer.java
 *
 * WebSocket
 * 如果需要实时发布信息怎么做？有个做法就是客户端一直轮询请求服务器，这种方式虽然可以达到目的，
 * 但是其缺点很多，也不是优秀的解决方案，为了解决这个问题，便出现了WebSocket。
 * WebSocket允许数据双向传输，而不需要请求-响应模式
 * Listing 8.6:     WebSocketServerInitializer.java
 *
 * 处理空闲连接和超时
 * 空闲的时候才发送心跳包,如果没有响应,那么就断开连接
 * Listing 8.7:     IdleStateHandlerInitializer.java
 *
 * Handling \r\n delimited frames(使用LineBasedFrameDecoder)
 * Listing 8.8:     LineBasedHandlerInitializer.java
 * 基于其他分隔符的协议可以使用DelimiterBasedFrameDecoder,例子略(同8.8)
 *
 * 自定义的基于分隔符的协议(继承LineBasedFrameDecoder)
 * Listing 8.9:     CmdHandlerInitializer.java
 *
 * 基于长度的协议(LengthFieldBasedFrameDecoder)
 * Listing 8.10:    LengthBasedInitializer.java
 *
 * 写大块数据
 * ChunkedWriteHandler allows you to write big chunks of data by handling ChunkedInput implementations
 * Listing 8.11:    ChunkedWriteHandlerInitializer.java
 *
 * 普通JDK序列化,没有实用价值,略
 *
 * JBoss Marshalling
 * JBoss Marshalling序列化的速度是JDK的3倍，并且序列化的结构更紧凑
 * Listing 8.12:    MarshallingInitializer.java
 *
 *
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
package netty.inaction.main.chapter8;
