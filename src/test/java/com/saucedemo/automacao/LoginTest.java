package com.saucedemo.automacao;
import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest {
	private WebDriver driver;
	
	@BeforeClass 
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "D:\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();	
	}
	
	public void loginMethod(String login, String password,boolean isLoginTest, boolean shouldFail){
		//isLoginTest = EN[Used to log off at the end of the test / Usada para realizar o logoff ao final
		driver.findElement(By.id("user-name")).clear();
		driver.findElement(By.id("user-name")).click();
		driver.findElement(By.id("user-name")).sendKeys(login);
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).sendKeys(password);	
		driver.findElement(By.id("login-button")).click();
		if(isLoginTest){
			if(shouldFail){
				assertEquals(driver.findElement(By.xpath("//html/body/div[1]/div/div[2]/div[1]/div[1]/div/form/div[3]/h3")).getText(), "Epic sadface: Username and password do not match any user in this service");
			}else if(shouldFail == false){
				assertEquals(driver.findElement(By.className("title")).getText(), "PRODUCTS");
				System.out.println("loginSuccess - OK");
			}
		}
	}
	
	@Test (priority = 1)
	public void loginDenied(){
		driver.get("https://www.saucedemo.com/");
		Assert.assertEquals("Swag Labs", driver.getTitle());
		loginMethod("WrongLogin", "WrongPassword", true, true); // X - X
		loginMethod("standard_user", "adasd", true, true); // V - X
		loginMethod("standard_user", " ", true, true); // V - X
		loginMethod(" ", "secret_sauce", true, true); // X - V
		loginMethod("", " ", true, true); //X - X
		System.out.println("Wrong login - Passed);
	}
	
	@AfterTest
	public void quit() {
		driver.close();
		driver.quit();
	}
	public void loginSuccess(String login, String password){
		loginMethod("problem_user", "secret_sauce", true, false);
		//Usuário, Senha, deve realizar logoff, deve falhar
	} 	
}
