package com.bnmoboxd.struct;

import com.bnmoboxd.models.Subscription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class SubscriptionPage {
    @XmlElement(name = "subscriptions")
    private List<Subscription> subscriptions;

    @XmlElement(name = "count")
    public int getCount() {
        return subscriptions.size();
    }
}
