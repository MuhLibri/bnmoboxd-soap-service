package com.bnmoboxd.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Log {
    private int id;
    private String description;
    private String endpoint;
    private String clientIp;
    private String requestTimestamp;
    private String soapRequest;
}