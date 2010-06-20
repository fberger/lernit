package it.lern.client;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.language.client.translation.Language;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class PickLanguageWidget extends TitleWidget {

	private static PickLanguageWidgetUiBinder uiBinder = GWT
			.create(PickLanguageWidgetUiBinder.class);

	interface PickLanguageWidgetUiBinder extends
			UiBinder<Widget, PickLanguageWidget> {
	}
	
	@UiField
	ListBox languageBox;
	
	@UiField
	ListBox translationBox;

	@UiField
	Button nextButton;

	private final DisplayManager displayManager;

	public PickLanguageWidget(DisplayManager displayManager) {
		super("Pick Languages", "Pick the language you would like to learn and the language you speak fluently.");
		this.displayManager = displayManager;
		initWidget(uiBinder.createAndBindUi(this));
		for (Language language : Language.values()) {
			languageBox.addItem(language.toString());
			translationBox.addItem(language.toString());
		}
	}

	@UiHandler("nextButton")
	void onClick(ClickEvent e) {
		String language = languageBox.getItemText(languageBox.getSelectedIndex());
		String translation = languageBox.getItemText(translationBox.getSelectedIndex());
		displayManager.showDisplay("entry", Maps.map("language", language, "translation", translation));
	}

	@Override
	public void setData(Map<String, ?> data) {
	}

}
