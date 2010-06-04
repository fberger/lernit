package it.lern.client;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.http.client.URL;

public class Uris {

	public static String buildParameters(Map<String, String> parameters) {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, String> entry : parameters.entrySet()) {
			builder.append('&').append(URL.encodeComponent(entry.getKey())).append('=').append(URL.encodeComponent(entry.getValue()));
		}
		return builder.toString();
	}
	
}
