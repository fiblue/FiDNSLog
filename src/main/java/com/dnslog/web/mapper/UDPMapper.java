package com.dnslog.web.mapper;

import com.dnslog.web.entity.UDPInfo;

import java.util.List;

//@Mapper
public interface UDPMapper{

    int insert(UDPInfo udpInfo);
    List<UDPInfo> select();
    int delete(int id);
    int clear();
}
