package testsuites;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import testsuites.categories.UITest;

@RunWith(Categories.class)
@Categories.ExcludeCategory(UITest.class)
@Suite.SuiteClasses({ AllTests.class })
public class ModelTests {

}
