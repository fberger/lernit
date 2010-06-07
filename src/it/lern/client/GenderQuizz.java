package it.lern.client;

import it.lern.shared.Entry;
import it.lern.shared.Entry.Category;
import it.lern.shared.Entry.Gender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.language.client.translation.Language;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GenderQuizz implements EntryPoint {

	private ArrayList<Entry> entries;
	private Label entryLabel;

	@Override
	public void onModuleLoad() {
		entries = new ArrayList<Entry>(Arrays.asList(Entry.ENTRIES));
		
		RootPanel rootPanel = RootPanel.get();
		rootPanel.clear();
		
		VerticalPanel panel = new VerticalPanel();
		rootPanel.add(panel);
		
		panel.add(new Label("What article goes with this word?"));
		
		entryLabel = new Label();
		panel.add(entryLabel);
		
		Language entryLanguage = entries.get(0).getLanguages().getEntryLanguage();
		Gender[] genders = Genders.getGenders(entryLanguage);
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		panel.add(buttonPanel);
		
		for (Gender gender : genders) {
			Button button = new Button(Genders.getArticle(gender, entryLanguage));
			final Gender buttonGender = gender;
			buttonPanel.add(button);
			button.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Entry entry = entries.get(0);
					if (entry.get(Gender.class) == buttonGender) {
						System.out.println("correct");
					} else {
						System.out.println("wrong");
					}
					showNext();
				}
			});
		}
		
		showNext();
	}

	private void showNext() {
		if (entries.isEmpty()) {
			entryLabel.setText("");
			return;
		}
		entries.remove(0);
		for (Iterator<Entry> i = entries.iterator(); i.hasNext();) {
			Entry entry = i.next();
			if (entry.getCategory() != Category.NOUN) {
				i.remove();
				continue;
			}
			entryLabel.setText(entry.getEntry());
			break;
		}
	}

}
