package it.lern.client.mediawiki;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.lern.client.Maps;

public class MediaWikiImagesRequest {
	
	private final MediaWikiApi mediaWikiApi;

	public MediaWikiImagesRequest(String domain, String locale) {
		mediaWikiApi = new MediaWikiApi(domain, locale);
	}
	
	public void request(String article, final AsyncCallback<JsArray<MediaWikiImage>> callback) {
		mediaWikiApi.request(Maps.of(MediaWikiApi.ACTION, "query", 
				"prop", "images", "titles", article), new AsyncCallback<MediaWikiImages>() {
					@Override
					public void onFailure(Throwable caught) {
						callback.onFailure(caught);
					}
					@Override
					public void onSuccess(MediaWikiImages result) {
						callback.onSuccess(result.getFiles());
					}
				});
	}
	
	private static class MediaWikiImages extends JavaScriptObject {
		@SuppressWarnings("unused")
		protected MediaWikiImages() {
		}
		public final native JsArray<MediaWikiImage> getFiles() /*-{
			var pages = this.query.pages;
			for (var page in pages) {
				return pages[page].images;
			}
		}-*/;
	}
	
}
