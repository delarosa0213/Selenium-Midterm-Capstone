package pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TopicOfInterest {
	private WebDriver driver;
	private ExtentReports extent;
    private ExtentTest test;

    
	
	  public  void topicOfInterest() {
		
	  	WebElement topics = driver.findElement(By.xpath("//a[contains(text(),'Topics of Interest')]"));
	  	topics.click();
	  	
	  	WebElement editBtn =  driver.findElement(By.xpath("//button[contains(text(),'EDIT')]"));
	  	editBtn.click();
	  }
	
	  @Test(dataProvider = "Topics")
	  public void topics(String topic1, String topic2) {
	  	
	  	WebElement tc1 = driver.findElement(By.xpath("//*[text()='" + topic1 +"']"));
	  	tc1.click();
	  	
	  	WebElement tc2 = driver.findElement(By.xpath("//*[text()='" + topic2 +"']"));
	  	tc2.click();
	      
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	      
	  	JavascriptExecutor js = (JavascriptExecutor) driver;
	  	js.executeScript("window.scrollTo(0, document.body.scrollHeight/2)");
	
	      
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	      
	    WebElement saveBtn = driver.findElement(By.xpath("//mat-tab-body//div//button[2]"));
	    saveBtn.click();
	    test.log(LogStatus.INFO, "Saved");
	    test.log(LogStatus.PASS, "Topics Saved");
	  }
	  @DataProvider(name = "Topics")
	  public Object[][] tcs() {
	      String filePath = "ExcelFile/topics.xlsx";
	      Object[][] data = null;
	      try (FileInputStream inputStream = new FileInputStream(filePath)) {
	          XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
	          Sheet loginSheet = workbook.getSheet("Sheet1");
	          data = new Object[loginSheet.getLastRowNum()][2];
	          for (int i = 0; i < loginSheet.getLastRowNum(); i++) {
	              Row row = loginSheet.getRow(i + 1);
	              Cell topic1Cell = row.getCell(0);
	              String topic1 = topic1Cell.getStringCellValue();
	              Cell topic2Cell = row.getCell(1);
	              String topic2 = topic2Cell.getStringCellValue();
	              data[i][0] = topic1;
	              data[i][1] = topic2;
	          }
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	      return data;
	  }
	  public TopicOfInterest(WebDriver driver) {
		  this.driver = driver;
		  PageFactory.initElements(driver, this);
		}
}
