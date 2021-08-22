package com.example.demo.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DAO {
	Connection con;
	public DAO() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/mobileshop";
		String username="root";
		String password="b17dccn160";
		con = DriverManager.getConnection(url,username,password);
	}
}