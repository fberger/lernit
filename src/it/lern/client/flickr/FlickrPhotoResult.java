package it.lern.client.flickr;

import com.google.gwt.core.client.JavaScriptObject;

class FlickrPhotoResult extends JavaScriptObject {

	protected FlickrPhotoResult() {
	}
	
	public final native String getId() /*-{
	    return this.id;
	}-*/;
	
}
