package netty.inaction.main.chapter1;


import java.util.concurrent.CountDownLatch;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 基于NIO2.0的Echo服务器
 * Created by Administrator on 2016/10/2.
 */
public class PlainNio2EchoServer {
    public void serve(int port) throws IOException {
        System.out.println("Listening for connections on port " + port);
        final AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(port);
        serverChannel.bind(address);    //#1 Bind Server to port
        final CountDownLatch latch = new CountDownLatch(1);
        serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
        //#2 Start to accept new Client connections. Once one is accepted the CompletionHandler will get called.
                    @Override
                    public void completed(final AsynchronousSocketChannel channel, Object attachment) {
                        serverChannel.accept(null, this);   //#3 和#2是一样的操作
                        ByteBuffer buffer = ByteBuffer.allocate(100);
                        channel.read(buffer, buffer, new EchoCompletionHandler(channel));
                        //#4 Trigger a read operation on the Channel, the given CompletionHandler will be notified once something was read
                    }

                    @Override
                    public void failed(Throwable throwable, Object attachment) {
                        try {
                            serverChannel.close();  //#5 Close the socket on error
                        } catch (IOException e) {
                            // ingnore on close
                        } finally {
                            latch.countDown();
                        }
                    }
                });
        try {
            latch.await();  // 不让serve函数返回,一直等待下去
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private final class EchoCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
        private final AsynchronousSocketChannel channel;

        EchoCompletionHandler(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void completed(Integer result, ByteBuffer buffer) {
            buffer.flip();
            channel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() { //#6
                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    if (buffer.hasRemaining()) {
                        channel.write(buffer, buffer, this);    //#7
                    } else {
                        buffer.compact();
                        channel.read(buffer, buffer, EchoCompletionHandler.this);    //#8
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        // ingnore on close
                    }
                }
            });
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            try {
                channel.close();
            } catch (IOException e) {
                // ingnore on close
            }
        }
    }
}