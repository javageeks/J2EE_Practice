package org.greenhorn.jdbc.practice;

import java.sql.*;
import java.io.*;

public class InsertImage {

	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost/j2ee_practice"; // change your db details
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";

	public static void main(String[] args) {
		try {
			storeImage();
			// retriveIMage();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void storeImage() {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "insert into imgtable values(?,?)";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			preparedStatement.setString(1, "jdbc");
			FileInputStream fin = new FileInputStream("d:\\g.jpg");
			preparedStatement.setBinaryStream(2, fin, fin.available());
			int i = preparedStatement.executeUpdate();
			System.out.println(i + " records affected");

			dbConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void retriveIMage() {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "select * from imgtable";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			dbConnection = getDBConnection();

			PreparedStatement ps = dbConnection.prepareStatement("select * from imgtable");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {// now on 1st row

				Blob b = rs.getBlob(2);// 2 means 2nd column data
				byte barr[] = b.getBytes(1, (int) b.length());// 1 means first image

				FileOutputStream fout = new FileOutputStream("d:\\jdbc.jpg");
				fout.write(barr);

				fout.close();
			} // end of if
			System.out.println("ok");

			dbConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
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
}
