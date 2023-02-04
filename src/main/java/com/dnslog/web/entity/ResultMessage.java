package com.dnslog.web.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ResultMessage {
    private String code;
    private String message;
    private Object data;
}
