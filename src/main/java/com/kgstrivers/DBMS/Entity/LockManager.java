package com.kgstrivers.DBMS.Entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockManager {
    private final Map<String, ReentrantReadWriteLock> tableLocks = new ConcurrentHashMap<>();

    public void acquireReadLock(String tableName) {
        tableLocks.computeIfAbsent(tableName, k -> new ReentrantReadWriteLock())
                .readLock().lock();
    }

    public void releaseReadLock(String tableName) {
        tableLocks.get(tableName).readLock().unlock();
    }

    public void acquireWriteLock(String tableName) {
        tableLocks.computeIfAbsent(tableName, k -> new ReentrantReadWriteLock())
                .writeLock().lock();
    }

    public void releaseWriteLock(String tableName) {
        tableLocks.get(tableName).writeLock().unlock();
    }
}
