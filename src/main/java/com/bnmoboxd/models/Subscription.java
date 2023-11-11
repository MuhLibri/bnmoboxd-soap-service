package com.bnmoboxd.models;

import com.bnmoboxd.struct.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class Subscription {
    @XmlElement(name = "curatorUsername")
    private String curatorUsername;
    @XmlElement(name = "subscriberUsername")
    private String subscriberUsername;
    @XmlElement(name = "status")
    private SubscriptionStatus status;
}
