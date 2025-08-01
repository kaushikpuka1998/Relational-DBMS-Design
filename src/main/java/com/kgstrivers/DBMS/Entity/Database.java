package com.kgstrivers.DBMS.Entity;

import java.util.HashMap;
import java.util.Map;

public class Database {
    public String name;
    Map<String, Table> tables = new HashMap<>();

    public Database(String name) {
        this.name = name;
    }

    public void createTable(String tableName, Schema schema, LockManager lockManager) {
        tables.putIfAbsent(tableName, new Table(tableName, schema,lockManager));
    }

    public Table getTable(String name) {
        return tables.get(name);
    }
}

