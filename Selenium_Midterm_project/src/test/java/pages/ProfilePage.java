package pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ProfilePage {
	
	private WebDriver driver;
	private ExtentReports extent;
    private ExtentTest test;


	public void myProfile() {
	  	
	  	driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	  	
	  	String profileTitle = driver.getTitle();
	  	String expectedTitle = "My Profile | Edureka";
	  	
	  	WebElement myProfileIcon = driver.findElement(By.xpath("//a[@role='button']//span//img"));
	  	myProfileIcon.click();
	  	
	  	WebElement myProfile = driver.findElement(By.xpath("//a[normalize-space()='My Profile']"));
	  	myProfile.click();
	  	
	  	if (profileTitle.equals(expectedTitle)) {
	          System.out.println("Title of the webpage is correct");
	          test.log(LogStatus.PASS, "Title of the webpage is the same...");
	       } else {
	          System.out.println("Title of the webpage is incorrect" + profileTitle);
	          test.log(LogStatus.FAIL, "Title of the webpage is the not same...");
	       }
	}
	public ProfilePage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		  this.driver = driver;
		    PageFactory.initElements(driver, this);
	}
}
