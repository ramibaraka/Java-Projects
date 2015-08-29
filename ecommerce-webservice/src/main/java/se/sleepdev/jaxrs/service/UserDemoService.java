package se.sleepdev.jaxrs.service;

import java.net.URI;
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
import se.sleepdev.ecommerce.model.User;
import se.sleepdev.ecommerce.service.OrderRepository;
import se.sleepdev.ecommerce.service.ProductRepository;
import se.sleepdev.ecommerce.service.RepositryException;
import se.sleepdev.ecommerce.service.UserRepository;

@Path("user")
@Produces({ MediaType.APPLICATION_JSON })
// ,MediaType.APPLICATION_XML})
@Consumes({ MediaType.APPLICATION_JSON })
// , MediaType.APPLICATION_XML})
public final class UserDemoService {

	@Context
	public UriInfo uriInfo;

	UserRepository userRep = new UserRepository();
	OrderRepository orderRep = new OrderRepository();
	ProductRepository productRep = new ProductRepository();
	EcommerceManager ecommerce = new EcommerceManager(productRep, userRep,
			orderRep);

	@GET
	@Path("{userId}")
	public Response getUser(@PathParam("userId") final int userId) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final User user = ecommerce.getUser(userId);
			return Response.ok(user).build();
		} catch (RepositryException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new BadMessageException("No user with id:");
	}
	
	@GET
	@Path("All")
	public Response getAllUsers() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final ArrayList<User> users = ecommerce.getAllUsers();
//			return Response.ok(users).build();
			return Response.ok(new Gson().toJson(users)).build();
		} catch (RepositryException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new BadMessageException("No user with id:");
	}

	@PUT
	public Response addUser(final User user) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			User addedUser = ecommerce.addUser(user);
			final int userId = addedUser.getUserId();
			String addedStringId = Integer.toString(userId);
			final URI location = uriInfo.getAbsolutePathBuilder()
					.path(addedStringId).build();
			return Response.created(location).build();

		} catch (Exception e) {
			throw new BadMessageException("Add failed");
		}

	}

	@DELETE
	@Path("{userId}")
	public final Response deleteUser(@PathParam("userId") final int userId)
			throws RepositryException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return Response.ok(ecommerce.removeUser(userId)).build();
		} catch (Exception e) {
			throw new BadMessageException("Delete failed");
		}
	}

	@POST
	@Path("{userId}")
	public Response updateUser(@PathParam("userId") final int userId,
			final User user) throws RepositryException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println(userId);
			final User use = ecommerce.updateUser(userId, user);
			System.out.println(userId);
			return Response.ok(use).build();
		} catch (Exception e) {
			throw new BadMessageException("Update failed");
		}

	}

}
