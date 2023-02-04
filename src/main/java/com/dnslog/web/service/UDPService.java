package com.dnslog.web.service;

import com.dnslog.web.entity.UDPInfo;
import com.dnslog.web.mapper.UDPMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UDPService {
    @Autowired
    private UDPInfo udpInfo;
    @Autowired
    private UDPMapper udpMapper;
    public void insertUDPInfo(String ip,String content){
        Long time=System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=formatter.format(time);
        udpInfo.setIp(ip);
        udpInfo.setContent(content);
        udpInfo.setDate(date);
        udpMapper.insert(udpInfo);
    }
    public List<UDPInfo> selectUDPInfo(){
        List<UDPInfo> udpInfos=udpMapper.select();
        return udpInfos;
    }
}
