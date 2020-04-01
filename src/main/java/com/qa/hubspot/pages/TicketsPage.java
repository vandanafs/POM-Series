package com.qa.hubspot.pages;

import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;

public class TicketsPage  extends BasePage{
	WebDriver driver;
	
	
	public TicketsPage(WebDriver driver) {
		this.driver=driver;
			System.out.println("line added from github ");
	}
}
