package it.lern.client.flickr;

import it.lern.client.Uris;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FlickrApi {
	
	private final String flickrUrl = "http://api.flickr.com/services/rest/?api_key=f28d7fd98583920ebded59f0ac56ecfa&format=json";
	
	private final String callbackName = "jsoncallback";
	
	public static final String METHOD = "method";

	private final JsonpRequestBuilder builder = new JsonpRequestBuilder();

	public FlickrApi() {
		builder.setCallbackParam(callbackName);
	}
	
	public <T extends JavaScriptObject> void request(Map<String, String> parameters, AsyncCallback<T> callback) {
		builder.requestObject(flickrUrl + Uris.buildParameters(parameters), callback);
	}
}
