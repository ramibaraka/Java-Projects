package se.sleepdev.ecommerce.model;

import java.util.ArrayList;

public class Order {

	public ArrayList<Integer> products = new ArrayList<Integer>();
	public int orderID;
	public int userID;

	public Order(int userID, ArrayList<Integer> products, int orderID) {
		this.userID = userID;
		this.products = products;
		this.orderID = orderID;

	}

	public int addProduct(int prodID) {
		products.add(prodID);
		return prodID;
	}

	public ArrayList<Integer> getProducts() {
		return products;
	}

	public int getOrderID() {
		return orderID;
	}

	public int getUserID() {
		return userID;
	}

}
