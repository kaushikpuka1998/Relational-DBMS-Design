package com.kgstrivers.DBMS.Entity;

import java.util.*;
import java.util.function.Predicate;

public class Table {
    public String name;
    Schema schema;
    List<Row> rows = new ArrayList<>();
    private final Map<String, Index> indexes = new HashMap<>();

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
            indexes.values().forEach(idx -> idx.addToIndex(row));
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
        Iterator<Row> it = rows.iterator();
        while (it.hasNext()) {
            Row r = it.next();
            if (condition.test(r)) {
                indexes.values().forEach(idx -> idx.removeFromIndex(r));
                it.remove();
            }
        }
    }

    public void update(Predicate<Row> condition, Map<String, Object> updates) {
        for (Row row : rows) {
            if (condition.test(row)) {
                row.update(updates);
            }
        }
    }

    public void createIndex(String columnName) {
        Index index = new Index(columnName);
        indexes.put(columnName, index);

        // Build index for existing rows
        for (Row row : rows) {
            index.addToIndex(row);
        }
    }

    public List<Row> searchByIndex(String columnName, Object value) {
        Index idx = indexes.get(columnName);
        if (idx != null) {
            return idx.search(value);
        }
        return new ArrayList<>();
    }
}

