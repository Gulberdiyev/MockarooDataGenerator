package com.dice;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DiceJobSearch {
	
	public static void main(String[] args) {
		
		//set up chrome driver path
		WebDriverManager.chromedriver().setup();
		
		//invoke selenium webdriver
		WebDriver driver = new ChromeDriver();
		
		//fulscreen
		driver.manage().window().fullscreen();
		
		//setime in case of page is slow
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
		
		//Stept 1 launch browser and nav to https://dice.com 
		String url = "http://dice.com";
		driver.get(url);
		
		
		String actualTitle = driver.getTitle();
		String expectedTitle = "Job Search for Technology Professionals | Dice.com";
		
		if(actualTitle.equals(expectedTitle)) {
			System.out.println("Step PASS. dice homepage successfully loaded");
			
		}else {
			
			System.out.println("Step FAIL. Dice homepage did not load");
			throw new RuntimeException("Step FAIL. Dice homepage did not load");
			
		}
		
		
		String keyword = "java developer";
		
		driver.findElement(By.id("search-field-keyword")).clear();
		
		driver.findElement(By.id("search-field-keyword")).sendKeys(keyword);
		
		String location = "77064";
		driver.findElement(By.id("search-field-location")).clear();
		driver.findElement(By.id("search-field-location")).sendKeys(location);
		
		driver.findElement(By.id("findTechJobs")).click();
		
		String count = driver.findElement(By.id("posiCountId")).getText();
		System.out.println(count);
		
		
		//ensure count is more than 0
		
		int countResult = Integer.parseInt(count.replace(",", ""));
		
		if(countResult > 0) {
			System.out.println("Keyword : " +keyword+ " search returned " +countResult+ "result is " +location);
			
		}else {
			System.out.println("Step Fail: Keyword : " + keyword +" serach returned " + countResult + " results in " +location);
		}
		
		driver.close();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}