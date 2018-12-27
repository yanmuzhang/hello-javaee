package com.hello.javaee;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author: zhangchao
 * @time: 2018-12-27 10:32
 **/
public class CopyFile {



    public void copyFile(String sourcePath,String targetPath) throws IOException {
        RandomAccessFile file = new RandomAccessFile(sourcePath,"rw");
        FileChannel channel = file.getChannel();

        File targetFile = new File(targetPath);
        System.out.println(targetFile.getAbsolutePath());
        RandomAccessFile file1 = new RandomAccessFile(targetFile, "rw");
        FileChannel channel1 = file1.getChannel();
        channel.transferTo(0,channel.size(),channel1);
    }


    public static void main(String[] args) throws IOException {
        new CopyFile().copyFile("D:\\apache-activemq-5.15.5-bin.zip","apache-activemq-5.15.5-bin.zip");
    }





}
