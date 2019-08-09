package ReusableComponents;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//import utilities.ExcelUtilities;

public class RandomMethods {
	
	public static Node Coverages;
	public static String limitVal;
	public static int vehicleNumber, rateVal;
	public static Element cov7;
	
	public static String randomNumber() {

		Random rand = new Random();
		int n = rand.nextInt(1000) + 1;
		String n1 = Integer.toString(n);
		return n1;
	}
	
	public static String randomPortfolio() {

		Random rand = new Random();
		int r = rand.nextInt(10000000) + 1;
		String r1 = Integer.toString(r);
		return r1;
	}

	// ###############################################################
	// Function to convert date from DD/MM/YYYY to DD-MM-YYYY
	// Author : TDI Automation Team
	// Date : September, 2017
	// ###############################################################
	public static String dateConvert(String date, Logger log) {

		String dateAsString = date;
		System.out.println("date is: " + date);
		log.info("date is: " + date);
		if (dateAsString.equalsIgnoreCase("")) {
			return "";
		} else {
			try {
				String[] parts = dateAsString.split("/");
				String part1 = parts[0];
				String part2 = parts[1];
				String part3 = parts[2];
				String dateCon = part3 + "-" + part2 + "-" + part1;
				return dateCon;
			} catch (ArrayIndexOutOfBoundsException e) {
				return "";
			}
		}
	}

	public static String Split(String name, Logger log) {

		String dateAsString = name;
		log.info("name is: " + name);
		if (dateAsString.equalsIgnoreCase(null)) {
			return "";
		} else {
			try {
				String[] parts = dateAsString.split(" ");
				String part1 = parts[0];
				return part1;
			} catch (ArrayIndexOutOfBoundsException e) {
				return "";
			}
		}
	}


	// ###############################################################
	// Function to convert date from DD/MM/YYYY to DD-MM-YYYY and
	// create Policy End date
	// Author : TDI Automation Team
	// Date : September, 2017
	// ###############################################################
	public static String makeEndDatedate(String date) {

		String dateAsString = date;
		if (dateAsString.equalsIgnoreCase(null)) {
			return "";
		} else {
			try {
				String[] parts = dateAsString.split("/");
				String part1 = parts[0];
				String part2 = parts[1];
				String part3 = parts[2];
				int year = Integer.parseInt(part3);
				int year1 = year + 1;
				String endYear = Integer.toString(year1);
				String dateCon = endYear + "-" + part2 + "-" + part1;
				return dateCon;
			} catch (ArrayIndexOutOfBoundsException e) {
				return "";
			}
		}
	}


	// ####################################################################
	// Function generate Driver experience years from Policy Effective date
	// & Driver First License date
	// Author : TDI Automation Team
	// Date : September, 2017
	// ####################################################################
	public static String driverExpYear(String effectiveDate, String firstLicDate) {

		String effectiveDateString = effectiveDate;
		String firstLicDateString = firstLicDate;
		if (effectiveDateString.equalsIgnoreCase("") || firstLicDateString.equalsIgnoreCase("")) {
			return "";
		} else {

			String[] effectparts = effectiveDateString.split("/");
			String effectpart1 = effectparts[0];
			String effectpart2 = effectparts[1];
			String effectpart3 = effectparts[2];
			int effectyear = Integer.parseInt(effectpart3);
			int effectMonth = Integer.parseInt(effectpart2);
			String[] firstparts = firstLicDateString.split("/");
			String firstpart1 = firstparts[0];
			String firstpart2 = firstparts[1];
			String firstpart3 = firstparts[2];
			int firstyear = Integer.parseInt(firstpart3);
			int firstMonth = Integer.parseInt(firstpart2);
			if (effectMonth - firstMonth > 0) {
				return Integer.toString((effectyear - firstyear) + 1);
			} else {
				return Integer.toString(effectyear - firstyear);
			}
		}
	}


