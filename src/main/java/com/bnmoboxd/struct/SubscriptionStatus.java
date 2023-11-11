package com.bnmoboxd.struct;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum SubscriptionStatus {
    PENDING, ACCEPTED, REJECTED;
}
