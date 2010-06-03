package it.lern.client.flickr;

import com.google.gwt.core.client.JavaScriptObject;

public class FlickrPhotoSizes extends JavaScriptObject {
	
	public static enum Size {
		Square,
		Thumbnail,
		Small,
		Medium,
		Large,
		Original
	}
	
	protected FlickrPhotoSizes() {
	}

	public final String getSourceUrls(Size size) {
		return getSourceUrl(size.ordinal());
	}
	
	private final native String getSourceUrl(int index) /*-{
		return this.sizes.size[index].source;
	}-*/;
	
}
