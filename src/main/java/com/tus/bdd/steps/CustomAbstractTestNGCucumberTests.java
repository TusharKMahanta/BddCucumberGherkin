package com.tus.bdd.steps;

import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apiguardian.api.API;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract TestNG Cucumber Test
 * <p>
 * Runs each cucumber scenario found in the features as separated test.
 *
 * @see TestNGCucumberRunner
 */
@API(status = API.Status.STABLE)
public class CustomAbstractTestNGCucumberTests extends AbstractTestNGSpringContextTests {
	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@SuppressWarnings("unused")
	@Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
		// the 'featureWrapper' parameter solely exists to display the feature
		// file in a test report
		testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
	}

	/**
	 *
	 * @return an iterator for scenarios filtered by tags.
	 */
	@DataProvider(parallel = true)
	public Iterator<Object[]> scenarios() {
		ArrayList<Object[]> modifiedList = new ArrayList<>();
		if (testNGCucumberRunner == null) {
			return modifiedList.iterator();
		}
		modifiedList = filterTheFeature(testNGCucumberRunner.provideScenarios());
		return modifiedList.iterator();
	}

	private ArrayList<Object[]> filterTheFeature(Object[][] data) {
		String tagValue = System.getProperty("tags");
		if (tagValue == null || tagValue.isEmpty()) {
			return getFeatureList(data);
		}
		List<String> excludeTagList = Arrays.asList(System.getProperty("exclude","DUMMY").split(","));
		List<String> includeTagList = Arrays.asList(tagValue.split(","));
		ArrayList<Object[]> modifiedList = new ArrayList<>();

		for (Object[] datum : data) {
			PickleWrapper pickleWrapper = (PickleWrapper) datum[0];
			List<String> tagList = removeSpecialCharacter(pickleWrapper.getPickle().getTags());

			if (tagList.stream().anyMatch(includeTagList::contains)
				&& tagList.stream().noneMatch(excludeTagList::contains)) {
				modifiedList.add(datum);
			}
		}
//		Collections.shuffle(modifiedList);
		return modifiedList;
	}

	private List<String> removeSpecialCharacter(List<String> strList) {
		return strList
			.stream()
			.map(tag -> tag.replace("@", ""))
			.collect(Collectors.toList());
	}

	private ArrayList<Object[]> getFeatureList(Object[][] data) {
		ArrayList<Object[]> modifiedList = new ArrayList<>();
		if (data != null) {
			modifiedList.addAll(Arrays.asList(data));
		}
		return modifiedList;
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		if (testNGCucumberRunner == null) {
			return;
		}
		testNGCucumberRunner.finish();
	}
}
