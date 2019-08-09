package ReusableComponents;

import java.io.*;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import au.com.bytecode.opencsv.CSVWriter;


public class CSVUtilities {
	
	@SuppressWarnings("null")
	public static void createOutputCSV(String path, String province) {
		try {
			ReadPropFile OutputSchemaProperties = new ReadPropFile("OutputSchema.properties");
			ArrayList<String> a = new ArrayList();
			FileWriter writer = new FileWriter(path, false);
			
			if(province.equalsIgnoreCase("AB"))
			{
			for (int i = 0; i < 20; i++) {				
				String columns= OutputSchemaProperties.getPropertyValue(Integer.toString(i + 1)+"_"+province);				
					a.add(columns);
					System.out.println(columns);
				}
			}
			else 
			{
				for (int i = 24; i < 50; i++) {				
					String columns= OutputSchemaProperties.getPropertyValue(Integer.toString(i + 1)+"_"+province);				
						a.add(columns);
						System.out.println(columns);
					}
				}
				
			for(int i=0;i<a.size();i++){
				if(a.get(i)!=null){
					writer.append(a.get(i));
					writer.append(",");
				}
			}
			writer.append("\n");
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
	public  static void storeDataCSV(String outputPath, ArrayList<String> data)  {
        
        try{   
		FileWriter writer = new FileWriter(outputPath, true); 
         for(int i=0;i<data.size();i++){
        	 writer.append(data.get(i));
        	 writer.append(",");
         }
         writer.append("\n");
          writer.close();  
        }catch(Exception e){
        	System.out.println(e);
        }
           
    }
    
    public static void convertCSVToXLSX() {
        try {
            String csvFileAddress = "C:\\Guidewire\\QuickWin_Auto_Quote\\Rating\\Result\\Output.csv"; //csv file address
            String xlsxFileAddress = "C:\\Guidewire\\QuickWin_Auto_Quote\\Rating\\Result\\Output.xlsx"; //xlsx file address
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("sheet1");
            String currentLine=null;
            int RowNum=0;
            BufferedReader br = new BufferedReader(new FileReader(csvFileAddress));
            while ((currentLine = br.readLine()) != null) {
                String str[] = currentLine.split(",");
                RowNum++;
                XSSFRow currentRow=sheet.createRow(RowNum);
                for(int i=0;i<str.length;i++){
                    currentRow.createCell(i).setCellValue(str[i]);
                }
            }

            FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
            System.out.println("Done");
        } catch (Exception ex) {
            System.out.println(ex.getMessage()+"Exception in try");
        }
    }


}
