package com.bnmoboxd.struct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "response")
public class ApiResponse {
    @XmlElement(name = "status")
    private ResponseStatus status;
    @XmlAnyElement(lax = true)
    private Object result;
}