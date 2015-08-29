package se.sleepdev.jaxrs.service;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;

import se.sleepdev.ecommerce.EcommerceManager;
import se.sleepdev.ecommerce.model.Order;
import se.sleepdev.ecommerce.service.OrderRepository;
import se.sleepdev.ecommerce.service.ProductRepository;
import se.sleepdev.ecommerce.service.RepositryException;
import se.sleepdev.ecommerce.service.UserRepository;

@Path("order")
@Produces({ MediaType.APPLICATION_JSON })
// ,MediaType.APPLICATION_XML})
@Consumes({ MediaType.APPLICATION_JSON })
// , MediaType.APPLICATION_XML})
public final class OrderDemoTest {

	@Context
	public UriInfo uriInfo;

	UserRepository userRep = new UserRepository();
	OrderRepository orderRep = new OrderRepository();
	ProductRepository productRep = new ProductRepository();
	EcommerceManager ecommerce = new EcommerceManager(productRep, userRep,
			orderRep);
	
	@GET
	@Path("{orderId}")
	public Response getOrder(@PathParam("orderId") final int orderId) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final Order order = ecommerce.getOrder(orderId);
			return Response.ok(order).build();
		} catch (RepositryException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new BadMessageException("No order with id:");
	}

	@PUT
	public Response postOrder(final Order order) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			int userId = order.getUserID();
			ArrayList<Integer> products = order.getProducts();
			Order order2 = ecommerce.createOrder(userId, products);

			return Response.ok(order2).build();

		} catch (RepositryException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new BadMessageException("Add failed");
	}
	
	@POST
	@Path("{orderId}")
	public Response updateOrder(final Order order) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			int orderId = order.getOrderID();
			ecommerce.updateOrder(orderId, order);

			return Response.ok(order).build();

		} catch (RepositryException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new BadMessageException("Update failed");
	}
	
	@DELETE
	@Path("{orderId}")
	public Response deleteOrder(@PathParam("orderId") final int orderId) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			ecommerce.removeOrder(orderId);
			return Response.ok().build();
		} catch (RepositryException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new BadMessageException("Delete failed");
	}
	
	@GET
	@Path("All/{userID}")
	public Response getAllOrders(@PathParam("userID") final int userID) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final ArrayList<Order> orders = ecommerce.getUsersOrders(userID);
			return Response.ok(new Gson().toJson(orders)).build();
		} catch (RepositryException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new BadMessageException("Request failed");
	}

}
