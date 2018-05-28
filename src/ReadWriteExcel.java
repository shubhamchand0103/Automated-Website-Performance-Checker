import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ReadWriteExcel {
	private static XSSFWorkbook wb;
	public static int count = 0;
	public static String checkUrl = "";
	
	static void test(String check, int ind, Sheet sheet, int count){
		
	
		
		
	    
	    
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
