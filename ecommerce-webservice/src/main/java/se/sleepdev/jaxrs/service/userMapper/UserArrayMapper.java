package se.sleepdev.jaxrs.service.userMapper;

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

import se.sleepdev.ecommerce.model.User;

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
public class UserArrayMapper  implements MessageBodyWriter<ArrayList<User>> {

	private Gson gson;

	public UserArrayMapper() {
		gson = new GsonBuilder().registerTypeAdapter(ArrayList.class,
				new UsersAdapter()).create();
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return (type.isAssignableFrom(ArrayList.class) && genericType.getClass().isAssignableFrom(User.class));
	}

	@Override
	public long getSize(ArrayList<User> t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return t.size();
	}

	@Override
	public void writeTo(ArrayList<User> b, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		try (final JsonWriter writer = new JsonWriter(new OutputStreamWriter(
				entityStream))) {
			gson.toJson(b, ArrayList.class, writer);
		}
	}

	private static final class UsersAdapter implements
			JsonSerializer<ArrayList<User>>, JsonDeserializer<User> {
		@Override
		public User deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			final JsonObject userJson = json.getAsJsonObject();
			final int userId = userJson.get("userId").getAsInt();
			final String name = userJson.get("username").getAsString();
			final String password = userJson.get("password").getAsString();
			return new User(userId, name, password);
		}

		@Override
		public JsonElement serialize(ArrayList<User> userlist,
				Type typeOfSrc, JsonSerializationContext context) {
			JsonObject usersJson = new JsonObject();

			JsonArray users = new JsonArray();
			for (User user : userlist) {
				JsonObject userProduct = new JsonObject();

				userProduct.add("userId",
						new JsonPrimitive(user.getUserId()));
				userProduct.add("username",
						new JsonPrimitive(user.getUserName()));
				userProduct.add("password",
						new JsonPrimitive(user.getPassWord()));
				users.add(userProduct);
			}
			usersJson.add("users", users);

			return usersJson;
		}

	}

}