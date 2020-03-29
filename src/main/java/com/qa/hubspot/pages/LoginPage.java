package com.qa.hubspot.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.ElementActions;
import com.qa.husspot.pojo.Credentials;

public class LoginPage extends BasePage {
	WebDriver driver;
	ElementActions elementActions;
Credentials credentials;
	
	// 1.By locators --page objects
 //no driver API in my code, just using ElementActions 
	By username = By.id("username");
	By password = By.id("password");
	By loginBtn = By.id("loginBtn");
	By signUpLink = By.linkText("Sign up");
	By headingOnHomePage = By.xpath("//h1[text()='Sales Dashboard']");
	By errorMsg = By.cssSelector("h5.private-alert__title");

	// 2.Constructors of page class
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementActions = new ElementActions(this.driver);

	}

	// 3.Page actions
	public String getPageTitle() {
		return elementActions.doGetPageTitle(AppConstants.LOGIN_PAGE_TITLE);
	

	}

	public boolean verifySignUpLink() {
		elementActions.waitForElementPresent(signUpLink);//
		return elementActions.doIsDisplayed(signUpLink);

	}

	public HomePage doLogin(Credentials credentials) {
		System.out.println("credentials are  :"+credentials.getEmailID()+ "  " +credentials.getPassword());
		elementActions.waitForElementPresent(username);// only UName bcos rest elemnts will visible 
		elementActions.doSendKeys(username,credentials.getEmailID());
		elementActions.doSendKeys(password, credentials.getPassword());
		elementActions.doClick(loginBtn);

		return new HomePage(driver);
	}

	public boolean checkLogInErrorMsg() {
		return elementActions.doIsDisplayed(errorMsg);
		
	}
}
