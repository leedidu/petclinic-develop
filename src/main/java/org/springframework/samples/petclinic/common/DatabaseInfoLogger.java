package org.springframework.samples.petclinic.common;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Component
public class DatabaseInfoLogger {

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	public void logDatabaseInfo() {
		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData metaData = connection.getMetaData();
			System.out.println("Connected to database: " + metaData.getURL());
			System.out.println("Database username: " + metaData.getUserName());
			System.out.println("Database product name: " + metaData.getDatabaseProductName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

