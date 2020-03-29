package com.qa.hubspot.test;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.pages.ContactsPage;
import com.qa.hubspot.pages.HomePage;
import com.qa.hubspot.pages.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.ExcelUtil;
import com.qa.husspot.pojo.Contacts;
import com.qa.husspot.pojo.Credentials;

public class ContactsPageTest {

	WebDriver driver;
	Properties prop;
	BasePage basePage;
	LoginPage loginPage;
	HomePage homePage;
	ContactsPage contactsPage;
	Credentials credentials;
	Contacts contacts;
	
	@BeforeTest
	public void setUp() {
		basePage=new BasePage();
		prop=basePage.init_prop();
		driver=basePage.init_driver(prop);
		loginPage=new LoginPage(driver);//obj of LP
		 credentials = new Credentials(prop.getProperty("username"),prop.getProperty("password"));
		homePage = loginPage.doLogin(credentials);
		//homePage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		contactsPage=homePage.goToContactsPage();
		
		
	}
	
	@Test(priority=1)
	public void verifyContactsPageTitleTest() {
		String title=contactsPage.getContactPageTitle();
		System.out.println(title);
		Assert.assertEquals(title , AppConstants.CONTACTS_PAGE_TITLE);
	}
	
	
	@DataProvider
	public Object[][] getContactsData() {
		Object data[][]=ExcelUtil.getTestData(AppConstants.CONTACTS_SHEET_NAME);//since data is 2 dimen hence store and return
		return data;
	}
	
	
	@Test(priority=2, dataProvider="getContactsData")
	public void createContactsTest(String email, String firstName, String lastName, String jobTitle)  { //pass holding paramenters 
	contacts = new Contacts(email,firstName,lastName,jobTitle);
		String createdContactName=contactsPage.createContact(contacts);
		Assert.assertEquals(createdContactName,firstName+" "+lastName);
	}
	
	
	//@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
