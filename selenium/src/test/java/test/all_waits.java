package test;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class all_waits {
    public static WebDriver driver;
    
	public static void main(String[] args) {
	WebDriverManager.chromedriver().setup();
	ChromeOptions options= new ChromeOptions();
	options.addArguments("--remote-allow-origins=*");
	driver= new ChromeDriver(options);
	driver.manage().window().maximize();
//  implicit wait
//	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.get("https://sellglobal.ebay.in");
//
//	try {
//	    driver.findElement(By.xpath("//div[@data-id='e744f3a']/div[@class='elementor-widget-container']/a[@class='buttn']")).click();
//		}
//	catch(Exception e)
//	{
//		System.out.println(e);	
//	}
//	
	
//	Explicit wait
//	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//	try
//	{
//	WebElement element= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/div[@data-id='e744f3a']/div[@class='elementor-widget-container']/a[@class='buttn']")));
//	element.click();	
//	}catch (Exception e)
//	{
//		System.out.println(e);
//	}
	
//  Fluent wait
	Wait <WebDriver> wait= new FluentWait<WebDriver>(driver)
			.withTimeout(Duration.ofSeconds(10))
			.pollingEvery(Duration.ofSeconds(2))
			.ignoring(NoSuchElementException.class);
	WebElement element= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-id='e744f3a']/div[@class='elementor-widget-container']/a[@class='buttn']")));
	element.click();
	}

}
