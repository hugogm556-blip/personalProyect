/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Product;
import util.DBConnection;

/**
 * @author hugog31 oct 2025
 */
public class ProductDAOImpl implements ProductDAO {
	
	public void addProduct(Product product) {
		
	
		String insertSQL = "INSERT INTO product (productCode, stock, warehouse) VALUES (?, ?, ?)";
		
        try (Connection connection = DBConnection.getConection();
				PreparedStatement ps = connection.prepareStatement(insertSQL);) {
            
            // Establecer los parámetros de la consulta
            ps.setString(1, product.getProductCode());
            ps.setInt(2, product.getStock());
            ps.setString(3, product.getWarehouse());
            
            // Ejecutar la inserción
            int result = ps.executeUpdate();
			System.out.println(" insert rows " + result);
            
            }
        catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Product getProductById(int id) {
		String selectSQL = "select * from product where id = ? ";

		try (Connection connection = DBConnection.getConection();
				PreparedStatement ps = connection.prepareStatement(selectSQL);) {
			// try-with-resource statement, nos cierra la conexion
			// y otros recursos automaticamente

			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			System.out.println("rows= " + resultSet.getFetchSize());

			while (resultSet.next()) {

				id = resultSet.getInt("id");
				String productCode = resultSet.getString("productCode");
				int stock = resultSet.getInt("stock");
				String warehouse = resultSet.getString("warehouse");
				
				

				System.out.println(" pedido id =  " + id + " productCode = " + productCode + " stock = " + stock + " Warehouse = " + warehouse);

				resultSet.close();
				return new Product( id , productCode, stock, warehouse);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateProduct(Product product) {
		String updateSQL = "UPDATE product SET stock = ?, productCode = ? ,Warehouse =?  where id = ? ";
		
		try(Connection connection = DBConnection.getConection();
				PreparedStatement ps = connection.prepareStatement(updateSQL);) {
			
			  ps.setInt(4, product.getId());
			  ps.setString(2, product.getProductCode());
	          ps.setInt(1, product.getStock());
	          ps.setString(3, product.getWarehouse());
	          
	          int result = ps.executeUpdate();
				System.out.println(" actualizado " + result);
			
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}

	}

	@Override
	public void deleteProduct(int id) {
		String deleteSQL = "DELETE From product where id = ? ";
		Connection connection = DBConnection.getConection();

		try {
			PreparedStatement ps = connection.prepareStatement(deleteSQL);

			ps.setInt(1, id);
			int result = ps.executeUpdate();
			System.out.println("we have delete " + result + " rows. ");
			ps.executeUpdate();

			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Override
	public void deductStock(int id, int stock) {
		String updateSQL = "UPDATE product SET stock = ? where id = ? ";
		try(Connection connection = DBConnection.getConection();
				PreparedStatement ps = connection.prepareStatement(updateSQL);)
		{
			ps.setInt(2, id);
			ps.setInt(1, stock);
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}

	}
	
	
	

}
