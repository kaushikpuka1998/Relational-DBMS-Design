package com.kgstrivers.DBMS.Entity;

import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class Schema {
    List<Column> columns;
}
