/**
 * 
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author hugog31 oct 2025
 */
public class DBConnection {
	private static String dbURL = "jdbc:mysql://localhost:3306/bbdd";
	private static String username = "root";
	private static String password = "admin";

	// clase que administra los drivers de diferentes base de datos
	DriverManager driverManager;
	// creamos una conexion con la base de datos mysql
	Connection connection;
	public static Connection getConection() {
		try {
			return DriverManager.getConnection(dbURL, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;
	}
}
