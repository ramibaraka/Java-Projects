package se.sleepdev.jaxrs.service.mappers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import se.sleepdev.ecommerce.model.Product;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonWriter;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdArrayMapper implements MessageBodyWriter<ArrayList<Product>> {

	private Gson gson;

	public ProdArrayMapper() {
		gson = new GsonBuilder().registerTypeAdapter(ArrayList.class,
				new ProductsAdapter()).create();
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return type.isAssignableFrom(ArrayList.class) && genericType.getClass().isAssignableFrom(Product.class);
	}

	@Override
	public long getSize(ArrayList<Product> t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return t.size();
	}

	@Override
	public void writeTo(ArrayList<Product> t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		try (final JsonWriter writer = new JsonWriter(new OutputStreamWriter(
				entityStream))) {
			gson.toJson(t, ArrayList.class, writer);
		}
	}

	private static final class ProductsAdapter implements
			JsonSerializer<ArrayList<Product>>, JsonDeserializer<Product> {
		@Override
		public Product deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			final JsonObject userJson = json.getAsJsonObject();
			final int productId = userJson.get("productId").getAsInt();
			final String name = userJson.get("productName").getAsString();
			final int price = userJson.get("productPrice").getAsInt();
			return new Product(productId, name, price);
		}

		@Override
		public JsonElement serialize(ArrayList<Product> productList,
				Type typeOfSrc, JsonSerializationContext context) {
			JsonObject productsJson = new JsonObject();

			JsonArray products = new JsonArray();
			for (Product product : productList) {
				JsonObject jsonProduct = new JsonObject();

				jsonProduct.add("productId",
						new JsonPrimitive(product.getProductId()));
				jsonProduct.add("productName",
						new JsonPrimitive(product.getProductName()));
				jsonProduct.add("productPrice",
						new JsonPrimitive(product.getProductPrice()));
				products.add(jsonProduct);
			}
			productsJson.add("products", products);

			return productsJson;
		}

	}

}