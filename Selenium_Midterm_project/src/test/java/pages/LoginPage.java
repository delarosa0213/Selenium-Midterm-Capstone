package pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class LoginPage {
  private WebDriver driver;
  public ExtentTest test;
  


  @Test(dataProvider = "loginCredentials")
  public void testValidLogin(String username, String password) {
	  
  	WebElement btnLogIn = driver.findElement(By.xpath("//span[@data-button-name='Login']"));
      btnLogIn.click();

      // Enter valid username and password
      WebElement usernameField = driver.findElement(By.xpath("//input[@id='si_popup_email']"));
      usernameField.sendKeys(username);
      
      WebElement passwordField = driver.findElement(By.xpath("//input[@id='si_popup_passwd']"));
      passwordField.sendKeys(password);
      
      WebElement loginButton = driver.findElement(By.xpath("//button[normalize-space()='Login']"));
      loginButton.click();
      
      test.log(LogStatus.PASS, "Login successful");
      
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

  }
  @DataProvider(name = "loginCredentials")
  public Object[][] loginData() {
      String filePath = "ExcelFile/loginCredentials.xlsx";
      Object[][] data = null;
      try (FileInputStream inputStream = new FileInputStream(filePath)) {
          XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
          Sheet loginSheet = workbook.getSheet("Sheet1");
          data = new Object[loginSheet.getLastRowNum()][2];
          for (int i = 0; i < loginSheet.getLastRowNum(); i++) {
              Row row = loginSheet.getRow(i + 1);
              Cell usernameCell = row.getCell(0);
              String username = usernameCell.getStringCellValue();
              Cell passwordCell = row.getCell(1);
              String password = passwordCell.getStringCellValue();
              data[i][0] = username;
              data[i][1] = password;
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
      return data;
  }
  public void validateTitle() {
  	String actualTitle = driver.getTitle();
      
  	String expectedTitle = "Instructor-Led Online Training with 24X7 Lifetime Support | Edureka";
      
  	if (actualTitle.equals(expectedTitle)) {
          System.out.println("Title of the webpage is correct");
          test.log(LogStatus.PASS, "Title Page is the same");
       } else {
          System.out.println("Title of the webpage is incorrect");
          test.log(LogStatus.FAIL, "Title Page is not the same");
       }
  }
  public LoginPage(WebDriver driver) {
	    this.driver = driver;
	    PageFactory.initElements(driver, this);
	}
  
}

