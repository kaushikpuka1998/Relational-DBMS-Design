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
		SpringApplication.run(DbmsApplication.class, args);
		DatabaseSystem dbSystem = new DatabaseSystem();
		LockManager lockManager = new LockManager();

		// 1️⃣ Test: Create Database
		dbSystem.createDatabase("TestDB");
		Database db = dbSystem.getDatabase("TestDB");
		System.out.println("✅ Database Created: " + db.name);

		// 2️⃣ Test: Create Table
		Schema userSchema = new Schema();
		userSchema.setColumns(Arrays.asList(
                new Column("id", DataType.INTEGER, true, false, true),
                new Column("name", DataType.STRING, false, false, false)
        ));

		db.createTable("Users", userSchema, lockManager);
		Table usersTable = db.getTable("Users");
		System.out.println("✅ Table Created: " + usersTable.name);

		// 3️⃣ Test: Insert and Select
		Row row1 = new Row();
		row1.values.put("id", 1);
		row1.values.put("name", "Alice");
		usersTable.insert(row1);

		Row row2 = new Row();
		row2.values.put("id", 2);
		row2.values.put("name", "Bob");
		usersTable.insert(row2);

		System.out.println("✅ Data Inserted: " + usersTable.select(r -> true));

		// 4️⃣ Test: Concurrent Inserts
		System.out.println("\n--- Concurrent Insert Test ---");
		ExecutorService executor = Executors.newFixedThreadPool(2);

		Runnable task1 = () -> {
			Row r = new Row();
			r.values.put("id", 3);
			r.values.put("name", "Charlie");
			usersTable.insert(r);
			System.out.println("Thread1 inserted Charlie");
		};

		Runnable task2 = () -> {
			Row r = new Row();
			r.values.put("id", 4);
			r.values.put("name", "David");
			usersTable.insert(r);
			System.out.println("Thread2 inserted David");
		};

		executor.submit(task1);
		executor.submit(task2);
		executor.shutdown();
		executor.awaitTermination(2, TimeUnit.SECONDS);

		System.out.println("✅ Final Data after concurrent insert: " + usersTable.select(r -> true));

		// 5️⃣ Test: Concurrent Read and Write
		System.out.println("\n--- Concurrent Read/Write Test ---");
		ExecutorService executor2 = Executors.newFixedThreadPool(2);

		Runnable writer = () -> {
			Row r = new Row();
			r.values.put("id", 5);
			r.values.put("name", "Eve");
			usersTable.insert(r);
			System.out.println("Writer inserted Eve");
		};

		Runnable reader = () -> {
			List<Row> data = usersTable.select(r -> true);
			System.out.println("Reader fetched: " + data);
		};

		executor2.submit(writer);
		executor2.submit(reader);
		executor2.shutdown();
		executor2.awaitTermination(2, TimeUnit.SECONDS);

		System.out.println("✅ Final Data after Read/Write: " + usersTable.select(r -> true));
	}

}
