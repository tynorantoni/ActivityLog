package com.mycompany.projectone.Test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mycompany.projectone.JDBCConn;

public class JunitTest {

	static final private String DBDriver = "com.mysql.jdbc.Driver";
	static final private String DBConnection = "jdbc:mysql://localhost:3306/mydb";
	static final private String DBUser = "testUser";
	static final private String DBPassword = "280588";

	@Test
	public void getDBConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		Class.forName(DBDriver);
		try {
			assertEquals("dbDriver", "com.mysql.jdbc.Driver", DBDriver);
			assertEquals("dbConnection", "jdbc:mysql://localhost:3306/mydb", DBConnection);
			assertEquals("dbUser", "testUser", DBUser);
			assertEquals("dbPassword", "280588", DBPassword);

			conn = DriverManager.getConnection(DBConnection, DBUser, DBPassword);
			assertNotNull(conn);
		} catch (SQLException e) {

			fail(e.toString());
		}

	}

	@Test
	public void insertToDB() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		assertNull(conn);
		assertNull(ps);
		String inputOne = "testerJunit";
		int inputTwo = 9;
		String sql = "INSERT INTO tasklist" + "(Action, TimeAction, CreatedTime)" + "VALUES" + "(?,?, SYSDATE())";
		assertEquals(sql, sql);
		try {
			conn = DriverManager.getConnection(DBConnection, DBUser, DBPassword);
			ps = conn.prepareStatement(sql);
			ps.setString(1, inputOne);
			ps.setInt(2, inputTwo);
			assertEquals("one", "testerJunit", inputOne);
			assertEquals("two", 9, inputTwo);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());
		} finally {
			assertNotNull(conn);
			assertNotNull(ps);
			if (conn != null) {
				conn.close();
			}
			if (ps != null) {
				ps.close();
			}
		}

	}

	@Test
	public void showFromDB() throws SQLException {

		Connection conn = null;
		Statement stat = null;
		assertNull(conn);
		assertNull(stat);
		String sql = "select*from tasklist";
		String result = "";
		assertNotNull(result);
		List<String> resultList = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(DBConnection, DBUser, DBPassword);
			stat = conn.createStatement();

			ResultSet rs = stat.executeQuery(sql);
			assertTrue(rs.next());
			assertEquals("lastrecord", "making this shit", rs.getString("Action"));
			assertEquals("lastRecord", 1, rs.getInt("TimeAction"));

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());
		} finally {
			assertNotNull(conn);
			assertNotNull(stat);
			if (conn != null) {
				conn.close();
			}
			if (stat != null) {
				stat.close();
			}
		}

	}

	@Test
	public void deletefromDB() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		assertNull(conn);
		assertNull(ps);
		int idTaskList = 9;

		String sql = "DELETE from tasklist where idTaskList=?";
		try {
			conn = DriverManager.getConnection(DBConnection, DBUser, DBPassword);
			ps = conn.prepareStatement(sql);

			ps.setInt(1, idTaskList);
			assertEquals("id = 9", 9, idTaskList);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());
		} finally {
			assertNotNull(conn);
			assertNotNull(ps);
			if (conn != null) {
				conn.close();
			}
			if (ps != null) {
				ps.close();
			}
		}

	}

}
