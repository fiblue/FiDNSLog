package com.dnslog.web.controller;

import com.alibaba.fastjson.JSON;
import com.dnslog.web.entity.ResultMessage;
import com.dnslog.web.entity.UDPInfo;
import com.dnslog.web.utils.DNSLogRecorder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class WebController {

    @Autowired
    private UDPInfo udpInfo;
    @Autowired
    private ResultMessage resultMessage;
    @RequestMapping("/")
    public String hello(){
        return "redirect:/index.html";
    }
    @ResponseBody
    @RequestMapping("/queryDNSLog")
    public String queryDNSLog(){
       // List<UDPInfo> udpInfos=udpService.selectUDPInfo();
        List<UDPInfo> udpInfos= DNSLogRecorder.getDNSLog();
        String json=null;
        if (!udpInfos.isEmpty()){
            resultMessage.setCode("200");
            resultMessage.setMessage("success!");
            resultMessage.setData(udpInfos);
        }
        else {
          resultMessage.setCode("400");
          resultMessage.setMessage("data is null!");
          log.info("data is null");
        }
        json= JSON.toJSONString(resultMessage);
        return json;
    }
}
