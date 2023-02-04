package com.dnslog.web;

import com.dnslog.web.controller.WebController;
import com.dnslog.web.entity.UDPInfo;
import com.dnslog.web.mapper.UDPMapper;
import com.dnslog.web.utils.UDPSend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.text.SimpleDateFormat;

@SpringBootTest
//@ServletComponentScan
class FiDnsLogApplicationTests {
    @Autowired
    private UDPMapper udpMapper;
    @Autowired
    private UDPInfo udpInfo;
    @Autowired
    private WebController webController;
    @Test
    void contextLoads() {
    }
    @Test
    void sendUDP(){
        int port=8056;
        String ip="127.0.0.1";
        String content="hello UDP!";
        UDPSend udpSend=new UDPSend();
        byte[] bt=content.getBytes();
        udpSend.send(ip,port,bt);
   }
   @Test
    void insert(){
        //udpInfo.setId(1);
        Long date=System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=formatter.format(date);
        udpInfo.setIp("127.0.0.12");
        udpInfo.setContent("123456");
        udpInfo.setDate(time);
        udpMapper.insert(udpInfo);
   }
   @Test
    void select(){
        webController.queryDNSLog();
   }
}