	// #######################################################################
	// Function to split Postal Code
	// Author : TDI Automation Team
	// Date : September, 2017
	// #######################################################################
	public static String postalCode(String name, String value, Logger log) {

		String postalAsString = value;
		//System.out.println("postalCode is: " + value);
		log.info("postalCode is: " + value);
		String RiskPostalCodeFSA = postalAsString.substring(0, 3);
		String RiskPostalCodeLDU = postalAsString.substring(3, 6);

		if (name.equals("RiskPostalCodeFSA"))
			return RiskPostalCodeFSA;
		else if (name.equals("RiskPostalCodeLDU"))
			return RiskPostalCodeLDU;

		return "";
	}


	// #########################################################################
	// Function to get driver birthdate from driver age in DD-MM-YYYY
	// Author : TDI Automation Team
	// Date : September, 2017
	// #########################################################################
	public static String getBirthDay(String age, String date, Logger log) {

		int ageData = Integer.parseInt(age);
		String dateTS = RandomMethods.dateConvert(date, log);
		String[] parts = dateTS.split("-");
		String part1 = parts[0];
		String part2 = parts[1];
		String part3 = parts[2];
		int partYear = Integer.parseInt(part1);
		int birthYear = partYear - ageData;
		return birthYear + "-" + part2 + "-" + part3;
	}

/*public static void func(Document doc, String covExist, String limit, String coverageName, String LimitName, int loop, Logger log){
	if(covExist.toUpperCase().equals("YES")){
	String covCodeTypr;
	String covName;
	if(coverageName.equals("FAML")){
		covName="44";
	}else if(coverageName.equals("REPL")){
		covName="43S";
	}else if(coverageName.equals("Accd")){
		covName="39";
	}
	else covName=coverageName;
	if(coverageName.equals("TPL")){
		limitVal=limit;
	}
	if(!covName.toUpperCase().equals("27S")){		
		if(!covName.equals("3")&&!covName.equals("2")&&!covName.equals("6A")&&!covName.equals("16")){
	System.out.println("Cov name is "+covName);
	log.info("Cov name is "+covName);
	//System.out.println("Cov loop num is: "+loop);
	Node DriverInformationsMul;
	//Node Coverages = doc.getElementsByTagName("Coverages").item(loop+1);
	//NodeList CoveragesNl=Coverages.getChildNodes();
	//System.out.println("CoveragesNl: "+CoveragesNl.getLength());
	DriverInformationsMul = doc.getElementsByTagName("Coverages").item(loop+1);
	Element cov=doc.createElement("Coverage");
	Element cov1=doc.createElement("CoverageCode");
	Element cov2=doc.createElement("CoverageTypeCode");
	Element cov3=doc.createElement("IsActive");
	Element cov4=doc.createElement("Limit");
	Element cov5=doc.createElement("RatedDeductible");
	Element cov6=doc.createElement("Premium");
	Element cov7=doc.createElement("DiscountSurcharges");
	for(int i=0;i<17;i++){
		Element cov8=doc.createElement("DiscountSurcharge");
		Element cov9=doc.createElement("DiscountSurchargeCode");
		Element cov10=doc.createElement("DiscountSurchargeIsActive");
		cov10.appendChild(doc.createTextNode("false"));
		cov9.appendChild(doc.createTextNode(RandomMethods.returnDiscountCode(i)));
		cov7.appendChild(cov8);
		cov8.appendChild(cov9);
		cov8.appendChild(cov10);
	}
	cov1.appendChild(doc.createTextNode(covName));
	if(covName.equals("COL")||covName.equals("AB")||covName.equals("TPL")||covName.equals("SPE")||covName.equals("CMP")||covName.equals("AP"))
		covCodeTypr="Coverage";
	else covCodeTypr="Endorsement";
	cov2.appendChild(doc.createTextNode(covCodeTypr));
	cov3.appendChild(doc.createTextNode("true"));	
	if(LimitName.toUpperCase().equals("LIMIT")){
		if(covName.equals("44")||covName.equals("28")){
			cov4.appendChild(doc.createTextNode(limitVal));
		}
		else cov4.appendChild(doc.createTextNode(limit));
		cov5.appendChild(doc.createTextNode("0"));
	}else{
	cov4.appendChild(doc.createTextNode("0"));
	cov5.appendChild(doc.createTextNode(limit));
	}
	DriverInformationsMul.appendChild(cov);
	cov.appendChild(cov1);
	cov.appendChild(cov2);
	cov.appendChild(cov3);
	cov.appendChild(cov4);
	cov.appendChild(cov5);
	cov.appendChild(cov6);	
	cov.appendChild(cov7);
	}
	}
	}
}*/

