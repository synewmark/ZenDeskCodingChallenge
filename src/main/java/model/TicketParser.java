package model;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import model.Ticket.PRIORITY;
import model.Ticket.STATUS;
import model.Ticket.TYPE;

public class TicketParser {
	static Gson gson = new GsonBuilder().registerTypeAdapter(Ticket.TYPE.class, new deserializeTicketType())
			.registerTypeAdapter(Ticket.PRIORITY.class, new deserializeTicketPriority())
			.registerTypeAdapter(Ticket.STATUS.class, new deserializeTicketStatus())
			.registerTypeAdapter(LocalDateTime.class, new deserializeTimeStamps()).create();

	public static Ticket getTicket(String jsonString) {
		return gson.fromJson(jsonString, Ticket.class);

	}

	static class deserializeTicketType implements JsonDeserializer<Ticket.TYPE> {
		@Override
		public TYPE deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return Ticket.TYPE.valueOf(json.getAsString().toUpperCase());
		}
	}

	static class deserializeTicketPriority implements JsonDeserializer<Ticket.PRIORITY> {
		@Override
		public PRIORITY deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return Ticket.PRIORITY.valueOf(json.getAsString().toUpperCase());
		}
	}

	static class deserializeTicketStatus implements JsonDeserializer<Ticket.STATUS> {
		@Override
		public STATUS deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return Ticket.STATUS.valueOf(json.getAsString().toUpperCase());
		}
	}

	static class deserializeTimeStamps implements JsonDeserializer<LocalDateTime> {
		@Override
		public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			String jsonString = json.getAsString();
			return LocalDateTime.parse(jsonString.substring(0, jsonString.length() - 1));
		}
	}

}
