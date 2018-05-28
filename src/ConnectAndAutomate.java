import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ConnectAndAutomate {

	public static WebDriver driver;
	public static String chromeWebDriverLocation="lib\\chromedriver.exe";
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
	public static final int clicks[] = {1,3,4,5,6};
	public static final String countryNames[] = {"Dallas, USA","London, UK","Mumbai, India","Sydney, Australia","Sao Paulo, Brazil"};
	public static String title = "";
	
	//Result values
	public static String pageSpeed;
	public static String yslow="";
	public static String loadTime;
	public static String pageSize;
	public static String requests;
	public static String pageSpeedIssue;
	public static String ySlowIssue;
	public static String checkUrl ;
	public static String pageSource;
	
	//Excel variables
	public static XSSFWorkbook wb;
	public static String inputExcelFileLocation = "Excel\\test.xlsx";
	public static String outputExcelFileLocation = "Excel\\Result.xlsx";
	
	
	
	
	// Extracting result from web page
	public static void test(String check, int ind, XSSFSheet sheet, int count){
		
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
			if(title.equals("Latest Performance Report for")){
				break;
			}
		}
		/*
		try {
	        // Create a URL for the desired page
			String u = driver.getCurrentUrl();
	        URL url = new URL(u);       

	        // Read all the text returned by the server
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        String str;
	        while ((str = in.readLine()) != null) {
	            str = in.readLine().toString();
	            
	            System.out.println(str);
	            // str is one line of text; readLine() strips the newline character(s)
	        }
	        in.close();
	    } catch (MalformedURLException e) {
	    } catch (IOException e) {
	    }*/
		pageSpeed = driver.findElement(By.xpath("//span[@class='report-score-percent']")).getText();
	    loadTime =  driver.findElement(By.xpath("//*[@class='report-page-detail']/.//span[contains(@class,'report-page-detail-value')]")).getAttribute("innerHTML");
	    pageSize = driver.findElement(By.xpath("//*[@class='report-page-detail report-page-detail-size']/.//span[contains(@class,'report-page-detail-value')]")).getAttribute("innerHTML");
	    requests = driver.findElement(By.xpath("//*[@class='report-page-detail report-page-detail-requests']/.//span[contains(@class,'report-page-detail-value')]")).getAttribute("innerHTML");
	    pageSpeedIssue = driver.findElement(By.xpath("//*[@class='rules-name']/.//a[contains(@href,'#')]")).getAttribute("innerHTML");
	    driver.findElement(By.xpath("//*[@class='r-tabs-anchor' and @href='#yslow']")).click();
	    //delay
	    for(int k=0;k<20;k++);
	    ySlowIssue = driver.findElement(By.xpath("//*[@class='rules-name']/.//a[contains(@href,'#')]")).getAttribute("innerHTML");;
	    
	    //writing result to excel sheet
	    
	    if(Integer.parseInt(pageSpeed)>90){
	    	pageSpeed = "A"+pageSpeed; 
	    }else if(Integer.parseInt(pageSpeed)>80){
	    	pageSpeed = "B" + pageSpeed;
	    }else{
	    	pageSpeed = "C" + pageSpeed;
	    }
	    Row row = sheet.createRow(ind);
	    row.createCell(0).setCellValue(check);
	    row.createCell(1).setCellValue(pageSpeed);
	    row.createCell(2).setCellValue("");
	    row.createCell(3).setCellValue(loadTime);
	    row.createCell(4).setCellValue(pageSize);
	    row.createCell(5).setCellValue(requests);	 
	    row.createCell(6).setCellValue(pageSpeedIssue);
	    row.createCell(7).setCellValue(ySlowIssue);
	    
	    //Printing result to console
	    System.out.println("Page Speed: "+pageSpeed + "Load Time: "+loadTime+"\npageSize: "+pageSize+"\nRequests: "+requests+"\nPage Issues: "+pageSpeedIssue+"\nySlow Issues: "+ySlowIssue);
		System.out.println();
	}
	
	public static void main(String[] args) {
		int count = 0;
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver",chromeWebDriverLocation );
		 driver = new ChromeDriver();
		 
		//Logging in to https://www.gtmetrix.com
		driver.get(pageUrl);
		driver.findElement(By.className(logInButton)).click();
		driver.findElement(By.id(userIdBox)).sendKeys(userId);
		driver.findElement(By.id(passwordBox)).sendKeys(password);
		driver.findElement(By.xpath("//button[contains(.,'Log In')]")).click();
		
		//waiting for the query page
		while(true){
			title = driver.getTitle();
			if(title.equals("Dashboard | GTmetrix")){
				break;
			}
		}
		
		
		 try{
			 //Reading excel file to input the website
			 File src=new File(inputExcelFileLocation);
			 FileInputStream fis=new FileInputStream(src);
			 wb = new XSSFWorkbook(fis);
			 XSSFSheet  sheet = wb.getSheetAt(0);
			 wb.setSheetName(0,countryNames[0]);
			 
			 //calling test function for 5 countries and all 7 websites
			 while (count<5) {
				   if(count>0){
					   sheet = (XSSFSheet)wb.cloneSheet(0);
					   wb.setSheetName(count,countryNames[count]);
				   }
				   
			     for(int i=1;i<7;i++){
						checkUrl = sheet.getRow(i).getCell(0).getStringCellValue();
						test(checkUrl,i,sheet,count);
						
					 }
			     count++;
			 }
			 
			//Saving the result to new Excel sheet
			FileOutputStream fout=new FileOutputStream(new File(outputExcelFileLocation));
			wb.write(fout);
			fout.close();

			}catch(Exception e){
				e.printStackTrace();
			}
		
		
				
		System.out.println("Executed Successfully..");
	}

}
