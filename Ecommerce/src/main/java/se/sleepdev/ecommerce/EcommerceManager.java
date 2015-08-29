package se.sleepdev.ecommerce;

import java.util.ArrayList;

import se.sleepdev.ecommerce.interfaces.OrderRep;
import se.sleepdev.ecommerce.interfaces.ProdRep;
import se.sleepdev.ecommerce.interfaces.UserRep;
import se.sleepdev.ecommerce.model.*;
import se.sleepdev.ecommerce.service.*;

public final class EcommerceManager {


	private final ProdRep productRepository;
	private final UserRep userRepository;
	private final OrderRep OrderRepository;

	public EcommerceManager(ProdRep productRepository, UserRep userRepository, OrderRep OrderRepository)
	{
		this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.OrderRepository = OrderRepository;
	}

	public Product addProduct(final Product product) throws RepositryException
	{
		if (product.getProductId() == Product.EMPTY_PRODUCT_ID)
		{
			try
			{
				return productRepository.addProduct(product);
			}
			catch (RepositryException e)
			{
				throw new RepositryException("could not add product", e);
			}
		}
		throw new RepositryException("could not add product");
	}

	public final Product getProduct(int productId) throws RepositryException
	{
		try
		{
			return productRepository.getProduct(productId);
		}
		catch (RepositryException e)
		{
			throw new RepositryException("could not get product", e);
		}

	}

	public final Product removeProduct(int productId) throws RepositryException
	{
		try
		{
			return productRepository.removeProduct(productId);
		}
		catch (RepositryException e)
		{
			throw new RepositryException("could not remove product", e);
		}

	}

	public final Product updateProduct(int productId, Product product) throws RepositryException
	{
		try
		{
			return productRepository.updateProduct(productId, product);
		}
		catch (RepositryException e)
		{
			throw new RepositryException("could not update product", e);
		}

	}
	
	public final ArrayList<Product> getAllProducts() throws RepositryException
	{
		try
		{
			return productRepository.getAllProducts();
		}
		catch (RepositryException e)
		{
			throw new RepositryException("could not update product", e);
		}

	}

	public final User getUser(int userId) throws RepositryException
	{
		try
		{
			return userRepository.getUser(userId);
		}
		catch (RepositryException e)
		{
			throw new RepositryException("could not get user", e);
		}
	}
	
	public final User addUser(User user) throws RepositryException
	{
		try
		{
			return userRepository.addUser(user);
		}
		catch (RepositryException e)
		{
			throw new RepositryException("could not add user", e);
		}
	}

	public final User updateUser(int userId, User user) throws RepositryException
	{
		try
		{
			return userRepository.updateUser(userId, user);
		}
		catch (RepositryException e)
		{
			throw new RepositryException("could not update user", e);
		}
	}

	public final User removeUser(int userId) throws RepositryException
	{
		try
		{
			return userRepository.removeUser(userId);
		}
		catch (RepositryException e)
		{
			throw new RepositryException("could not remove user", e);
		}
	}
	
	public final ArrayList<User> getAllUsers() throws RepositryException
	{
		try
		{
			return userRepository.getAllUsers();
		}
		catch (RepositryException e)
		{
			throw new RepositryException("could not get all users", e);
		}

	}

	public final Order createOrder(int userID, ArrayList<Integer> products) throws RepositryException
	{
			try
			{
				
				return OrderRepository.createOrder(userID, products);
				
			}
			catch (RepositryException e)
			{
				throw new RepositryException("could not create order", e);
			}
		
	}
	
	public final ArrayList<Order> getUsersOrders(int userID)  throws RepositryException
	{
		try
		{
			return OrderRepository.getUsersOrders(userID);
		}
		catch (RepositryException e)
		{
			throw new RepositryException("could not get orders", e);
		}
	}
	
	public final Order getOrder(int orderID) throws RepositryException
	{
		try
		{
			return OrderRepository.getOrder(orderID);
		}
		catch (RepositryException e)
		{
			throw new RepositryException("could not get order", e);
		}
	}
	
	public final Order updateOrder(int orderID, Order order) throws RepositryException
	{
		try
		{
			return OrderRepository.updateOrder(orderID, order);
		}
		catch (RepositryException e)
		{
			throw new RepositryException("could not update order", e);
		}
	}
	
	public final void removeOrder(int orderID) throws RepositryException
	{
		try
		{
			OrderRepository.removeOrder(orderID);
		}
		catch (RepositryException e)
		{
			throw new RepositryException("could not remove order", e);
		}
	}
	
	

}
