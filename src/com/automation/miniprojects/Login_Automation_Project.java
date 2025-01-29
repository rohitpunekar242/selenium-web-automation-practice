package com.automation.miniprojects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Login_Automation_Project {

	public static void main(String[] args) throws InterruptedException {

		//Invoke browser & redirect to the login website
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		String url = "https://rahulshettyacademy.com/locatorspractice/";
		driver.get(url);

		//Enter username & password
		String username = "qatesting";
		String password = "123";
		driver.findElement(By.id("inputUsername")).sendKeys(username);
		driver.findElement(By.cssSelector("[placeholder='Password']")).sendKeys(password);

		//Select the checkboxes
		driver.findElement(By.id("chkboxOne")).click();
		driver.findElement(By.id("chkboxTwo")).click();

		//Click on sign in button
		driver.findElement(By.className("submit")).click();

		//Validate the Invalid credentials error
		Thread.sleep(1000);
		String Actual_Error = "* Incorrect username or password";
		WebElement Expected_Error = driver.findElement(By.className("error"));
		Boolean validateError= Expected_Error.getText().equals(Actual_Error);
		System.out.println(validateError+": Error message validated successfully.");

		//Click on Forgot password link
		driver.findElement(By.linkText("Forgot your password?")).click();

		//Enter name, email & phone number
		Thread.sleep(1000);
		String name = "qatester123";
		String email = "qatester123@gmail.com";
		String phone = "9988776655";
		driver.findElement(By.xpath("(//*[@action='#'])/input[1]")).sendKeys(name);
		driver.findElement(By.xpath("(//*[@action='#'])/input[2]")).sendKeys(email);
		driver.findElement(By.xpath("(//*[@action='#'])/input[3]")).sendKeys(phone);

		//Click on reset login button
		driver.findElement(By.className("reset-pwd-btn")).click();

		//Validate the text message & get the actual password from text
		String tempPasswordExpectedText = "Please use temporary password 'rahulshettyacademy' to Login.";
		String tempPasswordActualText = driver.findElement(By.className("infoMsg")).getText();
		Boolean validateTempPasswordText = tempPasswordActualText.equals(tempPasswordExpectedText);
		System.out.println(validateTempPasswordText+": Temprory password message validated successfully.");

		//Trim the required password text
		String[] parts = tempPasswordActualText.split("'");
		String actualPassword = parts[1];

		//Click on 'go to login' button
		driver.findElement(By.className("go-to-login-btn")).click();

		//Enter username & actual password
		Thread.sleep(1000);
		driver.findElement(By.id("inputUsername")).sendKeys(username);
		driver.findElement(By.cssSelector("[placeholder='Password']")).sendKeys(actualPassword);

		//Click on sign in button
		driver.findElement(By.className("submit")).click();

		//Validate successful sign in message
		Thread.sleep(1000);
		String expectedSuccessfulText = "You are successfully logged in.";
		String actualSuccessfulText = driver.findElement(By.cssSelector(".login-container p")).getText();
		Boolean ValidateSuccessfulText = actualSuccessfulText.equals(expectedSuccessfulText);
		System.out.println(ValidateSuccessfulText+": Successful login message validated successfully.");

		//Validate that text message contains correct username that we have provided
		String actualSuccessfulTextOne = driver.findElement(By.cssSelector(".login-container h2")).getText();
		String[] SplitTextOne = actualSuccessfulTextOne.split(" ");
		String[] actualSplittedName = SplitTextOne[1].split(",");
		Boolean validateUsername = actualSplittedName[0].equals(username);
		System.out.println(validateUsername+": Username validated correctly in successful text message.");

		//Click on logout button
		driver.findElement(By.className("logout-btn")).click();

		//Validate that Page is redirected to the original login page.
		Thread.sleep(1000);
		Boolean signInText = driver.findElement(By.xpath("//*[text()='Sign in']")).isDisplayed();
		System.out.println(signInText+": Redirected to the original login page successfully.");

		//Quit the browser-teardown
		System.out.println("Login Test is successful.");
		driver.quit();
	}

}
