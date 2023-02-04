package com.dnslog.web.mapper;

import com.dnslog.web.entity.UDPInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UDPMapper{

    int insert(UDPInfo udpInfo);
    List<UDPInfo> select();
    int delete(int id);
    int clear();
}
