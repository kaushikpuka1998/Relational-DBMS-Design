package com.kgstrivers.DBMS.Entity;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;

public class TableOptimistic{
    List<VersionRow> rows;

    public void updateRow(int rowId, Map<String, Object> newData, long expectedVersion) {
        VersionRow row = rows.get(rowId);
        synchronized (row) {
            if (row.version != expectedVersion) {
                throw new ConcurrentModificationException("Row modified by another transaction");
            }
            row.update(newData);
            row.version++;
        }
    }
}
