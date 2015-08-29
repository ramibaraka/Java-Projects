package se.sleepdev.ecommerce.interfaces;

import java.util.ArrayList;

import se.sleepdev.ecommerce.model.User;
import se.sleepdev.ecommerce.service.RepositryException;

public interface UserRep {
	User addUser(User user) throws RepositryException;

	User removeUser(int userId) throws RepositryException;

	User updateUser(int userID, User user) throws RepositryException;

	User getUser(int userID) throws RepositryException;
	
	ArrayList<User> getAllUsers() throws RepositryException;

}
