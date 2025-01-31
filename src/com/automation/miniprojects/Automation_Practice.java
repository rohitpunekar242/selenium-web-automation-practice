package com.automation.miniprojects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Automation_Practice {

	public static void main(String[] args) throws InterruptedException {

		//Initialize browser, Maximize window and Apply implicit wait
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		//Redirect to the website 
		String url = "https://rahulshettyacademy.com/AutomationPractice/";
		driver.get(url);

		//Print the title
		String title = driver.getTitle();
		System.out.println(title);

		//Print the current url
		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl);

		//Print the runtime class of title object
		Class<? extends String> c = title.getClass();
		System.out.println(c);

		/*
		//Print the source of the current page
		String CurrentPageSource = driver.getPageSource();
		System.out.println(CurrentPageSource);
		 */

		//Refresh the page
		driver.navigate().refresh();

		/*
		//Navigates to a new URL while maintaining current browser session state.
		driver.navigate().to(url);
		 */

		System.out.println("------------------------------------------------");


		//Test case 1: Validate if the first radio button one can be selected.
		List<WebElement> radioBtn = driver.findElements(By.name("radioButton"));
		radioBtn.get(0).click();

		if(radioBtn.get(0).isSelected())
		{
			System.out.println("Radio button 1 is selected, TEST CASE 1 PASSED");
		} else {
			System.out.println("Radio button 1 is NOT selected, TEST CASE 1 FAILED");
		}

		System.out.println("--------------------TEST CASE 1 PASSED----------------------------");


		//Test case 2: Validate if correct suggestions of countries are getting selected in suggestion input box.
		WebElement suggestionBox = driver.findElement(By.id("autocomplete"));
		String countryName = "New Zealand";
		String CountryNameXpath = "//*[text()='New Zealand']";
		suggestionBox.sendKeys("new");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement suggestion = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CountryNameXpath)));
		suggestion.click();
		//validation
		String selectedValue = suggestionBox.getDomProperty("value");
		if(selectedValue.equals(countryName))
		{
			System.out.println("Correct suggested country selected, TEST CASE 2 PASSED");
		}else {
			System.out.println("Incorrect suggested country selected, TEST CASE 2 FAILED");
		}

		System.out.println("--------------------TEST CASE 2 PASSED----------------------------"); 

		//Test case 3: Validate if correct option is getting selected from the dropdown box.
		WebElement dropdownBox = driver.findElement(By.id("dropdown-class-example"));
		Select dropdown = new Select(dropdownBox);
		String DesiredOption = "Option2"; 
		dropdown.selectByVisibleText(DesiredOption);

		String selectedOption = dropdown.getFirstSelectedOption().getText();
		if(selectedOption.equals(DesiredOption))
		{
			System.out.println("Correct dropdown option selected, TEST CASE 3 PASSED");
		}else {
			System.out.println("Incorrect dropdown option selected, TEST CASE 3 FAILED");
		}

		System.out.println("--------------------TEST CASE 3 PASSED----------------------------");

		//Test case 4: Validate if checkboxes are deselected & if correct checkboxes getting selected
		List<WebElement> checkboxes = driver.findElements(By.cssSelector("div#checkbox-example fieldset label input"));
		String desiredOption = "option3";
		int x = 1;
		for(WebElement checkbox : checkboxes)
		{
			if(!checkbox.isSelected())
			{
				System.out.println("Option"+x+" checkbox is de-selected.");
			}

			String value = checkbox.getDomProperty("value");
			if(value.equals(desiredOption))
			{
				checkbox.click();

				if(checkbox.isSelected())
				{
					System.out.println(desiredOption+" checkbox is now selected, TEST CASE 4 PASSED");
				}else {
					System.out.println(desiredOption+" checkbox is still NOT selected, TEST CASE 4 FAILED");
				}
			}
			x++;
		}

		System.out.println("-------------------TEST CASE 4 PASSED-----------------------------");

		//Test case 5: Validate window switching functionality
		String newWindowTitleName = "QAClick Academy - A Testing Academy to Learn, Earn and Shine";
		String mainWindowTitleName = "Practice Page";

		String mainWindowHandle = driver.getWindowHandle(); //main window handle
		driver.findElement(By.id("openwindow")).click();
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		Set<String> allWindowHandles = driver.getWindowHandles(); //all window handles.

		for (String childWindow : allWindowHandles) {
			if (!childWindow.equals(mainWindowHandle)) {
				driver.switchTo().window(childWindow);
				wait.until(ExpectedConditions.titleContains(newWindowTitleName));

				String newWindowTitle = driver.getTitle();
				System.out.println("New Window Title is: "+newWindowTitle);

				if(newWindowTitle.equals(newWindowTitleName)) {
					System.out.println("New window page is loaded correctly");
				} else {
					System.out.println("New window page is not loaded correctly");
				}
				driver.close();
			}
		}
		driver.switchTo().window(mainWindowHandle);
		wait.until(ExpectedConditions.titleContains(mainWindowTitleName));
		String mainWindowTitle = driver.getTitle();

		if(mainWindowTitle.equals(mainWindowTitleName)) {
			System.out.println("Switched back to original window successfully, TEST CASE 5 PASSED");
		} else {
			System.out.println("Switched to the invalid window, TEST CASE 5 FAILED");
		}


		System.out.println("------------------TEST CASE 5 PASSED------------------------------");


		//Test case 6: Validate tab switching functionality
		WebElement newTab = driver.findElement(By.id("opentab"));
		newTab.click();
		// Get all window handles (tabs)
		Set<String> windowHandles = driver.getWindowHandles();
		// Convert the Set to a List
		ArrayList<String> tabs = new ArrayList<>(windowHandles);
		// Switch to the new tab (second tab)
		driver.switchTo().window(tabs.get(1));
		// Print the title of the new tab
		System.out.println("New Tab Title: " + driver.getTitle());
		driver.close();
		// Switch back to the original tab (first tab)
		driver.switchTo().window(tabs.get(0));
		// Print the title of the original tab
		System.out.println("Original Tab Title: " + driver.getTitle());
		//Validation
		if(mainWindowTitle.equals(driver.getTitle())) {
			System.out.println("Switched back to original window successfully, TEST CASE 6 PASSED");
		} else {
			System.out.println("Switched to the invalid window, TEST CASE 6 FAILED");
		}

		System.out.println("-------------------TEST CASE 6 PASSED-----------------------------");

		//Test case 7: Validate switching to alert functionality
		String alertUsername ="david";
		WebElement alertTextBox = driver.findElement(By.id("name"));
		alertTextBox.sendKeys(alertUsername);
		WebElement alertBtn = driver.findElement(By.id("alertbtn"));
		alertBtn.click();
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		String[] splitString = alertText.split(",");
		String[] actualSplittedString = splitString[0].split(" ");
		if(actualSplittedString[1].equals(alertUsername))
		{
			System.out.println("Alert modal username validated successfully");
		}else {
			System.out.println("Alert modal username validation failed.");
		}
		alert.accept();
		//Validation
		if(mainWindowTitle.equals(driver.getTitle())) {
			System.out.println("Switch to alert functionality validated successfully, TEST CASE 7 PASSED");
		} else {
			System.out.println("Switch to alert functionality validation failed, TEST CASE 7 FAILED");
		}

		System.out.println("------------------TEST CASE 7 PASSED------------------------------");

		// Scroll to the specific element
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[text()='Web Table Example']")));


		//Test case 8: Print total courses count and total courses price count in the Web Table Example
		List<WebElement> columnHeaders = driver.findElements(By.xpath("//*[@name='courses']/tbody/tr"));
		int columnCount= columnHeaders.size()-1;
		System.out.println("Total courses count is: "+columnCount);

		List<WebElement> prices = driver.findElements(By.xpath("//*[@name='courses']/tbody/tr/td[3]"));
		int totalPrice = 0;
		for(WebElement price : prices)
		{
			String actualPrice= price.getText();
			totalPrice += Integer.parseInt(actualPrice);
		}
		System.out.println("Total price of all courses: "+totalPrice);

		int expectedTotalPrice = 235;
		if(totalPrice == expectedTotalPrice)
		{
			System.out.println("Total price of all courses is validated successfully, TEST CASE 8 PASSED");
		}else {
			System.out.println("Total price of all courses is not correct, TEST CASE 8 PASSED");
		}

		System.out.println("------------------TEST CASE 8 PASSED------------------------------");

		//Test case 9: Element Displayed Example Validation
		WebElement elementTextbox = driver.findElement(By.id("displayed-text"));
		WebElement showBtn = driver.findElement(By.id("show-textbox"));
		WebElement hideBtn = driver.findElement(By.id("hide-textbox"));

		hideBtn.click();
		if(!elementTextbox.isDisplayed())
		{
			System.out.println("Element is hidden successfully");
		}else {
			System.out.println("Element is unexpectedly not hidden, TEST FAILED");
		}
		showBtn.click();
		if(elementTextbox.isDisplayed())
		{
			System.out.println("Element is displayed successfully.");
		}else {
			System.out.println("Element is unexpectedly hidden, TEST FAILED");
		}

		System.out.println("------------------TEST CASE 9 PASSED------------------------------");

		//Test case 10: In Web table fixed header example, Print the count of total people/Position/City & Print the total amount
		List<WebElement> fixedHeadTable = driver.findElements(By.xpath("//*[@class='tableFixHead']/table/tbody/tr"));
		int tableCount = fixedHeadTable.size();
		System.out.println("Total people count is: "+tableCount);

		int totalActualAmount = 0;
		List<WebElement> totalAmount = driver.findElements(By.xpath("//*[@class='tableFixHead']/table/tbody/tr/td[4]"));
		for(WebElement amount : totalAmount)
		{
			String actualAmount = amount.getText();
			totalActualAmount += Integer.parseInt(actualAmount);
		}
		System.out.println("Actual total amount collected: "+totalActualAmount);

		WebElement amountTotal = driver.findElement(By.cssSelector("div.totalAmount"));
		String amountCollected = amountTotal.getText();
		String[] SplittedTotalAmountCollected = amountCollected.split(":");
		int diplayedTotalAmount = Integer.parseInt(SplittedTotalAmountCollected[1].trim());
		System.out.println("Displayed total amount collected: "+diplayedTotalAmount);

		//Validate by comparing both values
		if (totalActualAmount == diplayedTotalAmount) {
			System.out.println("Validation Passed: Actual amount matches displayed amount.");
		} else {
			System.out.println("Validation Failed: Mismatch in amount!");
			System.out.println("Expected: " + diplayedTotalAmount + ", but found: " + totalActualAmount);
		}

		System.out.println("------------------TEST CASE 10 PASSED------------------------------");
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[text()='Mouse Hover Example']")));

		//Test case 11: Validating mouse hover functionality
		WebElement elementToHover = driver.findElement(By.id("mousehover"));
		Actions actions = new Actions(driver);
		actions.moveToElement(elementToHover).perform();
		WebElement topOption = driver.findElement(By.xpath("//a[text()='Top']"));
		WebElement reloadOption = driver.findElement(By.xpath("//a[text()='Reload']"));
		Thread.sleep(2000);
		// Validate if the options are displayed
		if (topOption.isDisplayed() && reloadOption.isDisplayed()) {
			System.out.println("Validation Passed: Hover menu options are displayed.");
		} else {
			System.out.println("Validation Failed: Hover menu options are NOT displayed.");
		}
		topOption.click();
		// Get final scroll position
		long finalScrollPosition = (long) js.executeScript("return window.pageYOffset;");
		// Validate if the window scrolled to the top
		if (finalScrollPosition == 0) {
			System.out.println("Validation Passed: Window scrolled to top.");
		} else {
			System.out.println("Validation Failed: Window did not scroll to top.");
		}

		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[text()='Mouse Hover Example']")));

		// Refresh the page using "Reload" option
		actions.moveToElement(elementToHover).perform();
		reloadOption.click();
		// Validate page reload (check URL remains the same)
		String currentURL = driver.getCurrentUrl();
		if (currentURL.equals("https://rahulshettyacademy.com/AutomationPractice/")) {
			System.out.println("Validation Passed: Page reloaded successfully.");
		} else {
			System.out.println("Validation Failed: Page did not reload.");
		}

		System.out.println("------------------TEST CASE 11 PASSED------------------------------");

		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[text()='iFrame Example']")));

		//Test case 12: Validating iFrame functionality
		WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("courses-iframe")));
		driver.switchTo().frame(iframe);
		// Perform action inside the iFrame
		WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='JOIN OUR ACADEMY']")));
		System.out.println("Text inside iFrame: "+heading.getText());
		//Switch back to main page
		driver.switchTo().defaultContent();
		//Perform action on the main page
		WebElement mainPageHeading = driver.findElement(By.xpath("//legend[text()='iFrame Example']"));
		if(mainPageHeading.isDisplayed())
		{
			System.out.println("Successfully switched back to main page.");
		}else {
			System.out.println("Failed to return to the main page.");
		}

		System.out.println("------------------TEST CASE 12 PASSED------------------------------");
		
		driver.quit();
	}



}
