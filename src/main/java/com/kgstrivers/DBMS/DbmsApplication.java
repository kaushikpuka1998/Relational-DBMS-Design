package com.kgstrivers.DBMS;

import com.kgstrivers.DBMS.Entity.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DbmsApplication {

	public static void main(String[] args) throws InterruptedException {

		DatabaseSystem dbSystem = new DatabaseSystem();
		LockManager lockManager = new LockManager();

		// 1️⃣ Create Database
		dbSystem.createDatabase("TestDB");
		Database db = dbSystem.getDatabase("TestDB");

		// 2️⃣ Create Table
		Schema userSchema = new Schema();
		userSchema.setColumns(Arrays.asList(
                new Column("id", DataType.INTEGER, true, false, true),
                new Column("name", DataType.STRING, false, false, false)
        ));

		db.createTable("Users", userSchema, lockManager);
		Table usersTable = db.getTable("Users");

		// 3️⃣ Insert Data
		insertUser(usersTable, 1, "Alice");
		insertUser(usersTable, 2, "Bob");
		insertUser(usersTable, 3, "Charlie");
		insertUser(usersTable, 4, "David");
		insertUser(usersTable, 5, "Eve");

		System.out.println("✅ Initial Data: " + usersTable.select(r -> true));

		// 4️⃣ Single Delete Test
		System.out.println("\n--- Single Delete Test ---");
		usersTable.delete(r -> (int) r.getValue("id") == 3);
		System.out.println("✅ Data after deleting Charlie: " + usersTable.select(r -> true));

		// 5️⃣ Delete Non-existing Record
		usersTable.delete(r -> (int) r.getValue("id") == 99);
		System.out.println("✅ Data after trying to delete non-existing record: " + usersTable.select(r -> true));

		// 6️⃣ Concurrent Delete Test
		System.out.println("\n--- Concurrent Delete Test ---");
		ExecutorService executor = Executors.newFixedThreadPool(2);

		Runnable deleteTask1 = () -> {
			usersTable.delete(r -> (int) r.getValue("id") == 2);
			System.out.println("Thread1 deleted Bob");
		};

		Runnable deleteTask2 = () -> {
			usersTable.delete(r -> (int) r.getValue("id") == 4);
			System.out.println("Thread2 deleted David");
		};

		executor.submit(deleteTask1);
		executor.submit(deleteTask2);
		executor.shutdown();
		executor.awaitTermination(2, TimeUnit.SECONDS);

		System.out.println("✅ Final Data after concurrent deletes: " + usersTable.select(r -> true));

		usersTable.createIndex("name");
		System.out.println("Search via index: " + usersTable.searchByIndex("name", "Alice"));

	}

	private static void insertUser(Table table, int id, String name) {
		Row row = new Row();
		row.values.put("id", id);
		row.values.put("name", name);
		table.insert(row);
	}

}
