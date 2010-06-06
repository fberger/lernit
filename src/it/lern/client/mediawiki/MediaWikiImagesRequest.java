package it.lern.client.mediawiki;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.lern.client.Maps;

public class MediaWikiImagesRequest {
	
	private final MediaWikiApi mediaWikiApi;

	public MediaWikiImagesRequest(MediaWikiApi mediaWikiApi) {
		this.mediaWikiApi = mediaWikiApi;
	}
	
	public void request(String article, final AsyncCallback<JsArray<MediaWikiImage>> callback) {
		mediaWikiApi.request(Maps.map(MediaWikiApi.ACTION, "query", 
				"prop", "images", "titles", article), new AsyncCallback<MediaWikiImages>() {
					@Override
					public void onFailure(Throwable caught) {
						callback.onFailure(caught);
					}
					@Override
					public void onSuccess(MediaWikiImages result) {
						request(result, callback);
					}
					
				});
	}
	
	public void request(MediaWikiImages images,	final AsyncCallback<JsArray<MediaWikiImage>> callback) {
		mediaWikiApi.request(Maps.map(MediaWikiApi.ACTION, "query",
				"titles", images.getTitles().join("|"), "prop", "imageinfo",
				"iiprop", "url|dimensions|size|mime"), new AsyncCallback<MediaWikiImageInfos>() {
					@Override
					public void onFailure(Throwable caught) {
						callback.onFailure(caught);
					}
					@Override
					public void onSuccess(MediaWikiImageInfos result) {
						callback.onSuccess(result.getImages());
					}
				});
	}
	
	private static class MediaWikiImages extends JavaScriptObject {
		@SuppressWarnings("unused")
		protected MediaWikiImages() {
		}
		public final native JsArrayString getTitles() /*-{
			var pages = this.query.pages;
			var titles = [];
			for (var page in pages) {
				var images = pages[page].images;
				for (var i = 0; i < images.length; i++) {
					titles.push(images[i].title);
				}
			}
			return titles;
		}-*/;
	}

	private static class MediaWikiImageInfos extends JavaScriptObject {
		@SuppressWarnings("unused")
		protected MediaWikiImageInfos() {
		}
		public final native JsArray<MediaWikiImage> getImages() /*-{
			var pages = this.query.pages;
			var images = [];
			for (var page in pages) {
				images.push(pages[page]);
			}
			return images;
		}-*/;
	}
}
