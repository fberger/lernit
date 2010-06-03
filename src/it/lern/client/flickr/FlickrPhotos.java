package it.lern.client.flickr;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class FlickrPhotos extends JavaScriptObject {
	
	protected FlickrPhotos() {
	}

	public final native JsArray<FlickrPhotoResult> getPhotos() /*-{
		return this.photos.photo;
	}-*/;
	
}
