package dao;

import model.Product; // Importa la clase 'Product' del paquete 'model'

/**
 * @author hugog31 oct 2025
 * * Interfaz de Objeto de Acceso a Datos (DAO) para la entidad 'Product'.
 * Define el contrato para las operaciones de persistencia (CRUD) relacionadas
 * con los productos en la base de datos.
 */

public interface ProductDAO {
    
    /**
     * Agrega un nuevo producto a la base de datos.
     * @param product El objeto Product a guardar.
     */
	void addProduct(Product product);
	
    /**
     * Recupera un producto de la base de datos usando su identificador único.
     * @param id El ID del producto a buscar.
     * @return El objeto Product encontrado, o null si no existe.
     */
	Product getProductById(int id);
	
    /**
     * Actualiza los datos de un producto existente en la base de datos.
     * @param product El objeto Product con los datos actualizados y el ID.
     */
	void updateProduct(Product product);
	
    /**
     * Elimina un producto de la base de datos por su ID.
     * @param id El ID del producto a eliminar.
     */
	void deleteProduct(int id);
	
    /**
     * Deduce (resta) una cantidad específica del stock de un producto.
     * NOTA: La implementación debe incluir una verificación para asegurar 
     * que el stock no sea negativo.
     * @param id El ID del producto cuyo stock se va a deducir.
     * @param stock La cantidad a restar del stock actual.
     */
	void deductStock(int id, int stock);
}

