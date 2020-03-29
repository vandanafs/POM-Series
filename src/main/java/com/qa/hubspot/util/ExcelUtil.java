package com.qa.hubspot.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	
    public static Workbook book;
    public static Sheet sheet;
    
	public static String TESTDATA_SHEET_PATH = "./src/main/java/com/qa/hubspot/TestData/hubspotTestData.xlsx";
 
	public static Object[][] getTestData(String sheetName) {
		   Object data[][]=null;
		try {
			FileInputStream ip = new FileInputStream(TESTDATA_SHEET_PATH);// coonect with excel sheet
			book =WorkbookFactory.create(ip);// load excel and store in book 
			sheet= book.getSheet(sheetName);//to get sheetname and store in sheet(contacts sheet)
			
			 data=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];// last row and last col cell no
			
			for(int i=0;i<sheet.getLastRowNum();i++) {
				for(int j=0; j<sheet.getRow(0).getLastCellNum();j++) {
					data[i][j]=sheet.getRow(i+1).getCell(j).toString(); //getRow(i+1) leave heading 
				}
			}	
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
      
}
