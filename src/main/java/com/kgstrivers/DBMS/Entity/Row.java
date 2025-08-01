package com.kgstrivers.DBMS.Entity;

import java.util.HashMap;
import java.util.Map;

public class Row {
    public Map<String, Object> values = new HashMap<>();

    public Object getValue(String columnName) {
        return values.get(columnName);
    }

    public void update(Map<String, Object> updates) {
        values.putAll(updates);
    }

    @Override
    public String toString() {
        return "Row{" +
                "values=" + values +
                '}';
    }
}

