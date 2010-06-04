package it.lern.client.mediawiki;

import it.lern.client.Maps;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class MediaWikiOpenSearch {
	
	private final MediaWikiApi mediaWikiApi;

	public MediaWikiOpenSearch(String domain, String locale) {
		mediaWikiApi = new MediaWikiApi(domain, locale);
	}
	
	public void search(String searchTerm, final AsyncCallback<MediaWikiOpenSearchResult> callback) {
		mediaWikiApi.request(Maps.of(MediaWikiApi.ACTION, "opensearch", "search", searchTerm), callback); 
	}
	
	
}
