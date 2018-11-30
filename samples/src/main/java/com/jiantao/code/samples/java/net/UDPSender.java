package com.jiantao.code.samples.java.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UDPSender {

    private static final int RECVER_LISTENING_PORT = 9999;

    public static void main(String[] args) throws Exception {
        List<DatagramPacket> messages = new ArrayList<>(12);
        for (int i = 0; i < 4; i++) {
            String msgBody = (i == 3 ? "" : "Hello UDP- 发动机考虑过" + i);

            DatagramPacket msg0 = parseMsg(
                    msgBody, "127.0.0.1", RECVER_LISTENING_PORT); // 发送给本机
            DatagramPacket msg1 = parseMsg(
                    msgBody, "192.168.137.177", 60886); // 发送给同一局域网的一台机器
//            DatagramPacket msg2 = parseMsg(
//                    msgBody, "120.77.**.***", RECVER_LISTENING_PORT); // 120.77.**.*** 是我阿里云主机的公网 IP 地址

            // JDK1.5 时 Collections 添加的 addAll 方法，可以一次往某个集合中添加多个元素
            Collections.addAll(messages, msg0, msg1);
        }

        startSending(messages);
    }

    private static void startSending(List<DatagramPacket> messages)
            throws IOException, InterruptedException {

        // 无参构造的 DatagramSocket 会随机选择一个端口进行监听
        // 因为此时 DatagramSocket 的作用是发送，所以无需显式指定固定端口
        try (DatagramSocket socket = new DatagramSocket()) {
            System.out.println("随机给发送端分配的端口为：" + socket.getLocalPort() + "\n");
            
            for (DatagramPacket msg : messages) {
                socket.send(msg); // 发送数据包

                int recverPort = msg.getPort();
                InetAddress recverAddr = msg.getAddress();
                System.out.printf("消息已经发送 -> (%s:%d)\n",
                        recverAddr.getHostAddress(), recverPort);

                Thread.sleep(500); // 设定 每隔 0.5 秒发送一个消息
            }
        }
    }

    private static DatagramPacket parseMsg(String msgBody, String addr, int port)
            throws UnknownHostException {

        byte[] msgData = msgBody.getBytes();
        byte[] packet = new byte[msgBody.getBytes().length+2];
        // 消息类型
        packet[0] = 0x01;
        packet[1] = (byte) msgBody.length();
        System.arraycopy(msgData,0, packet, 2, msgData.length);
        DatagramPacket msg = new DatagramPacket(
                packet, 0, packet.length, // 数据从位置 0 开始，长度为 msgData.length
                InetAddress.getByName(addr), port); // 目的地 地址为 addr，监听端口为 port

        return msg;
    }

}