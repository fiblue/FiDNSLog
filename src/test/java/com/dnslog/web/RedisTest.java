package com.dnslog.web;

import com.dnslog.web.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {
    @Autowired
    RedisUtils redisUtils;
    @Test
    public void test01(){
        redisUtils.set("1","root");
        redisUtils.setIncr("11",2);
    }
}
