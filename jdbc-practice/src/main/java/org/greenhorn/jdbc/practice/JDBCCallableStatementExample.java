package org.greenhorn.jdbc.practice;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCCallableStatementExample {

	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost/j2ee_practice";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";
	public static void main(String[] argv) {

		try {

			callStoredProcINParameter();
			//callStoredProcOUTParameter();
			//callStoredProcCURSORParameter();


		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

	}

	private static void callStoredProcINParameter() throws SQLException {

		Connection dbConnection = null;
		CallableStatement callableStatement = null;

		String insertStoreProc = "{call insertDBUSER(?,?,?,?)}";

		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(insertStoreProc);

			callableStatement.setInt(1, 1000);
			callableStatement.setString(2, "Hussain");
			callableStatement.setString(3, "system");
			callableStatement.setDate(4, getCurrentDate());

			// execute insertDBUSER store procedure
			callableStatement.executeUpdate();

			System.out.println("Record is inserted into DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (callableStatement != null) {
				callableStatement.close();
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

			dbConnection = DriverManager.getConnection(
				DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}

	private static void callStoredProcOUTParameter() throws SQLException {

		Connection dbConnection = null;
		CallableStatement callableStatement = null;

		String getDBUSERByUserIdSql = "{call getDBUSERByUserId(?,?,?,?)}";

		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(getDBUSERByUserIdSql);

			callableStatement.setInt(1, 10);
			callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(4, java.sql.Types.DATE);

			// execute getDBUSERByUserId store procedure
			callableStatement.executeUpdate();

			String userName = callableStatement.getString(2);
			String createdBy = callableStatement.getString(3);
			Date createdDate = callableStatement.getDate(4);

			System.out.println("UserName : " + userName);
			System.out.println("CreatedBy : " + createdBy);
			System.out.println("CreatedDate : " + createdDate);

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (callableStatement != null) {
				callableStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	private static void callStoredProcCURSORParameter()
			throws SQLException {

		Connection dbConnection = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;

		String getDBUSERCursorSql = "{call getDBUSERCursor(?,?)}";

		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(getDBUSERCursorSql);

			callableStatement.setString(1, "Hussain");
			//callableStatement.registerOutParameter(2, MysqlxDatatypes);

			// execute getDBUSERCursor store procedure
			callableStatement.executeUpdate();

			// get cursor and cast it to ResultSet
			rs = (ResultSet) callableStatement.getObject(2);

			while (rs.next()) {
				String userid = rs.getString("USER_ID");
				String userName = rs.getString("USERNAME");
				String createdBy = rs.getString("CREATED_BY");
				String createdDate = rs.getString("CREATED_DATE");

				System.out.println("UserName : " + userid);
				System.out.println("UserName : " + userName);
				System.out.println("CreatedBy : " + createdBy);
				System.out.println("CreatedDate : " + createdDate);
			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (rs != null) {
				rs.close();
			}

			if (callableStatement != null) {
				callableStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}
	private static java.sql.Date getCurrentDate() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}

}
