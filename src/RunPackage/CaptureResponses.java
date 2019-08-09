package RunPackage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import ReusableComponents.CSVUtilities;
import ReusableComponents.ConvertXml;
import ReusableComponents.CreateDialogFromOptionPane;
import ReusableComponents.ExcelUtilities;
import ReusableComponents.ReadPropFile;
import ReusableComponents.RestFullPost;
import ReusableComponents.SOAPClientRequest;
import StoreResponseResults.CaptureResponseDataAlberta;
import StoreResponseResults.CaptureResponseDataOntario;

public class CaptureResponses {
	
	public static String authSwithMode, authKey, cerficatePassword;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Logger log = Logger.getLogger("ResponseCaptureOntario");
		ReadPropFile properties = new ReadPropFile("Environment.properties");
		File directory = new File(properties.getPropertyValue("directory"));
		String runManagerPath = directory.getAbsolutePath() + properties.getPropertyValue("runManagerPath");
		String outputPath = directory.getAbsolutePath() + properties.getPropertyValue("OutputCSV");
		
		authKey = "";
		try {
			/*ExcelUtilities.setExcelFile(runManagerPath, "Environment", log);
			cerficatePassword = ExcelUtilities.getTcId(1, 2, log);
			System.out.println(cerficatePassword);
			authSwithMode = ExcelUtilities.getTcId(2, 1, log);
			System.out.println("SSO Switch is in " + authSwithMode + " state......................");
			log.info("SSO Switch is in " + authSwithMode + " state......................");
			if (authSwithMode.toUpperCase().equals("ON")) {
				System.out.println("Fetching Authentication key..............");
				log.info("Fetching Authentication key..............");
				authKey = ExcelUtilities.getTcId(2, 0, log);
				System.out.println("Key Fetched..............");
				log.info("Key Fetched..............");
			}*/
			
			ExcelUtilities.setExcelFile(runManagerPath, "Customer_TD", log);
			int colNum = ExcelUtilities.getColNum(log);
			String Province = ExcelUtilities.getTcId(1, ExcelUtilities.getColumnNum("ProducerCode", colNum), log);
			switch(Province){
			case "CSC_ON CSC Ontario" : CSVUtilities.createOutputCSV(outputPath, "ON");break;
			case "CSC_AB CSC Alberta" : CSVUtilities.createOutputCSV(outputPath, "AB");break;
			case "CSC_NB CSC Newrunswick" : CSVUtilities.createOutputCSV(outputPath, "NB");break;
			}
			ExcelUtilities.setExcelFile(runManagerPath, "Main", log);
			int loop;

			loop = ExcelUtilities.getRowNum(log);
			
			// ===========================================================================
			// Checking the execute status of test cases from run manager
			// ===========================================================================
			
			for (int l1 = 1; l1 <= loop; l1++) {
				ExcelUtilities.setExcelFile(runManagerPath, "Main", log);
				String TC_ID = ExcelUtilities.getTcId(l1, 0, log);
				String Execute = ExcelUtilities.getTcId(l1, 2, log);
				if (!TC_ID.equals("")) {
					if (Execute.toUpperCase().equals("YES")) {
						System.out.println(TC_ID + " Started at....... "
								+ new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date()));
						log.info(TC_ID + " Started at....... "
								+ new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date()));
						String filepath = directory.getAbsolutePath() + properties.getPropertyValue("OutputPath")
								+ TC_ID + ".xml";
						String outfilepath = directory.getAbsolutePath() + properties.getPropertyValue("OutputResponse")
								+ TC_ID + "_res.xml";

						DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
						Document doc = (Document) docBuilder.parse(filepath);
						DOMSource source = new DOMSource(doc);
						// ============================================================
						String resultString = ConvertXml.convertDocumentToString(source, log);
						//String response = null;
						//String response = RestFullPost.callSoapSSLNew(resultString, TC_ID, runManagerPath, log, authSwithMode, authKey);
						String response = SOAPClientRequest.createSOAPRequest(resultString,TC_ID,runManagerPath,log);
						//SOAPMessage response1 = RestFullPost.callSoapSSLNew(resultString, TC_ID, runManagerPath, log, authSwithMode, authKey);
						if (!response.equals("")) {
							ConvertXml.writeFile(response, outfilepath);
						}
						
						switch(Province){
						case "CSC_ON CSC Ontario" : CaptureResponseDataOntario.storeDataInCSV(TC_ID, outputPath, "xml_out", response, log);break;
						case "CSC_AB CSC Alberta" : CaptureResponseDataAlberta.storeDataInCSV(TC_ID, outputPath, "xml_out", response, log);break;
						}
					    
						System.out.println(TC_ID + " Completed at....... "
								+ new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date()));
						log.info(TC_ID + " Completed at....... "
								+ new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date()));
					}
				}

				else if (TC_ID.equals("")) {
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			log.error(e);
		} finally {
			CreateDialogFromOptionPane.setWarningMsg("Done");
			System.exit(0);
		}

	}

}
