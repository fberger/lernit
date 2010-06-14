package it.lern.client;

import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.Widget;

public class LazyLessonWidget extends LazyPanel {

	@Override
	protected Widget createWidget() {
		return new LessonWidget();
	}

}
