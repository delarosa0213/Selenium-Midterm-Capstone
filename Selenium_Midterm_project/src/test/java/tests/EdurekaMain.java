package tests;

import java.util.concurrent.TimeUnit;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import pages.LoginPage;
import pages.ProfilePage;
import pages.TopicOfInterest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class EdurekaMain {

	public WebDriver driver;
	public LoginPage login;
	public ProfilePage profile;
	public TopicOfInterest topics;
	public ExtentReports extent;
    public ExtentTest test;
    
    
	
	  @BeforeClass
	  public void setUp() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	    String currentDate = dateFormat.format(new Date());
	    File reportDir = new File("ExtentReport/" + currentDate);
	    reportDir.mkdirs();
	    String reportPath = reportDir.getAbsolutePath() + "/TestReport.html";
	    extent = new ExtentReports(reportPath);
        this.test = extent.startTest("ProfilePageTest");
        PageFactory.initElements(driver, this);
		    
		
		driver = new  ChromeDriver();
		
		login = new LoginPage(driver);
		
		profile = new ProfilePage(driver);
		
		topics = new TopicOfInterest(driver);
		
		test.log(LogStatus.INFO, "Opening login page...");
	    // Enter the login credentials
	    driver.get("https://www.edureka.co/");
	
	    // Maximize the browser window
	    driver.manage().window().maximize();
	
	    // Set implicit wait to 10 seconds
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	  
}
	  @Test(priority =1 ,dataProvider = "loginCredentials")
	    public void testLogin(String username, String password) {
	        login.testValidLogin(username, password);
		  	test.log(LogStatus.INFO, "My Profile Icon was clicked");
	        
	
	    }

	    @DataProvider(name = "loginCredentials")
	    public Object[][] loginData() {
	        
	        return login.loginData();
	    }
	    
	    @Test(priority = 2)
	    public void Myprofile() {
	    	profile.myProfile();
	        test.log(LogStatus.PASS, "Profile page opened successfully");
		  	test.log(LogStatus.INFO, "Navigated to My Profile | Edureka");
	    	
	    }
	    @Test(priority = 3)
	    public void ToI() {
	    	topics.topicOfInterest();
	    	test.log(LogStatus.PASS, "Topic of interest selected successfully");
	    }
	    
	    @Test(priority = 4, dataProvider = "Topics")
	    public void chooseTopic(String topic1, String topic2) {
	    	topics.topics(topic1, topic2);
	    	test.log(LogStatus.PASS, "Topic of interest selected successfully");
	    }
	    @DataProvider(name = "Topics")
	    public Object [][] tcs(){
	    	return topics.tcs();
	    }
	    @AfterClass
	    public void tearDown() {
	        // Close the browser
	    	extent.endTest(test);
	        extent.flush();
	    }
}
	  