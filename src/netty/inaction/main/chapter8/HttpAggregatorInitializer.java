package netty.inaction.main.chapter8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * 8.3
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {

    private final boolean client;

    public HttpAggregatorInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (client) {   // 和HttpPipelineInitializer一样地添加编解码器
            pipeline.addLast("codec", new HttpClientCodec());
        } else {
            pipeline.addLast("codec", new HttpServerCodec());
        }
        // 然后在添加消息聚合的类HttpObjectAggregator
        // Add HttpObjectAggregator to the ChannelPipeline, using a max message size of 512kb.
        pipeline.addLast("aggegator", new HttpObjectAggregator(512 * 1024));
        // 为了防止Dos攻击服务器，需要合理的限制消息的大小,可以根据实际情况减小消息的maxContentLength
    }
}
