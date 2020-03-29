package com.qa.hubspot.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.ElementActions;
import com.qa.hubspot.util.JavaScriptUtil;
import com.qa.husspot.pojo.Contacts;

public class ContactsPage extends BasePage {
	WebDriver driver;
	ElementActions elementActions;
	JavaScriptUtil jsUtil;
	Contacts contacts;

	// By createContact=By.xpath("//span[text()='Create contact']"); //vandana
	// created
	By createContactButton = By.xpath("(//button[@type='button']//span[text()='Create contact'])[position()=1]");
	By createContactFormButton = By.xpath("(//button[@type='button']//span[text()='Create contact'])[position()=2]");

	By email = By.xpath("//input[@data-field='email']");
	By firstName = By.xpath("//input[@data-field='firstname']");
	By lastName = By.xpath("//input[@data-field='lastname']");
	By jobTitle = By.xpath("//input[@data-field='jobtitle']");
	By contactsNavigate = By.xpath("(//i18n-string[text()='Contacts'])[2]");
	By CreatedContactName = By.xpath("//span[@data-selenium-test='highlightTitle']//span");

	public ContactsPage(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
		elementActions = new ElementActions(this.driver);

	}

	public String getContactPageTitle() {
		return elementActions.doGetPageTitle(AppConstants.CONTACTS_PAGE_TITLE);
	}

	public String createContact(Contacts contacts) {

		elementActions.waitForElementPresent(createContactButton);
		elementActions.doClick(createContactButton);

		elementActions.waitForElementPresent(this.email);
		System.out.println(contacts.getEmail());
		elementActions.doSendKeys(this.email, contacts.getEmail());

		elementActions.doSendKeys(this.firstName, contacts.getFirstName());

		elementActions.waitForElementPresent(this.lastName);
		elementActions.doSendKeys(this.lastName, contacts.getLastName());

		elementActions.waitForElementPresent(this.jobTitle);
		elementActions.doSendKeys(this.jobTitle, contacts.getJobTitle());

		jsUtil.clickElementByJS(elementActions.getElement(createContactFormButton));

		elementActions.waitForElementPresent(this.contactsNavigate);
		String contactName = elementActions.doGetText(CreatedContactName);
		elementActions.doClick(contactsNavigate);
		return contactName;
		// elementActions.waitForElementPresent(CreatedContactName);

	}

}
