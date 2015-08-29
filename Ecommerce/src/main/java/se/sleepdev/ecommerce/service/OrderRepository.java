package se.sleepdev.ecommerce.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import se.sleepdev.ecommerce.interfaces.OrderRep;
import se.sleepdev.ecommerce.model.*;

public final class OrderRepository implements OrderRep {
	private static final String URL_DB = "jdbc:mysql://localhost/ecommerce";
	private static final String password = "secret";
	private static final String username = "root";

	@Override
	public Order createOrder(int userID, ArrayList<Integer> products)
			throws RepositryException {
		try (Connection conn = DriverManager.getConnection(URL_DB, username,
				password)) {

			try {
				PreparedStatement stmt2 = conn.prepareStatement(
						"INSERT INTO Orders (userID) VALUES (?)",
						Statement.RETURN_GENERATED_KEYS);
				stmt2.setInt(1, userID);

				int orderID = stmt2.executeUpdate();
				if (orderID == 1) {
					ResultSet rs = stmt2.getGeneratedKeys();

					if (rs.next()) {
						int test = rs.getInt(1);
						orderID = test;
					}
				}

				for (Integer integer : products) {
					int prodID = integer;
					PreparedStatement stmt1 = conn
							.prepareStatement("INSERT INTO OrderItem (prodID, orderID) VALUES ( ?, ?)");
					stmt1.setInt(1, prodID);
					stmt1.setInt(2, orderID);
					stmt1.executeUpdate();
				}
				return new Order(userID, products, orderID);

			} catch (SQLException e) {
				throw new RepositryException("tinkle");
			}
		} catch (SQLException e) {
			throw new RepositryException("Could not create order", e);
		}
	}

	@Override
	public Order getOrder(int orderID) throws RepositryException {
		try (final Connection conn = DriverManager.getConnection(URL_DB,
				username, password)) {
			PreparedStatement stmt2 = conn
					.prepareStatement("SELECT userID FROM Orders WHERE orderID = ?");
			PreparedStatement stmt = conn
					.prepareStatement("SELECT * FROM OrderItem WHERE orderID = ?");
			stmt2.setInt(1, orderID);
			stmt.setInt(1, orderID);
			ResultSet rs2 = stmt2.executeQuery();
			rs2.next();
			int userID = rs2.getInt("userID");
			int prodID;

			ArrayList<Integer> products = new ArrayList<Integer>();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				prodID = rs.getInt("prodID");
				products.add(prodID);
			}
			return new Order(userID, products, orderID);

		} catch (SQLException e) {
			throw new RepositryException("Could not get order", e);
		}

	}

	@Override
	public void removeOrder(int orderID) throws RepositryException {
		try (final Connection conn = DriverManager.getConnection(URL_DB,
				username, password)) {

			PreparedStatement stmt = conn
					.prepareStatement("DELETE FROM OrderItem WHERE orderID = ?");
			stmt.setInt(1, orderID);
			PreparedStatement stmt2 = conn
					.prepareStatement("DELETE FROM Orders WHERE orderID = ?");
			stmt2.setInt(1, orderID);
			stmt.executeUpdate();
			stmt2.executeUpdate();

		} catch (SQLException e) {
			throw new RepositryException("Could not remove order", e);
		}
	}

	@Override
	public Order updateOrder(int orderID, Order order)
			throws RepositryException {
		try (final Connection conn = DriverManager.getConnection(URL_DB,
				username, password)) {

			PreparedStatement stmt = conn
					.prepareStatement("DELETE FROM OrderItem WHERE orderID = ?");
			stmt.setInt(1, orderID);
			stmt.executeUpdate();

			for (Integer integer : order.products) {
				int prodID = integer;
				PreparedStatement stmt1 = conn
						.prepareStatement("INSERT INTO OrderItem (prodID, orderID) VALUES (?, ?)");
				stmt1.setInt(1, prodID);
				stmt1.setInt(2, orderID);
				stmt1.executeUpdate();
			}

			return order;

		}

		catch (SQLException e) {
			throw new RepositryException("Could not update product", e);
		}

	}

	@Override
	public ArrayList<Order> getUsersOrders(int userID)
			throws RepositryException {
		ArrayList<Order> listan = new ArrayList<Order>();
		ArrayList<Integer> listan2 = new ArrayList<Integer>();
		listan2 = getOrderIDs(userID);
		for (Integer integer : listan2) {
			listan.add(getOrder(integer));
		}
		return listan;

	}

	public ArrayList<Integer> getOrderIDs(int userID) throws RepositryException {
		try (final Connection conn = DriverManager.getConnection(URL_DB,
				username, password)) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			PreparedStatement stmt = conn
					.prepareStatement("SELECT orderID FROM Orders WHERE userID = ?");
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt("orderID"));
			}
			return list;

		} catch (SQLException e) {
			throw new RepositryException("Could not remove product", e);
		}

	}

}