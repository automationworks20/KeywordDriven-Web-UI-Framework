package com.qa.fw.keyword.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.fw.keyword.base.Base;

public class KeywordEngine {

	public WebDriver driver;
	public Properties prop;

	// Initialize the Excel Sheet
	public static Workbook book;
	// Initialize the Sheet inside the Excel Sheet
	public static Sheet sheet;

	public Base base;
	public WebElement element;

	// Path for the Excel Sheet
	public final String SCENARIO_SHEET_PATH = "C:\\Automation\\KeywordDriven-Web-UI-Framework-master\\src"
			+ "\\main\\java\\com\\qa\\fw\\keyword\\scenarios\\fw_scenarios.xlsx";

	public void startExecution(String sheetName) {

		
		// Connect to the Excel Sheet
		FileInputStream file = null;

		try {
			file = new FileInputStream(SCENARIO_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);

		// Read the excel Sheet
		int k = 0;
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			try {
			String locatorType = sheet.getRow(i + 1).getCell(k + 1).toString().trim(); // id = uid
			String locatorValue = sheet.getRow(i + 1).getCell(k + 2).toString().trim(); // id = uid
			String action = sheet.getRow(i + 1).getCell(k + 3).toString().trim();
			String value = sheet.getRow(i + 1).getCell(k + 4).toString().trim();

			switch (action) {
			case "open browser":
				base = new Base();
				prop = base.init_properties();
				if (value.isEmpty() || value.equals("NA")) {
					driver = base.init_driver(prop.getProperty("browser"));
				} else {
					driver = base.init_driver(value);
				}
				break;

			case "enter url":
				if (value.isEmpty() || value.equals("NA")) {
					driver.get(prop.getProperty("url"));
				} else {
					driver.get(value);
				}
				break;

			case "quit":
				driver.quit();
				break;

			default:
				break;
			}

			switch (locatorType) {
			case "id":
				element = driver.findElement(By.id(locatorValue));
				if (action.equalsIgnoreCase("sendkeys")) {
					element.clear();
					element.sendKeys(value);
				} else if (action.equalsIgnoreCase("click")) {
					element.click();
				}
				locatorType = null;
				break;

			case "linkText":
				element = driver.findElement(By.linkText(locatorValue));
				element.click();
				locatorType = null;
				break;

			case "name":
				element = driver.findElement(By.name(locatorValue));
				if(action.equalsIgnoreCase("sendkeys")) {
					element.clear();
					element.sendKeys(value);
				}else if(action.equalsIgnoreCase("click")) {
					element.click();
				}
				break;
			default:
				break;
			}

		}
			catch(Exception e) {
				
			}

	}

	}
}
