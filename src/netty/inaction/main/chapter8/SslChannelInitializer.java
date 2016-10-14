package netty.inaction.main.chapter8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * 8.1
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {

    private final SslContext context;
    private final boolean startTls;
    // Netty扩展了Java的SslEngine，添加了一些新功能，使其更适合基于Netty的应用程序。
    // Netty提供的这个扩展是SslHandler，是SslEngine的包装类，用来对网络数据进行加密和解密
    public SslChannelInitializer(SslContext context, boolean startTls) {
        // 1 Use the constructor to pass the SSLContext to use and if it's a client and startTls should be used
        this.context = context;
        this.startTls = startTls;
    }
    @Override
    protected void initChannel(Channel ch) throws Exception {
        // 2 Obtain a new SslEngine from the SslContext. Use a new SslEngine for each SslHandler instance
        SSLEngine engine = context.newEngine(ch.alloc());
        // 3 Set if the SslEngine is used in client or server mode,
        // engine.setUseClientMode(true);

        // 4 Add the SslHandler in the pipeline as first handler
        ch.pipeline().addFirst("ssl", new SslHandler(engine, startTls));
        // ChannelPipeline就像是一个在处理“入站”数据时先进先出，在处理“出站”数据时后进先出的队列
        // SslHandler最好添加到ChannelPipeline的第一个位置
        // 最先添加的SslHandler会在其他Handler处理逻辑数据之前对数据进行加密，从而确保Netty服务端的所有的Handler的变化都是安全的
    }
}
