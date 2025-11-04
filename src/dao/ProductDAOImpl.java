/**
 * Este paquete contiene las clases de Data Access Object (DAO) para interactuar
 * con la capa de persistencia (la base de datos).
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Product; // Importa el modelo (POJO) del producto
import util.DBConnection; // Importa la utilidad para obtener la conexión a la base de datos

/**
 * @author hugog31 oct 2025
 * Implementación de la interfaz ProductDAO.
 * Esta clase maneja todas las operaciones CRUD (Crear, Leer, Actualizar, Borrar)
 * para la entidad 'Product' en la base de datos.
 */
public class ProductDAOImpl implements ProductDAO {
	
	/**
	 * Inserta un nuevo producto en la tabla 'product'.
	 * @param product Objeto Product con los datos a guardar.
	 */
	public void addProduct(Product product) {
		
	
		// Consulta SQL para insertar un nuevo registro. Se usan '?' como marcadores de posición.
		String insertSQL = "INSERT INTO product (productCode, stock, warehouse) VALUES (?, ?, ?)";
		
        // Uso de try-with-resources: asegura que la Connection y el PreparedStatement se cierren automáticamente.
        try (Connection connection = DBConnection.getConection();
				PreparedStatement ps = connection.prepareStatement(insertSQL);) {
            
            // Establecer los parámetros de la consulta en el orden de los '?'
            ps.setString(1, product.getProductCode()); // 1. productCode
            ps.setInt(2, product.getStock());          // 2. stock
            ps.setString(3, product.getWarehouse());   // 3. warehouse
            
            // Ejecutar la inserción (devuelve el número de filas insertadas)
            int result = ps.executeUpdate();
			System.out.println(" Filas insertadas: " + result);
            
            }
        catch (Exception e) {
            // Manejo de cualquier excepción (SQL, conexión, etc.)
			e.printStackTrace();
		}
	}

	/**
	 * Recupera un producto de la base de datos utilizando su ID.
	 * @param id El identificador único del producto.
	 * @return Un objeto Product si se encuentra, o null si no existe.
	 */
	@Override
	public Product getProductById(int id) {
		// Consulta SQL para seleccionar un producto por ID.
		String selectSQL = "select * from product where id = ? ";

		try (Connection connection = DBConnection.getConection();
				PreparedStatement ps = connection.prepareStatement(selectSQL);) {
			// try-with-resource statement, nos cierra la conexion
			// y otros recursos automaticamente.

			ps.setInt(1, id); // Asigna el ID al marcador de posición
			ResultSet resultSet = ps.executeQuery(); // Ejecuta la consulta SELECT
			System.out.println("rows= " + resultSet.getFetchSize()); // Nota: getFetchSize() no siempre es confiable para el número de filas.

			// Iterar sobre los resultados (debería haber solo uno si 'id' es clave primaria)
			while (resultSet.next()) {

				// Mapear las columnas de la tabla a las propiedades del objeto Product
				id = resultSet.getInt("id"); // Reasignamos la variable ID con el valor de la BD (aunque ya lo teníamos)
				String productCode = resultSet.getString("productCode");
				int stock = resultSet.getInt("stock");
				String warehouse = resultSet.getString("warehouse");
				
				// Imprimir los datos del producto recuperado
				System.out.println(" pedido id =  " + id + " productCode = " + productCode + " stock = " + stock + " Warehouse = " + warehouse);

				resultSet.close(); // Se recomienda cerrar el ResultSet explícitamente, aunque el try-with-resources lo maneja en algunos casos.
				return new Product( id , productCode, stock, warehouse); // Devuelve el objeto Product
			}
		} catch (SQLException e) {
			// Manejo de excepción SQL
			e.printStackTrace();
		}
		return null; // Devuelve null si no se encontró el producto o si hubo un error.
	}

