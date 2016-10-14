package netty.inaction.main.chapter5;

import io.netty.buffer.*;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;

/**
 * 演示ByteBuffer使用的一些例子
 * Created by norman on 29/12/14.
 */
public class ByteBufExamples {

    private final static Random random = new Random();

    /**
     * HEAP BUFFERS(堆缓冲区)
     * JDK的ByteBuffer类似,适合遗留代码
     * Listing 5.1
     */
    public static void heapBuffer(ByteBuf heapBuf) {
        if (heapBuf.hasArray()) {           // 1 Check if ByteBuf is backed by array
            // Accessing the array from a "nonheap" ByteBuf will result in an UnsupportedOperationException
            // 所以必须使用hasArray判断这个Buffer是数组实现的
            byte[] array = heapBuf.array(); // 2 Get reference to array
            // It also offers a way to directly access the backing array, which may
            // make it easier to interact with "legacy code"
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex(); //3 Calculate offset of first byte in it
            int length = heapBuf.readableBytes();   // 4 Get amount of readable bytes
            handleArray(array, offset, length);     // 5 Call method using array, offset, length as parameter
        }
    }

    /**
     * DIRECT BUFFERS(直接缓冲区)
     * Direct means that it allocates the memory directly, which is outside the "heap".
     * You won't see its memory usage in your heap space.
     * Listing 5.2
     */
    public static void directBuffer(ByteBuf directBuf) {
        if (!directBuf.hasArray()) {    // #1 Check if ByteBuf not backed by array which will be false for direct buffer
            // 直接缓冲区不支持数组访问数据，但是我们可以间接的访问数据数组
            int length = directBuf.readableBytes(); // #2 Get number of readable bytes
            // 需要申请一个新的数组,相当于重新拷贝了一份数据
            byte[] array = new byte[length]; // #3 Allocate new array with length of readable bytes
            directBuf.getBytes(directBuf.readerIndex(), array); // #4 Read bytes into array
            handleArray(array, 0, length);  // #5 Call method that takes array, offset, length as parameter
        }

    }

    /**
     * JDK中如果想合并两个Buffer的话,需要这样
     * Listing 5.3
     */
    public static void byteBufferComposite(ByteBuffer header, ByteBuffer body) {
        // 方法1: Use an array to hold the message parts(缺点:如果有以Buffer为输入的接口,那么就需要兼容输入参数为数组的情况)
        ByteBuffer[] message = { header, body };

        // 方法2: Use copy to merge both(缺点:需要多拷贝一次,性能有问题)
        // netty不推荐使用这两种方法,而是提供一个CompositeByteBuf类来处理这种需求,见Listing 5.4
        ByteBuffer message2 = ByteBuffer.allocate(header.remaining() + body.remaining());
        message2.put(header);
        message2.put(body);
        message2.flip();
    }


    /**
     * Netty提供了CompositeByteBuf类来处理复合缓冲区
     * Listing 5.4
     */
    public static void byteBufComposite(ByteBuf headerBuf, ByteBuf bodyBuf) {
        CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
        messageBuf.addComponents(headerBuf, bodyBuf); // #1 Append ByteBuf instances to the composite
        // ....
        messageBuf.removeComponent(0); // remove the header
        // 2 Remove ByteBuf on index 0 (heapBuf here)

        for (int i = 0; i < messageBuf.numComponents(); i++) {  //3
            System.out.println(messageBuf.component(i).toString());
        }
    }

    /**
     * 和处理其他ByteBuf类一样,处理ByteBuf
     * Listing 5.5
     */
    public static void byteBufCompositeArray(CompositeByteBuf compBuf) {
        int length = compBuf.readableBytes();						//1
        byte[] array = new byte[length];						//2
        compBuf.getBytes(compBuf.readerIndex(), array);							//3
        handleArray(array, 0, length);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // 下面演示ByteBuf的操作
    /**
     *
     * 操作1.随机访问
     * Listing 5.6
     */
    public static void byteBufRelativeAccess(ByteBuf buffer) {
        // index of the first byte is always 0 and the index of the last byte is always capacity-1
        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.getByte(i);
            System.out.println((char) b);
        }

    }

    /**
     * 操作2.获取顺序访问索引
     * 读操作是使用readerIndex()，写操作时使用writerIndex()
     * ByteBuf一定符合：0 <= readerIndex <= writerIndex <= capacity
     */

    /**
     * 操作3.Discardable bytes废弃字节
     * discardReadBytes()将丢弃从索引0到readerIndex之间的字节
     * discardReadBytes()可能会涉及内存复制，因为它需要移动ByteBuf中可读的字节到开始位置，
     * 这样的操作会影响性能，一般在需要马上释放内存的时候使用收益会比较大
     */

