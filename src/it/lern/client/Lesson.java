package it.lern.client;

import it.lern.shared.Entry;

public class Lesson {
	
	private final String name;
	private final String id;

	public Entry[] getEntries() {
		return Entry.ENTRIES;
	}
	
	public Lesson(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public final static Lesson[] LESSONS = new Lesson[] { new Lesson("Basic", "1") };
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
	
	public Lesson getById(String id) {
		for (Lesson lesson : LESSONS) {
			if (lesson.getId().equals(id)) {
				return lesson;
			}
		}
		return null;
	}
}
