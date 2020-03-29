package com.qa.hubspot.test;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.pages.HomePage;
import com.qa.hubspot.pages.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.husspot.pojo.Credentials;

public class LoginPageTest {

	BasePage basePage;
	WebDriver driver;
	Properties prop;
	LoginPage loginPage;
	HomePage homePage;
	Credentials credentials;
	
	@BeforeTest
	@Parameters({"browser"})
	public void setup(@Optional("opt") String browser) {
		basePage = new BasePage();
		prop = basePage.init_prop();// TO GET PROPERTIES OF FILE 
		  
		if(browser.equals("opt"))	//
		{
		driver = basePage.init_driver(prop); // get browser from config properrty 
		}
		else
		{
		driver=basePage.init_driver_parameter(browser); // to get browser from .xml
		}
		loginPage = new LoginPage(driver);
		homePage=new HomePage(driver);
	 credentials = new Credentials(prop.getProperty("username"),prop.getProperty("password"));
	}

	@Test(priority = 2)
	public void verifyLoginPageTitleTest() {

		String title = loginPage.getPageTitle();
		System.out.println("LoginPage title is :"+title);
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Test(priority=1, description="verify sign up link Test")
	public void verifySignUpLinkTest() {
		
		Assert.assertTrue(loginPage.verifySignUpLink());
	}
	
	@Test(priority=3)
	public void loginTest() {
		homePage=loginPage.doLogin(credentials);
		
		String loggedUser= homePage.loggedInUserName();
		 System.out.println("logged in user :"+loggedUser);
		 Assert.assertEquals(loggedUser, prop.getProperty("accountName"));
	}
	
	
   @DataProvider
   public Object[][] getLoginInvalidData() {
	   Object data[][]= {
			   {"test@gmail.com", "test@122"},
			   {"test@gmail.com","  "},
			   {"  ", "tesdts@3333"},
			   {"  ", "  "},
			   {"test","test"} //without domain name 
	   };
	return data;//
   }
	
	@Test(priority=4,dataProvider= "getLoginInvalidData", enabled=false)
	public void login_InvalidTestCases(String emailID, String pwd) {
		loginPage.doLogin(credentials);//we dont want navigate to homePage hence no reference 
	Assert.assertTrue(loginPage.checkLogInErrorMsg());
	}
	
	  
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
