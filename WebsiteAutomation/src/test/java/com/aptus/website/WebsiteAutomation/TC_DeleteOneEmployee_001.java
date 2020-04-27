package com.aptus.website.WebsiteAutomation;

import static org.testng.Assert.assertFalse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.Assert;


public class TC_DeleteOneEmployee_001 {

	Properties property;
	FileInputStream fs;
	WebDriver driver;
	
	
	public boolean GetNameValidate(WebDriver driver) {
		try {
			driver.switchTo().alert().accept();
			// System.out.println(driver.switchTo().alert().getText());
			return true;
		} // try
		catch (Exception e) {
			return false;
		} // catch
	}
	
	
	
	public void getNames(String Name) throws InterruptedException
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		List<WebElement> Names= driver.findElements(By.xpath(".//*[@class='card-body']/h5"));
		
		for(int i=0;i<Names.size();i++)
		{
			// Delete the Name
			
			if(Names.get(i).getText().equalsIgnoreCase(Name))
			{
						
						    Thread.sleep(100);
				       List<WebElement> getNames= driver.findElements(By.xpath(".//*[@class='card-body']/h5/following-sibling::a[@class='btn btn-danger employee-list-button']"));
							getNames.get(i).click();
							
				            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);			
							Thread.sleep(100);
							driver.switchTo().alert().accept();
							
						
							if(driver.getPageSource().contains(Name))
									{
								System.out.println("Name is present :: "+Name);
								Assert.fail("Name is present :: "+Name);
								}else
								System.out.println("Name is absent");
								}
													
			}
		}
	
	
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
	public void DeleteOneEmployee() throws IOException, InterruptedException
	{
     FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\testdata\\"+"TestCases.xlsx");
   	 XSSFWorkbook workbook = new XSSFWorkbook(fis);
   	 XSSFSheet sheet = workbook.getSheetAt(1);
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.xpath(".//*[@class='btn btn-success']")).click();
		driver.findElement(By.xpath(".//*[@id='firstName']")).sendKeys(sheet.getRow(5).getCell(2).toString());
		driver.findElement(By.xpath(".//*[@id='lastName']")).sendKeys(sheet.getRow(5).getCell(3).toString());
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Select JobTitle =new Select(driver.findElement(By.xpath(".//*[@id='title']")));
		JobTitle.selectByVisibleText(sheet.getRow(5).getCell(4).toString());
		Select CurrentProject =new Select(driver.findElement(By.xpath(".//*[@id='project']")));
		CurrentProject.selectByVisibleText(sheet.getRow(5).getCell(5).toString());
		driver.findElement(By.xpath(".//*[@type='submit']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		this.getNames(sheet.getRow(5).getCell(2).toString()+" "+sheet.getRow(5).getCell(3).toString());
		Reporter.log("DeleteOneEmployee :: Passed");
	}
	
	@AfterClass
	public void CloseBrowser()
	{
		driver.close();
	}
}