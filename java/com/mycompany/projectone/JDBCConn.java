package com.mycompany.projectone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCConn {
//fields used to get connection to DataBase
	static final private String DBDriver = "com.mysql.jdbc.Driver";
	static final private String DBConnection = "jdbc:mysql://localhost:3306/mydb";
	static final private String DBUser = "testUser";
	static final private String DBPassword = "280588";

	//connecting to DB
	public static Connection getDBConnection() {
		Connection conn = null;

		try {
			Class.forName(DBDriver);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			conn = DriverManager.getConnection(DBConnection, DBUser, DBPassword);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());
		}
		return conn;

	}
//Method inserting values to DB (values are passed from FXML textfields)
	public void insertToDB(String inputOne, int inputTwo) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO tasklist" + "(Action, TimeAction, CreatedTime)" + "VALUES" + "(?,?, SYSDATE())";

		try {
			conn = getDBConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, inputOne);
			ps.setInt(2, inputTwo);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());
		} finally {

			if (conn != null) {
				conn.close();
			}
			if (ps != null) {
				ps.close();
			}
		}

	}
//this method is filling listview field in FXML with records from DB
	public List<String> showFromDB() throws SQLException {

		Connection conn = null;
		Statement stat = null;
		String sql = "select*from tasklist";
		String result = "";
		List<String> resultList = new ArrayList<>();
		try {
			conn = getDBConnection();
			stat = conn.createStatement();

			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				String act = rs.getString("Action");
				int time = rs.getInt("TimeAction");
				String timestamp = rs.getString("CreatedTime");
				result = (act + " for " + time + " hours (at: "+timestamp+")");
				resultList.add(result);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());
		} finally {

			if (conn != null) {
				conn.close();
			}
			if (stat != null) {
				stat.close();
			}
		}
		return resultList;
	}
	
	//method deleting selected record from DB
	public void deletefromDB(int idTaskList) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "DELETE from tasklist where idTaskList=?";

		try {
			conn = getDBConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idTaskList);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());
		} finally {

			if (conn != null) {
				conn.close();
			}
			if (ps != null) {
				ps.close();
			}
		}

	}
}