	// #################################################################################
	// Function To Add Policy Level Coverages
	// Author : TDI Automation Team
	// Date : October,2017
	// #################################################################################

	public static void addpolLevelCoverage(Document doc, String limit, String Deduct, String covName, Logger log,
			String covType, String covStartDate, int endo2NameCount, String[] endorsement2Names) {

		Element cov8, cov9, cov10;
		System.out.println("Coverage name is " + covName);
		log.info("Coverage name is " + covName);
		Node DriverInformationsMul = doc.getElementsByTagName("Coverages").item(0);
		Element cov = doc.createElement("Coverage");
		Element cov1 = doc.createElement("CoverageCode");
		Element cov2 = doc.createElement("CoverageTypeCode");
		Element cov3 = doc.createElement("IsActive");
		Element cov4 = doc.createElement("Limit");
		Element cov5 = doc.createElement("RatedDeductible");
		Element cov6 = doc.createElement("Premium");
		Element cov7 = doc.createElement("CoverageAdditionalInfos");
		if (!covName.equalsIgnoreCase("27S")&&!(endo2NameCount>0)) {
			cov8 = doc.createElement("CoverageAdditionalInfo");
			cov9 = doc.createElement("CoverageStartDate");
			cov9.appendChild(doc.createTextNode(RandomMethods.dateConvert(covStartDate, log)));
			cov8.appendChild(cov9);
			cov7.appendChild(cov8);
		}
		else if(covName.equalsIgnoreCase("2")&&endo2NameCount>0){
			for(int i=0;i<endo2NameCount;i++){
				cov8 = doc.createElement("CoverageAdditionalInfo");
				cov9 = doc.createElement("CoverageStartDate");
				cov9.appendChild(doc.createTextNode(RandomMethods.dateConvert(covStartDate, log)));
				cov10 = doc.createElement("Name");
				cov10.appendChild(doc.createTextNode(endorsement2Names[i]));
				cov8.appendChild(cov9);
				cov8.appendChild(cov10);
				cov7.appendChild(cov8);
			}
			
		}

		cov1.appendChild(doc.createTextNode(covName));
		cov2.appendChild(doc.createTextNode(covType));
		cov3.appendChild(doc.createTextNode("true"));
		cov4.appendChild(doc.createTextNode(limit));
		cov5.appendChild(doc.createTextNode(Deduct));
		DriverInformationsMul.appendChild(cov);
		cov.appendChild(cov1);
		cov.appendChild(cov2);
		cov.appendChild(cov3);
		cov.appendChild(cov4);
		cov.appendChild(cov5);
	    cov.appendChild(cov6);
		cov.appendChild(cov7);
	}

	// ###################################################################
	// Function To Add Outside Province in request under Vehicle
	// Author : TDI Automation Team
	// Date : October,2017
	// ###################################################################

	public static String regionName(String col, Document doc, int loop, String runManegerPath, Logger log, String TC_ID,
			int loopToStart) {
		if (col.equalsIgnoreCase("VD_PPA_Outside__Used_British")) {
			System.out.println("British Columbia");
			return "British Columbia";
		} else if (col.equals("VD_PPA_Outside_Used_Canada")) {
			System.out.println("Other Canadian Provinces/Territories");
			return "Other Canadian Provinces/Territories";
		} else if (col.equals("VD_PPA_Outside_Used_Usa")) {
			ExcelUtilities.setExcelFile(runManegerPath, "Vehicle_TD", log);
			int colnum = ExcelUtilities.getColNum(log);
			String Outside_Used_Usa_Days = ExcelUtilities.getMultiCellDataString(
					ExcelUtilities.getColumnNum("VD_PPA_Outside_Used_Usa_Days", colnum), TC_ID, loop, log, loopToStart);
			System.out.println("Outside_Used_Usa_Days: " + Outside_Used_Usa_Days);
			Node Vehicle = doc.getElementsByTagName("Vehicle").item(loop - 1);
			Element NbOfDaysUsedOutsideProvince = doc.createElement("NbOfDaysUsedOutsideProvince");
			NbOfDaysUsedOutsideProvince.appendChild(doc.createTextNode(Outside_Used_Usa_Days));
			Vehicle.appendChild(NbOfDaysUsedOutsideProvince);
			System.out.println("USA");
			return "USA";
		} else
			return "Other Country";
	}

