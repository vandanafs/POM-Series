package com.qa.hubspot.test;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.pages.HomePage;
import com.qa.hubspot.pages.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.husspot.pojo.Credentials;

public class HomePageTest {
	HomePage homePage;
	BasePage basePage;
	WebDriver driver;
	Properties prop;
	LoginPage loginPage;
	Credentials credentials;

	@BeforeTest
	@Parameters({ "browser" })
	public void setup(@Optional("opt") String browser) {
		basePage = new BasePage();
		prop = basePage.init_prop();// TO GET PROPERTIES OF FILE
		if (browser.equals("opt")) {
			driver = basePage.init_driver(prop); // this is to get browser from config
		} else {
			driver = basePage.init_driver_parameter(browser);
		}

		loginPage = new LoginPage(driver);
		 credentials = new Credentials(prop.getProperty("username"),prop.getProperty("password"));
		homePage = loginPage.doLogin(credentials);
	//	homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password")); // if we dont return
																									// then can not any
																									// methods of
																									// homepage

	}

	@Test(priority = 2)
	public void verifyHomePageTitleTest() {
		String title = homePage.getHomePageTitle();
		System.out.println("Home Page title:" + title);
		Assert.assertEquals(title, AppConstants.HOME_PAGE_TITLE);
	}

	@Test(priority = 1)
	public void verifyHomePageHeaderExistTest() {
		String headerVal = homePage.isHomePageHeaderExist();
		System.out.println("Home PAge header :" + headerVal);
		Assert.assertEquals(headerVal, AppConstants.HOME_PAGE_HEADER);
	}

	@Test(priority = 3)
	public void verifyLoggedInUserNameTest() {

		String loggedUser = homePage.loggedInUserName();
		System.out.println("logged in user :" + loggedUser);
		Assert.assertEquals(loggedUser, prop.getProperty("accountName"));

	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
