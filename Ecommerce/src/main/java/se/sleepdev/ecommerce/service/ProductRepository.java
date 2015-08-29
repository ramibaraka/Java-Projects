package se.sleepdev.ecommerce.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import se.sleepdev.ecommerce.interfaces.ProdRep;
import se.sleepdev.ecommerce.model.Product;



public final class ProductRepository implements ProdRep {
	private static final String URL_DB = "jdbc:mysql://localhost/ecommerce";
	private static final String password = "secret";
	private static final String username = "root";

	@Override
	public Product addProduct(Product product) throws RepositryException {

		try (Connection conn = DriverManager.getConnection(URL_DB, username,
				password)) {
			conn.setAutoCommit(false);

			try (PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO product VALUES (null,?,?)",
					Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, product.getProductName());
				stmt.setInt(2, product.getProductPrice());
				int affectedRows = stmt.executeUpdate();

				if (affectedRows == 1) {
					ResultSet rs = stmt.getGeneratedKeys();

					while (rs.next()) {
						int id = rs.getInt(1);
						conn.commit();
						return new Product(id, product.getProductName(),
								product.getProductPrice());
					}
				}
				throw new RepositryException("Could not add user");
			} catch (SQLException e) {
				conn.rollback();
				throw new RepositryException("Could not add user");
			}
		} catch (SQLException e) {
			throw new RepositryException("Could not add user", e);
		}
	}

	@Override
	public Product getProduct(int productId) throws RepositryException {
		try (final Connection conn = DriverManager.getConnection(URL_DB,
				username, password)) {
			PreparedStatement stmt = conn
					.prepareStatement("SELECT * FROM product WHERE idproduct = ?");
			stmt.setInt(1, productId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String productName = rs.getString("productname");
				int productPrice = rs.getInt("productpris");
				return new Product(productId, productName, productPrice);
			}
			throw new RepositryException("Could not get product");

		} catch (SQLException e) {
			throw new RepositryException("Could not get product", e);
		}

	}

	@Override
	public Product removeProduct(int productId) throws RepositryException {
		try (final Connection conn = DriverManager.getConnection(URL_DB,
				username, password)) {

			PreparedStatement stmt1 = conn
					.prepareStatement("SELECT * FROM product WHERE idproduct = ?");
			stmt1.setInt(1, productId);
			ResultSet rs = stmt1.executeQuery();
			PreparedStatement stmt = conn
					.prepareStatement("DELETE FROM product WHERE idproduct = ?");
			stmt.setInt(1, productId);
			stmt.executeUpdate();

			if (rs.next()) {
				String productName = rs.getString("productname");
				int productPrice = rs.getInt("productpris");
				return new Product(productId, productName, productPrice);
			}
			throw new RepositryException("Could not remove product");
		} catch (SQLException e) {
			throw new RepositryException("Could not remove product", e);
		}
	}

	@Override
	public Product updateProduct(int productId, Product product) throws RepositryException {
		try (final Connection conn = DriverManager.getConnection(URL_DB,
				username, password)) {
			PreparedStatement stmt = conn
					.prepareStatement("UPDATE product SET productname = ?, productpris = ? WHERE idproduct = ?");
			stmt.setString(1, product.getProductName());
			stmt.setInt(2, product.getProductPrice());
			stmt.setInt(3, productId);
			stmt.executeUpdate();
			PreparedStatement stmt1 = conn
					.prepareStatement("SELECT * FROM product WHERE idproduct = ?");
			stmt1.setInt(1, productId);
			ResultSet rs = stmt1.executeQuery();
			if (rs.next()) {
				String productName = rs.getString("productname");
				int productPrice = rs.getInt("productpris");
				return new Product(productId, productName, productPrice);
			}
			throw new RepositryException("Could not update product");
		}

		catch (SQLException e) {
			throw new RepositryException("Could not update product", e);
		}

	}

	@Override
	public ArrayList<Product> getAllProducts() throws RepositryException {
		try (final Connection conn = DriverManager.getConnection(URL_DB,
				username, password)) {
			PreparedStatement stmt = conn
					.prepareStatement("SELECT * FROM product");
			
			ResultSet rs = stmt.executeQuery();
			ArrayList<Product> products = new ArrayList<Product>();

			while (rs.next()) {
				int productId = rs.getInt("idproduct");
				String productName = rs.getString("productname");
				int productPrice = rs.getInt("productpris");
				products.add(new Product(productId, productName, productPrice));
				
			}
			return products;

		} catch (SQLException e) {
			throw new RepositryException("Could not get products", e);
		}
		
	}
}