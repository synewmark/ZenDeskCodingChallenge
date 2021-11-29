package backend;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import model.Ticket;
import model.TicketParser;

public class TicketHandler {
	static final int numberToReturn = 25;
	private final String subdomain;
	private final String emailAddress;
	private final String pw;
	private final SortBy sortBy;
	private final SortOrder sortOrder;
	private URL subsequentCallsURL;
	private boolean hasMore = true;

	public TicketHandler(String subdomain, String emailAddress, String pw, SortBy sortBy, SortOrder sortOrder) {
		this.subdomain = subdomain;
		this.emailAddress = emailAddress;
		this.pw = pw;
		this.sortBy = sortBy;
		this.sortOrder = sortOrder;
	}

	public Ticket[] pageTicketResults() throws IOException {
		if (!hasMore) {
			throw new IllegalStateException();
		}
		URL requestURL = getRequestURL();
		JSONTokener jsonTokener = getJsonFromGet(requestURL, emailAddress, pw);
		JSONObject jsonObject = new JSONObject(jsonTokener);
		JSONArray jsonTicketsArray = jsonObject.getJSONArray("tickets");
		Ticket[] tickets = getTicketsFromJSON(jsonTicketsArray);
		setHasMore(jsonObject);
		setSubsequentCallsURL(jsonObject);
		return tickets;
	}

	public boolean hasMore() {
		return hasMore;
	}

	private Ticket[] getTicketsFromJSON(JSONArray array) {
		Ticket[] tickets = new Ticket[numberToReturn];
		for (int i = 0; i < array.length(); i++) {
			JSONObject jsonTicket = array.getJSONObject(i);
			Ticket ticket = TicketParser.getTicket(jsonTicket.toString());
			tickets[i] = ticket;
		}
		return tickets;
	}

	private void setSubsequentCallsURL(JSONObject jsonObject) {
		JSONObject links = jsonObject.getJSONObject("links");
		Object nextURL = links.get("next");

		try {
			subsequentCallsURL = new URL(nextURL.toString());
		} catch (MalformedURLException e) {
			throw new IllegalStateException();
		}
	}

	private void setHasMore(JSONObject jsonObject) {
		JSONObject meta = jsonObject.getJSONObject("meta");
		boolean has_moreJson = meta.getBoolean("has_more");
		hasMore = has_moreJson;
	}

	private JSONTokener getJsonFromGet(URL url, String email, String pw) throws IOException {
		JSONTokener tokener;
		HttpsURLConnection http;
		http = (HttpsURLConnection) url.openConnection();
		http.setRequestProperty("Accept", "application/json");
		http.setRequestProperty("Content-Type", "application/json");
		if (pw != null) {
			http.addRequestProperty("Authorization",
					"Basic " + new String(Base64.getEncoder().encode((email + ':' + pw).getBytes())));
		}
		if (sortBy != null) {
			http.addRequestProperty("sort_by", sortBy.toString().toLowerCase());
		}
		if (sortOrder != null) {
			http.addRequestProperty("sort_order", sortBy.getApiName());
		}
		int responseCode = http.getResponseCode();
		if (responseCode < 200 || responseCode >= 300) {
			throw new IOException("Invalid reponse code from the server: " + responseCode + "\n With response message: "
					+ http.getResponseMessage() + new String(http.getErrorStream().readAllBytes()));
		}
		String string = new String(http.getInputStream().readAllBytes());
		tokener = new JSONTokener(string);
		http.disconnect();
		return tokener;
	}

	private URL getRequestURL() {
		return subsequentCallsURL != null ? subsequentCallsURL : getInitialRequestURL();
	}

	private URL getInitialRequestURL() {
		try {
			return new URL(String.format("https://%s.zendesk.com/api/v2/tickets.json?page[size]=25", subdomain));
//			return new URL("https://enjhc7o7zv8epuu.m.pipedream.net");
		} catch (MalformedURLException e) {
			throw new IllegalStateException("URL invalid!");
		}
	}

	public enum SortBy {
		ASSIGNEE("assignee"), ASSIGNEENAME("assignee.name"), CREATED_AT("created_at"), GROUP("group"), ID("id"),
		LOCALE("locale"), REQUESTERS("requesters"), REQUESTERSNAME("requesters.name"), STATUS("status"),
		SUBJECT("subject"), UPDATED_AT("updated_at");

		private final String apiName;

		SortBy(String apiName) {
			this.apiName = apiName;
		}

		public String getApiName() {
			return apiName;
		}
	}

	public enum SortOrder {
		ASC, DESC
	}
}