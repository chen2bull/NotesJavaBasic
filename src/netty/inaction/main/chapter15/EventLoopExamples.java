package netty.inaction.main.chapter15;

import io.netty.channel.Channel;

import java.util.concurrent.Future;

/**
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
public class EventLoopExamples {
    /**
     * The benefit of executing the task in the event loop is that you don't need to worry about any
     * synchronization. The runnable will get executed in the same thread as all other events that are
     * related to the channel. This fits exactly in the expected thread model of Netty.
     * Listing 14.1
     */
    public static void executeTaskInEventLoop() {
        Channel channel = null; // get reference to channel
        channel.eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Run in the EventLoop");
            }
        });
    }

    /**
     * To check if the task was executed yet, use the returned Future.
     * This gives you access to many different operations.
     * Listing 14.2
     */
    public static void executeTaskInEventLoopAndCheck() {
        Channel channel = null; // get reference to channel
        Future<?> future = channel.eventLoop().submit(new Runnable() {
            @Override
            public void run() {
                // Do something
            }
        });
        if (future.isDone()) {  // 检查任务是否已经完成
            System.out.println("Task complete");
        } else {
            System.out.println("Task not complete yet");
        }
    }

    /**
     * Listing 14.3 检查当前线程是否在EventLoop中
     */
    public static void checkIfInEventLoop() {
        Channel channel = null; // get reference to channel
        if (channel.eventLoop().inEventLoop()) {    // #1 Check if calling thread is assigned to EventLoop
            System.out.println("In the EventLoop"); // #2 At this point, you're in the EventLoop
        } else {
            System.out.println("Outside the EventLoop"); // #3 At this point, thread isn't assigned to EventLoop
        }
    }
}
