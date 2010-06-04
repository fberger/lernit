package it.lern.client.mediawiki;

import com.google.gwt.core.client.JavaScriptObject;

public class MediaWikiImage extends JavaScriptObject {
	protected MediaWikiImage() {
	}
	
	public final native String getTitle() /*-{
		return this.title;
	}-*/;
}
