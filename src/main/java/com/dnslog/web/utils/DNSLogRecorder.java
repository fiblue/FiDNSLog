package com.dnslog.web.utils;


import com.dnslog.web.entity.UDPInfo;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DNSLogRecorder {
    private static LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
    private static List<UDPInfo> list=new ArrayList<>();

    public static void setDNSLog(String ip,String domain) {
        UDPInfo udpInfo=new UDPInfo();
        Long time = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = formatter.format(time);
        udpInfo.setIp(ip);
        udpInfo.setDomain(domain);
        udpInfo.setDate(date);
        if(list.size()>=10){
            list.remove(9);
        }
        list.add(0,udpInfo);
    }

    public static List<UDPInfo> getDNSLog() {
        list = list.stream().collect(Collectors.toList());
        return list;
    }
}
