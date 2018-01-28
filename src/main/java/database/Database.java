package database;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import aventurian.Aventurian;
import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;
import aventurian.Race;
import aventurian.RaceConfiguration;
import skills.BadProperty;
import skills.Language;
import skills.Property;

public class Database {

	private static final Predicate<Aventurian> NOREQUIREMENT = (Aventurian a) -> true;
	private static final Consumer<Aventurian> EMPTY = (Aventurian a) -> {
	};
	private List<Property> properties;
	private List<Language> languages;
	private Map<Race, RaceConfiguration> races;

	public Database() {
		initialize();
	}

	public List<Language> getLanguages() {
		return languages;
	}
	
	public List<Property> getAdvantages() {
		return properties.stream().filter(p -> p.isAdvantage()).collect(toList());
	}
	
	public List<Property> getDisadvantages() {
		return properties.stream().filter(p -> p.isDisadvantage()).collect(toList());
	}

	public RaceConfiguration getRaceConfiguration(Race race) {
		return races.get(race);
	}

	public List<Property> getRaceSkills(Race race) {
		final List<String> skillNames = getRaceConfiguration(race).getSkillNames();
		return properties.stream().filter(p -> skillNames.contains(p.getName())).collect(toList());
	}

	private void initialize() {
		properties = new ArrayList<>();
		languages = new ArrayList<>();
		races = new HashMap<>();

		// Races
		races.put(Race.MIDDLEGUY, new RaceConfiguration(10, -4, new ArrayList<>()));
		races.put(Race.THORWALAN, new RaceConfiguration(11, -5, Arrays.asList("Jaehzorn")));

		// Advantages
		properties.add(new Property("Adlig",
				"Der Charakter ist adliger Abstammung und hat Anspruch auf einen entsprechenden Titel. In manchen Bereichen unterliegt er nicht der gewöhnlichen Gerichtsbarkeit. Generell wird man ihm mehr Respekt entgegeben bringen und ihm mehr Gehör schenken. \nAllerdings hat er keine Aussicht darauf das Lehen seiner Eltern zu erben, da er ältere Geschwister besitzt oder aus einem Anderen Grund keinen Anspruch auf das Lehen hat.",
				250, EMPTY, EMPTY, NOREQUIREMENT));

		properties.add(new BadProperty("Jaehzorn", "wirklich wuetend", -75, NOREQUIREMENT));
		properties.add(new Property("Richtungssinn", "gutes Gespür fuer Richtungen", 150, EMPTY, EMPTY, NOREQUIREMENT));

		// Disadvantages

		// Bad Properties

		// Languages
		languages.add(new Language("Garethi", "wichtigste Sprache",
				(Aventurian a) -> a.getPrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE) >= 13, 5, 50));
		languages.add(new Language("BlaBla", "sinnlose Sprache", (Aventurian a) -> true, 5, 50));

	}
}
