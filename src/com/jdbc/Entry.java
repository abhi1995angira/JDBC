package com.jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Entry {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		Properties props = new Properties();
		FileInputStream fin = new FileInputStream("dbDetails.properties");
		props.load(fin);

		// LOAD AND REG DRIVER
		String driver = props.getProperty("jdbc.driver");
		Class.forName(driver);

		// GET DB CONN USING 'JDBC URL'
		String url = props.getProperty("jdbc.url");
		Connection dbCon;

		dbCon = DriverManager.getConnection(url);
		System.out.println("Connection successful?: " + (dbCon != null));
		
		Statement s = null;
		try{
			s = dbCon.createStatement();
			String insertQuery = props.getProperty("jdbc.query.insert");
			int rows;
			rows = s.executeUpdate(insertQuery);
			
			System.out.println(rows + "record is(are) added successfully");
		} finally {
			if(s != null)
				s.close();
		}
		
		try(Statement selectStatement = dbCon.createStatement()){
			String selectQuery = props.getProperty("jdbc.query.select");
			ResultSet result;
			result = selectStatement.executeQuery(selectQuery);
			
			while(result.next()){
				System.out.println(result.getString(1));
			}
		}
	}
}