	// ###############################################################
	// Function to return Driver's classes
	// Author : TDI Automation Team
	// Date : September, 2017
	// ###############################################################
	public static String returnGrade(String grade) {
		if (grade.equals("Class-5")) {
			return "Class5";
		} else if (grade.equals("Class-5 GDL")) {
			return "Class5GDL";
		} else if (grade.equals("Class-7")) {
			return "Class7";
		} else if (grade.equals("G-Regular")) {
			return "GRegular";
		} else
			return grade;
	}
	
	
	// ###############################################################
		// Function to return ProtectionDevices Codes 
		// Author : TDI Automation Team
		// Date : September, 2017
		// ###############################################################
	
	public static String returnProtectionCodes(String codes)
	{
		String returnVal = "";
		switch(codes)
		{
		case "One Alarm / Ignition disabler system":  returnVal="001"; break;
		case "More than one Alarm / Ignition disabler system": returnVal="002"; break;
		case "Engraved": returnVal="003"; break;
		case "Tracking system": returnVal="004"; break;
		case "Anti-theft device system": returnVal="005"; break;
		case "Active Disabling Device": returnVal="006"; break;
		case "Passive Disabling Device": returnVal="007"; break;
		case "VICC Approved Immobilizing Device": returnVal="008"; break;
		case "Theft Alarm": returnVal="009"; break;
		case "Intensive Engraving": returnVal="010"; break;
		
		}
		return returnVal;
		
		
	}

	// ###############################################################
	// Function to return Discount Codes to Vehicle level coverages
	// Author : TDI Automation Team
	// Date : September, 2017
	// ###############################################################
	public static String returnDiscountCode(int row) {
		if (row == 0) {
			return "DISCG";
		} else if (row == 1) {
			return "DISR1";
		} else if (row == 2) {
			return "DISR4";
		} else if (row == 3) {
			return "DISML";
		} else if (row == 4) {
			return "R7";
		} else if (row == 5) {
			return "DISHY";
		} else if (row == 6) {
			return "R9";
		} else if (row == 7) {
			return "SURUS";
		} else if (row == 8) {
			return "DISRC";
		} else if (row == 9) {
			return "GLO";
		} else if (row == 10) {
			return "GLP";
		} else if (row == 11) {
			return "R10";
		} else if (row == 12) {
			return "DISWT";
		} else if (row == 13) {
			return "DISXL";
		} else if (row == 14) {
			return "DISOU";
		} else if (row == 15) {
			return "DISPU";
		} else if (row == 16) {
			return "DISIN";
		}
		return "";
	}

	// ###############################################################
	// Function to return consent code
	// Author : TDI Automation Team
	// Date : September, 2017
	// ###############################################################
	public static String returnConsentCode(String Consent) {
		if (Consent.equals("Obtained with CA")) {
			return "obtained_with_CA";
		} else if (Consent.equals("Obtained without CA")) {
			return "obtained_without_CA";
		} else if (Consent.equals("Obtained digital")) {
			return "obtained_digital";
		} else
			return Consent;
	}

