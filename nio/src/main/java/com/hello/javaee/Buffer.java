package com.hello.javaee;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: zhangchao
 * @time: 2018-12-27 10:58
 **/
public class Buffer {


    public static void main(String[] args) throws Exception {

        //是咧一个缓冲区
        ByteBuffer allocate =  ByteBuffer.wrap("hello".getBytes());

        //读取一个文件
        RandomAccessFile file = new RandomAccessFile("buffer.txt", "rw");

        //获取文件通道
        FileChannel channel = file.getChannel();
        //将缓冲区的数据通过通道写入文件
        channel.write(allocate);


        //反转缓冲区为写
        allocate.flip();
        //清空缓冲区
        allocate.clear();


        //通过通道将文件中的数据读入缓冲区
        channel.read(allocate);
        StringBuffer content = new StringBuffer();

        //循环获取缓冲区中的数据
        while (allocate.hasRemaining()){
            content.append((char)allocate.get());
        }
        System.out.println(content);
        //关闭通道
        channel.close();

    }




}
