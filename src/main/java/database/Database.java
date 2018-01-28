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
	private static final Consumer<Aventurian> EMPTY = (Aventurian a) -> {};
	private List<Property> properties;
	private List<Language> languages;
	private Map<Race, RaceConfiguration> races;

	public Database() {
		initialize();
	}

	public List<Language> getLanguages() {
		return languages;
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
		
		races.put(Race.MIDDLEGUY, new RaceConfiguration(10, -4, new ArrayList<>()));
		races.put(Race.THORWALAN, new RaceConfiguration(11, -5, Arrays.asList("Jaehzorn")));

		properties.add(new BadProperty("Jaehzorn", "wirklich wuetend", -75, NOREQUIREMENT));
		properties.add(new Property("Richtungssinn", "gutes Gespür fuer Richtungen", 150, EMPTY, EMPTY, NOREQUIREMENT));

		languages.add(new Language("Garethi", "wichtigste Sprache",
				(Aventurian a) -> a.getPrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE) >= 13, 5, 50));
		languages.add(new Language("BlaBla", "sinnlose Sprache", (Aventurian a) -> true, 5, 50));

	}
}
