package it.lern.client;

import it.lern.client.flickr.FlickrPhoto;
import it.lern.client.flickr.FlickrPhotoSearch;
import it.lern.client.flickr.FlickrPhotoSizes.Size;
import it.lern.client.mediawiki.MediaWikiApi;
import it.lern.client.mediawiki.MediaWikiImage;
import it.lern.client.mediawiki.MediaWikiOpenSearch;
import it.lern.client.mediawiki.MediaWikiSuggestOracle;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.language.client.LanguageUtils;
import com.google.gwt.language.client.translation.Language;
import com.google.gwt.language.client.translation.Translation;
import com.google.gwt.language.client.translation.TranslationCallback;
import com.google.gwt.language.client.translation.TranslationResult;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class EntryWidget extends TitleWidget {
	
	private static EntryWidgetUiBinder uiBinder = GWT.create(EntryWidgetUiBinder.class);

	interface EntryWidgetUiBinder extends UiBinder<Widget, EntryWidget> {}
	interface EntryWidgetStyle extends CssResource {
	    String selected();
	}
	private MediaWikiApi mediaWikiApi = new MediaWikiApi("wiktionary.org", "de");
	
	private SuggestBox entryBox = new SuggestBox(new MediaWikiSuggestOracle(new MediaWikiOpenSearch(mediaWikiApi)));
	
	@UiField
	FlowPanel imagePanel;
	
	@UiField
	EntryWidgetStyle style;
	
	private String lastImageSearchTerm;

	private Language from;

	private Language to;

	private final DisplayManager displayManager;
	
	private Image selectedImage;
	
	@UiField
	FlowPanel entryBoxContainer;
	
	@UiField
	TextArea translationBox;
		
	public EntryWidget(DisplayManager displayManager) {
		super("Entry", "Enter a new word or phrase to learn.");
		this.displayManager = displayManager;
		initWidget(uiBinder.createAndBindUi(this));
		entryBoxContainer.add(entryBox);
		entryBox.setLimit(4);
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
								translationBox.setText(translation);
							}
						});
						if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
							System.out.println("enter");
							requestPhotos(current);
							translationBox.setFocus(true);
						}
					}
				});
			}
		});
	}
	
	private void addImage(String imageUrl, boolean select) {
		if (imagePanel.getWidgetCount() == 3) {
			return;
		}
		final Image image = new Image(imageUrl);
		image.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectImage(image);
			}
		});
		if (select) {
			selectImage(image);
		}
		imagePanel.add(image);
	}
	
	private void selectImage(Image image) {
		if (selectedImage != null) {
			selectedImage.removeStyleName(style.selected());
		}
		image.addStyleName(style.selected());
		selectedImage = image;
	}

	private void requestPhotos(String searchTerm) {
		if (searchTerm.equals(lastImageSearchTerm)) {
			return;
		}
		lastImageSearchTerm = searchTerm;
		imagePanel.clear();
		requestWiktionaryPhoto(searchTerm);
		requestFlickrPhotos(searchTerm);
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
						addImage(image.getUrl(), i == 0);
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

	@Override
	public void setData(Map<String, ?> data) {
		from = Language.valueOf((String)data.get("language"));
		to = Language.valueOf((String)data.get("translation"));
		mediaWikiApi.setLocale(from.getLangCode());
	}
}
