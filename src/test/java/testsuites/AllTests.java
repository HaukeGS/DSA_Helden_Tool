package testsuites;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import aventurian.AttributesAventurianManagerTest;
import aventurian.AventurianManagerTest;
import aventurian.AventurianTest;
import aventurian.LanguageAventurianManagerTest;
import aventurian.LevelCostCalculatorTest;
import aventurian.MiscelleanousAventurianManagerTest;
import aventurian.PrimaryAttributesTest;
import aventurian.PropertyAventurianManagerTest;
import aventurian.RaceAventurianManagerTest;
import aventurian.SecondaryAttributeHelperTest;
import aventurian.SecondaryAttributesTest;
import database.DatabaseTest;
import skills.LinearIncreasableSkillTest;
import skills.SkillTest;
import skills.attributes.primary.PrimaryAttributeTest;
import skills.attributes.secondary.AstralenergieTest;
import skills.attributes.secondary.LebenspunkteTest;
import skills.attributes.secondary.MagieresistenzTest;
import skills.attributes.secondary.SecondaryAttributeTest;
import skills.languages.AngramTest;
import skills.languages.AsdhariaTest;
import skills.languages.AurelianiTest;
import skills.languages.DrachischTest;
import skills.languages.IsdiraTest;
import skills.languages.KoboldischTest;
import skills.languages.LanguageTest;
import skills.languages.NeckergesangTest;
import skills.languages.UrTulamidyaTest;
import skills.properties.AdligTest;
import skills.properties.AdligesErbeTest;
import skills.properties.ArkanophobieTest;
import skills.properties.AusdauerndTest;
import skills.properties.AusruestungsvorteilTest;
import skills.properties.BadPropertyTest;
import skills.properties.KurzatmigTest;
import skills.properties.PropertyTest;
import ui.AttributePaneTest;
import ui.LanguagePaneTest;
import ui.MenuTest;
import ui.NavigatorTest;
import ui.PropertyPaneTest;
import ui.TopPaneTest;

@RunWith(Categories.class)
@Suite.SuiteClasses({ PrimaryAttributesTest.class, SecondaryAttributeHelperTest.class, SecondaryAttributesTest.class,
		AventurianTest.class, LevelCostCalculatorTest.class, LanguageTest.class, BadPropertyTest.class,
		PropertyTest.class, AventurianManagerTest.class, PropertyAventurianManagerTest.class,
		LanguageAventurianManagerTest.class, AttributesAventurianManagerTest.class, AttributePaneTest.class,
		NavigatorTest.class, LanguagePaneTest.class, MenuTest.class, RaceAventurianManagerTest.class,
		PropertyPaneTest.class, DatabaseTest.class, LinearIncreasableSkillTest.class, SkillTest.class,
		MiscelleanousAventurianManagerTest.class, AurelianiTest.class, AngramTest.class, AsdhariaTest.class,
		AurelianiTest.class, DrachischTest.class, IsdiraTest.class, KoboldischTest.class, NeckergesangTest.class,
		UrTulamidyaTest.class, TopPaneTest.class, AdligTest.class, ArkanophobieTest.class, AdligesErbeTest.class,
		AusdauerndTest.class, KurzatmigTest.class, AusruestungsvorteilTest.class, PrimaryAttributeTest.class,
		LebenspunkteTest.class, SecondaryAttributeTest.class, AstralenergieTest.class, MagieresistenzTest.class })
public class AllTests {

}
