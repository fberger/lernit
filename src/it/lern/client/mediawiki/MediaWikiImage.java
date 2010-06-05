package it.lern.client.mediawiki;

import com.google.gwt.core.client.JavaScriptObject;

public class MediaWikiImage extends JavaScriptObject {
	protected MediaWikiImage() {
	}
	
	public final native String getTitle() /*-{
		return this.title;
	}-*/;
	
	public final native String getUrl() /*-{
		return this.imageinfo[0].url;
	}-*/;
	
	public final native String getDescriptionUrl() /*-{
		return this.imageinfo[0].descriptionurl;
	}-*/;
	
	public final native int getSize() /*-{
		return this.imageinfo[0].size;
	}-*/;
	
	public final native int getWidth() /*-{
		return this.imageinfo[0].width;
	}-*/;
	
	public final native int getHeight() /*-{
		return this.imageinfo[0].height;
	}-*/;
}
