package com.dnslog.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@MapperScan(basePackages = "com.dnslog.web.mapper")
@ServletComponentScan
public class FiDnsLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiDnsLogApplication.class, args);
    }

}
