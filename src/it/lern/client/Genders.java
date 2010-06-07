package it.lern.client;

import it.lern.shared.Entry.Gender;

import com.google.gwt.language.client.translation.Language;

public class Genders {
	
	public static Gender[] getGenders(Language language) {
		switch (language) {
		case GERMAN:
			return new Gender[] { Gender.MALE, Gender.FEMALE, Gender.NEUTRAL };
		}
		throw new IllegalArgumentException();
	}
	
	public static String getArticle(Gender gender, Language language) {
		switch (language) {
		case GERMAN:
			switch (gender) {
			case MALE:
				return "der";
			case FEMALE:
				return "die";
			case NEUTRAL:
				return "das";
			}
		}
		throw new IllegalArgumentException("not supported");
	}
	
}