	// ###################################################################
	// Functions to get Vehicle rating group number for special cases like
	// import vehicle or antique vehicle
	// Author : TDI Automation Team
	// Date : September,2017
	// ###################################################################
	public static String getRatingInfo(String path, int col, int vehicleValue, Logger log) {
		rateVal = 0;
		ExcelUtilities.setExcelFile(path, "Vehicle Rate by Value", log);
		int loop = ExcelUtilities.getRowNum(log);
		int colnum = ExcelUtilities.getColNum(log);
		try {
			for (int i = 1; i <= loop; i++) {

				int lowerVal = (ExcelUtilities.getSubId(i, ExcelUtilities.getColumnNum("PurchasePrice>=", colnum), log));
				int higherVal = (ExcelUtilities.getSubId(i, ExcelUtilities.getColumnNum("PurchasePrice<=", colnum), log));
				if (vehicleValue >= (lowerVal) && vehicleValue <= (higherVal)) {
					rateVal = ExcelUtilities.getSubId(i, col, log);
					return (Integer.toString(rateVal));
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return (Integer.toString(rateVal));
		}
		return (Integer.toString(rateVal));
	}
	
	public static void addR9Discounts(Document doc,int vehicleCount, String R9values)
	{
		Node VehicleDriverUsages = doc.getElementsByTagName("VehicleDriverUsages").item(0);
		NodeList VehicleDriverUsagesChilds = VehicleDriverUsages.getChildNodes();
		for (int i = 0; i < VehicleDriverUsagesChilds.getLength(); i++) {
			Node VehicleDriverUsagesChild = VehicleDriverUsagesChilds.item(i);
			if(VehicleDriverUsagesChild.getNodeName().equals("VehicleDriverUsage")){
				NodeList VehicleDriverUsageChild = VehicleDriverUsagesChild.getChildNodes();
				for (int j = 0; j < VehicleDriverUsageChild.getLength(); j++){
					Node VehicleDriverUsageChildNode = VehicleDriverUsageChild.item(j);
						if (VehicleDriverUsageChildNode.getNodeName().equals("VehicleNumber")) 
						vehicleNumber = Integer.parseInt(VehicleDriverUsageChildNode.getTextContent());
						if(vehicleNumber==vehicleCount){
						if(VehicleDriverUsageChildNode.getNodeName().equals("Risk")){
							NodeList RsikChildNode=VehicleDriverUsageChildNode.getChildNodes();
							for(int k=0; k < RsikChildNode.getLength(); k++){
							Node RiskNode = RsikChildNode.item(k);
						if (RiskNode.getNodeName().equals("DiscountSurcharges")){
							NodeList DiscountSurcharges = RiskNode.getChildNodes();
								for(int l=0; l < DiscountSurcharges.getLength(); l++){
									Node DiscountSurcharge=DiscountSurcharges.item(l);
									if(DiscountSurcharge.getNodeName().equals("DiscountSurcharge")){
										NodeList DiscountSurchargeChild = DiscountSurcharge.getChildNodes();
										for(int m=0;m<DiscountSurchargeChild.getLength();m++){
											Node DiscountSurchargeCode=DiscountSurchargeChild.item(m);
											if(DiscountSurchargeCode.getTextContent().equals("R10")){
												if(!DiscountSurchargeCode.getNextSibling().getNextSibling().equals("#text")){
													//DiscountSurchargeCode.getNextSibling().getNextSibling().setTextContent(R9values);
													if(R9values.equalsIgnoreCase("Yes"))
													{
														DiscountSurchargeCode.getNextSibling().getNextSibling().setTextContent("true");
													}
													else
														DiscountSurchargeCode.getNextSibling().getNextSibling().setTextContent("false");
												}
												else
												{
													//DiscountSurchargeCode.getNextSibling().getNextSibling().setTextContent(R9values);
													if(R9values.equalsIgnoreCase("Yes"))
													{
														DiscountSurchargeCode.getNextSibling().getNextSibling().setTextContent("true");
													}
													else
														DiscountSurchargeCode.getNextSibling().getNextSibling().setTextContent("false");
												}
												
												 //System.out
														//.println("Value of "+R9values);
												 System.out.println(DiscountSurchargeCode.getTextContent()+" Value: "+R9values+" Added in Vehicle "+vehicleNumber);
														
											}
										}
										
									}
								}
							
							/*Node DiscountSurcharge = DiscountSurcharges.item(3);
							if(DiscountSurcharge.getNextSibling().getNextSibling().getNodeName().equals("IsActive")){
								String Value=DiscountSurcharge.getTextContent();
								System.out.println(Value);
							}*/
						}
						
							}
						
						}
						}
					
				}
			}
		}
		
	}
	
	
	

	// #########################################################################
	// Function to Add Vehicle level Coverages
	// Author : TDI Automation Team
	// Date : October,2017
	// #########################################################################

	public static void addVehicleLevelCoverages(Document doc, int vehicleID, String endorsementId, String limit,
			String deduct, String covType, String covStartDate) {

		Element cov, cov1, cov2, cov3, cov4, cov5, cov6, cov8, cov9, cov10, cov11, cov12, cov13;
		Node VehicleDriverUsages = doc.getElementsByTagName("VehicleDriverUsages").item(0);
		NodeList VehicleDriverUsagesChilds = VehicleDriverUsages.getChildNodes();
		for (int i = 0; i < VehicleDriverUsagesChilds.getLength(); i++) {
			Node VehicleDriverUsagesChild = VehicleDriverUsagesChilds.item(i);
			if (VehicleDriverUsagesChild.getNodeName().equals("VehicleDriverUsage")) {
				NodeList VehicleDriverUsageChild = VehicleDriverUsagesChild.getChildNodes();
				for (int j = 0; j < VehicleDriverUsageChild.getLength(); j++) {
					Node VehicleDriverUsageChildNode = VehicleDriverUsageChild.item(j);
					if (VehicleDriverUsageChildNode.getNodeName().equals("VehicleNumber")) {
						vehicleNumber = Integer.parseInt(VehicleDriverUsageChildNode.getTextContent());
						if (vehicleNumber == vehicleID) {
							//Node RiskNode = doc.getElementsByTagName("Risk").item(RandomMethods.getLoop(i));
							Node RiskNode;
							if(VehicleDriverUsageChildNode.getNextSibling().getNodeName().equalsIgnoreCase("#text")){
								RiskNode = VehicleDriverUsageChildNode.getNextSibling().getNextSibling();
							}
							else RiskNode = VehicleDriverUsageChildNode.getNextSibling();
							//RiskNode = VehicleDriverUsageChildNode.getNextSibling();
							NodeList RiskChilds = RiskNode.getChildNodes();
							for (int k = 0; k < RiskChilds.getLength(); k++) {
								Node RiskChildNode = RiskChilds.item(k);
								if (RiskChildNode.getNodeName().equals("Coverages")) {
									cov = doc.createElement("Coverage");
									cov1 = doc.createElement("CoverageCode");
									cov2 = doc.createElement("CoverageTypeCode");
									cov3 = doc.createElement("IsActive");
									cov4 = doc.createElement("Limit");
									cov5 = doc.createElement("RatedDeductible");
									cov6 = doc.createElement("Premium");
									if (covType.equalsIgnoreCase("Endorsement")) {
										cov7 = doc.createElement("CoverageAdditionalInfos");
									}

									cov8 = doc.createElement("DiscountSurcharges");
									for (int l = 0; l < 17; l++) {
										cov9 = doc.createElement("DiscountSurcharge");
										cov10 = doc.createElement("DiscountSurchargeCode");
										cov11 = doc.createElement("DiscountSurchargeIsActive");
										cov11.appendChild(doc.createTextNode("false"));
										cov10.appendChild(doc.createTextNode(RandomMethods.returnDiscountCode(l)));
										cov8.appendChild(cov9);
										cov9.appendChild(cov10);
										cov9.appendChild(cov11);
									}

									cov12 = doc.createElement("CoverageAdditionalInfo");
									cov13 = doc.createElement("CoverageStartDate");
									cov13.appendChild(doc.createTextNode(covStartDate));
									cov12.appendChild(cov13);

									cov1.appendChild(doc.createTextNode(endorsementId));
									cov2.appendChild(doc.createTextNode(covType));
									cov3.appendChild(doc.createTextNode("true"));
									cov4.appendChild(doc.createTextNode(limit));
									cov5.appendChild(doc.createTextNode(deduct));
									RiskChildNode.appendChild(cov);
									cov.appendChild(cov1);
									cov.appendChild(cov2);
									cov.appendChild(cov3);
									cov.appendChild(cov4);
									cov.appendChild(cov5);
									cov.appendChild(cov6);
									if (covType.equalsIgnoreCase("Endorsement")) {
										cov7.appendChild(cov12);
										cov.appendChild(cov7);
									}
									//cov.appendChild(cov8);
								}
							}
							break;
						}
					}
				}
			}
		}
	}

	public static int getLoop(int input) {
		if (input == 1) {
			return 0;
		} else if (input == 3) {
			return 1;
		} else if (input == 4) {
			return 2;
		} else if (input == 5) {
			return 3;
		} else if (input == 6) {
			return 4;
		} else if (input == 7) {
			return 5;
		} else if (input == 8) {
			return 6;
		} else if (input == 9) {
			return 7;
		} else if (input == 10) {
			return 8;
		} else if (input == 11) {
			return 9;
		} else if (input == 12) {
			return 10;
		} else if (input == 13) {
			return 11;
		} else if (input == 14) {
			return 12;
		} else if (input == 15) {
			return 13;
		} else if (input == 16) {
			return 14;
		} else if (input == 17) {
			return 15;
		} else if (input == 18) {
			return 16;
		} else if (input == 19) {
			return 17;
		} else if (input == 20) {
			return 18;
		} else if (input == 21) {
			return 19;
		} else if (input == 22) {
			return 20;
		} else if (input == 23) {
			return 21;
		} else if (input == 24) {
			return 22;
		} else if (input == 25) {
			return 23;
		} else if (input == 26) {
			return 24;
		} else if (input == 27) {
			return 25;
		} else if (input == 28) {
			return 26;
		} else if (input == 29) {
			return 27;
		} else if (input == 30) {
			return 28;
		} else if (input == 31) {
			return 29;
		} else if (input == 32) {
			return 30;
		} else if (input == 33) {
			return 31;
		} else if (input == 34) {
			return 32;
		} else if (input == 35) {
			return 33;
		} else if (input == 36) {
			return 34;
		} else if (input == 37) {
			return 35;
		} else if (input == 38) {
			return 36;
		} else if (input == 39) {
			return 37;
		} else if (input == 40) {
			return 38;
		} else if (input == 41) {
			return 39;
		} else if (input == 42) {
			return 40;
		} else if (input == 43) {
			return 41;
		} else if (input == 44) {
			return 42;
		} else if (input == 45) {
			return 43;
		} else if (input == 46) {
			return 44;
		} else if (input == 47) {
			return 45;
		} else if (input == 48) {
			return 46;
		} else if (input == 49) {
			return 47;
		} else if (input == 50) {
			return 48;
		} else if (input == 51) {
			return 49;
		} else if (input == 52) {
			return 50;
		} else if (input == 53) {
			return 51;
		} else if (input == 54) {
			return 52;
		} else if (input == 55) {
			return 53;
		} else if (input == 56) {
			return 54;
		} else if (input == 57) {
			return 55;
		} else if (input == 58) {
			return 56;
		}
		return 0;
	}

	// ###############################################################
	// Function to fetch type of lead from Lifetime Profitability response
	// Author : TDI Automation Team
	// Date : September, 2017
	// ###############################################################
	public static String getlifetimeTypeOfLead(String TC_ID, Logger log) {
		String typeOfLead;
		ReadPropFile properties = new ReadPropFile("Environment.properties");
		File directory = new File(properties.getPropertyValue("directory"));
		String LifetimeResponsePath = directory.getAbsolutePath() + properties.getPropertyValue("RatingResponsePath")
				+ TC_ID + "_Profitability_res.xml";
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc1 = docBuilder.parse(LifetimeResponsePath);
			Node typeOfLeadNode = doc1.getElementsByTagName("typeOfLead").item(0);
			typeOfLead = typeOfLeadNode.getTextContent();
			switch (typeOfLead) {
			case "AAAA":
				typeOfLead = "HotLead";
				break;
			case "AAA":
				typeOfLead = "GoodLead";
				break;
			case "AA":
				typeOfLead = "HighRisk";
				break;
			case "A":
				typeOfLead = "ColdLead";
				break;
			}

		} catch (Exception e) {
			return "HotLead";
		}

		return typeOfLead;
	}



	// ###############################################################
	// Function to add specific element to specified position
	// Author : TDI Automation Team
	// Date : September, 2017
	// ###############################################################
	public static void addConditionalElement(Document doc, String prevElement, String elementToAdd, String elementValue,
			int row) {

		Node prevElementNode = doc.getElementsByTagName(prevElement).item(row - 1);
		Node nextSublingElementNode = prevElementNode.getNextSibling();
		Node parentNode = prevElementNode.getParentNode();
		Element newElement = doc.createElement(elementToAdd);
		newElement.appendChild(doc.createTextNode(elementValue));
		nextSublingElementNode.getParentNode().insertBefore(newElement, nextSublingElementNode);

	}

	public static String convertToTrueFalseValue(String valToConvert) {
		if (valToConvert.equalsIgnoreCase("Yes")) {
			return "true";
		} else
			return "false";
	}
	

	//###############################################################
		// Function to add specific element to specified position
		// Author : TDI Automation Team
		// Date : September, 2017
		// ###############################################################
	public static String convertBooleanToInt(String data)
	{
		String returnVal = "";
		switch(data)
		{
		case "true":returnVal= "1";break;
		case "false":returnVal ="0";break;
		default:returnVal = data;break;
		}
		
		return returnVal;
		}
	
			//###############################################################
			// Function to add specific element to specified position
			// Author : TDI Automation Team
			// Date : September, 2017
			// ###############################################################
	public static void cloneXML(DocumentBuilder docBuilder,Document doc,String SamplePath,Node node,String tagName) throws SAXException, IOException
	{
		
		NodeList NList = doc.getElementsByTagName(tagName);
		for(int m=0;m<NList.getLength();m++)
		{
			Node targetSections = doc.getElementsByTagName(tagName).item(m);
			Document doc5=docBuilder.parse(SamplePath);
			NodeList nodeList=doc5.getElementsByTagName(tagName).item(0).getChildNodes();
			for(int n=0;n<nodeList.getLength();n++)
			{
				node = nodeList.item(n);
				targetSections.appendChild(doc.adoptNode(node.cloneNode(true)));
			}
		}
		
	}
	
	
	public static void deleteElement(Document doc, String parent, String elementToDelete, int row) {
        Node parentNode = doc.getElementsByTagName(parent).item(row - 1);
        NodeList parentNodeList = parentNode.getChildNodes();
        for (int i = 0; i < 50; i++) {
               Node childNode = parentNodeList.item(i);
               if (childNode.getNodeName().equals(elementToDelete)) {
                     parentNode.getParentNode().removeChild(childNode);
               }
        }
 }

 public static void addConditionalElementAfter(Document doc, String prevElement, String elementToAdd,
               String elementValue, int row) {

        Node prevElementNode = doc.getElementsByTagName(prevElement).item(row - 1);
        Node parentNode = prevElementNode.getParentNode();
        Node nextSublingElementNode = prevElementNode.getNextSibling();
        Element newElement = doc.createElement(elementToAdd);
        newElement.appendChild(doc.createTextNode(elementValue));
        if(nextSublingElementNode!=null){
        	nextSublingElementNode.getParentNode().insertBefore(newElement, nextSublingElementNode);
        }
        else parentNode.appendChild(newElement);    
 }

 public static void addConditionalElementBefore(Document doc, String nextElement, String elementToAdd,
               String elementValue, int row) {

        Node nextElementNode = doc.getElementsByTagName(nextElement).item(row - 1);
        Element newElement = doc.createElement(elementToAdd);
        newElement.appendChild(doc.createTextNode(elementValue));
        nextElementNode.getParentNode().insertBefore(newElement, nextElementNode);
 }

 public static void addConditionalElementAtEnd(Document doc, Node Element, String elementToAdd,
               String elementValue) {

        Node parentNode = Element.getParentNode();
        Element newElement = doc.createElement(elementToAdd);
        newElement.appendChild(doc.createTextNode(elementValue));
        parentNode.appendChild(newElement);
 }

 public static void addChildNode(Document doc, String parentNode, String elementToAdd, String elementValue,
               int row) {

        Node ParentNode = doc.getElementsByTagName(parentNode).item(row);
        Element newElement = doc.createElement(elementToAdd);
        newElement.appendChild(doc.createTextNode(elementValue));
        ParentNode.appendChild(newElement);
 }

 public static String ifExists(Document doc, String parentNode, String nodeToSearch, int row) {
        String returnVal = "";
        Node ParentNode = doc.getElementsByTagName(parentNode).item(row);
        NodeList list = ParentNode.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
               String nodeName = list.item(i).getNodeName();
               if (nodeName.equalsIgnoreCase(nodeToSearch)) {
                     returnVal = "true";
               } else
                     returnVal = "false";
        }
        return returnVal;
 }

	
	

}




