package com.bnmoboxd.models;

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
    @XmlElement(name = "curator_id")
    private int curatorId;
    @XmlElement(name = "subscriber_id")
    private int subscriberId;

    // TODO: Switch to enum?
    @XmlElement(name = "status")
    private String status; // PENDING, ACCEPTED, REJECTED
}
