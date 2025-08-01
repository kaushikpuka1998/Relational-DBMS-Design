package com.kgstrivers.DBMS;

import com.kgstrivers.DBMS.Entity.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class DbmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbmsApplication.class, args);
		DatabaseSystem dbSystem = new DatabaseSystem();

		// 1️⃣ Test: Create Database
		dbSystem.createDatabase("TestDB");
		Database db = dbSystem.getDatabase("TestDB");
		System.out.println("✅ Database Created: " + db.name);

		// 2️⃣ Test: Create Table
		Schema userSchema = new Schema();
		Column c1 = new Column("id", DataType.INTEGER, true, false, true);
		Column c2 = new Column("name", DataType.STRING, false, false, false);
		userSchema.setColumns(Arrays.asList(
               c1,c2
        ));

		db.createTable("Users", userSchema);
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
	}

}
