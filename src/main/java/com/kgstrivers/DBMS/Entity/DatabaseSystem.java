package com.kgstrivers.DBMS.Entity;

import java.util.HashMap;
import java.util.Map;

public class DatabaseSystem {
    Map<String, Database> databases = new HashMap<>();

    public void createDatabase(String name) {
        databases.putIfAbsent(name, new Database(name));
    }

    public void dropDatabase(String name) {
        databases.remove(name);
    }

    public Database getDatabase(String name) {
        return databases.get(name);
    }
}
