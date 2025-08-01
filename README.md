# Relational-DBMS-Design

🗄️ Relational Database Management System (RDBMS) - Low-Level Design
A simplified, in-memory Relational Database Management System (RDBMS) implemented in Java, focusing on Low-Level Design (LLD) concepts.
This project demonstrates how relational databases work under the hood, covering schema design, CRUD operations, indexing, transactions, and concurrency handling.

📌 Features
✅ Database & Table Management – Create and manage multiple databases and tables.

✅ Schema Definition – Define columns with data types and constraints (Primary Key, Unique, Not Null).

✅ CRUD Operations – Insert, Select, Update, Delete rows from tables.

✅ Index Support – Basic indexing for faster lookups.

✅ Transaction Handling – Commit and Rollback operations.

✅ Concurrency Handling – Row-level locking using ReentrantReadWriteLock for safe multi-threaded operations.

✅ In-Memory Storage – Data stored in memory (can be extended to persistent storage).

✅ Test Cases included in Main class for quick execution.

🏗️ System Design (LLD)
1️⃣ Core Components
```
DatabaseSystem – Manages multiple databases.

Database – Contains multiple tables.

Table – Handles rows, schema, indexes, and CRUD operations.

Schema & Column – Defines table structure and constraints.

Row – Represents a record in the table.

Index – Maintains a mapping for faster reads.

TransactionManager – Supports commit and rollback.

LockManager – Ensures safe concurrent read/write operations.
```


```
DatabaseSystem
     |
     ├── Database
     |       |
     |       ├── Table
     |       |      ├── Schema
     |       |      ├── Row
     |       |      └── Index
     |
     ├── TransactionManager
     └── LockManager
```



```
Client → DatabaseSystem → Database → Table → Row
    |         |             |         |
    | createDatabase        |         |
    | createTable           |         |
    | insert(row)           | ------> Validate schema
    |                       | ------> Acquire write lock
    |                       | ------> Store row


```
Output:
```
✅ Database Created: TestDB
✅ Table Created: Users
✅ Data Inserted: [Row{values={name=Alice, id=1}}, Row{values={name=Bob, id=2}}]
```
