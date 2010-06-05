package it.lern.client.mediawiki;

import it.lern.client.Maps;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class MediaWikiOpenSearch {
	
	private final MediaWikiApi mediaWikiApi;

	public MediaWikiOpenSearch(MediaWikiApi mediaWikiApi) {
		this.mediaWikiApi = mediaWikiApi;
	}
	
	public void search(String searchTerm, final AsyncCallback<MediaWikiOpenSearchResult> callback) {
		mediaWikiApi.request(Maps.map(MediaWikiApi.ACTION, "opensearch", "search", searchTerm), callback); 
	}
	
	
}
