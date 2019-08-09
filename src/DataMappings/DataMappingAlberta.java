package DataMappings;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import ReusableComponents.ExcelUtilities;
import ReusableComponents.RandomMethods;
import ReusableComponents.ReadPropFile;

public class DataMappingAlberta {
		
	public static ReadPropFile properties = new ReadPropFile("Environment.properties");
	public static File directory = new File(properties.getPropertyValue("SamplePathAB"));
	public static String ClaimConvicCodePath = directory.getAbsolutePath() + properties.getPropertyValue("ClaimConvicCodePath");
	
	    // ##############################################################################
		// Function to fetch data from Excel using element name
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################

		public static String createElementDataFetch(String NodeName, String TC_ID, int row, String runManagerPath,
				int count, int VehicleNum, String ParentNodeName, Logger log, int loopToStart, Document doc) {
			try {
				String elementName = NodeName;
				String returnVal=""; int colNum;
				System.out.println("elementName: " + elementName);
				log.info("elementName: " + elementName);
				
				switch(elementName){
				case "IsActive" : 
					if (ParentNodeName.equals("Vehicle") || ParentNodeName.equals("DriverInformation")) {
						returnVal= "true";
					};break;
				
				case "RatingElement" : returnVal="Complete"; break;
				case "VehicleType" :
				case "SubVehicleType" :	
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String VehicleType = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_VehicleType", colNum), TC_ID, row, log, loopToStart);
					switch(VehicleType){
					case "Private Passenger Automobile": returnVal="AU";break;
					case "Utility Trailer": returnVal="UT";break;
					default : returnVal="";break;
					};break;
				case "ProtectionDeviceCode"	:
					switch(count){
					case 1 : returnVal= "Intensive Engraving";break;
					case 2 : returnVal= "After-Market Immobilizer Systems";break;
					default : returnVal= "Tracking system";break;
				};break;
				case "ProtectionDeviceDetail" : 
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					switch(count){
					case 1 : returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_PPA_Antitheft_Intensive", colNum), TC_ID, row, log,loopToStart);break;
					case 2 : returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_PPA_Antitheft_VICC", colNum),TC_ID, row, log, loopToStart);break;
					default : returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_PPA_Antitheft_Tracking", colNum),TC_ID, row, log, loopToStart);break;
					};break;
				case "PurchaseDate"	:
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = RandomMethods.dateConvert(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_PPA_PurchaseDate", colNum),TC_ID, row, log, loopToStart),log)+"-05:00";
					break;
				case "PurchasePrice"	:
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String PurchasePriceValue = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("PurchasePrice", colNum),TC_ID, row, log, loopToStart);
					if(PurchasePriceValue.equalsIgnoreCase(""))
					{
						returnVal="0";
						break;
						
					}
					else
					returnVal=PurchasePriceValue;
					 break;
					
				case "AccidentBenefit" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal= ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("AccidentBenefit", colNum),TC_ID, row, log, loopToStart);
					if(returnVal.equalsIgnoreCase(""))
					{
						returnVal="0";
					}		
					break;
				case "Collision" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal= ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Collision", colNum), TC_ID,row, log, loopToStart);
					if(returnVal.equalsIgnoreCase(""))
					{
						returnVal="0";
					}
					break;
				case "Comprehensive" :	
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal= ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Comprehensive", colNum), TC_ID,row, log, loopToStart);
					if(returnVal.equalsIgnoreCase(""))
					{
						returnVal="0";
					}
					break;
				case "ThirdParty" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal= ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("ThirdParty", colNum), TC_ID,row, log, loopToStart);
					if(returnVal.equalsIgnoreCase(""))
					{
						returnVal="0";
					}
					break;
				case "AirBags" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Air_Bags", colNum),	TC_ID, row, log, loopToStart);
					break;
				case "Hybrid" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal= ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Hybrid", colNum), TC_ID, row,	log, loopToStart);
					break;
				case "IsAntique" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = RandomMethods.convertToTrueFalseValue(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("IsAntique", colNum), TC_ID, row,	log, loopToStart));
					break;
				case "IsFiberglass" :
					returnVal = "false";
					break;
				case "IsWinterTire" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = RandomMethods.convertToTrueFalseValue(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("IsWinterTire", colNum), TC_ID, row,	log, loopToStart));
					break;
				case "IsModernClassic"	:
				case "IsHighPerformance"	:
				case "IsMVRReceived"	:
				case "IsMVRSuspension"	:
				case "IsCappingEvaluationRequired"	:
				case "IsRenewed"	:
				case "IsRSPSystemSuggested"	:
				case "IsAddedOnRenewal"	: returnVal = "false";
				break;
				case "IsNewVehicle" : 	
					String VD_PPA_RateKm_Demo;int rate_Km;
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String isNew = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_PPA_Purchase", colNum), TC_ID, row, log, loopToStart);
					if (isNew.equals("New")) {
						returnVal = "true";
					} 
					else if(isNew.equals("Demo")){
						VD_PPA_RateKm_Demo = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_PPA_RateKm_Demo", colNum), TC_ID, row, log, loopToStart);
						if(VD_PPA_RateKm_Demo.equalsIgnoreCase("")){
							rate_Km=0;
						} else rate_Km = Integer.parseInt(VD_PPA_RateKm_Demo);
						
						if(rate_Km<=10000){
							returnVal = "true";
						}else returnVal = "false";
					}
						else returnVal = "false";					
					break;
				case "IsModifiedVehicle" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = RandomMethods.convertToTrueFalseValue(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_PPA_VehicleModified", colNum), TC_ID, row, log, loopToStart));
					break;
				case "VICCCode" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VICCCode", colNum), TC_ID, row, log, loopToStart);
					break;
				case "DriveTrain" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("DriveTrain", colNum), TC_ID, row, log, loopToStart);
					break;
				case "Market" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Market", colNum), TC_ID, row, log, loopToStart);
					break;
				case "Year" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Year", colNum), TC_ID, row, log, loopToStart);
					break;
				case "BodyCode" :
				case "BodyStyle" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("BodyCode", colNum), TC_ID, row, log, loopToStart);
					break;
				case "CCNumber"	:
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("CCNumber", colNum), TC_ID, row, log, loopToStart);
					break;
				case "VICCCodeMultiple" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VICCCodeMultiple", colNum), TC_ID, row, log, loopToStart);
					break;
				case "ABS"	:
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("ABS", colNum), TC_ID, row, log, loopToStart);
					break;
				case "AudibleAlarm" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("audibleAlarmDesc", colNum), TC_ID, row, log, loopToStart);
					break;
				case "CutOffSystem"	:
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("cutOffSystemDesc", colNum), TC_ID, row, log, loopToStart);
					break;
				case "IBCApproved"	:
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("ibcApprovedDesc", colNum), TC_ID, row, log, loopToStart);
					break;
				case "SecurityKeySystem" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("securityKeySystemDesc", colNum), TC_ID, row, log, loopToStart);
					break;
				case "StabilityControl" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("stabilityDesc", colNum), TC_ID, row, log, loopToStart);
					break;	
				case "TractionControl" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("tractionControlDesc", colNum), TC_ID, row, log, loopToStart);
					break;
				case "NumberWheelDrive" :
				case "Wheelbase" :	
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("NumberWheelDrive", colNum), TC_ID, row, log, loopToStart);
					break;
				case "VehicleGeneration" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VehicleGeneration", colNum), TC_ID, row, log, loopToStart);
					break;	
				case "Cylinder" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Cylinder", colNum), TC_ID, row, log, loopToStart);
					break;	
				case "HorsePower" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("HorsePower", colNum), TC_ID, row, log, loopToStart);
					break;	
				case "Size" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Size", colNum), TC_ID, row, log, loopToStart);
					break;	
				case "MarketValue" :
				case "RetailPrice" :	
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("MarketValue", colNum), TC_ID, row, log, loopToStart);
					break;
				case "Weight" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Weight", colNum), TC_ID, row, log, loopToStart);
					break;
				case "ForcedInduction" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("ForcedInduction", colNum), TC_ID, row, log, loopToStart);
					break;
				case "Fuel" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Fuel", colNum), TC_ID, row, log, loopToStart);
					break;
				case "ABDisTDILossCostRatingGroup" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("ABDisTDILossCostRatingGroup", colNum), TC_ID, row, log, loopToStart);
					break;	
				case "ABMedTDILossCostRatingGroup" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("ABMedTDILossCostRatingGroup", colNum), TC_ID, row, log, loopToStart);
					break;
				case "BITDILossCostRatingGroup" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("BITDILossCostRatingGroup", colNum), TC_ID, row, log, loopToStart);
					break;
				case "CMPTDILossCostRatingGroup" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("CMPTDILossCostRatingGroup", colNum), TC_ID, row, log, loopToStart);
					break;
				case "COLTDILossCostRatingGroup" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("COLTDILossCostRatingGroup", colNum), TC_ID, row, log, loopToStart);
					break;	
				case "DCTDILossCostRatingGroup" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("DCTDILossCostRatingGroup", colNum), TC_ID, row, log, loopToStart);
					break;	
				case "IsImportedVehicle" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = RandomMethods.convertToTrueFalseValue(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_PPA_VehicleImported", colNum), TC_ID, row, log, loopToStart));
					break;
				case "IsRightHandDriveVehicle" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = RandomMethods.convertToTrueFalseValue(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_PPA_VehicleImported_Right", colNum), TC_ID, row, log, loopToStart));
					break;
				case "IsVehicleStored" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = RandomMethods.convertToTrueFalseValue(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_VehicleStorage", colNum), TC_ID, row, log, loopToStart));
					break;
				case "IsRatedByValue" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = RandomMethods.convertToTrueFalseValue(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("IsRatedByValue", colNum), TC_ID, row, log, loopToStart));
					break;
					
				case "claimId" :
					returnVal = Integer.toString(row);
					break;
				case "ClaimDate" :
					ExcelUtilities.setExcelFile(runManagerPath, "Claims_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String claimDate = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Date_of_loss", colNum), TC_ID, row, count, 8, log, loopToStart, "Claim");
					System.out.println("claimDate :" + claimDate);
					log.info("claimDate :" + claimDate);
					returnVal = RandomMethods.dateConvert(claimDate, log)+"-05:00";
					break;
				case "ResponsibilityPercentage" :
					ExcelUtilities.setExcelFile(runManagerPath, "Claims_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String responsabilityPercentage = ExcelUtilities.getMultiCellDataInt(ExcelUtilities.getColumnNum("Responsibility", colNum), TC_ID, row, count, 8, log, loopToStart,	"Claim");
					System.out.println("responsabilityPercentage: " + responsabilityPercentage);
					log.info("responsabilityPercentage: " + responsabilityPercentage);
					returnVal = responsabilityPercentage;
					break;
				case "IsPardonned" :
					if (ParentNodeName.equals("PolicyClaim")) {
						ExcelUtilities.setExcelFile(runManagerPath, "Claims_TD", log);
						colNum = ExcelUtilities.getColNum(log);
						String IsPardonned = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Pardon", colNum), TC_ID, row, count, 8, log, loopToStart, "Claim");
						returnVal = RandomMethods.convertToTrueFalseValue(IsPardonned);
					} else if (ParentNodeName.equals("Conviction")) {
						ExcelUtilities.setExcelFile(runManagerPath, "Convictions_TD", log);
						colNum = ExcelUtilities.getColNum(log);
						String IsPardonned = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Pardon", colNum), TC_ID, row, count, 5, log, loopToStart, "Conviction");
						returnVal = RandomMethods.convertToTrueFalseValue(IsPardonned);
					} else if (ParentNodeName.equals("LicenseSuspension")) {
						ExcelUtilities.setExcelFile(runManagerPath, "Convictions_TD", log);
						colNum = ExcelUtilities.getColNum(log);
						String IsPardonned = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Pardon", colNum), TC_ID, row, count, 5, log, loopToStart, "Suspension");
						returnVal = RandomMethods.convertToTrueFalseValue(IsPardonned);
					};break;
				case "OriginalRiskType" :
					ExcelUtilities.setExcelFile(runManagerPath, "Claims_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String damagedVehicleType = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Vehicle_Type", colNum), TC_ID, row, count, 8, log,loopToStart, "Claim");
					switch(damagedVehicleType){
					case "Private Passenger Automobile" : returnVal = "AU";break;
					case "Utility Trailor" : returnVal = "UT";break;
					default : returnVal = "";break;
					};break;
				case "IsCatastrophe" :	
				case "IsMVRConviction" :
				case "IsIncidentOnly" : returnVal = "false";break;	
				case "TotalPaidAmount" :
				case "PaidAmount" :
					ExcelUtilities.setExcelFile(runManagerPath, "Claims_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String totalPaidAmount = ExcelUtilities.getMultiCellDataInt(ExcelUtilities.getColumnNum("Amount", colNum), TC_ID, row, count, 8, log, loopToStart, "Claim");
					System.out.println("totalPaidAmount: " + totalPaidAmount);
					log.info("totalPaidAmount: " + totalPaidAmount);
					returnVal = totalPaidAmount;
					break;
				case "LossCode" :
					ExcelUtilities.setExcelFile(runManagerPath, "Claims_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String claim = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Loss_type", colNum), TC_ID, row, count, 8, log, loopToStart, "Claim");
					System.out.println("claim name: " + claim);
					log.info("claim name: " + claim);
					ExcelUtilities.setExcelFile(ClaimConvicCodePath, "ClaimConvictionCode", log);
					String claimCode = ExcelUtilities.getClaimConvCode(claim, log, 1);
					returnVal = claimCode;
					break;
				case "ConvictionDate" :
					ExcelUtilities.setExcelFile(runManagerPath, "Convictions_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String convictionDate = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Date_of_Conviction", colNum), TC_ID, row, count, 5, log,	loopToStart, "Conviction");
					returnVal = RandomMethods.dateConvert(convictionDate, log);
					break;
				case "SuspensionRevocationDate" :
					ExcelUtilities.setExcelFile(runManagerPath, "Convictions_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = RandomMethods.dateConvert(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Date_of_Conviction", colNum), TC_ID, row, count, 5, log, loopToStart, "Suspension"),log);
					break;
				case "ConvictionCode" :
					ExcelUtilities.setExcelFile(runManagerPath, "Convictions_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String conviction = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Conviction_type", colNum), TC_ID, row, count, 5, log,loopToStart, "Conviction");
					System.out.println("conviction is: " + conviction);
					log.info("conviction is: " + conviction);
					ExcelUtilities.setExcelFile(ClaimConvicCodePath, "ClaimConvictionCode", log);
					returnVal = ExcelUtilities.getClaimConvCode(conviction, log, 1);
					break;
				case "DriverViolationInformationCode" :
					ExcelUtilities.setExcelFile(runManagerPath, "Convictions_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String Suspension = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Conviction_type", colNum), TC_ID, row, count, 5, log, loopToStart, "Suspension");
					System.out.println("conviction is: " + Suspension);
					log.info("conviction is: " + Suspension);
					ExcelUtilities.setExcelFile(ClaimConvicCodePath, "ClaimConvictionCode", log);
					returnVal = ExcelUtilities.getClaimConvCode(Suspension, log, 1);
					break;
				case "ContractNumber" :
					returnVal = "0000000000" + RandomMethods.randomNumber();
					break;
				case "PortfolioNumber" :
					returnVal = "00" + RandomMethods.randomPortfolio();
					break;
				case "ClientGroupNumber" :
				case "RatingGroupNumber" :
					ExcelUtilities.setExcelFile(runManagerPath, "Policy_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String ratingGroup = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("RatingGroupNumber", colNum),	TC_ID, 1, log, loopToStart);
					returnVal = ratingGroup;
					break;
				case "NumberMonthTerm" :
					ExcelUtilities.setExcelFile(runManagerPath, "Policy_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String NumberMonthTerm = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("POL_Term_Type", colNum), TC_ID, row, log, loopToStart);
					if (NumberMonthTerm.toUpperCase().equals("ANNUAL")) {
						returnVal = "12";
					};break;
				case "InsurerName" :
					ExcelUtilities.setExcelFile(runManagerPath, "Policy_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String insurer = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("InsurerName", colNum),TC_ID, 1, log, loopToStart);
					returnVal = insurer;
					break;
				case "TransactionType" :
					returnVal = "Renewal";
					break;
				case "DisplayProvinceState" :
					returnVal = "AB";
					break;
			case "EffectiveDate" :
					ExcelUtilities.setExcelFile(runManagerPath, "Policy_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String effectiveDate = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("EffectiveDate", colNum), TC_ID, 1, log, loopToStart);
					if (ParentNodeName.equals(" ")) {
						returnVal = RandomMethods.dateConvert(effectiveDate, log);
					} else
						returnVal = RandomMethods.dateConvert(effectiveDate, log) + "T00:01:00-05:00";
					;break;
				case "RevisedGridStepDate" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String RevisedGridStep = ExcelUtilities.getMultiCellDataInt(ExcelUtilities.getColumnNum("Grid_Step", colNum),TC_ID, row, log, loopToStart);
					if (RevisedGridStep.equals("")) {
						returnVal = "";
					} else {
						ExcelUtilities.setExcelFile(runManagerPath, "Policy_TD", log);
						int colNum1 = ExcelUtilities.getColNum(log);						
						returnVal = RandomMethods.dateConvert(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("EffectiveDate", colNum1), TC_ID, 1, log, loopToStart), log);
					};break;
				case "IsGWPolicy" :
				case "IsExperienceChange" :	
				case "IsAutomaticRating" :
				case "IsOriginalContract" :	returnVal = "true" ;break;
				case "OriginalCreationDate" :
					ExcelUtilities.setExcelFile(runManagerPath, "Policy_TD", log);
					colNum = ExcelUtilities.getColNum(log);					
					returnVal = RandomMethods.dateConvert(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("OriginalCreationDate", colNum), TC_ID, 1, log, loopToStart), log);
					break;
				case "EndDate" :
					ExcelUtilities.setExcelFile(runManagerPath, "Policy_TD", log);
					colNum = ExcelUtilities.getColNum(log);					
					returnVal = RandomMethods.makeEndDatedate(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("EffectiveDate", colNum), TC_ID, 1, log, loopToStart)) + "T00:01:00-05:00";
                    break;
				case "RatingSource" :
					returnVal = "renewal";break;
				case "LifetimeCustomerValue" :
					returnVal = "";break;
				case "VersionEffectiveDate" :
					ExcelUtilities.setExcelFile(runManagerPath, "Policy_TD", log);
					colNum = ExcelUtilities.getColNum(log);					
					returnVal = RandomMethods.dateConvert(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("EffectiveDate", colNum), TC_ID, 1, log, loopToStart), log) + "T00:01:00-05:00";
					break;
				case "DisplayPostalZipCode" :
					ExcelUtilities.setExcelFile(runManagerPath, "Customer_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String DisplayPostalZipCode = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Postal Code", colNum),TC_ID, 1, log, loopToStart);
					returnVal=DisplayPostalZipCode;
					break;
				case "RiskPostalCodeFSA" :
					ExcelUtilities.setExcelFile(runManagerPath, "Customer_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String RiskPostalCodeFSA = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Postal Code", colNum),TC_ID, 1, log, loopToStart);
					returnVal = RandomMethods.postalCode("RiskPostalCodeFSA", RiskPostalCodeFSA, log);
					break;
				case "RiskPostalCodeLDU" :
					ExcelUtilities.setExcelFile(runManagerPath, "Customer_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String RiskPostalCodeLDU = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Postal Code", colNum),TC_ID, 1, log, loopToStart);
					returnVal = RandomMethods.postalCode("RiskPostalCodeLDU", RiskPostalCodeLDU, log);
					break;
				case "AnnualMileage" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataInt(ExcelUtilities.getColumnNum("VD_PPA_AnnualKm", colNum), TC_ID, VehicleNum, log,loopToStart);
					break;
				case "BusinessMileage" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataInt(ExcelUtilities.getColumnNum("VD_PPA_Use_Business_KM", colNum), TC_ID, VehicleNum, log,loopToStart);
					break;
				case "ClientAssessmentScore" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataInt(ExcelUtilities.getColumnNum("ClientAssessmentScore", colNum), TC_ID, VehicleNum, log,loopToStart);
					break;	
				case "IsRSPAssignedByAnalyst" :
					returnVal="false";
					break;
					
				case "WorkMileage" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataInt(ExcelUtilities.getColumnNum("VD_PPA_Use_Commute_KM", colNum), TC_ID, VehicleNum, log,loopToStart);
					break;
				case "VehicleNumber" :
					returnVal = Integer.toString(VehicleNum);
					break;
				case "DriverNumber" :
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					if (ParentNodeName.equals("VehicleDriverUsage")) {
						if (count > 0) {
							String Occ = ExcelUtilities.getMultiCellDataOcc(ExcelUtilities.getColumnNum("Occasional ", colNum),	TC_ID, VehicleNum, log, loopToStart);
							String[] arr = Occ.split(",");
							System.out.println("Occassional Number: " + arr[count - 1]);
							log.info("Occassional Number: " + arr[count - 1]);
							returnVal = arr[count - 1];

						} else
							returnVal = ExcelUtilities.getMultiCellDataInt(ExcelUtilities.getColumnNum("Principal_Driver", colNum),	TC_ID, VehicleNum, log, loopToStart);
					} else if (ParentNodeName.equals("DriverInformation")) {
						returnVal = Integer.toString(row);
					}
					break;
					
				case "CurrentVehicleUsageType" :
					if (count > 0) {
						returnVal = "Occasional";
					} else
						returnVal = "Principal";
					break;
				case "TransactionCurrentDate" :
					returnVal = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					break;
				case "RateDate" :
					ExcelUtilities.setExcelFile(runManagerPath, "Policy_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = RandomMethods.dateConvert(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Rate Date", colNum),TC_ID, 1, log, loopToStart), log)+"-05:00";
					break;
				case "ConsentDate"	:
					returnVal = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					break;
				case "IsAddedOnPolicyVersion" :
					returnVal = "false";break;
				case "IsTemporaryLicenseStatusUpdated" :
				case "IsValidLicense" : returnVal = "false" ;break;
				case "IsPrincipalOnAnotherTDAPolicy" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
	                returnVal = RandomMethods.convertToTrueFalseValue(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Principal_Driver_on_Another_Policy", colNum), TC_ID, row, log,loopToStart));
					break;
				case "IsGridStepOverridden" :
					returnVal = "false" ;break;
				case "IsDriverEligibleToStudentDiscount" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = RandomMethods.convertToTrueFalseValue(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("College_University", colNum), TC_ID, row, log, loopToStart));
					break;
				case "IsOlderVehicle" :
					returnVal = "false" ;
					break;
				case "IsDriverTraining" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					if(ParentNodeName.equalsIgnoreCase("MotorCycle")){
						returnVal = RandomMethods.convertToTrueFalseValue(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("IsDriverTrainingMC", colNum), TC_ID, row, log, loopToStart));
					}
					else returnVal = RandomMethods.convertToTrueFalseValue(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Driving_Course", colNum), TC_ID, row, log, loopToStart));
					break;
				case "BirthDate" :
					ExcelUtilities.setExcelFile(runManagerPath, "Insured_Td", log);
					colNum = ExcelUtilities.getColNum(log);
					String Age = ExcelUtilities.getMultiCellDataInt(ExcelUtilities.getColumnNum("RatedAge", colNum), TC_ID, row,	log, loopToStart);
					ExcelUtilities.setExcelFile(runManagerPath, "Policy_TD", log);
					String EffectiveDate = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("EffectiveDate", colNum), TC_ID, 1, log, loopToStart);
					returnVal = RandomMethods.getBirthDay(Age, EffectiveDate, log);
					break;
				case "NamedInsuredType" :
					ExcelUtilities.setExcelFile(runManagerPath, "Insured_Td", log);
					colNum = ExcelUtilities.getColNum(log);
					String InsuredName=ExcelUtilities.getMultiCellDataString(colNum , TC_ID, row, log, loopToStart);
				case "ConsentCode" :
					ExcelUtilities.setExcelFile(runManagerPath, "Customer_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String ConsentCode = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Consent", colNum), TC_ID, 1, log, loopToStart);
					returnVal = RandomMethods.returnConsentCode(ConsentCode);
					break;
				case "EnvironmentID" :
					ExcelUtilities.setExcelFile(runManagerPath, "Environment", log);
				    String EnvironmentID=ExcelUtilities.getTcId(1, 2, log);
					returnVal = EnvironmentID;
					break;
					
				case "KmFromPrimaryResidence" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String KmFromPrimaryResidenceValue = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("KmFromPrimaryResidence", colNum), TC_ID, 1, log, loopToStart);
					if(KmFromPrimaryResidenceValue.equalsIgnoreCase(""))
					{
						returnVal="0";
					}
					else
					returnVal=KmFromPrimaryResidenceValue;
					break;
				case "IsLicenseNumberAccepted" :
					returnVal = "false";
					break;
				case "MaritalStatus" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String Marital_Status = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Marital_Status", colNum), TC_ID, row, log, loopToStart);
					if (Marital_Status.equals("Common-Law")) {
						returnVal = "CommonLaw";
					} else if (Marital_Status.equals("Widow(er)")) {
						returnVal = "Widowed";
					} else
						returnVal = Marital_Status;
                    break;
				case "Gender" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Gender", colNum), TC_ID, row, log, loopToStart);
					break;
				case "PriorInsurance" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String PriorInsurance = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Prior_Ins", colNum), TC_ID, row, log, loopToStart);
					switch (PriorInsurance) {
					case "TDI":
						PriorInsurance = "TDI";
						break;
					case "No Prior":
						PriorInsurance = "None";
						break;
					case "Other":
						PriorInsurance = "Other";
						break;
					default:
						PriorInsurance = "";
						break;
					}
					returnVal = PriorInsurance;
                    break;
				case "ResidenceType" :
					ExcelUtilities.setExcelFile(runManagerPath, "Other_Required_Data", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Clt_Home_Main_Loc", colNum), TC_ID, row, log, loopToStart);
                    break;
				case "CreditScore" :
					ExcelUtilities.setExcelFile(runManagerPath, "Other_Required_Data", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Credit_Score", colNum), TC_ID, row, log, loopToStart);
					break;
				case "AutoplusGridStep" :
					ExcelUtilities.setExcelFile(runManagerPath, "Other_Required_Data", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Autoplus_Grid_Step", colNum), TC_ID, row, log, loopToStart);
					break;
				case "AutoplusGridStepDate" :
					ExcelUtilities.setExcelFile(runManagerPath, "Other_Required_Data", log);
					colNum = ExcelUtilities.getColNum(log);
					String AutoplusGridStepDate = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Autoplus_Grid_Step_Date", colNum), TC_ID, row, log, loopToStart);
					if (AutoplusGridStepDate.equals("")) {
						returnVal = "";
					} else
						returnVal = RandomMethods.dateConvert(AutoplusGridStepDate, log);
                    break;
				case "LicenseClassCode" :
					if(ParentNodeName.equalsIgnoreCase("MotorCycle"))
					{
						returnVal= "Motorcycle";
					}
					else
						
					returnVal = "PrivatePassenger";
                    break;
				case "Grade" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String Grade = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Lic_Class", colNum), TC_ID, row, log, loopToStart);
					if (Grade.equalsIgnoreCase("GRegular")) {						
						String GRegularLicenseDate = ExcelUtilities.getMultiCellDataString(	ExcelUtilities.getColumnNum("GRegularLicenseDate", colNum), TC_ID, row, log, loopToStart);
					}
					returnVal = RandomMethods.returnGrade(Grade);
					break;
				case "FirstLicenseDate" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String FirstLicenseDate = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Lic_Date", colNum), TC_ID, row, log, loopToStart);
					returnVal = RandomMethods.dateConvert(FirstLicenseDate, log);
					break;
				case "LicenseDate" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					if(ParentNodeName.equalsIgnoreCase("MotorCycle"))
					{
						String LicenseDate = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("LicenseDateMC", colNum), TC_ID, row, log, loopToStart);
						returnVal = RandomMethods.dateConvert(LicenseDate, log)+"-05:00";
					}
					else
					{
					String LicenseDate = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Lic_Date", colNum), TC_ID, row, log, loopToStart);
					returnVal = RandomMethods.dateConvert(LicenseDate, log)+"-05:00";
					}
					break;
				case "NumberYearsOwnerMotorcycle" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("NumberYearsOwnerMotorcycle", colNum), TC_ID, row, log, loopToStart);
					break;
				case "ElsewhereLicenseDate" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					returnVal = RandomMethods.dateConvert(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Date_Lic_Elsewhere", colNum), TC_ID, row, log, loopToStart), log);
					break;
				case "EmploymentStatus" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String EmploymentStatus = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Employment_Status", colNum), TC_ID, row, log, loopToStart);
					switch (EmploymentStatus) {
					case "Employed":
						EmploymentStatus = "EMPLOYED_EXT";
						break;
					case "Retired":
						EmploymentStatus = "RET";
						break;
					case "Unemployed":
						EmploymentStatus = "UNEM";
						break;
					default:
						EmploymentStatus = "";
						break;
					}
					returnVal = EmploymentStatus;
				    break;
				case "OriginalQuoteSource" :
					ExcelUtilities.setExcelFile(runManagerPath, "Policy_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String OriginalQuoteSource = ExcelUtilities.getMultiCellDataString(	ExcelUtilities.getColumnNum("OriginalQuoteSource", colNum), TC_ID, row, log, loopToStart);
					if (OriginalQuoteSource.equalsIgnoreCase("Phone Channel")) {
						OriginalQuoteSource = "phone_channel";
					}
					returnVal = OriginalQuoteSource;
					break;
				case "UBIDriverIsActive" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String UBIDriverIsActive = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("UBIDriverIsActive", colNum), TC_ID, row, log, loopToStart);
					returnVal = RandomMethods.convertToTrueFalseValue(UBIDriverIsActive);
                    break;
				case "UBINumberOfDays" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String UBINumberOfDays = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("UBINumberOfDays", colNum), TC_ID, row, log, loopToStart);
					returnVal = UBINumberOfDays;
					break;
				case "UBINumberOfKM" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String UBINumberOfKM = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("UBINumberOfKM", colNum), TC_ID, row, log, loopToStart);
					returnVal = UBINumberOfKM;
					break;
				case "UBIScore" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String UBIScore = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("UBIScore", colNum), TC_ID, row, log, loopToStart);
					returnVal = UBIScore;
					break;
				case "UBIScoreDate" :
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					String UBIScoreDate = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("UBIScoreDate", colNum), TC_ID, row, log, loopToStart);
					returnVal = RandomMethods.dateConvert(UBIScoreDate,log);
					break;
				case "LineOfBusiness":
					returnVal = "AUTOMOBILE";break;
					
				default : returnVal = "";break;
				
				} 
				return returnVal;
			} catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}
			return "";
		}




}
