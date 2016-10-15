package netty.inaction.main.chapter15;

import io.netty.channel.Channel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
public class ScheduleExamples {

    /**
     * Schedule task with a ScheduledExecutorService
     * Listing 14.4 不用channel的情况下
     */
    public static void schedule() {
        ScheduledExecutorService executor = Executors
                .newScheduledThreadPool(10);

        ScheduledFuture<?> future = executor.schedule(
                new Runnable() { // #1 Create a new runnable to schedule for later execution
                    @Override
                    public void run() {
                        System.out.println("Now it is 60 seconds later");
                    }
                }, 60, TimeUnit.SECONDS);
        // do something
        //

        executor.shutdown();
    }


    /**
     * After the 60 seconds passes, it will get executed by the EventLoop that's assigned to the channel.
     * Listing 14.5 用channel的情况下
     */
    public static void scheduleViaEventLoop() {
        Channel ch = null; // Get reference to channel
        ScheduledFuture<?> future = ch.eventLoop().schedule(
                new Runnable() {    // #1 Create a new runnable to schedule for later execution
                    @Override
                    public void run() {
                        System.out.println("Now its 60 seconds later");
                    }
                }, 60, TimeUnit.SECONDS);
    }


    /**
     * 没60秒执行某个任务
     * Listing 14.6
     */
    public static void scheduleFixedViaEventLoop() {
        Channel ch = null; // Get reference to channel
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Run every 60 seconds");
                    }
                }, 60, 60, TimeUnit.SECONDS);
    }
}
