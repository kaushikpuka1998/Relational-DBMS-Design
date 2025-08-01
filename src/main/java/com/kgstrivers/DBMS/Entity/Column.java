package com.kgstrivers.DBMS.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Column {
    String name;
    DataType type;
    boolean isPrimaryKey;
    boolean isNullable;
    boolean isUnique;


}
