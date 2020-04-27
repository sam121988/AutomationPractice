package com.aptus.website.WebsiteAutomation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.concurrent.TimeUnit;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;




public class ExcelUtil {

		WebDriver driver;
		public FileInputStream fis= null;
		public FileOutputStream fileOut = null;
		private XSSFWorkbook workbook = null;
		private XSSFSheet sheet = null;
		private XSSFRow row = null;
		private XSSFCell cell=null;
		
		String path = null;
		
		public WebDriver getDriver() {
			return driver;
		}
		
	/*	public ExcelReader() throws IOException
		{
			path = System.getProperty("user.dir")+"\\testdata\\TestCases.xlsx";
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			
		}*/
		
		@SuppressWarnings({ "deprecation" })
		public String[][] getDataFromSheet(String sheetName, String ExcelName) {
			String dataSets[][] = null;
				try {
					// get sheet from excel workbook
					XSSFSheet sheet = workbook.getSheet(sheetName);
					// count number of active tows
					int totalRow = sheet.getLastRowNum() + 1;
					// count number of active columns in row
					int totalColumn = sheet.getRow(0).getLastCellNum();
					System.out.println("Total Column rows::"+totalColumn);
					// Create array of rows and column
					dataSets = new String[totalRow - 1][totalColumn];
					// Run for loop and store data in 2D array
					// This for loop will run on rows
					for (int i = 1; i < totalRow; i++) {
						XSSFRow rows = sheet.getRow(i);
						// This for loop will run on columns of that row
						for (int j = 0; j < totalColumn; j++) {
							// get Cell method will get cell
							XSSFCell cell = rows.getCell(j);
						
							// If cell of type String , then this if condition will work
							if (cell.getCellType() == Cell.CELL_TYPE_STRING)
								dataSets[i - 1][j] = cell.getStringCellValue();
							// If cell of type Number , then this if condition will work
							else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								String cellText = String.valueOf(cell.getNumericCellValue());
								dataSets[i - 1][j] = cellText;
							} else
								// If cell of type boolean , then this if condition will work
								dataSets[i - 1][j] = String.valueOf(cell.getBooleanCellValue());
						}

					}
					return dataSets;
				} catch (Exception e) {
					System.out.println("Exception in reading Xlxs file" + e.getMessage());
					e.printStackTrace();
				}
				return dataSets;
			}
		
		public int getSheetColumns(String sheetName)
		{
			int index = workbook.getSheetIndex(sheetName);
			sheet = workbook.getSheetAt(index);
			
			row = sheet.getRow(0);
		   return (row.getLastCellNum());
		}
		
		public int getSheetRow(String sheetName)
		{
			int index=workbook.getSheetIndex(sheetName);
		    sheet = workbook.getSheetAt(index);
		    
		    return (sheet.getLastRowNum()+1);
		}
		
		public String getCellData(String sheetName,String colName,int rowNum)
		{
			int colNum = -1;
			int index = workbook.getSheetIndex(sheetName);
			sheet = workbook.getSheetAt(index);
			for(int i=0;i<getSheetColumns(sheetName);i++)
			{
				row = sheet.getRow(0);
				cell = row.getCell(i);
				
				if(cell.getStringCellValue().equals(colNum))
				{
				colNum = cell.getColumnIndex();
				break;
				}
			}
			row = sheet.getRow(rowNum);
			cell = row.getCell(colNum);
			return(cell.getStringCellValue());
		}
		
		public void setCellData(String sheetName,int colNum,int rowNum,String str)
		{
			int index = workbook.getSheetIndex(sheetName);
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum);
			cell = row.createCell(colNum);
			cell.setCellValue(str);
			
			try {
				fileOut = new FileOutputStream(path);
				try {
					workbook.write(fileOut);
					fileOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
		
	}
