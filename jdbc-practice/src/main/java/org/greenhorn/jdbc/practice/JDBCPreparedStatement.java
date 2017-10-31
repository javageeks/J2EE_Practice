package org.greenhorn.jdbc.practice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

public class JDBCPreparedStatement {

	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost/j2ee_practice";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";

	public static void main(String[] argv) {

		try {

		//createTable();
		//	insertRecordIntoTable();
		//	updateRecordToTable();
		//	deleteRecordFromTable();
		//	batchInsertRecordsIntoTable();
			selectRecordsFromTable();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

	}

	private static void createTable() throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String createTableSQL = "CREATE TABLE DBUSER1("
				+ "USER_ID INT(5) NOT NULL, "
				+ "USERNAME VARCHAR(20) NOT NULL, "
				+ "CREATED_BY VARCHAR(20) NOT NULL, "
				+ "CREATED_DATE DATE NOT NULL, " + "PRIMARY KEY (USER_ID) "
				+ ")";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(createTableSQL);

			System.out.println(createTableSQL);

			// execute create SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Table \"dbuser\" is created!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	private static void insertRecordIntoTable() throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO DBUSER1"
				+ "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) VALUES"
				+ "(?,?,?,?)";



		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			preparedStatement.setInt(1, 11);
			preparedStatement.setString(2, "TestUser");
			preparedStatement.setString(3, "system");
			preparedStatement.setTimestamp(4, getCurrentTimeStamp());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Record is inserted into DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	private static void updateRecordToTable() throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String updateTableSQL = "UPDATE DBUSER1 SET USERNAME = ? "
				                  + " WHERE USER_ID = ?";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(updateTableSQL);

			preparedStatement.setString(1, "SUNIL");
			preparedStatement.setInt(2, 11);

			// execute update SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Record is updated to DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	private static void deleteRecordFromTable() throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE from DBUSER1 WHERE USER_ID = ?";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, 11);

			// execute delete SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Record is deleted!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}
	
	
	private static void selectRecordsFromTable() throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT USER_ID, USERNAME FROM DBUSER1 WHERE CREATED_BY = ?";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);
			preparedStatement.setString(1, "system");

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();

			// user for loop ... then 
		// for(Datatype varable : rs){ --------}
			while (rs.next()) {

				String userid = rs.getString("USER_ID");
				String username = rs.getString("USERNAME");

				System.out.println("userid : " + userid);
				System.out.println("username : " + username);

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	private static void batchInsertRecordsIntoTable() throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO DBUSER1"
				+ "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) VALUES"
				+ "(?,?,?,?)";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			dbConnection.setAutoCommit(false);

			preparedStatement.setInt(1, 101);
			preparedStatement.setString(2, "USER101");
			preparedStatement.setString(3, "system");
			preparedStatement.setTimestamp(4, getCurrentTimeStamp());
			preparedStatement.addBatch();

			preparedStatement.setInt(1, 102);
			preparedStatement.setString(2, "USER102");
			preparedStatement.setString(3, "system");
			preparedStatement.setTimestamp(4, getCurrentTimeStamp());
			preparedStatement.addBatch();

			preparedStatement.setInt(1, 103);
			preparedStatement.setString(2, "USER103");
			preparedStatement.setString(3, "system");
			preparedStatement.setTimestamp(4, getCurrentTimeStamp());
			preparedStatement.addBatch();

			preparedStatement.executeBatch();

			dbConnection.commit();

			System.out.println("Record is inserted into DBUSER table!");

		} catch (SQLTimeoutException e) {

			System.out.println(e.getMessage());
			dbConnection.rollback();

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}
	
	private static Connection getDBConnection() throws SQLException   {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(
                            DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;

		} catch (SQLTimeoutException e) {

			
			System.out.println(e.getMessage());

		}

		return dbConnection;

	}
	
	
}
