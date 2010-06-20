package it.lern.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class LernitFrame extends Composite {

	private DeckPanel mainPanel = new DeckPanel();

	private LazyLessonWidget lessonWidget = new LazyLessonWidget();

	private MenuBar menuBar;

	private Label titleLabel;

	private Label descriptionLabel;

	public LernitFrame() {
		LayoutPanel layout = new LayoutPanel();
		initWidget(layout);
		
		menuBar = new MenuBar();
		DockLayoutPanel flowPanel = new DockLayoutPanel(Unit.EM);
		flowPanel.addNorth(menuBar, 1.5);
		layout.add(flowPanel);
		layout.setWidgetLeftRight(flowPanel, 10, Unit.PCT, 10, Unit.PCT);
				
		MenuBar challengeMenu = new MenuBar();
		challengeMenu.addItem("New Challenge", new Command() {
			@Override
			public void execute() {
				History.newItem("entry");
			}
		});
		menuBar.addItem(new MenuItem("Create", challengeMenu));
		
		createPracticeMenu();
		
		// title widget
		DecoratorPanel titlePanel = new DecoratorPanel();
		FlowPanel verticalPanel = new FlowPanel();
		titlePanel.add(verticalPanel);
		titleLabel = new Label("Place holder");
		verticalPanel.add(titleLabel);
		descriptionLabel = new Label("Longer text here");
		verticalPanel.add(descriptionLabel);
		flowPanel.addNorth(verticalPanel, 5);
		
//		layout.setWidgetLeftRight(titlePanel, 10, Unit.PCT, 10, Unit.PCT);
//		layout.setWidgetTopHeight(titlePanel, 5, Unit.EM, 20, Unit.EM);
		
						
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		decoratorPanel.add(mainPanel);
		//mainPanel.setSize("780px", "450px");
		//layout.add(decoratorPanel);
		
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String value = event.getValue();
				if (value.contains("/")) {
					if (value.startsWith("lesson")) {
						showWidget(lessonWidget);
					}
				} else {
					if (value.equals("entry")) {
					//	showWidget(entryWidget);
					}
				} 
			}
		});
	}
	
	private void createPracticeMenu() {
		MenuBar lessonBar = new MenuBar();
		for (final Lesson lesson : Lesson.LESSONS) {
			lessonBar.addItem(lesson.getName(), new Command() {
				@Override
				public void execute() {
					History.newItem("lesson/" + lesson.getId());
				}
			});
		}
		menuBar.addItem(new MenuItem("Practice", lessonBar));
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
