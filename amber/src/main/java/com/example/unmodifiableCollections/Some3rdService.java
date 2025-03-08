package com.example.unmodifiableCollections;

import java.util.ArrayList;
import java.util.List;

public class Some3rdService {

    // return modifiable list by default. Do not change.
    public List<Integer> getList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        return list;
    }
}
