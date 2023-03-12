package com.dnslog.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.xbill.DNS.*;
import java.io.IOException;
import java.net.*;

@Slf4j
@Component
public class UDPListener implements ApplicationRunner {

    private static int udpPort = 53;
    private static int maxUdpDataSize = 1024;
    static DatagramSocket socket;

    static {
        try {
            socket = new DatagramSocket(udpPort);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public UDPListener() throws SocketException {
    }

    private void Listener(int port) throws SocketException {
        log.info("===========UDPListener Start> port:"+port+ "===========");
        String a="";
        while (true) {
            byte[] buffer = new byte[maxUdpDataSize];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                buffer = packet.getData();
                Message message=new Message(buffer);
                Record record=message.getQuestion();
                String name=record.getName().toString();
                InetAddress address = packet.getAddress();
                String ip = address.getHostAddress();
                int targetPort = packet.getPort();
                //String content=DatatypeConverter.printHexBinary(buffer);
                if(!ip.equals("127.0.0.1")&&!ip.isEmpty()&&ip!=null){
                    DNSLogRecorder.setDNSLog(ip,name.substring(0,name.length()-1).toLowerCase());
                }
                answer(message,address,targetPort);
                log.info("receive new UDPMessage>"+ " ip:"+ip+" port:"+targetPort+" name:"+name);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
    private static void answer(Message message,InetAddress sourceAddress,int sourcePort) throws IOException {
        Record record=message.getQuestion();
        String addressIP="127.0.0.1";
        InetAddress answerIpAddr = Address.getByAddress(addressIP);//域名
        answerIpAddr.getCanonicalHostName();
        Message answerMessage =message.clone();
        Record answer = new ARecord(record.getName(), record.getDClass(), 64, answerIpAddr);
        answerMessage.addRecord(answer, Section.ANSWER);
        byte[] buff = answerMessage.toWire();
        DatagramPacket response = new DatagramPacket(buff, buff.length, sourceAddress, sourcePort);
        socket.send(response);
        log.info("answer>"+"Address:"+sourceAddress.getAddress().toString()+","+"HostAddress:"+answerIpAddr.getHostAddress()+","+"HostName:"+answerIpAddr.getHostName());
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            log.info("========================线程启动==============================");
            Listener(udpPort);
        }
        catch (SocketException e){
            log.error(e.getMessage());
        }
    }
}
