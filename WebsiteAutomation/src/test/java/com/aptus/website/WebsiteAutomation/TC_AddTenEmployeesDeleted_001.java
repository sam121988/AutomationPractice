package com.aptus.website.WebsiteAutomation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TC_AddTenEmployeesDeleted_001 {

	Properties property;
	FileInputStream fs;
	WebDriver driver;
	
	
	public void getNames(String Name) throws InterruptedException
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		List<WebElement> Names= driver.findElements(By.xpath(".//*[@class='card-body']/h5"));
		
		for(int i=0;i<Names.size();i++)
		{
			if(Names.get(i).getText().equalsIgnoreCase(Name))
			{
			                     	Thread.sleep(100);
						    	    WebElement element = driver.findElements(By.xpath(".//*[@class='card-body']/h5/following-sibling::a[@class='btn btn-danger employee-list-button']")).get(i);
						            JavascriptExecutor executor = (JavascriptExecutor)driver;
						            executor.executeScript("arguments[0].click();", element);
									Thread.sleep(100);
									driver.switchTo().alert().accept();
									
									JavascriptExecutor js1 = (JavascriptExecutor) driver;
				                    js1.executeScript("window.scrollBy(0,400)", ""); 
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
	public void AddTenEmployee() throws IOException, InterruptedException
	{
     FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\testdata\\"+"TestCases.xlsx");
   	 XSSFWorkbook workbook = new XSSFWorkbook(fis);
   	 XSSFSheet sheet = workbook.getSheetAt(1);
   			
   			 for(int i=3;i<=12;i++)
   			 {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.xpath(".//*[@class='btn btn-success']")).click();
		
		driver.findElement(By.xpath(".//*[@id='firstName']")).sendKeys(sheet.getRow(7).getCell(i*2).toString());
		driver.findElement(By.xpath(".//*[@id='lastName']")).sendKeys(sheet.getRow(7).getCell(i*2+1).toString());
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Select JobTitle =new Select(driver.findElement(By.xpath(".//*[@id='title']")));
		JobTitle.selectByVisibleText(sheet.getRow(7).getCell(4).toString());
		Select CurrentProject =new Select(driver.findElement(By.xpath(".//*[@id='project']")));
		CurrentProject.selectByVisibleText(sheet.getRow(7).getCell(5).toString());
		driver.findElement(By.xpath(".//*[@type='submit']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
   			 }
   			 
   			   boolean staleElement = true; 
			   while(staleElement){
				   
		    	try{
   			 for(int j=3;j<=12;j++)
   			 {
   				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
   				 this.getNames(sheet.getRow(7).getCell(j*2).toString()+" "+sheet.getRow(7).getCell(j*2+1).toString());
   			 }
   			staleElement = false;
			      } catch(StaleElementReferenceException e){
			        staleElement = true;	
			      }
			    }
			   
			  			   			
		Reporter.log("AddTwoEmployee :: Passed");
	}
	
	@AfterClass
	public void CloseBrowser()
	{
		driver.close();
	}
}