package com.kgstrivers.DBMS.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Table {
    public String name;
    Schema schema;
    List<Row> rows = new ArrayList<>();
    List<Index> indexes = new ArrayList<>();

    public Table(String name, Schema schema) {
        this.name = name;
        this.schema = schema;
    }

    public void insert(Row row) {
        // Validate against schema constraints
        rows.add(row);
    }

    public List<Row> select(Predicate<Row> condition) {
        return rows.stream().filter(condition).toList();
    }

    public void delete(Predicate<Row> condition) {
        rows.removeIf(condition);
    }

    public void update(Predicate<Row> condition, Map<String, Object> updates) {
        for (Row row : rows) {
            if (condition.test(row)) {
                row.update(updates);
            }
        }
    }


}

