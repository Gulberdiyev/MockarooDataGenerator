package com.mockaroo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MockarooDataValidation {
	WebDriver driver;
	private static final String FILENAME = "C:\\Users\\murat\\Desktop\\mock_data.csv";

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://mockaroo.com/");
	}

	@Test
	public void test1() throws InterruptedException {
		// deleted one space after Mockaroo
		String expected = "Mockaroo - Random Data Generator and API Mocking " + "Tool | JSON / CSV / SQL / Excel";

		String actual = driver.getTitle();

		// step 3
		assertEquals(actual, expected, "HomePage title does not match");
		// step 4
		String logo = driver.findElement(By.className("brand")).getText();
		String logoName = driver.findElement(By.className("tagline")).getText();

		// In requirement Mockaroo with capital
		assertEquals(logo, "mockaroo");
		assertEquals(logoName, "realistic data generator");

		// Step 5
		List<WebElement> x = driver.findElements(By.className("column-remove"));

		for (WebElement webElement : x) {
			// Thread.sleep(2000);
			webElement.click();
		}

		// Step 6
		String FName = driver.findElement(By.xpath("//div[@class='column" + " column-header column-name']")).getText();

		String type = driver.findElement(By.xpath("//div[@class='column" + " column-header column-type']")).getText();

		String options = driver.findElement(By.xpath("//div[@class='column" + " column-header column-options']"))
				.getText();

		assertEquals(FName, "Field Name");
		assertEquals(type, "Type");
		assertEquals(options, "Options");

		// Step 7
		assertTrue(driver.findElement(By.linkText("Add another field")).isEnabled());

		// Step 8
		String numberOfRow = driver.findElement(By.id("num_rows")).getAttribute("value");

		assertEquals(numberOfRow, "1000");

		// Step 9
		Select format = new Select(driver.findElement(By.id("schema_file_format")));

		assertEquals(format.getFirstSelectedOption().getText(), "CSV");

		// Step 10
		Select line = new Select(driver.findElement(By.id("schema_line_ending")));

		assertEquals(line.getFirstSelectedOption().getText(), "Unix (LF)");

		// Step 11
		assertTrue(driver.findElement(By.id("schema_include_header")).isSelected());

		assertFalse(driver.findElement(By.id("schema_bom")).isSelected());

		// Step 12
		WebElement button = driver.findElement(By.linkText("Add another field"));
		button.click();
		Thread.sleep(1000);
		WebElement Fname = driver.findElement(By.xpath("(//input[@placeholder='enter name...'])[7]"));
		Fname.sendKeys("City");

		// Step 13
		WebElement Type = driver.findElement(By.xpath("(//input[@placeholder='choose type...'])[7]"));
		Type.click();
		Thread.sleep(1000);
		String title = driver.findElement(By.xpath("(//h3[@class='modal-title'])[1]")).getText();
		assertEquals("Choose a Type", title);

		// Step 14
		driver.findElement(By.id("type_search_field")).sendKeys("City");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='type-name']")).click();

		// Step 15
		Thread.sleep(1000);
		button.click();
		Thread.sleep(1000);
		Fname.clear();
		Fname.sendKeys("Country");

		Type.click();
		Thread.sleep(1000);
		String title1 = driver.findElement(By.xpath("(//h3[@class='modal-title'])[1]")).getText();
		assertEquals("Choose a Type", title1);

		driver.findElement(By.id("type_search_field")).sendKeys("Country");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='type-name']")).click();

		// Step 16
		Thread.sleep(1000);
		// driver.findElement(By.id("download")).click();

		// Step 17
		ArrayList<String> Cities = new ArrayList<>();
		ArrayList<String> Country = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			String strCurrentLine;

			while ((strCurrentLine = br.readLine()) != null) {

				String[] str = strCurrentLine.split(",");

				
				// Step 20
				Country.add(str[0]); 

				// Step 21
				Cities.add(str[1]); 
				
				// System.out.println(strCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Step 18
		assertEquals(Cities.get(0), "City");
		assertEquals(Country.get(0), "Country");

		// Step 19
		Cities.remove(0);
		Country.remove(0);
		
		assertEquals(Cities.size(), 1000);
		assertEquals(Country.size(), 1000);
		
		//Step 22
				String longest = Cities.get(0);
				String shortest = Cities.get(0);
				
				for (int i = 1; i < Cities.size(); i++) {
					if(longest.length() < Cities.get(i).length()) {
						longest = Cities.get(i);
					}
					if(shortest.length() > Cities.get(i).length()) {
						shortest = Cities.get(i);
					}
				}
				System.out.println("Longest name of city: " + longest);
				System.out.println("Shortest name of city: " + shortest);
				
				//Step 23
				String [] countries = Country.toArray(new String[Country.size()]);
				Arrays.sort(countries);
				int count = 1;
				int uniqueCountry = 1;
				for (int i = 1; i < countries.length; i++) {
					if(countries[i-1].equals(countries[i])) {
						count++;
						continue;
					}	
					System.out.println(countries[i] +"-"+count);
					uniqueCountry++;
					count = 1;
				}
				
				//Step 24 
				Set<String> citiesSet = new HashSet<>(Cities);
				
				
				//Step 25
				int uniqueCity = 1;
				String [] citiesSorted = Cities.toArray(new String[Cities.size()]);
				Arrays.sort(citiesSorted);
				for (int i = 1; i < citiesSorted.length; i++) {
					if(!citiesSorted[i-1].equals(citiesSorted[i])) {
						uniqueCity++;
					}
				}
				assertEquals(citiesSet.size(), uniqueCity);
				
				
				// Step 26
				Set<String> countrySet = new HashSet<>(Country);
				
				//Step 27
				assertEquals(countrySet.size(), uniqueCountry);
		
		

	}

}
