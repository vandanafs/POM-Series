package com.qa.hubspot.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;

import com.aventstack.extentreports.utils.FileUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {

	public WebDriver driver;
	public Properties prop;

	public OptionsManager optionsManager;
	// ThreadLocal have 2 concepts set and get
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>(); // parallel execution helpful

	// getter
	public static synchronized WebDriver getDriver() { // if one thread is getting then other should not use the same
		return tlDriver.get();

	}

	/**
	 * this method is used initialise the webDriver on the basis of browserName
	 * 
	 * @param browserName
	 * @return
	 */
	public WebDriver init_driver(Properties prop) {
		String browserName = prop.getProperty("browser");
		optionsManager = new OptionsManager(prop);
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));// to set browser

		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));// to set browser

		} else if (browserName.equalsIgnoreCase("safari")) {
			WebDriverManager.getInstance(SafariDriver.class).setup();
			tlDriver.set(new SafariDriver());// to set browser

		} else {
			System.out.println(browserName + "is not found, please pass the right browser name");
		}
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		// driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);// no mixing
		// of both wait iMp and exp
		return getDriver();

	}

	/**
	 * this method is used to get browserName from .xml file
	 * 
	 * @param browserName1
	 * @return
	 */
	public WebDriver init_driver_parameter(String browserName1) {
		if (browserName1.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		} else if (browserName1.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (browserName1.equalsIgnoreCase("safari")) {
			WebDriverManager.getInstance(SafariDriver.class).setup();
			driver = new SafariDriver();

		} else {
			System.out.println(browserName1 + "is not found, please pass the right browser name");
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(prop.getProperty("url"));

		return driver;
	}

	/**
	 * @return this method returns properties -prop available in config.properties
	 * @return
	 */
	public Properties init_prop() {

		prop = new Properties();
		String path = null;
		String env = null;

		try {
			env = System.getProperty("env");// from cmd line "env" is coming from mvn command
			if (env == null) {
				path = "./src/main/java/com/qa/hubspot/config/config.properties";
			} else {
				switch (env) {
				case "qa":
					path = "./src/main/java/com/qa/hubspot/config/config.qa.properties";
					break;
				case "stg":
					path = "./src/main/java/com/qa/hubspot/config/config.stg.properties";
					break;
				case "prod":
					path = "./src/main/java/com/qa/hubspot/config/config.properties";
					break;
				default:
					path = "./src/main/java/com/qa/hubspot/config/config.properties";
					System.out.println("No env is passed");
					break;
				}
			}

			FileInputStream ip = new FileInputStream(path);// connection
															// established
			prop.load(ip);// load file
		} catch (FileNotFoundException e) {
			e.printStackTrace();

			System.out.println("config file is not found....");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * take screenshot util
	 */
	public String getScreenshot() {
		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE); // type cast into takeScreenshot
																						// interface
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		File dst = new File(path);
		try {
			FileUtils.copyFile(src, dst);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

}
