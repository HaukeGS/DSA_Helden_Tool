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
import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;
import skills.Language;
import skills.properties.Adlig_I;
import skills.properties.Adlig_II;
import skills.properties.Adlig_III;
import skills.properties.BadProperty;
import skills.properties.Property;

public class Database {

	private static final Predicate<Aventurian> NOREQUIREMENT = (Aventurian a) -> true;
	private static final Consumer<Aventurian> EMPTY = (Aventurian a) -> {
	};
	private static final RaceConfiguration DEFAULT_RACECONFIGURATION = new RaceConfiguration(0, 0, new ArrayList<>());
	private List<Property> properties;
	private List<Language> languages;
	private Map<Race, RaceConfiguration> races;

	public Database() {
		reset();
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public List<Property> getAdvantages() {
		return properties.stream().filter(Property::isAdvantage).collect(toList());
	}

	public List<Property> getDisadvantages() {
		return properties.stream().filter(Property::isDisadvantage).collect(toList());
	}

	public List<Property> getSkillsFor(Race race) {
		final List<String> skillNames = races.getOrDefault(race, DEFAULT_RACECONFIGURATION).getSkillNames();
		return properties.stream().filter(p -> skillNames.contains(p.getName())).collect(toList());
	}

	public void reset() {
		initRaces();
		initProperties();
		initLanguages();

	}

	private void initLanguages() {
		languages = new ArrayList<>();
		languages.add(new Language("Garethi", "wichtigste Sprache",
				(Aventurian a) -> a.getPrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE) >= 13, 5, 50));
		languages.add(new Language("BlaBla", "sinnlose Sprache", (Aventurian a) -> true, 5, 50));
	}

