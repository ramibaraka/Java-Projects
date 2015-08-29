package se.sleepdev.ecommerce.interfaces;

import java.util.ArrayList;

import se.sleepdev.ecommerce.model.Order;
import se.sleepdev.ecommerce.service.RepositryException;

public interface OrderRep {

	Order createOrder(int userID, ArrayList<Integer> products)
			throws RepositryException;

	ArrayList<Order> getUsersOrders(int userID) throws RepositryException;

	Order getOrder(int orderID) throws RepositryException;

	Order updateOrder(int orderID, Order order) throws RepositryException;

	void removeOrder(int orderID) throws RepositryException;

}
