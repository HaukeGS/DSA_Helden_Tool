package testsuites;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import aventurian.AttributesAventurianManagerTest;
import aventurian.AventurianManagerTest;
import aventurian.AventurianTest;
import aventurian.LanguageAventurianManagerTest;
import aventurian.LevelCostCalculatorTest;
import aventurian.PrimaryAttributesTest;
import aventurian.PropertyAventurianManagerTest;
import aventurian.RaceAventurianManagerTest;
import aventurian.SecondaryAttributeHelperTest;
import aventurian.SecondaryAttributesTest;
import database.DatabaseTest;
import skills.BadPropertyTest;
import skills.IncreasableSkillTest;
import skills.LanguageTest;
import skills.PropertyTest;
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
		PropertyPaneTest.class, DatabaseTest.class, IncreasableSkillTest.class })
public class AllTests {

}
