package org.greenhorn.jdbc.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JDBCStatement {

	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost/j2ee_practice";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";

	public static void main(String[] args) {

		try {

			//createDbUserTable(); // create a table 
			
			//insertRecordIntoDbUserTable(); //insert records 
			//updateRecordIntoDbUserTable(); //udate
			//deleteRecordFromDbUserTable(); //delete
			//batchInsertRecordsIntoTable(); // batchupdate
			selectRecordsFromDbUserTable(); // list of records
			 

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
	}

	
	private static void createDbUserTable() throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;

		String createTableSQL = "CREATE TABLE DBUSER(" + "USER_ID INT(5) NOT NULL, "
				+ "USERNAME VARCHAR(20) NOT NULL, " + "CREATED_BY VARCHAR(20) NOT NULL, "
				+ "CREATED_DATE DATE NOT NULL, " + "PRIMARY KEY (USER_ID) " + ")";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(createTableSQL);
			// execute the SQL stetement
			statement.execute(createTableSQL);

			System.out.println("Table \"dbuser\" is created!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	// Insert record 
	private static void insertRecordIntoDbUserTable() throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;

		String insertTableSQL = "INSERT INTO DBUSER"
				+ "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) " + "VALUES"
				+ "(2,'Shaik','HUSSAIN', SYSDATE())";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(insertTableSQL);

			// execute insert SQL stetement
			statement.executeUpdate(insertTableSQL);

			System.out.println("Record is inserted into DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}
	

	//update	
	private static void updateRecordIntoDbUserTable() throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;

		String updateTableSQL = "UPDATE DBUSER"
				+ " SET USERNAME = 'SANDEEP' "
				+ " WHERE USER_ID = 2";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(updateTableSQL);

			// execute update SQL stetement
			statement.execute(updateTableSQL);

			System.out.println("Record is updated to DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}


	private static void deleteRecordFromDbUserTable() throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;

		String deleteTableSQL = "DELETE FROM DBUSER WHERE USER_ID = 2";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(deleteTableSQL);

			// execute delete SQL stetement
			statement.execute(deleteTableSQL);

			System.out.println("Record is deleted from DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}
	
	
	private static void selectRecordsFromDbUserTable() throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;

		String selectTableSQL = "SELECT USER_ID, USERNAME from DBUSER";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(selectTableSQL);

			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {

				String userid = rs.getString("USER_ID");
				String username = rs.getString("USERNAME");

				System.out.println("userid : " + userid);
				System.out.println("username : " + username);

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	private static void batchInsertRecordsIntoTable() throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;

		String insertTableSQL1 = "INSERT INTO DBUSER"
				+ "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) " + "VALUES"
				+ "(101,'SUNIL','HUSSAIN', SYSDATE())";

		String insertTableSQL2 = "INSERT INTO DBUSER"
				+ "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) " + "VALUES"
				+ "(102,'SANDEEP','HUSSAIN', SYSDATE())";

		String insertTableSQL3 = "INSERT INTO DBUSER"
				+ "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) " + "VALUES"
				+ "(103,'HARSHA','HUSSAIN', SYSDATE())";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			dbConnection.setAutoCommit(false);

			statement.addBatch(insertTableSQL1);
			statement.addBatch(insertTableSQL2);
			statement.addBatch(insertTableSQL3);

			statement.executeBatch();

			dbConnection.commit();

			System.out.println("Records are inserted into DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}
	
	//no use yet
	private static String getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return dateFormat.format(today.getTime());

	}
	private static final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
}
