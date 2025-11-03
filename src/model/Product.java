/**
 * 
 */
package model;

/**
 * @author hugog31 oct 2025
 */
public class Product {
	int id;
	String productCode;
	int stock;
	String warehouse;
	/**
	 * @param id2
	 * @param productCode2
	 * @param stock2
	 * @param warehouse2
	 */
	public Product(int id, String productCode, int stock, String warehouse) {
		this.id = id;
		this.productCode = productCode;
		this.stock = stock;
		this.warehouse = warehouse;



		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

}
