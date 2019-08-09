package RunPackage;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import RequestGenProvince.RequestDriverAlberta;
import RequestGenProvince.RequestDriverNewBrunswick;
import RequestGenProvince.RequestDriverAlb;
import ReusableComponents.CreateDialogFromOptionPane;
import ReusableComponents.ExcelUtilities;
import ReusableComponents.ReadPropFile;

public class CreateRequestXMLs {
	

	public static void main(String[] args) /*throws Exception*/ {
		Thread.currentThread().setPriority(1);
		Logger log= Logger.getLogger("DriverClassOntario");
	   try {
		//#####################################################################################   
		//Setting sample request xml and data excel path
		//#####################################################################################   
		   ReadPropFile directoriesProp=new ReadPropFile("Environment.properties");		   
		   File directory = new File(directoriesProp.getPropertyValue("directory"));
		   String runManagerPath = directory.getAbsolutePath()+directoriesProp.getPropertyValue("runManagerPath"); 
		
		//####################################################################################	
		//Creating DOM parser instance
		//####################################################################################	
		ExcelUtilities.setExcelFile(runManagerPath, "Main", log);
		int loop=ExcelUtilities.getRowNum(log);
		//###############################################################################
		//Checking the execute status of test cases from run manager
		//###############################################################################
		for(int loopToStart=1;loopToStart<=loop;loopToStart++){
			try{
			ExcelUtilities.setExcelFile(runManagerPath, "Main", log);
			String TC_ID=ExcelUtilities.getTcId(loopToStart, 0, log);
		    String Execute=ExcelUtilities.getTcId(loopToStart, 2, log);
		    
		    
		
		if(!TC_ID.equals("")){

		if(Execute.equals("Yes")){
		//String TC_ID=ExcelUtilities.getTcId(loopToStart, 0, log);
			
		//####################################################################################
		//Changing Claim Ids in claim sheet
		//####################################################################################
			
			ExcelUtilities.makeClaimIdChange(TC_ID, runManagerPath, "Claims_TD", log, "Claim_Id");
		
		//####################################################################################
		//Changing conviction Ids and Conviction types in conviction sheet
		//####################################################################################
			
			ExcelUtilities.makeConvitionIdChange(TC_ID, runManagerPath, "Convictions_TD", log, "Conviction_Id");
		
		//####################################################################################
		
		System.out.println(TC_ID+" Started at...... "+new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date()));
		log.info(TC_ID+" Started at....... "+new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date()));
		
		
		ExcelUtilities.setExcelFile(runManagerPath, "Customer_TD", log);
	    int colNum = ExcelUtilities.getColNum(log);
	    String Province = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("ProducerCode", colNum), TC_ID, 1, log, loopToStart);
	    
	    switch(Province){
	    case "CSC_ON CSC Alb" : RequestDriverAlb.requestGenerationAlb(directoriesProp, TC_ID, loopToStart, log); break;
	   // case "CSC_AB CSC Alberta" : RequestDriverAlberta.requestGenerationAlberta(directoriesProp, TC_ID, loopToStart, log);;break;
	    //case "CSC_NB CSC NewBrunswick" : RequestDriverNewBrunswick.requestGenerationNewBrunswick(directoriesProp, TC_ID, loopToStart, log);break;
	    }
		
		 
	//###############################################################################
		
		System.out.println(TC_ID+" Completed at "+new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date()));
		log.info(TC_ID+" Completed at "+new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date()));
		
		}		
		}
		else if(TC_ID.equals("")){			
			break;
		}
			}catch (ParserConfigurationException pce) {
				System.out.println(pce);
				log.error(pce);
			   } catch (TransformerException tfe) {
				System.out.println(tfe);
				log.error(tfe);
			   } catch (IOException ioe) {
				System.out.println(ioe);
				log.error(ioe);
			   } catch (SAXException sae) {
				System.out.println(sae);
				log.error(sae);
			   }
			   catch (Exception e) {
					System.out.println(e);
					log.error(e);
				   }
		}
	   }finally{
		   CreateDialogFromOptionPane.setWarningMsg("Done");
		   System.exit(0);
	   }
	   } 	
	

}
