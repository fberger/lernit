/**
 * 
 */
package it.lern.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class LernitWidget extends Composite implements DisplayManager {

	private static LernitWidgetUiBinder uiBinder = GWT
			.create(LernitWidgetUiBinder.class);

	interface LernitWidgetUiBinder extends UiBinder<Widget, LernitWidget> {
	}
	
	private final Map<String, TitleWidget> widgets = new HashMap<String, TitleWidget>();
	
	{
		widgets.put("pick", new PickLanguageWidget(this));
		widgets.put("entry", new EntryWidget(this));
	}
	
	@UiField
	HeadingElement titleText;
	
	@UiField
	ParagraphElement descriptionText;
	
	@UiField
	MenuBar menuBar;
	
	@UiField
	HTMLPanel titlePanel;
	
	@UiField
	DeckPanel mainPanel;

	public LernitWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
		MenuBar challengeMenu = new MenuBar();
		challengeMenu.addItem("New Challenge", new Command() {
			@Override
			public void execute() {
				History.newItem("entry");
			}
		});
		menuBar.addItem(new MenuItem("Create", challengeMenu));
		//showDisplay("entry", Maps.map("language", "GERMAN", "translation", "ENGLISH"));
		showDisplay("pick", Displayable.EMPTY);
	}
	
	@Override
	public void showDisplay(String id, Map<String, ?> arguments) {
		TitleWidget widget = widgets.get(id);
		int index = mainPanel.getWidgetIndex(widget);
		if (index == -1) {
			mainPanel.add(widget);
			index = mainPanel.getWidgetIndex(widget);
		}
		titleText.setInnerText(widget.getTitle());
		descriptionText.setInnerText(widget.getDescription());
		widget.setData(arguments);
		mainPanel.showWidget(index);
	}

}
