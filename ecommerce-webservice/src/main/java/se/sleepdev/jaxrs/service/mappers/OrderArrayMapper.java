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

import se.sleepdev.ecommerce.model.Order;

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
public class OrderArrayMapper  implements MessageBodyWriter<ArrayList<Order>> {

	private Gson gson;

	public OrderArrayMapper() {
		gson = new GsonBuilder().registerTypeAdapter(ArrayList.class,
				new OrdersAdapter()).create();
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		// TODO Auto-generated method stub
		return type.isAssignableFrom(ArrayList.class) && genericType.getClass().isAssignableFrom(Order.class);
	}

	@Override
	public long getSize(ArrayList<Order> t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return t.size();
	}

	@Override
	public void writeTo(ArrayList<Order> t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		try (final JsonWriter writer = new JsonWriter(new OutputStreamWriter(
				entityStream))) {
			gson.toJson(t, ArrayList.class, writer);
		}
	}

	private static final class OrdersAdapter implements
			JsonSerializer<ArrayList<Order>>, JsonDeserializer<Order> {
		@Override
		public Order deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			ArrayList<Integer> products2 = new ArrayList<>();

			final JsonObject orderJson = json.getAsJsonObject();
			int userID = orderJson.get("userID").getAsInt();
			int orderID = orderJson.get("orderID").getAsInt();

//			final JsonObject prods = orderJson.getAsJsonObject("products");

			JsonArray jsArray = orderJson.getAsJsonObject().get("products").getAsJsonArray();
			
			
			for (JsonElement jsonElement : jsArray) {
				int prodID = jsonElement.getAsInt();
				products2.add(prodID);
			}

			return new Order(userID, products2, orderID);
		}

		@Override
		public JsonElement serialize(ArrayList<Order> orderlist,
				Type typeOfSrc, JsonSerializationContext context) {
			
			JsonObject ordersJson2 = new JsonObject();
			
			int i = 0;
			for (Order order : orderlist) {
				JsonObject orderJson = new JsonObject();

				orderJson.add("userID", new JsonPrimitive(order.getUserID()));
				orderJson.add("orderID", new JsonPrimitive(order.getOrderID()));
				ArrayList<Integer> products = order.getProducts();

				JsonArray jsonPrrodArray = new JsonArray();
				for (final Integer prodID : products) {
					final JsonPrimitive prods = new JsonPrimitive(prodID);
					jsonPrrodArray.add(prods);
				}
				orderJson.add("products", jsonPrrodArray);
				
				ordersJson2.add("order" + i, orderJson);
				i++;
			}
			

			return ordersJson2;
		}

	}

}