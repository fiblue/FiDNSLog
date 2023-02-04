package com.dnslog.web.utils;

import com.dnslog.web.entity.UDPInfo;
import com.dnslog.web.mapper.UDPMapper;
import com.dnslog.web.service.UDPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
@Slf4j
@Component
public class UDPListener implements ApplicationRunner {

    private static int udpPort = 53;
    private static int maxUdpDataSize = 16;

    private void Listener(int port) throws SocketException {
        log.info("===========UDPListener Start> port:"+port+ "===========");
        DatagramSocket socket = new DatagramSocket(port);
        while (true) {
            byte[] buffer = new byte[maxUdpDataSize];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                buffer = packet.getData();
                InetAddress address = packet.getAddress();
                String ip = address.getHostAddress();
                int targetPort = packet.getPort();
                String content=DatatypeConverter.printHexBinary(buffer);
                if(!ip.equals("127.0.0.1")){
                    DNSLogRecorder.setDNSLog(ip);
                }
                log.info("receive new UDPMessage>"+ " ip:"+ip+" port:"+targetPort+" content:"+content);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
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
