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
    LockManager lockManager;

    public Table(String name, Schema schema,LockManager lockManager) {
        this.name = name;
        this.schema = schema;
        this.lockManager = lockManager;
    }

    public void insert(Row row) {
        lockManager.acquireWriteLock(name);
        try {
            rows.add(row);
        } finally {
            lockManager.releaseWriteLock(name);
        }
    }

    public List<Row> select(Predicate<Row> condition) {
        lockManager.acquireReadLock(name);
        try {
            return rows.stream().filter(condition).toList();
        } finally {
            lockManager.releaseReadLock(name);
        }
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

