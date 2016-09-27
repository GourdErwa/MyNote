package com.gourd.erwa.io.nio;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 以下是设置和复位索引以及查询的方法：
 * capacity() ： 返回此缓冲区的容量。
 * clear() ：    清除此缓冲区。
 * flip() ：     反转此缓冲区。
 * limit() ：    返回此缓冲区的限制。
 * limit(int newLimit) ：设置此缓冲区的限制。
 * mark() ：     在此缓冲区的位置设置标记。
 * position() ： 返回此缓冲区的位置。
 * position(int newPosition) ：设置此缓冲区的位置。
 * remaining() ：返回当前位置与限制之间的元素数。
 * reset() ：    将此缓冲区的位置重置为以前标记的位置。
 * rewind() ：   重绕此缓冲区。
 *
 * @author wei.Li by 14-8-22.
 */
public class ByteBuffer_Example {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ByteBuffer_Example.class);

    /**
     * 0 <= mark <= position <= limit <= capacity
     */
    private static void basicProperty() {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        int capacity = buffer.capacity();
        for (byte i = 0; i < capacity; i++) {
            if (i == 20) {
                buffer.limit(30);
                capacity = 30 - 1;
            }
            buffer.put(i);
        }


        //初始化后的 buffer
        LOGGER.info("init limit  is <{}>", buffer.limit());
        LOGGER.info("init position  is <{}>", buffer.position());

        //取第二个 index 位置的数据
        byte b = buffer.get(2);
        LOGGER.info("run {buffer.get(2)} after position  is <{}>", buffer.position());

        //将位置设置10
        buffer.position(10);
        LOGGER.info("run {buffer.position(10)} after position  is <{}>", buffer.position());

        //读取数据 从位置10开始读取
        byte b1 = buffer.get();
        LOGGER.info("run {buffer.get()} after position  is <{}> , b1 is <{}>", buffer.position(), b1);

        //标记position的位置
        buffer.mark();
        LOGGER.info("run {buffer.mark()} after position  is <{}> ", buffer.position());


        //继续读取数据
        byte b2 = buffer.get();
        LOGGER.info("run {buffer.get()} after position  is <{}> , b2 is <{}>", buffer.position(), b2);

        /**
         *将此缓冲区的位置重置为以前标记的位置。
         */
        buffer.reset();
        LOGGER.info("run {buffer.reset()} after position  is <{}> , limit is <{}>", buffer.position(), buffer.limit());

        /**
         *Buffer flip()
         把 limit 设为当前 position ，把 position 设为 0 ，如果已定义了标记，则丢弃该标记 , 一般在从 Buffer 读出数据前调用。*/
        buffer.flip();
        LOGGER.info("run {buffer.flip()} after position  is <{}> , limit is <{}>", buffer.position(), buffer.limit());

        /**
         * Buffer rewind()
         把 position 设为 0 ， limit 不变，一般在把数据重写入 Buffer 前调用。
         */
        buffer.rewind();
        LOGGER.info("run {buffer.rewind()} after position  is <{}> , limit is <{}>", buffer.position(), buffer.limit());


        /**
         * Buffer clear()
         把 position 设为 0 ，把 limit 设为 capacity ，一般在把数据写入 Buffer 前调用。
         */
        buffer.clear();
        LOGGER.info("run {buffer.clear()} after position  is <{}> , limit is <{}>", buffer.position(), buffer.limit());
    }


    private static void readInSystem() {
        // 创建一个 capacity 为 256 的 ByteBuffer
        ByteBuffer buf = ByteBuffer.allocate(256);

        while (true) {
            // 从标准输入流读入一个字符
            int c = 0;
            try {
                c = System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 当读到输入流结束时，或者输入“#”后，退出循环
            if (c == -1 || c == '#') {
                LOGGER.info("break -> <>", ((char) c));
                break;
            }

            // 把读入的字符写入 ByteBuffer 中
            buf.put((byte) c);
            // 当读完一行时，输出收集的字符
            if (c == '\n') {
                // 调用 flip() 使 limit 变为当前的 position 的值 ,position 变为 0,
                // 为接下来从 ByteBuffer 读取做准备
                buf.flip();
                // 构建一个 byte 数组
                byte[] content = new byte[buf.limit()];
                // 从 ByteBuffer 中读取数据到 byte 数组中
                buf.get(content);
                // 把 byte 数组的内容写到标准输出
                LOGGER.info(new String(content));
                // 调用 clear() 使 position 变为 0,limit 变为 capacity 的值，
                // 为接下来写入数据到 ByteBuffer 中做准备
                buf.clear();
            }
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        //readInSystem();
        String export_path = "/lw//a//b//c.txt";
        export_path = export_path.replace("//", "/");
        String path_0 = export_path.substring(0, export_path.lastIndexOf("/"));

        System.out.println(path_0);

        /*File file = new File("/lw/a/a.txt");
        if (!file.exists()) {
            try {

                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }
}
