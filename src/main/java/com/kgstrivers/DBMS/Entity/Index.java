package com.kgstrivers.DBMS.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Index {
    private String columnName;
    Map<Object, List<Row>> indexMap = new HashMap<>();

    public Index(String columnName) {
        this.columnName = columnName;
    }

    // Add a row to the index
    public void addToIndex(Row row) {
        Object key = row.getValue(columnName);
        indexMap.computeIfAbsent(key, k -> new ArrayList<>()).add(row);
    }

    // Remove a row from the index
    public void removeFromIndex(Row row) {
        Object key = row.getValue(columnName);
        if (indexMap.containsKey(key)) {
            indexMap.get(key).remove(row);
            if (indexMap.get(key).isEmpty()) {
                indexMap.remove(key);
            }
        }
    }

    public List<Row> search(Object value) {
        return indexMap.getOrDefault(value, new ArrayList<>());
    }

    public String getColumnName() {
        return columnName;
    }
}

