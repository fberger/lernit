package it.lern.client.mediawiki;

import it.lern.client.Uris;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MediaWikiApi {
	
	public static final String ACTION = "action";

	private final JsonpRequestBuilder builder = new JsonpRequestBuilder();

	private final String domain;

	private String locale;
	
	private String apiUrl;

	public MediaWikiApi(String domain, String locale) {
		this.domain = domain;
		this.locale = locale;
		updateApiUrl();
	}
	
	private void updateApiUrl() {
		apiUrl = "http://" + locale + "." + domain + "/w/api.php?format=json";
	}
	
	public void setLocale(String locale) {
		this.locale = locale;
		updateApiUrl();
	}
	
	public <T extends JavaScriptObject> void request(Map<String, String> parameters, AsyncCallback<T> callback) {
		String url = apiUrl + Uris.buildParameters(parameters);
		builder.requestObject(url, callback);
	}
	
	public void search(String searchTerm, AsyncCallback<MediaWikiOpenSearchResult> callback) {
		new MediaWikiOpenSearch(this).search(searchTerm, callback);
	}
	
	public void getImages(String article, AsyncCallback<JsArray<MediaWikiImage>> callback) {
		new MediaWikiImagesRequest(this).request(article, callback);
	}
	
}
