package com.dnslog.web.utils;


import com.dnslog.web.entity.UDPInfo;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DNSLogRecorder {
    private static LinkedHashMap<String,String> linkedHashMap=new LinkedHashMap<>();
    public static void setDNSLog(String ip){
        Long time=System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=formatter.format(time);
        if (!linkedHashMap.containsKey(ip)&&linkedHashMap.size()>=10){
            String oldIP=linkedHashMap.entrySet().stream().findFirst().get().getKey();
            linkedHashMap.remove(oldIP);
        }
        else if (linkedHashMap.containsKey(ip)) {
            //如果不删除而直接更新则无法影响先后顺序
            linkedHashMap.remove(ip);
        }
        if(!ip.equals("127.0.0.1")){
            linkedHashMap.put(ip,date);
        }

    }
    public static List<UDPInfo> getDNSLog(){
        int index=10;
        List<UDPInfo> list=new ArrayList<>();
        for (Map.Entry<String,String> entry:linkedHashMap.entrySet()){
            UDPInfo udpInfo=new UDPInfo();
            String ip=entry.getKey();
            String date=entry.getValue();
            udpInfo.setId(index);
            udpInfo.setIp(ip);
            udpInfo.setDate(date);
            list.add(udpInfo);
            index--;
        }
        list=list.stream().sorted(Comparator.comparing(UDPInfo::getId)).collect(Collectors.toList());
        return list;
    }
}