	/**
	 * Actualiza los datos de un producto existente.
	 * @param product Objeto Product con los nuevos datos y el ID para la identificación.
	 */
	@Override
	public void updateProduct(Product product) {
		// Consulta SQL para actualizar los campos. El ID debe ir al final en la cláusula WHERE.
		// Nota: El orden de los parámetros en la consulta es: stock, productCode, Warehouse, id.
		String updateSQL = "UPDATE product SET stock = ?, productCode = ? ,Warehouse =?  where id = ? ";
		
		try(Connection connection = DBConnection.getConection();
				PreparedStatement ps = connection.prepareStatement(updateSQL);) {
			
			  // Asignar los parámetros en el orden correcto de la consulta:
			  ps.setInt(1, product.getStock());       // 1. stock
			  ps.setString(2, product.getProductCode()); // 2. productCode
	          ps.setString(3, product.getWarehouse());  // 3. Warehouse
	          ps.setInt(4, product.getId());          // 4. id (en la cláusula WHERE)
	          
	          // Ejecutar la actualización (devuelve el número de filas actualizadas)
	          int result = ps.executeUpdate();
			  System.out.println(" Filas actualizadas: " + result);
			
		} catch (Exception e) {
			// Manejo de cualquier excepción
			e.printStackTrace();
		}

	}

	/**
	 * Elimina un producto de la base de datos por su ID.
	 * @param id El identificador único del producto a eliminar.
	 */
	@Override
	public void deleteProduct(int id) {
		// Consulta SQL para eliminar un registro.
		String deleteSQL = "DELETE From product where id = ? ";
		// Nota: Es mejor usar try-with-resources para la conexión. Aquí se inicializa fuera, lo que obliga al 'finally' o manejo manual.
		Connection connection = DBConnection.getConection();

		try {
			PreparedStatement ps = connection.prepareStatement(deleteSQL);

			ps.setInt(1, id); // Asigna el ID al marcador de posición
			
			// Ejecutar la eliminación (devuelve el número de filas eliminadas)
			int result = ps.executeUpdate(); 
			System.out.println("Hemos eliminado " + result + " filas. ");
			
			// Nota: La siguiente línea repite la ejecución sin necesidad, y sin guardar el resultado:
			// ps.executeUpdate(); 
			
			// Cierre manual de recursos (necesario porque no se usó try-with-resources para Connection y PreparedStatement)
			ps.close();
			connection.close();
		} catch (SQLException e) {
			// Manejo de excepción SQL
			e.printStackTrace();
		}

	}



	public void deductStock(int id, int cantidadARestar) {
	    
	    // La consulta intenta restar la cantidad, pero SOLO lo permite si el stock actual 
	    // es mayor o igual a la cantidad que se quiere restar.
	    String updateSQL = "UPDATE product SET stock = stock - ? WHERE id = ? AND stock >= ?";
	    int filasAfectadas = 0; 
	    
	    try (Connection connection = DBConnection.getConection();
	         PreparedStatement ps = connection.prepareStatement(updateSQL)) {
	        
	        // 1. Cantidad para la resta
	        ps.setInt(1, cantidadARestar); 
	        // 2. ID del producto
	        ps.setInt(2, id);             
	        // 3. Cantidad para la verificación de stock
	        ps.setInt(3, cantidadARestar); 
	        
	        // Ejecutamos la actualización
	        filasAfectadas = ps.executeUpdate();
	        
	        // Si no se afectó ninguna fila (0), significa que la deducción falló
	        if (filasAfectadas == 0) {
	            // **NOTIFICACIÓN SILENCIOSA:** Solo registramos el problema
	            System.err.println("❌ ADVERTENCIA DE STOCK: No se pudo deducir " 
	                + cantidadARestar + " unidades para el ID " + id + 
	                ". Stock insuficiente o ID de producto incorrecto.");
	        }
	        
	    } catch (Exception e) {
	        // Capturamos y registramos cualquier error de conexión o SQL
	        System.err.println("⚠️ ERROR CRÍTICO DE BD al deducir stock para ID: " + id);
	        e.printStackTrace();
	    }
	    // El método termina aquí sin devolver nada y sin lanzar excepciones.
	}
	
	

}
