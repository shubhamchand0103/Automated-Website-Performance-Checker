import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ConnectAndAutomate {

	//Constants
	public static final String pageUrl = "https://gtmetrix.com";
	public static final String userId = "shubhamchand0103@gmail.com";
	public static final String password = "shubhamchand0103";
	//Element selectors
	public static final String searchBox = "identifier";
	public static final String analyseButton  = "analyze-form-button";
	public static final String userIdBox = "li-email";
	public static final String passwordBox = "li-password";
	public static final String logInButton = "js-auth-widget-link";
	public static final String countryButton = "af-info-region";
	public static String title = "";
	//Result values
	public static String pageSpeed;
	public static String yslow;
	public static String loadTime;
	public static String pageSize;
	public static String requests;
	public static String checkUrl = "www.google.com";
	
	static WebDriver driver;
	
	
	static void test(String check){
		for(int i=0;i<5;i++){
			driver.get(pageUrl);
			for(int j=0;j<i;j++){
				driver.findElement(By.id(countryButton)).click();
				for(int k=0;k<50;k++);
			}
		driver.findElement(By.xpath("//input[@placeholder='Enter URL to Analyze...']")).sendKeys(check);;
		driver.findElement(By.xpath("//button[contains(.,'Analyze')]")).click();
		while(true){
			title= driver.getTitle();
			title = title.substring(0, 29);
			//System.out.println(title);
			if(title.equals("Latest Performance Report for")){
				break;
			}
		}
		
		System.out.println("Result Page : ");
		pageSpeed = driver.findElement(By.xpath("//span[@class='report-score-percent']")).getText();
	    loadTime =  driver.findElement(By.xpath("//*[@class='report-page-detail']/.//span[contains(@class,'report-page-detail-value')]")).getAttribute("innerHTML");
	    pageSize = driver.findElement(By.xpath("//*[@class='report-page-detail report-page-detail-size']/.//span[contains(@class,'report-page-detail-value')]")).getAttribute("innerHTML");
	    requests = driver.findElement(By.xpath("//*[@class='report-page-detail report-page-detail-requests']/.//span[contains(@class,'report-page-detail-value')]")).getAttribute("innerHTML");
	    
	    System.out.println("Load Time: "+loadTime+"\npageSize: "+pageSize+"\nRequests: "+requests);
		}
	    
	    
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "E:\\#Study materials\\Projects\\Motaingage\\Selenium Jars\\chromedriver_win32\\chromedriver.exe");
		 driver = new ChromeDriver();
		driver.get(pageUrl);
		driver.findElement(By.className(logInButton)).click();
		driver.findElement(By.id(userIdBox)).sendKeys(userId);
		driver.findElement(By.id(passwordBox)).sendKeys(password);
		driver.findElement(By.xpath("//button[contains(.,'Log In')]")).click();
		while(true){
			title = driver.getTitle();
			if(title.equals("Dashboard | GTmetrix")){
				break;
			}
		}
		
		test(checkUrl);		
		System.out.println("Executed Successfully..");
	}

}
