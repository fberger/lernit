/**
 * 
 */
package it.lern.client.mediawiki;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

class MediaWikiOpenSearchResult extends JavaScriptObject {
	
	protected MediaWikiOpenSearchResult() {
	}
	
	public final native String getSearchTerm() /*-{
		return this[0];
	}-*/;
	
	public final native JsArrayString getResults() /*-{
		return this[1];
	}-*/;
}