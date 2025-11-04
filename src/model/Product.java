/**
 * Indica que este archivo pertenece al paquete 'model', 
 * donde se definen las estructuras de datos o entidades.
 */
package model;

/**
 * @author hugog31 oct 2025
 * Clase que representa la entidad Producto.
 * Contiene los atributos de un producto tal como se almacenaría en la base de datos.
 */
public class Product {
	
	// --- Atributos de la clase (Campos) ---
	
	/** Identificador único del producto (Clave primaria en la BD). */
	int id;
	
	/** Código alfanumérico que identifica el tipo de producto. */
	String productCode;
	
	/** Cantidad de unidades disponibles en inventario. */
	int stock;
	
	/** Ubicación del producto dentro de la bodega o almacén. */
	String warehouse;
	
	/**
	 * Constructor completo para inicializar todos los atributos del producto.
	 * @param id Identificador único del producto.
	 * @param productCode Código del producto.
	 * @param stock Cantidad de stock inicial.
	 * @param warehouse Ubicación del almacén.
	 */
	public Product(int id, String productCode, int stock, String warehouse) {
		// Inicializa los atributos de la instancia (this) con los valores pasados.
		this.id = id;
		this.productCode = productCode;
		this.stock = stock;
		this.warehouse = warehouse;
	}
	
	// --- Métodos Getters (Lectura de Atributos) ---
	
	/**
	 * Obtiene el ID del producto.
	 * @return El ID del producto.
	 */
	public int getId() {
		return id;
	}
	
	// --- Métodos Setters (Escritura/Modificación de Atributos) ---
	
	/**
	 * Establece el ID del producto.
	 * @param id El nuevo ID del producto.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Obtiene el código del producto.
	 * @return El código del producto.
	 */
	public String getProductCode() {
		return productCode;
	}
	
	/**
	 * Establece el código del producto.
	 * @param productCode El nuevo código del producto.
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	/**
	 * Obtiene el stock actual del producto.
	 * @return La cantidad de stock.
	 */
	public int getStock() {
		return stock;
	}
	
	/**
	 * Establece el stock del producto.
	 * @param stock La nueva cantidad de stock.
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	/**
	 * Obtiene la ubicación del almacén.
	 * @return La ubicación del almacén.
	 */
	public String getWarehouse() {
		return warehouse;
	}
	
	/**
	 * Establece la ubicación del almacén.
	 * @param warehouse La nueva ubicación del almacén.
	 */
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

}
