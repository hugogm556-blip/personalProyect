/**
 * Indica que este archivo pertenece al paquete 'util' (utilidades),
 * donde se encuentran clases de ayuda que no son modelos ni DAOs.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author hugog31 oct 2025
 * Clase de utilidad para gestionar y obtener conexiones a la base de datos.
 * Esta clase usa el patrón Singleton (a través de un método estático) 
 * para facilitar el acceso a la base de datos desde cualquier parte de la aplicación.
 */
public class DBConnection {
	
	// --- Configuración de la Conexión ---
	
	/** URL de conexión a la base de datos (protocolo, servidor, puerto y nombre de la BD). */
	private static String dbURL = "jdbc:mysql://localhost:3306/bbdd";
	
	/** Nombre de usuario para acceder a la base de datos. */
	private static String username = "root";
	
	/** Contraseña para acceder a la base de datos. */
	private static String password = "admin";

	// --- Atributos de Instancia (No usados en la implementación actual) ---
	
	// clase que administra los drivers de diferentes base de datos
	// Nota: DriverManager es una clase estática, por lo que este atributo no es necesario.
	DriverManager driverManager; 
	
	// creamos una conexion con la base de datos mysql
	// Nota: Este atributo de instancia no se está usando; el método getConection() 
	// crea una nueva conexión cada vez.
	Connection connection;
	
	// --- Método Estático para Obtener la Conexión ---
	
	/**
	 * Crea y devuelve una nueva conexión a la base de datos MySQL.
	 * @return Un objeto Connection si la conexión es exitosa, o null si falla.
	 */
	public static Connection getConection() {
		try {
			// Intenta establecer la conexión usando la URL, usuario y contraseña definidos.
			return DriverManager.getConnection(dbURL, username, password);
		} catch (SQLException e) {
			// Captura cualquier error de SQL (ej: credenciales incorrectas, BD no disponible).
			// TODO Auto-generated catch block
			e.printStackTrace(); // Imprime la traza del error para diagnóstico.
		}
	// Devuelve null si ocurre una excepción y la conexión no pudo ser establecida.
	return null;
	}
}