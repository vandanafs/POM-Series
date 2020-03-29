package com.qa.hubspot.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.ElementActions;
import com.qa.hubspot.util.JavaScriptUtil;

public class HomePage extends BasePage {
	WebDriver driver;
	ElementActions elementActions;
	JavaScriptUtil jsUtil;
	
	//ContactsPage contactsPage; //vandana logic needs to test 
	// By locators
	By homePageHeader = By.cssSelector(" h1.private-page__title");
	By accImage = By.cssSelector("img.nav-avatar");
	By loggedInUserName = By.cssSelector("div.navAccount-accountName");
	By parentContacts = By.id("nav-primary-contacts-branch");
	By childContacts = By.id("nav-secondary-contacts");

	public HomePage(WebDriver driver) {
		
		this.driver = driver;
		elementActions = new ElementActions(this.driver);
		jsUtil=new JavaScriptUtil(this.driver);
	}

	public String getHomePageTitle() {
		return elementActions.doGetPageTitle(AppConstants.HOME_PAGE_TITLE);
	}

	public String isHomePageHeaderExist() {
		elementActions.waitForElementPresent(homePageHeader);
		if (elementActions.doIsDisplayed(homePageHeader)) {
			return elementActions.doGetText(homePageHeader);

		} else {
			return null;
		}
	}

	public String loggedInUserName() {
		elementActions.waitForElementPresent(accImage);
		elementActions.doClick(accImage);
		if (elementActions.doIsDisplayed(loggedInUserName)) {
			return elementActions.doGetText(loggedInUserName);
		} else {
			return null;
		}
	}

	public ContactsPage goToContactsPage() {
		clickOnContacts();
		return new ContactsPage(driver);
	//	return contactsPage; //vandana logic needs to tested 
	}
	
	public void clickOnContacts() {
		elementActions.waitForElementPresent(parentContacts);
		elementActions.doClick(parentContacts);
		elementActions.waitForElementPresent(childContacts);
		//jsUtil.clickElementByJS(elementActions.getElement(childContacts));
		elementActions.doClick(childContacts);
	}
}
