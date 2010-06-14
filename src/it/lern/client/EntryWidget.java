package it.lern.client;

import it.lern.client.flickr.FlickrPhoto;
import it.lern.client.flickr.FlickrPhotoSearch;
import it.lern.client.flickr.FlickrPhotoSizes.Size;
import it.lern.client.mediawiki.MediaWikiApi;
import it.lern.client.mediawiki.MediaWikiImage;
import it.lern.client.mediawiki.MediaWikiOpenSearch;
import it.lern.client.mediawiki.MediaWikiSuggestOracle;

import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.language.client.LanguageUtils;
import com.google.gwt.language.client.translation.Language;
import com.google.gwt.language.client.translation.Translation;
import com.google.gwt.language.client.translation.TranslationCallback;
import com.google.gwt.language.client.translation.TranslationResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

public class EntryWidget extends Composite {
	
	private MediaWikiApi mediaWikiApi = new MediaWikiApi("wiktionary.org", "de");
	
	private SuggestBox entryBox = new SuggestBox(new MediaWikiSuggestOracle(new MediaWikiOpenSearch(mediaWikiApi)));
	
	private TextArea translationArea = new TextArea();
	
	private Button saveButton = new Button("Save");
	
	private Button previousButton = new Button("Previous");
	
	private FlowPanel imagePanel = new FlowPanel();
	
	private String lastImageSearchTerm;

	private final Language from;

	private final Language to;
	
	public EntryWidget(Language from, Language to) {
		this.from = from;
		this.to = to;
		mediaWikiApi.setLocale(from.getLangCode());
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		
		initWidget(decoratorPanel);
		
		FlexTable layout = new FlexTable();
		FlexCellFormatter formatter = layout.getFlexCellFormatter();
		//layout.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		//layout.setSize("600px", "400px");
		decoratorPanel.add(layout);
		
		layout.setText(0, 0, "Entry:");
		layout.setWidget(1, 0, entryBox);
		formatter.setColSpan(1, 0, 2);
		formatter.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
		
		layout.setText(2, 0, "Translation:");
		layout.setWidget(3, 0, translationArea);
		formatter.setColSpan(3, 0, 2);
		
		layout.setText(0, 2, "Picture:");
		layout.setWidget(1, 2, imagePanel);
		
		layout.setText(2, 2, "Sound:");
		
		layout.setWidget(5, 0, previousButton);
		layout.setWidget(5, 1, saveButton);
		
		installKeyListeners();
	}
	
	private void installKeyListeners() {
		LanguageUtils.loadTranslation(new Runnable() {
			public void run() {
				entryBox.addKeyUpHandler(new KeyUpHandler() {
					@Override
					public void onKeyUp(KeyUpEvent event) {
						final String current = entryBox.getText();
						Translation.translate(current, from,
								to, new TranslationCallback() {
							@Override
							protected void onCallback(TranslationResult result) {
								String translation = result.getTranslatedText();
								translationArea.setText(translation);
							}
						});
						if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
							System.out.println("enter");
							requestPhotos(current);
							translationArea.setFocus(true);
						}
					}
				});
			}
		});
	}
	
	private void addImage(String imageUrl, boolean select) {
		Image image = new Image(imageUrl);
		image.setSize("100px", "100px");
		imagePanel.add(image);
	}
	
	private void requestPhotos(String searchTerm) {
		if (searchTerm.equals(lastImageSearchTerm)) {
			return;
		}
		lastImageSearchTerm = searchTerm;
		imagePanel.clear();
		requestWiktionaryPhoto(searchTerm);
		//requestFlickrPhotos(searchTerm);
	}
	
	private void requestWiktionaryPhoto(String searchTerm) {
		mediaWikiApi.getImages(searchTerm, new AsyncCallback<JsArray<MediaWikiImage>>() {
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
			@Override
			public void onSuccess(JsArray<MediaWikiImage> array) {
				for (int i = 0; i < array.length(); i++) {
					MediaWikiImage image = array.get(i);
					if (image.getContentType().equals("image/jpeg")) {
						System.out.println("wiki image " + image.getTitle());
						addImage(image.getUrl(), true);
					}
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
					for (FlickrPhoto photo : photos) {
						System.out.println("flickr photo ");
						addImage(photo.getSourceUrl(Size.Square), false);
					}
				} 
			});
	}
}
