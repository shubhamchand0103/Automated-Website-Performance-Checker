import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ConnectAndAutomate {

	//Constants
	public static final String pageUrl = "https://gtmetrix.com";
	public static final String userId = "shubhamchand0103@gmail.com";
	public static final String password = "shubhamchand0103";
	private static XSSFWorkbook wb;
	public static WebDriver driver;
	public static XSSFSheet sh1,sh2,sh3,sh4,sh5;
	//Element selectors
	public static final String searchBox = "identifier";
	public static final String analyseButton  = "analyze-form-button";
	public static final String userIdBox = "li-email";
	public static final String passwordBox = "li-password";
	public static final String logInButton = "js-auth-widget-link";
	public static final String countryButton = "af-info-region";
	public static final int clicks[] = {1,3,4,5,6};
	public static String title = "";
	//Result values
	public static String pageSpeed;
	public static String yslow="";
	public static String loadTime;
	public static String pageSize;
	public static String requests;
	public static String checkUrl ;
	
	
	
	
	static void test(String check, int ind, Sheet sheet, int count){
		driver.get(pageUrl);
		for(int i=0;i<clicks[count];i++){
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
	    
	    Row row = sheet.createRow(ind);
	    row.createCell(0).setCellValue(check);
	    row.createCell(1).setCellValue(pageSpeed);
	    row.createCell(2).setCellValue("");
	    row.createCell(3).setCellValue(loadTime);
	    row.createCell(4).setCellValue(pageSize);
	    row.createCell(5).setCellValue(requests);
		    
	 
	    System.out.println("Load Time: "+loadTime+"\npageSize: "+pageSize+"\nRequests: "+requests);
		
	    
	    
	}
	
	public static void main(String[] args) {
		int count = 0;
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
		
		
		//worksheet
		 try{
			 File src=new File("E:\\eclipse\\Automate\\Excel\\test.xlsx");
			 FileInputStream fis=new FileInputStream(src);
			 wb = new XSSFWorkbook(fis);
			 
			 Iterator<Sheet> sheetIterator = wb.iterator();
			 while (count<5) {
				 XSSFSheet  sheet = (XSSFSheet)sheetIterator.next();
			     for(int i=1;i<7;i++){
						checkUrl = sheet.getRow(i).getCell(0).getStringCellValue();
						test(checkUrl,i,sheet,count);
						
					 }
			     count++;
			 }
		
			FileOutputStream fout=new FileOutputStream(new File("E:\\eclipse\\Automate\\Excel\\Results.xlsx"));
			wb.write(fout);
			fout.close();

			}catch(Exception e){
				e.printStackTrace();
			}
		
		
				
		System.out.println("Executed Successfully..");
	}

}
