package com.bnmoboxd.struct;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pair<T1, T2> {
    private T1 first;
    private T2 second;
}
