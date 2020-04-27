package com.aptus.website.WebsiteAutomation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TC_AddNewEmployee_004 {

	Properties property;
	FileInputStream fs;
	WebDriver driver;
	
	
	@BeforeClass
	public void invokeBrowserWithUrl() throws IOException
	{
		fs = new FileInputStream(System.getProperty("user.dir")+"\\config.properties");
	    property = new Properties();
	    property.load(fs);
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\chromedriver.exe");
		driver = new ChromeDriver();
	    driver.get(property.getProperty("url"));
	    driver.manage().window().maximize();
	}
	
	@Test
	public void TC_CurrentProjectBlank_004() throws IOException
	{
     FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\testdata\\"+"TestCases.xlsx");
   	 XSSFWorkbook workbook = new XSSFWorkbook(fis);
   	 XSSFSheet sheet = workbook.getSheetAt(1);
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.xpath(".//*[@class='btn btn-success']")).click();
		driver.findElement(By.xpath(".//*[@id='firstName']")).sendKeys(sheet.getRow(4).getCell(2).toString());
		driver.findElement(By.xpath(".//*[@id='lastName']")).sendKeys(sheet.getRow(4).getCell(3).toString());
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Select JobTitle =new Select(driver.findElement(By.xpath(".//*[@id='title']")));
		JobTitle.selectByVisibleText(sheet.getRow(4).getCell(4).toString());
		driver.findElement(By.xpath(".//*[@type='submit']")).click();
		
		Assert.assertEquals(driver.findElement(By.xpath(".//*[@id='project']/following-sibling::div[@class='red-text']")).getText(),"Please select a project");
		Reporter.log("TC_AddNewEmployee_004 :: Passed");
	}
	
	@AfterClass
	public void CloseBrowser()
	{
		driver.close();
	}
}
	