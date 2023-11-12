package com.bnmoboxd.struct;

import javax.xml.bind.annotation.XmlEnum;

// TODO: Either use or remove ResponseStatus
@XmlEnum
public enum ResponseStatus {
    SUCCESS,
    INVALID_API_KEY
}
