package it.lern.shared;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.language.client.translation.Language;

public class Entry {
	
	private final String entry;
	private final String translation;
	private final Category category;
	
	private final Map<Class<?>, Object> attributes = new HashMap<Class<?>, Object>();
	private final Languages languages;

	public static enum Gender {
		MALE,
		FEMALE,
		NEUTRAL
	};
	
	public static enum Category {
		NOUN,
		VERB,
		ADJECTIVE,
		ADVERB,
		PHRASE
	}
	
	public static enum Conjugation {
		REGULAR,
		IRREGULAR
	}
	
	public static enum Languages {
		DE_EN(Language.GERMAN, Language.ENGLISH);
		
		private final Language entryLanguage;
		private final Language translationLanguage;

		private Languages(Language entryLanguage, Language translationLanguage) {
			this.entryLanguage = entryLanguage;
			this.translationLanguage = translationLanguage;
		}
		
		public Language getEntryLanguage() {
			return entryLanguage;
		}
		
		public Language getTranslationLanguage() {
			return translationLanguage;
		}
	}
	
	public Entry(String word, String translation, Languages languages,
			Category category) {
		this.entry = word;
		this.translation = translation;
		this.languages = languages;
		this.category = category;
	}
	
	public static final Entry[] ENTRIES = new Entry[] {
		new Entry("Schneebesen", "whisk", Languages.DE_EN, Category.NOUN).put(Gender.class, Gender.MALE),
		new Entry("Besen", "broom", Languages.DE_EN, Category.NOUN).put(Gender.class, Gender.MALE),
		new Entry("essen", "to eat", Languages.DE_EN, Category.VERB).put(Conjugation.class, Conjugation.REGULAR),
		new Entry("kaufen", "to buy", Languages.DE_EN, Category.VERB).put(Conjugation.class, Conjugation.REGULAR),
		new Entry("gehen", "to go", Languages.DE_EN, Category.VERB).put(Conjugation.class, Conjugation.IRREGULAR),
		new Entry("Kirche", "church", Languages.DE_EN, Category.NOUN).put(Gender.class, Gender.FEMALE),
		new Entry("Hose", "pants", Languages.DE_EN, Category.NOUN).put(Gender.class, Gender.FEMALE),
		new Entry("Kuchen", "cake", Languages.DE_EN, Category.NOUN).put(Gender.class, Gender.MALE),
		new Entry("kochen", "to cook", Languages.DE_EN, Category.VERB).put(Conjugation.class, Conjugation.REGULAR),
		new Entry("braten", "to sautee", Languages.DE_EN, Category.VERB).put(Conjugation.class, Conjugation.REGULAR),
		new Entry("helfen", "to help", Languages.DE_EN, Category.VERB).put(Conjugation.class, Conjugation.REGULAR),
		new Entry("lachen", "to laugh", Languages.DE_EN, Category.VERB).put(Conjugation.class, Conjugation.REGULAR),
	};
	
	public Entry put(Class<?> key, Object value) {
		attributes.put(key, value);
		return this;
	}
	
	public Object get(Class<?> key) {
		return attributes.get(key);
	}

	public String getEntry() {
		return entry;
	}

	public String getTranslation() {
		return translation;
	}

	public Category getCategory() {
		return category;
	}

	public Languages getLanguages() {
		return languages;
	}
}
