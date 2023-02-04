package com.dnslog.web.entity;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Component
public class UDPInfo {
    private int id;
    private String ip;
    private String date;
    private String content;
}
