package se.sleepdev.ecommerce;

import java.util.ArrayList;

import se.sleepdev.ecommerce.model.Order;
import se.sleepdev.ecommerce.model.Product;
import se.sleepdev.ecommerce.model.User;
import se.sleepdev.ecommerce.service.OrderRepository;
import se.sleepdev.ecommerce.service.ProductRepository;
import se.sleepdev.ecommerce.service.RepositryException;
import se.sleepdev.ecommerce.service.UserRepository;

public class OrderTest {

	public static void main(String[] args) throws RepositryException {

		ArrayList<Integer> products = new ArrayList<Integer>();
		
		products.add(1);
		products.add(2);
		products.add(3);
		products.add(4);
		int userID = 1234;

		OrderRepository rep = new OrderRepository();
		UserRepository rep1 = new UserRepository();
		ProductRepository rep3 = new ProductRepository();

		EcommerceManager ecom = new EcommerceManager(rep3, rep1, rep);
		
		
		
		ArrayList<Order> orders = ecom.getUsersOrders(1234);
		
		for (Order order : orders) {
			System.out.println("begining of order");
			for (Integer inten : order.getProducts()){{
				System.out.println(inten);
				}
			}
			System.out.println("end of order");
		}
		
		

//		

	}

}
