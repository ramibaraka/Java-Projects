package se.sleepdev.ecommerce.service;

import java.sql.*;
import java.util.ArrayList;

import se.sleepdev.ecommerce.interfaces.UserRep;
import se.sleepdev.ecommerce.model.User;

public final class UserRepository implements UserRep {
	private static final String URL_DB = "jdbc:mysql://localhost/ecommerce";
	private static final String password = "secret";
	private static final String username = "root";

	@Override
	public User addUser(User user) throws RepositryException {
		try (Connection conn = DriverManager.getConnection(URL_DB, username,
				password)) {
			conn.setAutoCommit(false);
			try (PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO user VALUES (null,?,?)",
					Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, user.getUserName());
				stmt.setString(2, user.getPassWord());
				int affectedRows = stmt.executeUpdate();

				if (affectedRows == 1) {
					ResultSet rs = stmt.getGeneratedKeys();

					if (rs.next()) {
						int idUser = rs.getInt(1);
						conn.commit();
						return new User(idUser, user.getUserName(),
								user.getPassWord());
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
	public User removeUser(int userId) throws RepositryException {

		try (final Connection conn = DriverManager.getConnection(URL_DB,
				username, password)) {

			PreparedStatement stmt1 = conn
					.prepareStatement("SELECT * FROM user WHERE id = ?");
			stmt1.setInt(1, userId);
			ResultSet rs = stmt1.executeQuery();
			PreparedStatement stmt = conn
					.prepareStatement("DELETE FROM user WHERE id = ?");
			stmt.setInt(1, userId);
			stmt.executeUpdate();

			if (rs.next()) {
				String userName = rs.getString("username");
				String passWord = rs.getString("password");
				return new User(userId, userName, passWord);
			}
			throw new RepositryException("Could not remove user");
		} catch (SQLException e) {
			throw new RepositryException("Could not remove user", e);
		}
	}

	@Override
	public User updateUser(int userId, User user) throws RepositryException {
		try (final Connection conn = DriverManager.getConnection(URL_DB,
				username, password)) {
			PreparedStatement stmt = conn
					.prepareStatement("UPDATE user SET username = ?, password = ? WHERE id = ?");
			String username = user.getUserName();
			String password = user.getPassWord();
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setInt(3, userId);
			stmt.executeUpdate();
			PreparedStatement stmt1 = conn
					.prepareStatement("SELECT * FROM user WHERE id = ?");
			stmt1.setInt(1, userId);
			ResultSet rs = stmt1.executeQuery();
			if (rs.next()) {
				String userName = rs.getString("username");
				String passWord = rs.getString("password");
				return new User(userId, userName, passWord);
			}
			throw new RepositryException("Could not update user");
		}

		catch (SQLException e) {
			throw new RepositryException("Could not update user", e);
		}

	}

	@Override
	public User getUser(int userId) throws RepositryException {

		try (final Connection conn = DriverManager.getConnection(URL_DB,
				username, password)) {

			PreparedStatement stmt = conn
					.prepareStatement("SELECT * FROM user WHERE id = ?");
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String userName = rs.getString("username");
				String passWord = rs.getString("password");
				return new User(userId, userName, passWord);
			}
			throw new RepositryException("Could nott get user");

		} catch (SQLException e) {
			throw new RepositryException("Could nota get user", e);
		}
	}

	@Override
	public ArrayList<User> getAllUsers() throws RepositryException {
		try (final Connection conn = DriverManager.getConnection(URL_DB,
				username, password)) {
			PreparedStatement stmt = conn
					.prepareStatement("SELECT * FROM user");
			
			ResultSet rs = stmt.executeQuery();
			ArrayList<User> users = new ArrayList<User>();

			while (rs.next()) {
				int userId = rs.getInt("id");
				String userName = rs.getString("username");
				String passWord = rs.getString("password");
				users.add(new User(userId, userName, passWord));
				
			}
			return users;

		} catch (SQLException e) {
			throw new RepositryException("Could not get products", e);
		}
		
	}

}