    /**
     * 操作4.访问可读字节
     * Listing 5.7
     */
    public static void readAllData(ByteBuf buffer) {
        // ByteBuf provides two pointer variables to support sequential read and write operations:
        // readerIndex for a read operation and writerIndex for a write operation
        while (buffer.isReadable()) {
            System.out.println(buffer.readByte());
        }
    }

    /**
     * 操作5.访问可写字节
     * Listing 5.8
     */
    public static void write(ByteBuf buffer) {
        while (buffer.writableBytes() >= 4) {
            buffer.writeInt(random.nextInt());
        }
    }

    /**
     * 操作6.clear清除缓冲区索引
     * 调用ByteBuf.clear()可以设置readerIndex和writerIndex为0，clear()不会清除缓冲区的内容，只是将两个索引值设置为0。
     * 注意ByteBuf.clear()与JDK的ByteBuffer.clear()的语义不同
     * 和discardReadBytes()相比，clear()性能更高,因为它不会复制任何内容
     */

    /**
     * 操作7.搜索操作Search operations
     * Listing 5.9
     */
    @SuppressWarnings("deprecation")
    public static void byteBufProcessor(ByteBuf buffer) {
        // 检查数据中的空字节的index
        int index = buffer.forEachByte(ByteBufProcessor.FIND_CR);
    }

    /**
     * 操作8.标准和重置Mark and reset
     * 可以通过调用readerIndex(int readerIndex)和writerIndex(int writerIndex)
     * 移动读索引和写索引到指定位置，调用这两个方法设置指定索引位置时可能抛出IndexOutOfBoundException
     */

    /**
     * 操作9. Slice,切片出来的Buf和原Buf共享一片内存,对于一个的修改影响另一个
     * Listing 5.10
     */
    public static void byteBufSlice() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);     //1

        ByteBuf sliced = buf.slice(0, 14);                                       //2
        System.out.println(sliced.toString(utf8));                               //3

        buf.setByte(0, (byte) 'J');                                              //4
        assert buf.getByte(0) == sliced.getByte(0); // #5 Won't fail as both ByteBuf share the same content
        // so modifications to one of them are visible on the other too
    }

    /**
     * 操作10.Copy,复制出来的Buf和原Buf不共享一片内存,对于一个的修改不影响另一个
     * Listing 5.11
     */
    public static void byteBufCopy() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);     //1

        ByteBuf copy = buf.copy(0, 14);                                          //2
        System.out.println(copy.toString(utf8));                                 //3

        buf.setByte(0, (byte) 'J');                                              //4
        assert buf.getByte(0) != copy.getByte(0);
    }

    /**
     * 操作11.set和get,不会移动相应的索引
     * Listing 5.12
     */
    public static void byteBufSetGet() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);	//1
        System.out.println((char)buf.getByte(0));					//2

        int readerIndex = buf.readerIndex();						//3
        int writerIndex = buf.writerIndex();

        buf.setByte(0, (byte)'B');							//4

        System.out.println((char)buf.getByte(0));					//5
        assert readerIndex == buf.readerIndex();					//6
        assert writerIndex ==  buf.writerIndex();
    }

    /**
     * 操作12.read和write,会移动相应的索引
     * Listing 5.13
     */
    public static void byteBufWriteRead() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);	//1
        System.out.println((char)buf.readByte());					//2

        int readerIndex = buf.readerIndex();						//3
        int writerIndex = buf.writerIndex();						//4

        buf.writeByte((byte)'?');							//5

        assert readerIndex == buf.readerIndex();
        assert writerIndex != buf.writerIndex();
    }

    private static void handleArray(byte[] array, int offset, int len) {
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // 下面演示一些辅助类
    // Netty goes even further by providing a set of utility classes
    // which makes creating and using the various buffers even easier.

    // 1.ByteBufHolder
    // 如果需要实现一些对象,它的数据成员是从ByteBuf中获取的数据,
    // 那么用ByteBufHolder存储这些数据还是一个比较好的主意

    // 2.ByteBufAllocator
    // 这个类有一系列方法可以申请各种ByteBuf
    // 申请的时候可以将capacity设置得大一点,不是一开始就分配空间的,最多能扩展到capacity
    // 而得到一个ByteBufAllocator类的方法如下
    // ByteBufAllocator allocator = channel.alloc();
    // ByteBufAllocator allocator2 = ctx.alloc();
    // Netty默认使用PooledByteBufAllocator(which is the pooled implementation of ByteBufAllocator)
    // 但可以通过ChannelConfig改变,或者在bootstrapping的时候指定一个

    // 3.Unpooled  Buffer creation made easy
    // 当无法获取 ByteBufAllocator的引用时,可以用Unpooled类来创建unpooled ByteBuf instances

    // 4.ByteBufUtil提供一些静态帮助方法来操作ByteBuf
    // 比如hexdump用十六进制来表示数据,可以用log来打印这些数据,方便debug
    // 还有比较Buf内容是否相同等一系列各种功能的辅助方法
}
