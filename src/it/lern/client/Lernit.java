package it.lern.client;

import it.lern.client.flickr.FlickrPhoto;
import it.lern.client.flickr.FlickrPhotoSearch;
import it.lern.client.flickr.FlickrPhotoSizes.Size;
import it.lern.client.mediawiki.MediaWikiSuggestOracle;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.language.client.LanguageUtils;
import com.google.gwt.language.client.translation.Language;
import com.google.gwt.language.client.translation.Translation;
import com.google.gwt.language.client.translation.TranslationCallback;
import com.google.gwt.language.client.translation.TranslationResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Lernit implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		initializeGoogleTranslation();
	}
	
	private void initializeInput() {
		final SuggestBox input = new SuggestBox(new MediaWikiSuggestOracle("wiktionary.org", "en"));
		RootPanel.get("input").add(input);
		input.setFocus(true);
		final Label suggestions = new Label();
		RootPanel.get("suggestions").add(suggestions);
		input.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				final String current = input.getText();
				Translation.translate(current, Language.ENGLISH,
						Language.GERMAN, new TranslationCallback() {
					@Override
					protected void onCallback(TranslationResult result) {
						String translation = result.getTranslatedText();
						suggestions.setText(translation);
					}
				});
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					requestFlickrPhotos(current);
				}
			}
		});
	}
	
	private void requestFlickrPhotos(String searchTerm) {
		FlickrPhotoSearch search = new FlickrPhotoSearch();
		search.search(searchTerm, 4, new AsyncCallback<List<FlickrPhoto>>() {
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
			@Override
			public void onSuccess(List<FlickrPhoto> photos) {
				RootPanel panel = RootPanel.get("images");
				panel.clear();
				for (FlickrPhoto photo : photos) {
					panel.add(new Image(photo.getSourceUrl(Size.Square)));
				}
			} 
		});
	}
	
	private void initializeGoogleTranslation() {
		LanguageUtils.loadTranslation(new Runnable() {
			public void run() {
				initializeInput();
			}
		});
	}
	
}
