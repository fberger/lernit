package it.lern.client.mediawiki;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle;

public class MediaWikiSuggestOracle extends SuggestOracle {

	private final MediaWikiOpenSearch mediaWikiOpenSearch;

	public MediaWikiSuggestOracle(MediaWikiOpenSearch mediaWikiOpenSearch) {
		this.mediaWikiOpenSearch = mediaWikiOpenSearch;
	}
	
	@Override
	public void requestSuggestions(final Request request, final Callback callback) {
		mediaWikiOpenSearch.search(request.getQuery(), new AsyncCallback<MediaWikiOpenSearchResult>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(MediaWikiOpenSearchResult result) {
				callback.onSuggestionsReady(request, new Response(toSuggestions(result.getResults(), request.getLimit())));
			}
		});
	}
	
	private List<Suggestion> toSuggestions(JsArrayString array, int limit) {
		List<Suggestion> suggestions = new ArrayList<Suggestion>();
		limit = Math.min(limit, array.length());
		for (int i = 0; i < limit; i++) {
			String suggestion = array.get(i);
			suggestions.add(new MultiWordSuggestOracle.MultiWordSuggestion(suggestion, suggestion));
		}
		return suggestions;
	}

	
}
