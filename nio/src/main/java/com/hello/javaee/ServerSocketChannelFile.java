package com.hello.javaee;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: zhangchao
 * @time: 2018-12-27 11:19
 **/
public class ServerSocketChannelFile {


    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);
        final Selector selector = Selector.open();
        serverSocketChannel.register(selector,   SelectionKey.OP_ACCEPT  );
        while(true) {
            int readyChannels = selector.select();
            if(readyChannels == 0) continue;
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();
                if(key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                    System.out.println("isAcceptable");
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.
                    System.out.println("isConnectable");
                } else if (key.isReadable()) {
                    // a channel is ready for reading
                    System.out.println("isReadable");
                    key.interestOps(SelectionKey.OP_WRITE);
                } else if (key.isWritable()) {
                    // a channel is ready for writing
                    System.out.println("isWritable");
                }
            }
        }


//
//        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
//        while (true){
//            SocketChannel accept = serverSocketChannel.accept();
//            if(accept != null){
//                StringBuffer stringBuffer = new StringBuffer();
//                while (accept.read(byteBuffer) != -1){
//                    byteBuffer.flip();
//                    while (byteBuffer.hasRemaining()){
//                        stringBuffer.append( (char)byteBuffer.get());
//                    }
//                    byteBuffer.clear();
//                }
//                System.out.println(stringBuffer);
//            }
//        }
    }


}
