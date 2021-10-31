package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection dbConnection() {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://220.70.33.29:3306/review";
			String id = "review";
			String pw = "1234";
			conn = DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}