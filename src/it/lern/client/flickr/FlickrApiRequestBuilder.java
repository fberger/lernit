package it.lern.client.flickr;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FlickrApiRequestBuilder {
	
	private final String flickrUrl = "http://api.flickr.com/services/rest/?api_key=f28d7fd98583920ebded59f0ac56ecfa&format=json";
	
	private final String callbackName = "jsoncallback";

	public FlickrApiRequestBuilder() {
	}
	
	public <T extends JavaScriptObject> void request(String apiMethod, Map<String, String> parameters, AsyncCallback<T> callback) {
		JsonpRequestBuilder builder = new JsonpRequestBuilder();
		builder.setCallbackParam(callbackName);
		builder.requestObject(buildUrl(flickrUrl, apiMethod, parameters), callback);
	}

	private String buildUrl(String flickrUrl, String apiMethod, Map<String, String> parameters) {
		StringBuilder builder = new StringBuilder(flickrUrl);
		builder.append('&').append(URL.encodeComponent("method")).append('=').append(URL.encodeComponent(apiMethod));
		for (Entry<String, String> entry : parameters.entrySet()) {
			builder.append('&').append(URL.encodeComponent(entry.getKey())).append('=').append(URL.encodeComponent(entry.getValue()));
		}
		return builder.toString();
	}
	
}
