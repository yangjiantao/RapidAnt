package com.jiantao.code.samples.java.net;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.channels.DatagramChannel;

public class UDPReceiver {

    private static final int LISTENING_PORT = 9999;
    private static final int BUFFER_SIZE = 512;
    private static int count;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args) throws Exception {
        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket msg = new DatagramPacket(buffer, buffer.length);

        try (DatagramSocket socket = new DatagramSocket(LISTENING_PORT)) {
            System.out.println("接收端已经启动...\n");
            while (true) {
                socket.receive(msg); // 接收数据包

                byte type = msg.getData()[0];
                byte len = msg.getData()[1];
                String msgBody = new String(
                        msg.getData(), 2, msg.getLength() - 2);
                if (msgBody.isEmpty()) { // 发现接收的消息是空字符串("")便跳出循环
                    break;
                }

                int senderPort = msg.getPort();
                InetAddress senderAddr = msg.getAddress();

                System.out.printf("发送端 地址和端口 -> (%s:%d)\n",
                        senderAddr.getHostAddress(), senderPort);

                System.out.println("发送端 发送的消息 type = " + type + "; len = " + len + " -> " + msgBody + "\n");

                // response

                int senderListeningPort = Integer.valueOf(msgBody);

                byte[] msgData = "jiantaombp-response-data".getBytes();
                byte[] packet = new byte[msgData.length + 2];
                // 消息类型
                if (count == 3) {
                    packet[0] = 0x02;
                } else {
                    packet[0] = 0x01;
                }
                packet[1] = (byte) msgData.length;
                System.arraycopy(msgData, 0, packet, 2, msgData.length);
                DatagramPacket outPacket = new DatagramPacket(
                        packet, 0, packet.length, // 数据从位置 0 开始，长度为 msgData.length
                        InetAddress.getByName(senderAddr.getHostAddress()), senderListeningPort); // 目的地 地址为 addr，监听端口为 port
                socket.send(outPacket);
                int recverPort = outPacket.getPort();
                InetAddress recverAddr = outPacket.getAddress();
                System.out.printf("消息已经发送 -> (%s:%d)\n",
                        recverAddr.getHostAddress(), recverPort);
                count++;

            }
        }

        System.out.println("接收端已经关闭。");
    }
}