	private void initProperties() {
		properties = new ArrayList<>();
//		properties.add(new Property("Adlig I",
//				"Der Charakter ist adliger Abstammung und hat Anspruch auf einen entsprechenden Titel. In manchen Bereichen unterliegt er nicht der gew�hnlichen Gerichtsbarkeit. Generell wird man ihm mehr Respekt entgegeben bringen und ihm mehr Geh�r schenken. \nAllerdings hat er keine Aussicht darauf das Lehen seiner Eltern zu erben, da er �ltere Geschwister besitzt oder aus einem Anderen Grund keinen Anspruch auf das Lehen hat. \n Er ist Kind eines Edelmannes oder einer Edelfrau, also eines Ritters oder eiens Junkers. \n Kann nicht mit 'Adlig II' oder 'Adlig III' gew�hlt werden!",
//				250, EMPTY, EMPTY, (Aventurian a) -> !a.hasSkill("Adlig II") && !a.hasSkill("Adlig III")));
//		properties.add(new Property("Adlig II",
//				"Der Charakter ist adliger Abstammung und hat Anspruch auf einen entsprechenden Titel. In manchen Bereichen unterliegt er nicht der gew�hnlichen Gerichtsbarkeit. Generell wird man ihm mehr Respekt entgegeben bringen und ihm mehr Geh�r schenken. \nAllerdings hat er keine Aussicht darauf das Lehen seiner Eltern zu erben, da er �ltere Geschwister besitzt oder aus einem Anderen Grund keinen Anspruch auf das Lehen hat. \n Er ist Kind eines mittleren Adligen, also eines Barons o.�. \n Kann nicht mit 'Adlig I' oder 'Adlig III' gew�hlt werden!",
//				350, EMPTY, EMPTY, (Aventurian a) -> !a.hasSkill("Adlig I") && !a.hasSkill("Adlig III")));
//		properties.add(new Property("Adlig III",
//				"Der Charakter ist adliger Abstammung und hat Anspruch auf einen entsprechenden Titel. In manchen Bereichen unterliegt er nicht der gew�hnlichen Gerichtsbarkeit. Generell wird man ihm mehr Respekt entgegeben bringen und ihm mehr Geh�r schenken. \nAllerdings hat er keine Aussicht darauf das Lehen seiner Eltern zu erben, da er �ltere Geschwister besitzt oder aus einem Anderen Grund keinen Anspruch auf das Lehen hat. \\n Er ist Kind eines h�heren Adligen, also eines Grafen o.�. \n Kann nicht mit 'Adlig I' oder 'Adlig II' gew�hlt werden!",
//				500, EMPTY, EMPTY, (Aventurian a) -> !a.hasSkill("Adlig I") && !a.hasSkill("Adlig II")));
		properties.add(new Adlig_I());
		properties.add(new Adlig_II());
		properties.add(new Adlig_III());
		properties.add(new Property("Adliges Erbe", "Nur f�r adlige Charaktere.\r\n" + 
				"Der Charakter ist der erste in der Erbfolge und es ist zu erwarten, dass er das Erbe seines Vaters oder seiner Mutter antritt, sollte diese/r dahinscheiden.", 250, EMPTY, EMPTY, (Aventurian a) -> a.hasSkill("Adlig I") || a.hasSkill("Adlig II") || a.hasSkill("Adlig III")));
		properties.add(new Property("Amtsadel", "Der Charakter ist nicht von adliger Geburt, hat aber durch seinen Rang oder seinen Beruf einen �hnlichen, wenn auch nicht so ausgepr�gten, Titel und damit vergleichbare Vorteile.	", 150, EMPTY, EMPTY, NOREQUIREMENT));
		properties.add(new Property("Altersresistenz", "Normalerweise nur f�r Elfen und Zwerge.\r\n" + 
				"Der Charakter altert merklich langsamer. Sein Aussehen und seine Werte ver�ndern sich dementsprechend anders.", 50, EMPTY, EMPTY, NOREQUIREMENT));
		properties.add(new Property("Ausdauernd", "Die Ersch�pfungsschwelle des Charakters erh�ht sich um 1. \n Kann nicht mit 'Kurzatmig' gew�hlt werden!", 150, (Aventurian a) -> a.increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.EXHAUSTIONTHRESHHOLD, 1), (Aventurian a) -> a.decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.EXHAUSTIONTHRESHHOLD,  1), (Aventurian a) -> !a.hasSkill("Kurzatmig")));
		//funktioniert bisher noch nicht. ben�tigt konstante kosten.
		//properties.add(new Property("Ausr�stungsvorteil", "Pro Stufe erh�lt der Charakter 15D mehr Startgeld. Kostet 50 AP pro Stufe.", cost, effectOnGain, effectOnLose, requirement, minLevel, maxLevel))
		//funktioniert auch nicht ganz. ben�tigt konstante kosten.
		properties.add(new Property("Besonderer Besitz", "Nach Absprache mit dem Meister erh�lt der Charakter einen mittelm�chtigen Gegenstand zum Spielstart.", 350, EMPTY, EMPTY, NOREQUIREMENT));
		properties.add(new Property("Daemmerungssicht", "Charaktere mit Dunkelsicht halbieren die Abzuege durch fehlendes Licht, au�er bei absoluter Dunkelheit. \n Kann nicht mit 'Nachtblind' gew�hlt werden!", 250, EMPTY, EMPTY, (Aventurian a) -> !a.hasSkill("Nachtblind")));
		properties.add(new Property("Dichschaedel", "Auch am Kopf werden nur 100% TP(B) verursacht. \nDas Manoever Kopfsto� ist um 2 Punkte erleichtert und verursacht 1 TP(B) mehr.", 100, EMPTY, EMPTY, NOREQUIREMENT));
		properties.add(new Property("Eisern", "Erhoet die Wundschwelle um 1. \nKann nicht mit Glasknochen gewaehlt werden.", 200, (Aventurian a) -> a.increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.WOUNDTHRESHHOLD, 1), (Aventurian a) -> a.decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.WOUNDTHRESHHOLD, 1), (Aventurian a) -> !a.hasSkill("Glasknochen"))); 
		properties.add(new Property("Entfernungssinn", "IN-Proben zum Einsch�tzen der Entfernung sind um bis zu 5 Punkte erleichtert. Beim Fernkampf sind die Proben auf Ziele, die mindestens 50 Schritt entfernt sind, nicht erschwert.", 150, EMPTY, EMPTY, NOREQUIREMENT));
		properties.add(new Property("Feenfreund", "Feen helfen haeufiger und ohne eine Gegenleistung zu erwarten.", 250, EMPTY, EMPTY, NOREQUIREMENT));
		//Maybe add effectOnGain for dodging.
		properties.add(new Property("Flink", "Ausweichen um 1 erh�ht. Au�erdem sind Athletik-Proben f�r Geschwindigkeit (Verfolgung, Wettrennen etc.) um 3 Punkte erleichtert. Diese Punkte k�nnen auch als TaP* �berbehalten werden.", 300, EMPTY, EMPTY, (Aventurian a) -> !a.hasSkill("Behaebig")));
		//requirement needs to be added
		properties.add(new Property("Glueck", "Kann pro aventurischem Tag 1W3-1 W�rfelw�rfe wiederholen (oder vom Meister wiederholen lassen) und das bessere Ergebnis w�hlen.", 600, EMPTY, EMPTY, NOREQUIREMENT));
		properties.add(new Property("Glueck im Spiel", "Erleichtert Proben auf Brett-/Kartenspiel um bis zu 7 Punkte, je nachdem wie viel Gl�ck im Spiel vorhanden ist.", 100, EMPTY, EMPTY, NOREQUIREMENT));
		properties.add(new Property("Gutes Aussehen", "Erleichtert passende Proben auf Interaktionstalente um 2/4/6 Punkte. \nKann nicht mit 'Unansehnlich' oder 'Widerw�rtiges Aussehen' gew�hlt werden!", 250, EMPTY, EMPTY, (Aventurian a) -> !a.hasSkill("Unansehnlich") && !a.hasSkill("Widerw�rtiges Aussehen"), 1, 3));
		
		
		properties.add(new Property("Nachtsicht", "Der Charakter ignoriert 6 Stufen fehlenden Lichts, au�er bei absoluter Dunkelheit.\n Kann nicht mit 'Nachtblind' gew�hlt werden!", 750, EMPTY, EMPTY, (Aventurian a) -> !a.hasSkill("Nachtblind")));
		
		
		
		
		
		properties.add(new Property("Richtungssinn", "gutes Gesp�r fuer Richtungen", 150, EMPTY, EMPTY, NOREQUIREMENT));
		properties.add(new Property("Gestank", "bestialischer Gestank", -150, EMPTY, EMPTY, NOREQUIREMENT));

		properties.add(new BadProperty("Jaehzorn", "wirklich wuetend", -75, NOREQUIREMENT));
	}

	private void initRaces() {
		races = new HashMap<>();
		races.put(Race.MIDDLEGUY, new RaceConfiguration(10, -4, new ArrayList<>()));
		races.put(Race.THORWALAN, new RaceConfiguration(11, -5, Arrays.asList("Jaehzorn")));
	}

	public int getHitPointsModFor(Race race) {
		return races.getOrDefault(race, DEFAULT_RACECONFIGURATION).getHitPointsMod();
	}

	public int getMagicResistanceModFor(Race race) {
		return races.getOrDefault(race, DEFAULT_RACECONFIGURATION).getMagicResistanceMod();
	}
}
