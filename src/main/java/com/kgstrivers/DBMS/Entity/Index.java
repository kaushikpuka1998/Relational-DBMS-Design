package com.kgstrivers.DBMS.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Index {
    String columnName;
    Map<Object, List<Row>> indexMap = new HashMap<>();

    public void addToIndex(Row row) {
        Object key = row.getValue(columnName);
        indexMap.computeIfAbsent(key, k -> new ArrayList<>()).add(row);
    }
}

