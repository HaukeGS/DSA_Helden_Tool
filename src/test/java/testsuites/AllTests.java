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
import skills.IncreasableSkillTest;
import skills.SkillTest;
import skills.languages.AngramTest;
import skills.languages.AsdhariaTest;
import skills.languages.AurelianiTest;
import skills.languages.DrachischTest;
import skills.languages.IsdiraTest;
import skills.languages.KoboldischTest;
import skills.languages.LanguageTest;
import skills.languages.NeckergesangTest;
import skills.languages.UrTulamidyaTest;
import skills.properties.BadPropertyTest;
import skills.properties.PropertyTest;
import ui.AttributePaneTest;
import ui.LanguagePaneTest;
import ui.MenuTest;
import ui.NavigatorTest;
import ui.PropertyPaneTest;

@RunWith(Categories.class)
@Suite.SuiteClasses({ PrimaryAttributesTest.class, SecondaryAttributeHelperTest.class, SecondaryAttributesTest.class,
		AventurianTest.class, LevelCostCalculatorTest.class, LanguageTest.class, BadPropertyTest.class,
		PropertyTest.class, AventurianManagerTest.class, PropertyAventurianManagerTest.class,
		LanguageAventurianManagerTest.class, AttributesAventurianManagerTest.class, AttributePaneTest.class,
		NavigatorTest.class, LanguagePaneTest.class, MenuTest.class, RaceAventurianManagerTest.class,
		PropertyPaneTest.class, DatabaseTest.class, IncreasableSkillTest.class, SkillTest.class,
		MiscelleanousAventurianManagerTest.class, AurelianiTest.class, AngramTest.class, AsdhariaTest.class,
		AurelianiTest.class, DrachischTest.class, IsdiraTest.class, KoboldischTest.class, NeckergesangTest.class,
		UrTulamidyaTest.class })
public class AllTests {

}
