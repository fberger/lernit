package it.lern.client;

import it.lern.shared.Entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

public class Quizz implements EntryPoint {

	private List<Entry> entries;
	private Button iKnowButton;
	private Button noClueButton;
	private Label entryLabel;
	
	@Override
	public void onModuleLoad() {
		entries = new ArrayList<Entry>(Arrays.asList(Entry.ENTRIES));
		RootPanel rootPanel = RootPanel.get();
		rootPanel.clear();
		
		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		rootPanel.add(panel);
		
		entryLabel = new Label();
		panel.add(entryLabel);
		
		iKnowButton = new Button("I know!");
		panel.add(iKnowButton);
		iKnowButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				entries.remove(0);
				if (!entries.isEmpty()) {
					showNext();
				} else {
					entryLabel.setText("");
				}
			}
		});
		
		noClueButton = new Button("No clue");
		panel.add(noClueButton);
		noClueButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Entry entry = entries.remove(0);
				int size = entries.size();
				int offset = Math.min(2, size);
				int newIndex = offset + Random.nextInt(size - offset);
				entries.add(newIndex, entry);
				showNext();
			}
		});
		
		showNext();
	}

	private void showNext() {
		entryLabel.setText(entries.get(0).getEntry());
	}

}
