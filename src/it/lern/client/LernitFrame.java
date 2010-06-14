package it.lern.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LernitFrame extends Composite {

	private FlexTable table;
	
	private DeckPanel mainPanel = new DeckPanel();

	private StackPanel menuPanel = new StackPanel();
	
	private LazyEntryWidget entryWidget = new LazyEntryWidget();
	
	private LazyLessonWidget lessonWidget = new LazyLessonWidget();

	public LernitFrame() {
		table = new FlexTable();
		table.setCellPadding(20);
		initWidget(table);
		
		menuPanel.setHeight("500px");
		DecoratorPanel decorator = new DecoratorPanel();
		decorator.add(menuPanel);
		table.setWidget(0, 0, decorator);
		table.getFlexCellFormatter().setRowSpan(0, 0, 3);
		
		createCreate();
		createPracticeMenu();
		
		table.setWidget(0, 1, mainPanel);
		table.getFlexCellFormatter().setColSpan(0, 1, 2);
		table.getFlexCellFormatter().setRowSpan(0, 1, 3);
		
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String value = event.getValue();
				if (value.contains(".")) {
					if (value.startsWith("lesson")) {
						showWidget(lessonWidget);
					}
				} else {
					if (value.equals("entry")) {
						showWidget(entryWidget);
					}
				} 
			}
		});
	}
	
	private void createCreate() {
		Hyperlink link = new Hyperlink("New Challenge", "entry");
		VerticalPanel panel = new VerticalPanel();
		panel.add(link);
		menuPanel.add(panel, "Create");
	}
	
	private void createPracticeMenu() {
		VerticalPanel panel = new VerticalPanel();
		for (Lesson lesson : Lesson.LESSONS) {
			panel.add(new Hyperlink(lesson.getName(), "lesson." + lesson.getId()));
		}
		menuPanel.add(panel, "Practice");
	}
	
	public void showWidget(Widget widget) {
		int index = mainPanel.getWidgetIndex(widget);
		if (index == -1) {
			mainPanel.add(widget);
		}
		index = mainPanel.getWidgetIndex(widget);
		mainPanel.showWidget(index);
	}
	
}
