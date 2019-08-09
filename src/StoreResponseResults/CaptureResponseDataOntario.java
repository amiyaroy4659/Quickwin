package StoreResponseResults;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ReusableComponents.CSVUtilities;
import ReusableComponents.ExcelUtilities;
import ReusableComponents.RandomMethods;

public class CaptureResponseDataOntario {
	
	public static void storeDataInCSV(String TC_ID, String outputPath,String sheetName, String responseData, Logger log){
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			ByteArrayInputStream bis = new ByteArrayInputStream(responseData.getBytes());
			org.w3c.dom.Document docRes = db.parse(bis);
			NodeList nl, nl1, nl2, nl3, nl4, nl5, nl6, nl7, nl8, nl9, nl10, nl11;
			Node an, an2, an3, an4, an5, an6;
			String nodeName, TermAmount, RSPPremiums;
		String Covpollevelamount_27S=""; String Covpollevelamount_27=""; String Covpollevelamount_3=""; String Covpollevelamount_2=""; String Covpollevelamount_48=""; String Covpollevelamount_MRAC="";
		String Covpollevelamount_OCI=""; String Covpollevelamount_IR=""; String Covpollevelamount_IN=""; String Covpollevelamount_DT=""; String Covpollevelamount_DCB=""; String Covpollevelamount_CGH="";
		String IsRSPEligible=""; String RSPScore=""; String IsRSPAssignedBySystem=""; String IsRSPAssignedByAnalyst="";		
		String TotalCoverageAmountVehicle=""; String TotalCoverageAmountOccasional="";
		String IsVehicleOnGrid=""; String GovernmentVehicleAmount=""; String GovernmentOccasionalAmount="";
		TermAmount = "";RSPPremiums = "";
		
		Node PolLevCoverage=docRes.getElementsByTagName("Coverages").item(0);
		nl9=PolLevCoverage.getChildNodes();
		for(int a=0;a<nl9.getLength();a++){
			an3=nl9.item(a);
			if(an3.getNodeName().equals("Coverage")){
				nl10=an3.getChildNodes();
				String Cov_pol_level_Code=nl10.item(0).getTextContent();
				for(int b=0;b<nl10.getLength();b++){
					if(nl10.item(b).getNodeName().equalsIgnoreCase("Premium")){
						nl11=nl10.item(b).getChildNodes();
						switch(Cov_pol_level_Code){
						case "27S" : Covpollevelamount_27S=nl11.item(0).getTextContent();break;
						case "27" : Covpollevelamount_27=nl11.item(0).getTextContent();break;
						case "3" : Covpollevelamount_3=nl11.item(0).getTextContent();break;
						case "2" : Covpollevelamount_2=nl11.item(0).getTextContent();break;
						case "48" : Covpollevelamount_48=nl11.item(0).getTextContent();break;
						case "MRAC" : Covpollevelamount_MRAC=nl11.item(0).getTextContent();break;
						case "OCI" : Covpollevelamount_OCI=nl11.item(0).getTextContent();break;
						case "IR" : Covpollevelamount_IR=nl11.item(0).getTextContent();break;
						case "IN" : Covpollevelamount_IN=nl11.item(0).getTextContent();break;
						case "DT" : Covpollevelamount_DT=nl11.item(0).getTextContent();break;
						case "DCB" : Covpollevelamount_DCB=nl11.item(0).getTextContent();break;
						case "CGH" : Covpollevelamount_CGH=nl11.item(0).getTextContent();break;
						}						
					}
				}										
			}
		}
			Node VehicleDriverUsages = docRes.getElementsByTagName("VehicleDriverUsages").item(0);
			nl = VehicleDriverUsages.getChildNodes();
			System.out.println("VehicleDriverUsage count: " + nl.getLength());
			log.info("VehicleDriverUsage count: " + nl.getLength());
			for (int i = 0; i < nl.getLength(); i++) {
				an = nl.item(i);
				System.out.println(an.getNodeName());
				log.info(an.getNodeName());
				if (an.getNodeName().equals("VehicleDriverUsage")) {
					nl1 = an.getChildNodes();
					if (nl1.getLength() > 1) {
						String VehicleNum = nl1.item(2).getTextContent();
						System.out.println("VehicleNum is: " + VehicleNum);
						String DriverNum = nl1.item(1).getTextContent();
						System.out.println("DriverNum is: " + DriverNum);
						Node Coverages = docRes.getElementsByTagName("Coverages").item(i + 1);
				nl2=Coverages.getChildNodes();
				for(int j=0;j<nl2.getLength();j++){
					an2=nl2.item(j);
					if(an2.getNodeName().equals("Coverage")){
								nl3 = an2.getChildNodes();
								String CovCod = nl3.item(0).getTextContent();
								System.out.println("Coverage name: " + CovCod);
								log.info("Coverage name: " + CovCod);
						/*Node Premium=nl3.item(4);
						nl4=Premium.getChildNodes();*/
								for (int ps = 0; ps < nl3.getLength(); ps++) {
									an3 = nl3.item(ps);
									if (an3.getNodeName().equals("Premium")) {
										nl4 = an3.getChildNodes();
										TermAmount = nl4.item(0).getTextContent();
										System.out.println("TermAmount :" + TermAmount);
										log.info("TermAmount :" + TermAmount);
										for (int rs = 0; rs < nl4.getLength(); rs++) {
											RSPPremiums = "";
											an6 = nl4.item(rs);
											if (an6.getNodeName().equals("RSPPremium")) {
												RSPPremiums = an6.getTextContent();
											}
										}
									}
								}
						/*String TermAmount=nl4.item(0).getTextContent();
						System.out.println("TermAmount :"+TermAmount);
						log.info("TermAmount :"+TermAmount);*/
						/*String RSPPremiums = nl4.item(3).getTextContent();
						System.out.println("RSPPremiums :"+RSPPremiums);
						log.info("RSPPremiums :"+RSPPremiums);*/
						/*for(int rs=0;rs<nl4.getLength();rs++){
							RSPPremiums="";
							an6=nl4.item(rs);
							if(an6.getNodeName().equals("RSPPremium")){
								RSPPremiums=an6.getTextContent();
							}
						}*/
						Node Vehicle=docRes.getElementsByTagName("Vehicle").item(Integer.parseInt(VehicleNum)-1);
						nl4=Vehicle.getChildNodes();
						//Changes for credit score
						//==============================================================
								for (int cs = 0; cs < nl4.getLength(); cs++) {
									an5 = nl4.item(cs);
									if (an5.getNodeName().equals("RSPInformation")) {
										nl5 = an5.getChildNodes();
										for (int m = 0; m < nl5.getLength(); m++) {
											an4 = nl5.item(m);
											if (an4.getNodeName().equals("IsRSPAssignedByAnalyst")) {
												IsRSPAssignedByAnalyst = an4.getTextContent();
											} else if (an4.getNodeName().equals("IsRSPAssignedBySystem")) {
												IsRSPAssignedBySystem = an4.getTextContent();
											} else if (an4.getNodeName().equals("RSPScore")) {
												RSPScore = an4.getTextContent();
											} else if (an4.getNodeName().equals("IsRSPEligible")) {
												IsRSPEligible = an4.getTextContent();
											}
										}
									}
							
							//===================================================================
							//To be used in future
							//===================================================================
							/*else if(an5.getNodeName().equals("VehicleAlbertaGrid")){								
								        nl6=an5.getChildNodes();
										GovernmentOccasionalAmount=nl6.item(0).getTextContent();
										GovernmentVehicleAmount=nl6.item(1).getTextContent();
										IsVehicleOnGrid=nl6.item(2).getTextContent();
										TotalCoverageAmountOccasional=nl6.item(4).getTextContent();
										TotalCoverageAmountVehicle=nl6.item(5).getTextContent();
							}*/
							
							//===================================================================
						}
						/*Node RSPInformation=nl4.item(3);
						nl5=RSPInformation.getChildNodes();
						for(int m=0;m<nl5.getLength();m++){
							an4=nl5.item(m);							
							if(an4.getNodeName().equals("IsRSPAssignedByAnalyst")){
								IsRSPAssignedByAnalyst=an4.getTextContent();
							}
							else if(an4.getNodeName().equals("IsRSPAssignedBySystem")){
								IsRSPAssignedBySystem=an4.getTextContent();
							}
							else if(an4.getNodeName().equals("RSPScore")){
								RSPScore=an4.getTextContent();
							}
							else if(an4.getNodeName().equals("IsRSPEligible")){
								IsRSPEligible=an4.getTextContent();
							}
						}*/
								/*String RSPScore=nl5.item(0).getTextContent();
								String IsRSPEligible=nl5.item(1).getTextContent();*/
						/*Node VehicleAlbertaGrid=nl4.item(4);
						nl6=VehicleAlbertaGrid.getChildNodes();
								String GovernmentOccasionalAmount=nl6.item(0).getTextContent();
								String GovernmentVehicleAmount=nl6.item(1).getTextContent();
								String IsVehicleOnGrid=nl6.item(2).getTextContent();
								String TotalCoverageAmountOccasional=nl6.item(4).getTextContent();
								String TotalCoverageAmountVehicle=nl6.item(5).getTextContent();*/
						//============================================================================================
						//To be decided later on
						//============================================================================================
						/*Node DriverInformation=docRes.getElementsByTagName("DriverInformation").item(Integer.parseInt(DriverNum)-1);
						nl7=DriverInformation.getChildNodes();						
							Node DriverAlbertaGrid=nl7.item(4);
							nl8=DriverAlbertaGrid.getChildNodes();
								String ActualStep=nl8.item(0).getTextContent();*/
								
						//============================================================================================
								
								/*Node PolLevCoverage=docRes.getElementsByTagName("Coverages").item(0);
								nl9=PolLevCoverage.getChildNodes();
								for(int a=0;a<nl9.getLength();a++){
									an3=nl9.item(a);
									if(an3.getNodeName().equals("Coverage")){
										nl10=an3.getChildNodes();
										String Cov_pol_level_Code=nl10.item(0).getTextContent();
										for(int b=0;b<nl10.getLength();b++){
											if(nl10.item(b).getNodeName().equalsIgnoreCase("Premium")){
												nl11=nl10.item(b).getChildNodes();
												Cov_pol_level_amount_27s=nl11.item(0).getTextContent();
											}
										}										
									}
								}*/
								
						//=============================================================================================
								
								
								
								/*ExcelUtilities.setExcelFile(outputPath, sheetName, log);
								int k = ExcelUtilities.getRowNum(log);
								log.info("loop :" + k);

								ExcelUtilities.setDataStringFirst(TC_ID, k, 0, sheetName, outputPath, log);
								ExcelUtilities.setDataInt(Integer.parseInt(VehicleNum), k, 1, sheetName, outputPath, log);
								ExcelUtilities.setDataInt(Integer.parseInt(DriverNum), k, 2, sheetName, outputPath, log);
								ExcelUtilities.setDataInt(Integer.parseInt(RSPScore), k, 3, sheetName, outputPath, log);
								if (IsRSPEligible.equals("true")) {
									ExcelUtilities.setDataInt(1, k, 4, sheetName, outputPath, log);
								} else
									ExcelUtilities.setDataInt(0, k, 4, sheetName, outputPath, log);
								ExcelUtilities.setData(CovCod, k, 7, sheetName, outputPath, log);
								ExcelUtilities.setDataInt(Integer.parseInt(TermAmount), k, 5, sheetName, outputPath, log);
								ExcelUtilities.setDataInt(Integer.parseInt(TermAmount), k, 6, sheetName, outputPath, log);
								
								if (IsRSPAssignedByAnalyst.equals("true")) {
									ExcelUtilities.setDataInt(1, k, 21, sheetName, outputPath, log);
								} else if (IsRSPAssignedByAnalyst.equals("false")) {
									ExcelUtilities.setDataInt(0, k, 21, sheetName, outputPath, log);
								} else
									ExcelUtilities.setData(IsRSPAssignedByAnalyst, k, 21, sheetName, outputPath, log);
								if (IsRSPAssignedBySystem.equals("true")) {
									ExcelUtilities.setDataInt(1, k, 22, sheetName, outputPath, log);
								} else if (IsRSPAssignedBySystem.equals("false")) {
									ExcelUtilities.setDataInt(0, k, 22, sheetName, outputPath, log);
								} else
									ExcelUtilities.setData(IsRSPAssignedBySystem, k, 22, sheetName, outputPath, log);

								if (RSPPremiums.equals("")) {
									ExcelUtilities.setData(RSPPremiums, k, 8, sheetName, outputPath, log);
								} else
									ExcelUtilities.setDataInt(Integer.parseInt(RSPPremiums), k, 8, sheetName, outputPath, log);

								ExcelUtilities.setData(Covpollevelamount_27S, k, 9, sheetName, outputPath, log);
								ExcelUtilities.setData(Covpollevelamount_27, k, 10, sheetName, outputPath, log);
								ExcelUtilities.setData(Covpollevelamount_3, k, 11, sheetName, outputPath, log);
								ExcelUtilities.setData(Covpollevelamount_2, k, 12, sheetName, outputPath, log);
								ExcelUtilities.setData(Covpollevelamount_48, k, 13, sheetName, outputPath, log);
								ExcelUtilities.setData(Covpollevelamount_MRAC, k, 14, sheetName, outputPath, log);
								ExcelUtilities.setData(Covpollevelamount_OCI, k, 15, sheetName, outputPath, log);
								ExcelUtilities.setData(Covpollevelamount_IR, k, 16, sheetName, outputPath, log);
								ExcelUtilities.setData(Covpollevelamount_IN, k, 17, sheetName, outputPath, log);
								ExcelUtilities.setData(Covpollevelamount_DT, k, 18, sheetName, outputPath, log);
								ExcelUtilities.setData(Covpollevelamount_DCB, k, 19, sheetName, outputPath, log);
								ExcelUtilities.setData(Covpollevelamount_CGH, k, 20, sheetName, outputPath, log);
								*/
								ArrayList<String> data= new ArrayList<String>();
								
								data.add(TC_ID);
								data.add(VehicleNum);
								data.add(DriverNum);
								data.add(RSPScore);
								data.add(RandomMethods.convertBooleanToInt(IsRSPEligible));
								data.add(TermAmount);								
								data.add(TermAmount);
								data.add(CovCod);
								data.add(RSPPremiums);
								data.add(Covpollevelamount_27S);
								data.add(Covpollevelamount_27);
								data.add(Covpollevelamount_3);
								data.add(Covpollevelamount_2);
								data.add(Covpollevelamount_48);
								data.add(Covpollevelamount_MRAC);
								data.add(Covpollevelamount_OCI);
								data.add(Covpollevelamount_IR);
								data.add(Covpollevelamount_IN);
								data.add(Covpollevelamount_DT);
								data.add(Covpollevelamount_DCB);
								data.add(Covpollevelamount_CGH);
								data.add(RandomMethods.convertBooleanToInt(IsRSPAssignedByAnalyst));
								data.add(RandomMethods.convertBooleanToInt(IsRSPAssignedBySystem));
								
								
								CSVUtilities.storeDataCSV(outputPath, data);
						//}}
						
					}	
				}				
			}			
		}
					}
	}catch (NullPointerException e) {
		System.out.println("Warning: No value found in DataSheet....");
		log.error("Warning: No value found in DataSheet....");
		log.error(e);
	}catch(Exception e){
		System.out.println(e);
		log.error(e);		
	}
	}
	

}
