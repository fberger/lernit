package it.lern.client;

import com.google.gwt.user.client.ui.Composite;

public abstract class TitleWidget extends Composite implements Displayable {

	private String title;
	private String description;

	public TitleWidget(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
}
