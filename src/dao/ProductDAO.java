/**
 * 
 */
package dao;

import model.Product;

/**
 * @author hugog31 oct 2025
 */

public interface ProductDAO {
		void addProduct(Product product);
		
		Product getProductById(int id);
		
		void updateProduct(Product product);
		
		void deleteProduct(int id);
		
		void deductStock(int id, int stock);
		}

