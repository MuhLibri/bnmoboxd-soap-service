package com.bnmoboxd.struct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@AllArgsConstructor
public class Pagination {
    private int page;
    private int take;
}
