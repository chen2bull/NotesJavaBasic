package netty.inaction.main.chapter8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * 8.2
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {

    private final boolean client;

    public HttpPipelineInitializer(boolean client) {
        this.client = client;
    }
    //HttpRequestEncoder，将HttpRequest或HttpContent编码成ByteBuf
    //HttpRequestDecoder，将ByteBuf解码成HttpRequest和HttpContent
    //HttpResponseEncoder，将HttpResponse或HttpContent编码成ByteBuf
    //HttpResponseDecoder，将ByteBuf解码成HttpResponse和HttpContent
    // 注意,这里的HttpRequest等都是netty中的类

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (client) {   // 客户端需要封装请求,解码响应
            pipeline.addLast("decoder", new HttpResponseDecoder());
            pipeline.addLast("encoder", new HttpRequestEncoder());
        } else {        // 服务端需要解码请求,封装响应
            pipeline.addLast("decoder", new HttpRequestDecoder());
            pipeline.addLast("encoder", new HttpResponseEncoder());
        }
        // 也可以用HttpClientCodec or HttpServerCodec
    }
}
