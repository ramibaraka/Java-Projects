package se.sleepdev.ecommerce.interfaces;

import java.util.ArrayList;

import se.sleepdev.ecommerce.model.Product;
import se.sleepdev.ecommerce.service.RepositryException;

public interface ProdRep {
	Product addProduct(Product product) throws RepositryException;

	Product getProduct(int productId) throws RepositryException;

	Product removeProduct(int productId) throws RepositryException;

	Product updateProduct(int productId, Product product) throws RepositryException;

	ArrayList<Product> getAllProducts() throws RepositryException;
}
