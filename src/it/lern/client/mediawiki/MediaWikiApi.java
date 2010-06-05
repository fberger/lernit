package it.lern.client.mediawiki;

import it.lern.client.Uris;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MediaWikiApi {
	
	private final String apiUrl;
	
	public static final String ACTION = "action";

	private final JsonpRequestBuilder builder = new JsonpRequestBuilder();

	public MediaWikiApi(String domain, String locale) {
		apiUrl = "http://" + locale + "." + domain + "/w/api.php?format=json";
	}
	
	public <T extends JavaScriptObject> void request(Map<String, String> parameters, AsyncCallback<T> callback) {
		builder.requestObject(apiUrl + Uris.buildParameters(parameters), callback);
	}
	
	public void search(String searchTerm, AsyncCallback<MediaWikiOpenSearchResult> callback) {
		new MediaWikiOpenSearch(this).search(searchTerm, callback);
	}
	
	public void getImages(String article, AsyncCallback<JsArray<MediaWikiImage>> callback) {
		new MediaWikiImagesRequest(this).request(article, callback);
	}
	
}
