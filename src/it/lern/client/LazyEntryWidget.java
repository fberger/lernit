package it.lern.client;

import com.google.gwt.language.client.translation.Language;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.Widget;

public class LazyEntryWidget extends LazyPanel {

	public LazyEntryWidget() {
		setVisible(false);
	}
	
	@Override
	protected Widget createWidget() {
		return new EntryWidget(Language.GERMAN, Language.ENGLISH);
	}

}
