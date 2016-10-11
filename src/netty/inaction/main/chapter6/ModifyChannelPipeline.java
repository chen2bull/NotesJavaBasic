package netty.inaction.main.chapter6;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelPipeline;

/**
 * Listing 6.1 of <i>Netty in Action</i>
 *
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
public class ModifyChannelPipeline {
    public static void modifyPipeline() {
        ChannelPipeline pipeline = null; // get reference to pipeline;
        FirstHandler firstHandler = new FirstHandler();
        pipeline.addLast("handler1", firstHandler);  // Add FirstHandler to ChannelPipeline
        pipeline.addFirst("handler2", new SecondHandler()); // This means it will be before the already existing FirstHandler
        pipeline.addLast("handler3", new ThirdHandler()); // Add ThirdHandler to ChannelPipeline on the last position

        pipeline.remove("handler3");    // Remove ThirdHandler by using name it was added with
        pipeline.remove(firstHandler);  // Remove FirstHandler by using reference to instance

        pipeline.replace("handler2", "handler4", new ForthHandler());

    }

    private static final class FirstHandler extends ChannelHandlerAdapter {

    }

    private static final class SecondHandler extends ChannelHandlerAdapter {

    }

    private static final class ThirdHandler extends ChannelHandlerAdapter {

    }

    private static final class ForthHandler extends ChannelHandlerAdapter {

    }
}
