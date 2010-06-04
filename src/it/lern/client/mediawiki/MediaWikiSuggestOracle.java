package it.lern.client.mediawiki;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle;

public class MediaWikiSuggestOracle extends SuggestOracle {

	private final MediaWikiOpenSearch mediaWikiOpenSearch;

	public MediaWikiSuggestOracle(String domain, String locale) {
		mediaWikiOpenSearch = new MediaWikiOpenSearch(domain, locale);
	}
	
	@Override
	public void requestSuggestions(final Request request, final Callback callback) {
		mediaWikiOpenSearch.search(request.getQuery(), new AsyncCallback<MediaWikiOpenSearchResult>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(MediaWikiOpenSearchResult result) {
				callback.onSuggestionsReady(request, new Response(toSuggestions(result.getResults())));
			}
		});
	}
	
	private List<Suggestion> toSuggestions(JsArrayString array) {
		List<Suggestion> suggestions = new ArrayList<Suggestion>();
		for (int i = 0; i < array.length(); i++) {
			String suggestion = array.get(i);
			suggestions.add(new MultiWordSuggestOracle.MultiWordSuggestion(suggestion, suggestion));
		}
		return suggestions;
	}

	
}
