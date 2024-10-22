package com.example.apptools.utils.soul.util;

import com.example.apptools.utils.XThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class XSocket {

    public static Socket getSoulSocket(String ip, Integer port) {
        Socket socket = new Socket();
       new Thread(() -> {
            try {
                socket.connect(new InetSocketAddress(ip, port), 10000);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        return socket;
    }

}
