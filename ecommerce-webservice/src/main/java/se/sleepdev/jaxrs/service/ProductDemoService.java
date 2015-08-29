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
import se.sleepdev.ecommerce.model.Product;
import se.sleepdev.ecommerce.service.OrderRepository;
import se.sleepdev.ecommerce.service.ProductRepository;
import se.sleepdev.ecommerce.service.RepositryException;
import se.sleepdev.ecommerce.service.UserRepository;

@Path("product")
@Produces({ MediaType.APPLICATION_JSON })
// ,MediaType.APPLICATION_XML})
@Consumes({ MediaType.APPLICATION_JSON })
// , MediaType.APPLICATION_XML})
public final class ProductDemoService {
	@Context
	public UriInfo uriInfo;
	ProductRepository productRep = new ProductRepository();
	UserRepository userRep = new UserRepository();
	OrderRepository orderRep = new OrderRepository();
	EcommerceManager ecommerce = new EcommerceManager(productRep, userRep,
			orderRep);

	@GET
	@Path("{productId}")
	public Response getProduct(@PathParam("productId") final int productId) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			final Product prod = ecommerce.getProduct(productId);
			return Response.ok(prod).build();
		} catch (RepositryException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new BadMessageException("No product with that ID");
	}

	@PUT
	public Response createProduct(final Product product) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Product addedProduct = ecommerce.addProduct(product);
			int addedId = addedProduct.getProductId();
			String addedStringId = Integer.toString(addedId);
			final URI location = uriInfo.getAbsolutePathBuilder()
					.path(addedStringId).build();
			Response.ok(addedProduct).build();
			return Response.created(location).build();

		} catch (RepositryException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		throw new BadMessageException("Product add failed");
	}

	@DELETE
	@Path("{productId}")
	public Response deleteProduct(@PathParam("productId") final int productId) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			ecommerce.removeProduct(productId);
			Response.ok().build();
		} catch (RepositryException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new BadMessageException("Product remove failed");
	}

	@POST
	@Path("{productId}")
	public Response uppdateProduct(@PathParam("productId") final int productId,
			final Product product) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Product uppdatedProduct = ecommerce.updateProduct(productId,
					product);
			Response.ok(uppdatedProduct).build();
		} catch (RepositryException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new BadMessageException("Product update failed");

	}
	
	@GET
	@Path("/All")
	public Response getAllProducts() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			final ArrayList<Product> prod = ecommerce.getAllProducts();
//			return Response.ok(prod).build();
			return Response.ok(new Gson().toJson(prod)).build();
		} catch (RepositryException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new BadMessageException("Cant get all products");
	}
}
