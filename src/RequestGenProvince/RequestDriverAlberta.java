package RequestGenProvince;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

import DataMappings.DataMappingAlberta;
import ReusableComponents.ExcelUtilities;
import ReusableComponents.RandomMethods;
import ReusableComponents.ReadPropFile;

public class RequestDriverAlberta {	
	
	
	private static final String String = null;
	public static int counterCount,policiesCount,colNum=0, prevOccCount,prevOccCount1;
	public static NodeList list,childNodeList,childNodeList1,childNodeList2,childNodeList3,childNodeList4,childNodeList5,childNodeList6;
	public static Node node,childNode,childNode1,childNode2,childNode3,childNode4,childNode5,childNode6,staff,claim,conviction,LicenseSuspension;
	public static String nodeName,nodeName1,nodeName2,nodeName3,limitName,covName,covVehName,covDate;
	
	public static void requestGenerationAlberta(ReadPropFile directoriesProp, String TC_ID,int loopToStart, Logger log){
		
		try{
		    ReadPropFile propertiesCov=new ReadPropFile("CoverageCodesAB.properties");
		    ReadPropFile propertiesCovField=new ReadPropFile("CoverageFieldsAB.properties");
		    ReadPropFile propertiesCovTypes=new ReadPropFile("CoverageTypesAB.properties");
		    File directory = new File(directoriesProp.getPropertyValue("directory"));
		    File samplePathDirectory=new File(directoriesProp.getPropertyValue("SamplePathAB"));
			String filepath = samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("InputSample");
			String claimFilepath = samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("ClaimSample");
			String convictionFilepath = samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("ConvictionSample");
			String runManagerPath = directory.getAbsolutePath()+directoriesProp.getPropertyValue("runManagerPath");
			String OutsideOfProvinceRegionPath = samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("OutsideProvince");
			String LocationPath=samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("Location");
			//String SamplePath=samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("SamplePathON");
			String ProtectionDevicePath=samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("ProtectionDevicePath");
			String LicenseSuspensionPath=samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("LicenseSuspensionSample");
			String UBIFilepath = samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("UBISample");
			//String DriverCountersPath=samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("DriverExperiencesCountersSample");
			String DriverCountersSamplePath=samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("DriverCountersSample");
			String AutomobilePolicyCountersPath=samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("AutomobilePolicyCountersSample");
			//String AutomobilePolicyCountersPath=samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("AutomobilePolicyCountersSample");
			String VehicleDriverUsageFilepath = samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("VehcileDriverUsageSample");
			String DriverInformationFilepath = samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("DriverInformationSample");
			String MCLicense = samplePathDirectory.getAbsolutePath()+directoriesProp.getPropertyValue("MCLicense");
		
		//####################################################################################
				//creating instance of output request xml according to the test case name
				//####################################################################################
				
				String newFilePath= directory.getAbsolutePath()+directoriesProp.getPropertyValue("OutputPath")+TC_ID+".xml";
				File newFile= new File(newFilePath);
				BufferedReader reader = new BufferedReader(new FileReader(filepath));
		        BufferedWriter writer = new BufferedWriter(new FileWriter(newFilePath));
		        String line = null;

		        while ((line = reader.readLine()) != null)
		        {
		            writer.write(line);
		        }
		        reader.close();
		        writer.close();
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(newFilePath);
				
			    //####################################################################################		
				
				
				
			   //####################################################################################
			   //Making xml for one driver combination
			   //####################################################################################
				
				staff=doc.getElementsByTagName("v4:calculateAutoPremium").item(0);
				list=staff.getChildNodes();
				System.out.println("Count: "+list.getLength());
				log.info("Count: "+list.getLength());
				for(int i=0;i<list.getLength();i++){
					node=list.item(i);
					if(!node.getNodeName().equals("#text")){
					childNodeList=node.getChildNodes();
					if(childNodeList.getLength()>1){
						for(int j=0;j<childNodeList.getLength();j++){
							childNode=childNodeList.item(j);
							if(!childNode.getNodeName().equals("#text")){
								childNodeList1=childNode.getChildNodes();
								if(childNodeList1.getLength()>1){
									for(int k=0;k<childNodeList1.getLength();k++){
										childNode1=childNodeList1.item(k);
										if(!childNode1.getNodeName().equals("#text")){
											childNodeList2=childNode1.getChildNodes();
											if(childNodeList2.getLength()>1){
												for(int l=0;l<childNodeList2.getLength();l++){
													childNode2=childNodeList2.item(l);
												   if(!childNode2.getNodeName().equals("#text")){
														childNodeList3=childNode2.getChildNodes();
														if(childNodeList3.getLength()>1){
															for(int m=0;m<childNodeList3.getLength();m++){
																childNode3=childNodeList3.item(m);
																if(!childNode3.getNodeName().equals("#text")){
																	childNodeList4=childNode3.getChildNodes();
																	if(childNodeList4.getLength()>1){
																		for(int n=0;n<childNodeList4.getLength();n++){
																			childNode4=childNodeList4.item(n);
																			if(!childNode4.getNodeName().equals("#text")){
																				childNodeList5=childNode4.getChildNodes();
																				childNode4.setTextContent(DataMappingAlberta.createElementDataFetch(childNode4.getNodeName(), TC_ID, 1, runManagerPath, 0, 1, childNode3.getNodeName(), log, loopToStart, doc));
																			}
																		}
																	} else childNode3.setTextContent(DataMappingAlberta.createElementDataFetch(childNode3.getNodeName(), TC_ID, 1, runManagerPath, 0, 1, childNode2.getNodeName(), log, loopToStart, doc));
																}
															}
														}else childNode2.setTextContent(DataMappingAlberta.createElementDataFetch(childNode2.getNodeName(), TC_ID, 1, runManagerPath, 0, 1, childNode1.getNodeName(), log, loopToStart, doc));
													}
												}
											} else childNode1.setTextContent(DataMappingAlberta.createElementDataFetch(childNode1.getNodeName(), TC_ID, 1, runManagerPath, 0, 1, childNode.getNodeName(), log, loopToStart, doc));
										}
									}
								} else childNode.setTextContent(DataMappingAlberta.createElementDataFetch(childNode.getNodeName(), TC_ID, 1, runManagerPath, 0, 1, node.getNodeName(), log, loopToStart, doc));
							}
						}
					}else node.setTextContent(DataMappingAlberta.createElementDataFetch(node.getNodeName(), TC_ID, 1, runManagerPath, 0, 1, staff.getNodeName(), log, loopToStart, doc));
					
				}
				}
				
				//####################################################################################
				//Adding Policy Level Coverages and Endorsements
				//####################################################################################
				      
				      ExcelUtilities.setExcelFile(runManagerPath, "Policy_TD", log);
				      colNum=ExcelUtilities.getColNum(log);
				      covDate=ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("EffectiveDate", colNum), TC_ID, 1, log, loopToStart); 
				
				     for(int i=1;i<4;i++){				
						int endo2NameCount=0; String[] endorsement2Names=new String[2];String names="";
						ExcelUtilities.setExcelFile(runManagerPath, "Coverages_TD", log);
						colNum=ExcelUtilities.getColNum(log);
						covName=propertiesCov.getPropertyValue(Integer.toString(i));
						if(!(covName.equals(" ")) && covName.length()>0){
						String covFieldName=propertiesCovField.getPropertyValue(covName);
						int covPolLevelCount=ExcelUtilities.covPolLevelCount(TC_ID, covFieldName, log, colNum);
						System.out.println("Count of the Coverage "+covName+" is : "+covPolLevelCount);
						if(covName.equals("2")){
							endo2NameCount=ExcelUtilities.endo2NameCount(TC_ID, "", log, colNum);
							System.out.println("Number of names present for endorsement 2 is: "+endo2NameCount);
							for(int j=0;j<endo2NameCount;j++){
								endorsement2Names[j]=ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Acov_2_Pcov_Name"+(j+1), colNum), TC_ID, 1, log, loopToStart);
								System.out.println("names are "+endorsement2Names[j]);
							}
						}
						
						if(covPolLevelCount>0){
							String covLimit=propertiesCovField.getPropertyValue(covName+"_Limit");
							String covLimitVal=covLimit;
							if(!(covLimit.equals("0"))&&!(covLimit.equals("1000000"))){
								
								covLimitVal=ExcelUtilities.getPolLevelCovLimitVal(TC_ID, covFieldName, covLimit, log, colNum, loopToStart);
							}
							
							String covDeduct=propertiesCovField.getPropertyValue(covName+"_Deduct");
							RandomMethods.addpolLevelCoverage(doc, covLimitVal, covDeduct, covName, log, propertiesCovTypes.getPropertyValue(covName), covDate, endo2NameCount, endorsement2Names);
							
						}
					}		
					} 
				
				//####################################################################################
			    // Adding Vehicles to the policy
			    //####################################################################################
				
				ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
				int VehicleCount=ExcelUtilities.getCount(TC_ID, log, loopToStart);
				System.out.println("Number of Vehicles: "+VehicleCount);
				log.info("Number of Vehicles: "+VehicleCount);
				if(VehicleCount>0){		
				for(int j=0;j<VehicleCount;j++){
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum=ExcelUtilities.getColNum(log);
					String VehicleType=ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_VehicleType",colNum), TC_ID, j+1, log, loopToStart);
					Document doc3 = docBuilder.parse(samplePathDirectory+"\\"+VehicleType+".xml");
					Node Vehicles = doc.getElementsByTagName("Vehicles").item(0);
					Element vehicleElement=doc.createElement("Vehicle");
					Vehicles.appendChild(vehicleElement);
					Node Vehicle = doc3.getElementsByTagName("Vehicle").item(0);			
					NodeList list1 = Vehicle.getChildNodes();
					for (int i = 1; i < list1.getLength(); i++) {
		                childNode = list1.item(i);
		                childNodeList=childNode.getChildNodes();
		                String nodaName=childNode.getNodeName();
		                if(!nodaName.equals("#text")){
		                	if(!nodaName.equals("NbOfDaysUsedOutsideProvince")&&!nodaName.equals("OutsideOfProvinceRegion")){
		                	Element newElement=doc.createElement(nodaName);
		                	String value1=DataMappingAlberta.createElementDataFetch(nodaName, TC_ID, j+1, runManagerPath,0,j+1, Vehicle.getNodeName(), log, loopToStart, doc);
		                	if(!value1.equals("null")){
		                		newElement.appendChild(doc.createTextNode(value1));
		                    	vehicleElement.appendChild(newElement);
		                	}
		                	else System.out.println("");
		                	if(childNodeList.getLength()>1){
		                		for(int k=0;k<childNodeList.getLength();k++){
		                			childNode1 = childNodeList.item(k);
		                			childNodeList2=childNode1.getChildNodes();
		                		    nodeName1=childNode1.getNodeName();
		                		if(!nodeName1.equals("#text")&&!nodeName1.equals("OutsideOfProvinceRegion")){
		                			Element newElement1=doc.createElement(nodeName1);
		                			String value2=DataMappingAlberta.createElementDataFetch(nodeName1, TC_ID, j+1, runManagerPath,0,j+1, nodaName, log, loopToStart, doc);
		                			if(!value2.equals("null")){
		                				newElement1.appendChild(doc.createTextNode(value2));
		                    			newElement.appendChild(newElement1);
		                			}
		                			else System.out.println("");
		                			if(childNodeList2.getLength()>1){
		                				for(int l=0;l<childNodeList2.getLength();l++){
		                        			childNode2 = childNodeList2.item(l);
		                        		    nodeName2=childNode2.getNodeName();
		                        		if(!nodeName2.equals("#text")&&!nodeName2.equals("OutsideOfProvinceRegion")){
		                        			Element newElement2=doc.createElement(nodeName2);
		                        			String value3=DataMappingAlberta.createElementDataFetch(nodeName2, TC_ID, j+1, runManagerPath,0,j+1, nodeName1, log, loopToStart, doc);
		                        			if(!value3.equals("null")){
		                        				newElement2.appendChild(doc.createTextNode(value3));
		                            			newElement1.appendChild(newElement2);
		                        			}
		                        			else System.out.println("");
		                        		}
		                				}
		                			}
		                		}
		                		}
		                	} 
		                }
		                }                
					}
					
				//####################################################################################
				//Adding Outside Provinces to the vehicle
				//####################################################################################
					
					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum=ExcelUtilities.getColNum(log);
					int startMul=ExcelUtilities.getColumnNum("VD_PPA_Outside__Used_British", colNum);
					int endMul=ExcelUtilities.getColumnNum("VD_PPA_Outside_Used_Other", colNum);
					String VD_PPA_Outside_Used_mul = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_PPA_Outside_Used", colNum), TC_ID, j+1, log, loopToStart);
					if(VD_PPA_Outside_Used_mul.equals("Yes")){
						/*for(int i=startMul;i<=endMul;i++){
							String Outside_Used_name_mul = ExcelUtilities.getMultiCellDataString(i, TC_ID, j+1, log, loopToStart);
							if(Outside_Used_name_mul.equals("Yes")){
								Document doc1 = docBuilder.parse(OutsideOfProvinceRegionPath);
								Node OutsideOfProvinceRegions=doc.getElementsByTagName("OutsideOfProvinceRegions").item(j);
								Element OutsideOfProvinceRegionElement=doc.createElement("OutsideOfProvinceRegion");
								OutsideOfProvinceRegions.appendChild(OutsideOfProvinceRegionElement);
								node=doc1.getElementsByTagName("OutsideOfProvinceRegion").item(0);
								list=node.getChildNodes();
								for(int mj=0;mj<list.getLength();mj++){
									childNode=list.item(mj);
									if(childNode.getNodeName().equals("RegionName")){
										Element RegionNameElement=doc.createElement(childNode.getNodeName());
										RegionNameElement.appendChild(doc.createTextNode(RandomMethods.regionName(ExcelUtilities.getTcId(0, i, log), doc, j+1,runManagerPath,log,TC_ID, loopToStart)));
										OutsideOfProvinceRegionElement.appendChild(RegionNameElement);
									}
								}
							}
						}*/
					}
				/*	Node ProtectionDevices=doc.getElementsByTagName("ProtectionDevices").item(j);
					for(int mk=1;mk<4;mk++){
						Document doc4 = docBuilder.parse(ProtectionDevicePath);
						Node ProtectionDevice=doc4.getElementsByTagName("ProtectionDevice").item(0);
						Element ProtectionDeviceElelemt=doc.createElement(ProtectionDevice.getNodeName());
						ProtectionDevices.appendChild(ProtectionDeviceElelemt);
						childNodeList=ProtectionDevice.getChildNodes();
						for(int mm=0;mm<childNodeList.getLength();mm++){
							childNode=childNodeList.item(mm);
							if(!childNode.getNodeName().equals("#text")){
								Element ProtectionDeviceChildElement=doc.createElement(childNode.getNodeName());
								ProtectionDeviceChildElement.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(childNode.getNodeName(), TC_ID, j+1, runManagerPath, mk, j+1, ProtectionDevice.getNodeName(), log, loopToStart, doc)));
								ProtectionDeviceElelemt.appendChild(ProtectionDeviceChildElement);
							}
						}
					}*/
				}
				}
		        
				
				//####################################################################################
				//Adding ProtectionDevice to Vehicles
				//####################################################################################								
				
				
				
				/*for (int k=0;k<VehicleCount;k++)
				{
					int ProtectionDevicesCount=ExcelUtilities.protectionDeviceCount(TC_ID, k+1, log);
					String[] protectionDeviceValue=ExcelUtilities.protectionDeviceValue(TC_ID, k+1, log);
					Node ProtectionDevices = doc.getElementsByTagName("ProtectionDevices").item(k);
					
					for(int l=0;l<ProtectionDevicesCount;l++)
						{
							
							String protectionDeviceCodes=RandomMethods.returnProtectionCodes(protectionDeviceValue[l]);
							Element ProtectionDevice=doc.createElement("ProtectionDevice");
							Element ProtectionDeviceCode=doc.createElement("ProtectionDeviceCode");
							ProtectionDeviceCode.appendChild(doc.createTextNode(protectionDeviceCodes));
							ProtectionDevice.appendChild(ProtectionDeviceCode);
							ProtectionDevices.appendChild(ProtectionDevice);
						}
					
				}*/
				
				int startProtect=ExcelUtilities.getColumnNum("1_Protection_Devices", colNum);
				int endProtect=ExcelUtilities.getColumnNum("3_Protection_Devices", colNum);
				for(int k=0;k<VehicleCount;k++){
					Node ProtectionDevices = doc.getElementsByTagName("ProtectionDevices").item(k);
				for(int l=startProtect;l<=endProtect;l++){
					String protectionDevice=ExcelUtilities.getMultiCellDataString(l, TC_ID, k+1, log, loopToStart);
					if(!(protectionDevice.equals(""))){
						Element ProtectionDevice=doc.createElement("ProtectionDevice");
						Element ProtectionDeviceCode=doc.createElement("ProtectionDeviceCode");
						ProtectionDeviceCode.appendChild(doc.createTextNode(RandomMethods.returnProtectionCodes(protectionDevice)));
						ProtectionDevice.appendChild(ProtectionDeviceCode);
						ProtectionDevices.appendChild(ProtectionDevice);
					}
				}
				}
				//####################################################################################
			    //Adding Occasional Driver usage to Vehicle 1   
				//####################################################################################
				
		        ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
		        int OccassionalDriversCount=ExcelUtilities.occassionalCount(TC_ID, 1, log);
		        System.out.println("Number of Occasional Drivers of Vehicle One: "+OccassionalDriversCount);
		        log.info("Number of Occasional Drivers of Vehicle One: "+OccassionalDriversCount);
		        if(OccassionalDriversCount>=1){
		        for(int x=0;x<OccassionalDriversCount;x++){    		
						Node VehicleDriverUsages = doc.getElementsByTagName("VehicleDriverUsages").item(0);
						Element VehicleDriverUsageElement=doc.createElement("VehicleDriverUsage");
						VehicleDriverUsages.appendChild(VehicleDriverUsageElement);
						Node VehicleDriverUsageOc = doc.getElementsByTagName("VehicleDriverUsage").item(0);			
						NodeList list1 = VehicleDriverUsageOc.getChildNodes();
						for (int i = 1; i < list1.getLength(); i++) {
			                childNode = list1.item(i);
			                childNodeList=childNode.getChildNodes();
			                String nodaName=childNode.getNodeName();
			                if(!nodaName.equals("#text")){
			                	Element newElement=doc.createElement(nodaName);
			                	newElement.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodaName, TC_ID, x+1, runManagerPath,x+1,1, VehicleDriverUsageOc.getNodeName(), log, loopToStart, doc)));
			                	VehicleDriverUsageElement.appendChild(newElement);
			                	if(childNodeList.getLength()>1){
			                		for(int k=0;k<childNodeList.getLength();k++){
			                			childNode1 = childNodeList.item(k);
			                			childNodeList2=childNode1.getChildNodes();
			                		    nodeName1=childNode1.getNodeName();
			                		if(!nodeName1.equals("#text")){
			                			Element newElement1=doc.createElement(nodeName1);
			                			newElement1.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodeName1, TC_ID, x+1, runManagerPath,x+1,1, nodaName, log, loopToStart, doc)));
			                			newElement.appendChild(newElement1);
			                			if(childNodeList2.getLength()>1){
			                				for(int l=0;l<childNodeList2.getLength();l++){
			                        			childNode2 = childNodeList2.item(l);
			                        		    nodeName2=childNode2.getNodeName();
			                        		if(!nodeName2.equals("#text")){
			                        			if(!nodeName2.equals("Coverage")){
			                        			Element newElement2=doc.createElement(nodeName2);                        			
			                                    newElement2.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodeName2, TC_ID, x+1, runManagerPath,x+1,1, nodeName1, log, loopToStart, doc)));
			                                    newElement1.appendChild(newElement2);}
			                        		}
			                				}
			                			}
			                		}
			                		}
			                	}                	 
			                }	                
						}
		        }
		        }
		        
		      //####################################################################################
		      //Adding VehicleDriverUsage to More than one vehicles 
		      //Reading all the elements of vehicleDriverUsage from sample request and adding the same in the xml for multiple times
		      //####################################################################################
		        
		        if(VehicleCount>1){
		        	for(int x=1;x<VehicleCount;x++){ 
		        		 ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
		        		 prevOccCount=0;prevOccCount1=0;
		        		 //======================================================================
		        		 //Changes
		        		 //======================================================================
		        		 for(int l=1;l<=x;l++){
		        			 prevOccCount=ExcelUtilities.occassionalCount(TC_ID, l, log);
		        			 prevOccCount1=prevOccCount+prevOccCount1;
		        		 }
		 		        int mulOccassionalDriversPrevCount=prevOccCount1;
		 		        System.out.println("mulOccassionalDriversPrevCount: "+mulOccassionalDriversPrevCount);
						Node VehicleDriverUsages = doc.getElementsByTagName("VehicleDriverUsages").item(0);
						Element VehicleDriverUsageElement=doc.createElement("VehicleDriverUsage");
						VehicleDriverUsages.appendChild(VehicleDriverUsageElement);
						Node VehicleDriverUsageOc = doc.getElementsByTagName("VehicleDriverUsage").item(0);			
						NodeList list1 = VehicleDriverUsageOc.getChildNodes();
						for (int i = 1; i < list1.getLength(); i++) {
			                childNode = list1.item(i);
			                childNodeList=childNode.getChildNodes();
			                String nodaName=childNode.getNodeName();
			                if(!nodaName.equals("#text")){
			                	Element newElement=doc.createElement(nodaName);
			                	newElement.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodaName, TC_ID, x+1, runManagerPath,0,x+1, VehicleDriverUsageOc.getNodeName(), log, loopToStart, doc)));
			                	VehicleDriverUsageElement.appendChild(newElement);
			                	if(childNodeList.getLength()>1){
			                		for(int k=0;k<childNodeList.getLength();k++){
			                			childNode1 = childNodeList.item(k);
			                			childNodeList2=childNode1.getChildNodes();
			                		    nodeName1=childNode1.getNodeName();
			                		if(!nodeName1.equals("#text")){
			                			Element newElement1=doc.createElement(nodeName1);
			                			newElement1.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodeName1, TC_ID, x+1, runManagerPath,0,x+1, nodaName, log, loopToStart, doc)));
			                			newElement.appendChild(newElement1);
			                			if(childNodeList2.getLength()>1){
			                				for(int l=0;l<childNodeList2.getLength();l++){
			                        			childNode2 = childNodeList2.item(l);
			                        		    nodeName2=childNode2.getNodeName();
			                        		if(!nodeName2.equals("#text")){
			                        			if(!nodeName2.equals("Coverage")){
			                        			Element newElement2=doc.createElement(nodeName2);
			                        			newElement2.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodeName2, TC_ID, x+1, runManagerPath,0,x+1, nodeName1, log, loopToStart, doc)));
			                        			newElement1.appendChild(newElement2);}
			                        		}
			                				}
			                			}
			                		}
			                		}
			                	}                	 
			                }	                
						}
						
				        ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
				        int mulOccassionalDriversCount=ExcelUtilities.occassionalCount(TC_ID, x+1, log);
				        System.out.println("multiple Occassional Drivers Count: "+mulOccassionalDriversCount);
				        log.info("multiple Occassional Drivers Count: "+mulOccassionalDriversCount);
		                if(mulOccassionalDriversCount>=1){
		                for(int y=1;y<=mulOccassionalDriversCount;y++){
		                	Node VehicleDriverUsagesOc = doc.getElementsByTagName("VehicleDriverUsages").item(0);
		    				Element VehicleDriverUsageElementOc=doc.createElement("VehicleDriverUsage");
		    				VehicleDriverUsagesOc.appendChild(VehicleDriverUsageElementOc);
		    				Node VehicleDriverUsageOcc = doc.getElementsByTagName("VehicleDriverUsage").item(0);			
		    				NodeList list = VehicleDriverUsageOcc.getChildNodes();
		    				for (int i = 1; i < list.getLength(); i++) {
		    	                childNode = list.item(i);
		    	                childNodeList=childNode.getChildNodes();
		    	                String nodaName=childNode.getNodeName();
		    	                if(!nodaName.equals("#text")){
		    	                	Element newElement=doc.createElement(nodaName);
		    	                	newElement.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodaName, TC_ID, y+1, runManagerPath,y,x+1, VehicleDriverUsageOcc.getNodeName(), log, loopToStart, doc)));
		    	                	VehicleDriverUsageElementOc.appendChild(newElement);
		    	                	if(childNodeList.getLength()>1){
		    	                		for(int k=0;k<childNodeList.getLength();k++){
		    	                			childNode1 = childNodeList.item(k);
		    	                			childNodeList2=childNode1.getChildNodes();
		    	                		    nodeName1=childNode1.getNodeName();
		    	                		if(!nodeName1.equals("#text")){
		    	                			Element newElement1=doc.createElement(nodeName1);
		    	                			newElement1.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodeName1, TC_ID, y+1, runManagerPath,y,x+1, nodaName, log, loopToStart, doc)));
		    	                			newElement.appendChild(newElement1);
		    	                			if(childNodeList2.getLength()>1){
		    	                				for(int l=0;l<childNodeList2.getLength();l++){
		    	                        			childNode2 = childNodeList2.item(l);
		    	                        		    nodeName2=childNode2.getNodeName();
		    	                        		if(!nodeName2.equals("#text")){
		    	                        			if(!nodeName2.equals("Coverage")){
		    	                        			Element newElement2=doc.createElement(nodeName2);
		    	                        			newElement2.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodeName2, TC_ID, y+1, runManagerPath,y,x+1, nodeName1, log, loopToStart, doc)));
		    	                        			newElement1.appendChild(newElement2);}
		    	                        		}
		    	                				}
		    	                			}
		    	                		}
		    	                		}
		    	                	}                	 
		    	                }
		    				}
		                }
		                }
		        }
		        }
		        
		        
		      //####################################################################################	
			  // Driver Information elements add to Policy	
		      //####################################################################################
		        
				ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
				int DriverCount=ExcelUtilities.getCount(TC_ID, log, loopToStart);
				System.out.println("Number of Drivers "+DriverCount);
				log.info("Number of Drivers "+DriverCount);
				if(DriverCount>=1){
					for(int j=0;j<DriverCount;j++){
						counterCount=0;policiesCount=0;
						Document driverInfoDoc = docBuilder.parse(DriverInformationFilepath);
						Node DriverInformationsMul = doc.getElementsByTagName("DriverInformations").item(0);
						Element DriverInformationsElementMul=doc.createElement("DriverInformation");
						DriverInformationsMul.appendChild(DriverInformationsElementMul);
						Node DriverInformationNode = driverInfoDoc.getElementsByTagName("DriverInformation").item(0);			
						NodeList list = DriverInformationNode.getChildNodes();
						for (int i = 1; i < list.getLength(); i++) {
			                childNode = list.item(i);	                
			                String nodaName=childNode.getNodeName();
			                if(!nodaName.equals("#text")){
			                	Element newElement=doc.createElement(nodaName);
			                	childNodeList=childNode.getChildNodes();
			                	newElement.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodaName, TC_ID, j+1, runManagerPath,counterCount,0, DriverInformationNode.getNodeName(), log, loopToStart, doc)));
			                	DriverInformationsElementMul.appendChild(newElement);
			                	if(childNodeList.getLength()>1){
			                		for(int k=0;k<childNodeList.getLength();k++){
			                			childNode1 = childNodeList.item(k);
			                			childNodeList2=childNode1.getChildNodes();
			                		    nodeName1=childNode1.getNodeName();
			                		if(!nodeName1.equals("#text")){
			                			Element newElement1=doc.createElement(nodeName1);
			                			newElement1.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodeName1, TC_ID, j+1, runManagerPath,counterCount,0, nodaName, log, loopToStart, doc)));
			                			newElement.appendChild(newElement1);
			                			if(childNodeList2.getLength()>1){
			                				for(int l=0;l<childNodeList2.getLength();l++){
			                        			childNode2 = childNodeList2.item(l);
			                        		    nodeName2=childNode2.getNodeName();
			                        		if(!nodeName2.equals("#text")){	
			                        			if(!nodeName2.equals("PolicyClaim")){
			                        				if(!nodeName2.equals("Conviction")&&!nodeName2.equals("LicenseSuspension")){
			                        			Element newElement2=doc.createElement(nodeName2);
			                        			childNodeList3=childNode2.getChildNodes();
			                        			newElement2.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodeName2, TC_ID, j+1, runManagerPath,counterCount,0, nodeName1, log, loopToStart, doc)));
			                        			newElement1.appendChild(newElement2);
			                        			if(childNodeList3.getLength()>1){
			    	                				for(int m=0;m<childNodeList3.getLength();m++){
			    	                        			childNode3 = childNodeList3.item(m);
			    	                        		    nodeName3=childNode3.getNodeName();
			    	                        		if(!nodeName3.equals("#text")){
			    	                        			if(!nodeName3.equals("PolicyClaim")){
			    	                        				if(!nodeName3.equals("Conviction")&&!nodeName3.equals("LicenseSuspension")){
			    	                        			Element newElement3=doc.createElement(nodeName3);
			    	                        			childNodeList4=childNode3.getChildNodes();
			    	                        			newElement3.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodeName3, TC_ID, j+1, runManagerPath,counterCount,0, nodeName2, log, loopToStart, doc)));
			    	                        			newElement2.appendChild(newElement3);
			    	                        		}
			    	                        		}
			    	                        		}
			    	                				}
			    	                			}
			                        		}
			                        		}
			                        		}
			                				}
			                			}	                		
			                		}
			                		}
			                	} 
					}
				}
						
				//####################################################################################
				//Adding claims to drivers
				//####################################################################################		
						
						ExcelUtilities.setExcelFile(runManagerPath, "Claims_TD", log);
						int claimCount2=ExcelUtilities.getClaimCount(TC_ID, j+1, 8, log);
					    System.out.println("Number of Claims for Driver "+(j+1)+" is: "+claimCount2);
					    log.info("Number of Claims for Driver "+(j+1)+" is: "+claimCount2);
					    if(claimCount2>0){
					    	for(int k=0;k<claimCount2;k++){
					    	Node claims = doc.getElementsByTagName("PolicyClaims").item(j);
							Element claimElement=doc.createElement("PolicyClaim");
							claims.appendChild(claimElement);
					    	Document doc1 = docBuilder.parse(claimFilepath);
					    	claim=doc1.getElementsByTagName("PolicyClaim").item(0);
					    	childNodeList=claim.getChildNodes();
					    	for(int l=0;l<childNodeList.getLength();l++){
					    		node=childNodeList.item(l);
					    		if(!node.getNodeName().equals("#text")){
					    			nodeName=node.getNodeName();
					    			Element claimChildElement=doc.createElement(nodeName);
					    			claimChildElement.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodeName, TC_ID, k+1, runManagerPath,j+1,k, claimElement.getNodeName(), log, loopToStart, doc)));
					    			claimElement.appendChild(claimChildElement);
					    			childNodeList1=node.getChildNodes();
					    			if(childNodeList1.getLength()>1){
					    				for(int m=0;m<childNodeList1.getLength();m++){
					    					childNode=childNodeList1.item(m);
					    					if(!childNode.getNodeName().equals("#text")){
					    						nodeName1=childNode.getNodeName();
								    			Element claimChildElement1=doc.createElement(nodeName1);
								    			claimChildElement1.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodeName1, TC_ID, k+1, runManagerPath,j+1,k, nodeName, log, loopToStart, doc)));
								    			claimChildElement.appendChild(claimChildElement1);
								    			childNodeList2=childNode.getChildNodes();
								    			if(childNodeList2.getLength()>1){
								    				for(int n=0;n<childNodeList2.getLength();n++){
								    					childNode1=childNodeList2.item(n);
								    					if(!childNode1.getNodeName().equals("#text")){
								    						nodeName2=childNode1.getNodeName();
											    			Element claimChildElement2=doc.createElement(nodeName2);
											    			claimChildElement2.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodeName2, TC_ID, k+1, runManagerPath,j+1,k, nodeName1, log, loopToStart, doc)));
											    			claimChildElement1.appendChild(claimChildElement2);
								    					}
								    				}
								    			}
					    					}
					    				}			    				
					    			}
					    		}
					    	}
					    }
					    }
					    
					 //######################################################################
					 //Convictions add to drivers
					 //######################################################################
					    
					    ExcelUtilities.setExcelFile(runManagerPath, "Convictions_TD", log);
						int convicCount2=ExcelUtilities.getConvicCount(TC_ID, j+1, ExcelUtilities.getColumnNum("Driver", colNum), log, "Convic_Suspen_type", "Conviction");
					    System.out.println("Number of Convictions for Driver "+(j+1)+" is: "+convicCount2);
					    log.info("Number of Convictions for Driver "+(j+1)+" is: "+convicCount2);
					    if(convicCount2>0){
					    	for(int k=0;k<convicCount2;k++){
					    	Node convictions = doc.getElementsByTagName("Convictions").item(j);
							Element convictionElement=doc.createElement("Conviction");
							convictions.appendChild(convictionElement);
					    	Document doc2 = docBuilder.parse(convictionFilepath);
					    	conviction=doc2.getElementsByTagName("Conviction").item(0);
					    	childNodeList=conviction.getChildNodes();
					    	for(int i=0;i<childNodeList.getLength();i++){
					    		node=childNodeList.item(i);
					    		if(!node.getNodeName().equals("#text")){
					    			nodeName=node.getNodeName();
					    			Element convictionChildElement=doc.createElement(nodeName);
					    			convictionChildElement.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodeName, TC_ID, k+1, runManagerPath,j+1,k, convictionElement.getNodeName(), log, loopToStart, doc)));
					    			convictionElement.appendChild(convictionChildElement);
					    		}			    		
					    	}
						    }
					    }
					    
					 //#####################################################################
					 //License Suspension add to drivers
					 //#####################################################################   
					    
					    ExcelUtilities.setExcelFile(runManagerPath, "Convictions_TD", log);
						int LicSusCount=ExcelUtilities.getConvicCount(TC_ID, j+1, ExcelUtilities.getColumnNum("Driver", colNum), log, "Convic_Suspen_type", "Suspension");
					    System.out.println("Number of Suspensions for Driver "+(j+1)+" is: "+LicSusCount);
					    log.info("Number of Suspensions for Driver "+(j+1)+" is: "+LicSusCount);
					    if(LicSusCount>0){
					    	for(int k=0;k<LicSusCount;k++){
					    	Node LicenseSuspensions = doc.getElementsByTagName("LicenseSuspensions").item(j);
							Element LicenseSuspensionElement=doc.createElement("LicenseSuspension");
							LicenseSuspensions.appendChild(LicenseSuspensionElement);
					    	Document doc2 = docBuilder.parse(LicenseSuspensionPath);
					    	LicenseSuspension=doc2.getElementsByTagName("LicenseSuspension").item(0);
					    	childNodeList=LicenseSuspension.getChildNodes();
					    	for(int i=0;i<childNodeList.getLength();i++){
					    		node=childNodeList.item(i);
					    		if(!node.getNodeName().equals("#text")){
					    			nodeName=node.getNodeName();
					    			Element LicenseSuspensionChildElement=doc.createElement(nodeName);
					    			LicenseSuspensionChildElement.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(nodeName, TC_ID, k+1, runManagerPath,j+1,k, LicenseSuspensionElement.getNodeName(), log, loopToStart, doc)));
					    			LicenseSuspensionElement.appendChild(LicenseSuspensionChildElement);
					    		}			    		
					    	}
						    }
					    }
					    
					  //#####################################################################
					    
					}		
				}
				
			//###############################################################################
			//Locations Add
			//###############################################################################
				
				Document doc1=docBuilder.parse(LocationPath);
				Node Locations=doc.getElementsByTagName("LocationList").item(0);
				ExcelUtilities.setExcelFile(runManagerPath, "Other_Required_Data", log);
				colNum=ExcelUtilities.getColNum(log);
				int LocationCount=ExcelUtilities.getCount(TC_ID, log, loopToStart);
				for(int i=0;i<LocationCount;i++){			
					ExcelUtilities.setExcelFile(runManagerPath, "Other_Required_Data", log);
					String PriorInsurance= ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Clt_Home_Main_Loc",colNum),TC_ID,i+1, log, loopToStart);
					if(!PriorInsurance.equals("")){
						Element Location=doc.createElement("Location");
						Locations.appendChild(Location);
					node=doc1.getElementsByTagName("Location").item(0);
					childNodeList=node.getChildNodes();
					for(int j=0;j<childNodeList.getLength();j++){
						childNode=childNodeList.item(j);
						if(!childNode.getNodeName().equals("#text")){
							Element newEl=doc.createElement(childNode.getNodeName());
							newEl.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(childNode.getNodeName(), TC_ID, i+1, runManagerPath,i+1,i, node.getNodeName(), log, loopToStart, doc)));
							Location.appendChild(newEl);
							childNodeList1=childNode.getChildNodes();
							if(childNodeList1.getLength()>0){
								for(int k=0;k<childNodeList1.getLength();k++){
									childNode1=childNodeList1.item(k);
									if(!childNode1.getNodeName().equals("#text")){
										Element newEl1=doc.createElement(childNode1.getNodeName());
										newEl1.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(childNode1.getNodeName(), TC_ID, i+1, runManagerPath,i+1,i, childNode.getNodeName(), log, loopToStart, doc)));
										newEl.appendChild(newEl1);
									}
								}
							}
						}
					}
				}
				}
				
			  //#########################################################################	
			  //Adding Conditional elements to request
			  //#########################################################################
			  
				//==============================================================
				//Original Creation Date
				//==============================================================
				ExcelUtilities.setExcelFile(runManagerPath, "Policy_TD", log);
			    colNum=ExcelUtilities.getColNum(log);
			    String originalCreationDate = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("ClientSinceDate_Auto", colNum), TC_ID, 1, log, loopToStart);
			    if(!(originalCreationDate.equalsIgnoreCase(""))){
			    	RandomMethods.addConditionalElementBefore(doc, "EndDate", "OriginalCreationDate", RandomMethods.dateConvert(originalCreationDate, log), 1);
			    }
				
			    
			  //==============================================================
				//Add conditional PurchaseDate Date
				//==============================================================
			    for (int k=0;k<VehicleCount;k++)
				{
			    	ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					colNum = ExcelUtilities.getColNum(log);
					//String PurchaseDate = RandomMethods.dateConvert(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_PPA_PurchaseDate", colNum),TC_ID, k+1, log, loopToStart),log)+"-05:00";
					String PurchaseDate = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_PPA_PurchaseDate", colNum),TC_ID, k+1, log, loopToStart);
					if(!PurchaseDate.equalsIgnoreCase(""))
					{
						String PurchaseDateValue = RandomMethods.dateConvert(PurchaseDate, log)+"-05:00";
						RandomMethods.addConditionalElementBefore(doc, "PurchasePrice", "PurchaseDate", PurchaseDateValue, k+1);
					}
					
			
				}
			    
			    
			    
			  //#########################################################################
			  //Vehicle level Coverages And Endorsement Add	
			  //#########################################################################
				
				ExcelUtilities.setExcelFile(runManagerPath, "Policy_TD", log);
			    colNum=ExcelUtilities.getColNum(log);
			    String covVehDate=RandomMethods.dateConvert(ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("EffectiveDate", colNum), TC_ID, 1, log, loopToStart), log);		
				
				for(int i=11;i<=33;i++){
					 covVehName=propertiesCov.getPropertyValue(Integer.toString(i));				
					if(!(covVehName.equals(" ")) && !(covVehName.equals(null))){
						String covVehFieldName=propertiesCovField.getPropertyValue(covVehName);
						String covVehLimit=propertiesCovField.getPropertyValue(covVehName+"_Limit");
						String covVehDeduct=propertiesCovField.getPropertyValue(covVehName+"_Deduct");
						String covVehLimitVal=covVehLimit;String covVehDeductVal=covVehDeduct;
						ExcelUtilities.setExcelFile(runManagerPath, "Coverages_TD", log);
						colNum=ExcelUtilities.getColNum(log);
						int covCount=ExcelUtilities.getCount(TC_ID, log, loopToStart);
						for(int j=1;j<=covCount;j++){
							if(!covVehName.equals("5")&&!covVehName.equals("40")/*&&!covVehName.equals("6A")*/&&!covVehName.equals("16")){
								
								ExcelUtilities.setExcelFile(runManagerPath, "Coverages_TD", log);
								String covVehStatus=ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum(covVehFieldName, colNum), TC_ID, j, log, loopToStart);
								
								if(covVehStatus.equalsIgnoreCase("Yes")){
								if(!(covVehLimit.equals("0"))){
									covVehLimitVal=ExcelUtilities.getMultiCellDataInt(ExcelUtilities.getColumnNum(covVehLimit, colNum), TC_ID, j, log, loopToStart);
								}
								if(!(covVehDeduct.equals("0"))){
									covVehDeductVal=ExcelUtilities.getMultiCellDataInt(ExcelUtilities.getColumnNum(covVehDeduct, colNum), TC_ID, j, log, loopToStart);
								}
								
								RandomMethods.addVehicleLevelCoverages(doc, j, covVehName, covVehLimitVal, covVehDeductVal, propertiesCovTypes.getPropertyValue(covVehName), covVehDate);
								System.out.println(covVehName+" has been added to Vehicle "+j+" .......");
								}
							}
							else if(covVehName.equals("16")){
								ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
								int vehColNum=ExcelUtilities.getColNum(log);
								String VehicleStorage=ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_VehicleStorage", vehColNum), TC_ID, j, log, loopToStart);
								if(VehicleStorage.equalsIgnoreCase("Yes")){
									int NbDaysStorage=Integer.parseInt(ExcelUtilities.getMultiCellDataInt(ExcelUtilities.getColumnNum("VD_PPA_NbDaysStorage", vehColNum), TC_ID, j, log, loopToStart));
									if(NbDaysStorage>44){
										RandomMethods.addVehicleLevelCoverages(doc, j, covVehName, covVehLimit, covVehDeduct, propertiesCovTypes.getPropertyValue(covVehName), covVehDate);
										System.out.println(covVehName+" has been added to Vehicle "+j+" .......");
									}
								}						
							}
							else if(covVehName.equals("5")){
								ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
								int vehColNum=ExcelUtilities.getColNum(log);
								String PropertyType=ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_PropertyType", vehColNum), TC_ID, j, log, loopToStart);
								if(PropertyType.toUpperCase().equals("LEASED")||PropertyType.toUpperCase().equals("FINANCED AND LEASED")){
									
										RandomMethods.addVehicleLevelCoverages(doc, j, covVehName, covVehLimit, covVehDeduct, propertiesCovTypes.getPropertyValue(covVehName), covVehDate);
										System.out.println(covVehName+" has been added to Vehicle "+j+" .......");
								}						
							}
							/*else if(covVehName.equals("6A")){
								ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
								int vehColNum=ExcelUtilities.getColNum(log);
								String passengerForCompensation=ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_PPA_Ouse_Passenger", vehColNum), TC_ID, j, log, loopToStart);
								if(passengerForCompensation.toUpperCase().equals("YES")){							
										RandomMethods.addVehicleLevelCoverages(doc, j, covVehName, covVehLimit, covVehDeduct, propertiesCovTypes.getPropertyValue(covVehName), covVehDate);
										System.out.println(covVehName+" has been added to Vehicle "+j+" .......");
								}						
							}*/
							else if(covVehName.equals("40")){
								ExcelUtilities.setExcelFile(runManagerPath, "Coverages_TD", log);
								int covColNum=ExcelUtilities.getColNum(log);
								String cov_19=ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Acov_19", covColNum), TC_ID, j, log, loopToStart);
								String cov_19A=ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Acov_19A", covColNum), TC_ID, j, log, loopToStart);
								//String Cov_40=ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Cov_40", covColNum), TC_ID, j, log, loopToStart);
								if(cov_19.toUpperCase().equals("YES")||cov_19A.toUpperCase().equals("YES")/*||Cov_40.toUpperCase().equals("YES")*/){							
										RandomMethods.addVehicleLevelCoverages(doc, j, covVehName, covVehLimit, covVehDeduct, propertiesCovTypes.getPropertyValue(covVehName), covVehDate);
										System.out.println(covVehName+" has been added to Vehicle "+j+" .......");
								}						
							}
						}
					}
				}
				
			
			//###############################################################################
			//Adding UBI Information under Vehicle Driver Coverages
			//###############################################################################	
			
				for(int i=1;i<=DriverCount;i++){
					int driverNumber;
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
				    colNum=ExcelUtilities.getColNum(log);
				    String UBIStatus=ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("UBIDriverIsActive", colNum), TC_ID, i, log, loopToStart);
				    if(UBIStatus.equalsIgnoreCase("Yes")){	
					Node VehicleDriverUsages=doc.getElementsByTagName("VehicleDriverUsages").item(0);
					NodeList VehicleDriverUsagesChilds=VehicleDriverUsages.getChildNodes();
					for(int j=0;j<VehicleDriverUsagesChilds.getLength();j++){
						Node VehicleDriverUsagesChild=VehicleDriverUsagesChilds.item(j);
						if(VehicleDriverUsagesChild.getNodeName().equals("VehicleDriverUsage")){
							NodeList VehicleDriverUsageChild = VehicleDriverUsagesChild.getChildNodes();
							for(int k=0;k<VehicleDriverUsageChild.getLength();k++){				
								Node VehicleDriverUsageChildNode=VehicleDriverUsageChild.item(k);
								if(VehicleDriverUsageChildNode.getNodeName().equals("DriverNumber")){
									driverNumber=Integer.parseInt(VehicleDriverUsageChildNode.getTextContent());
									//break;
									if(driverNumber==i){
										Document doc5=docBuilder.parse(UBIFilepath);
										childNodeList=VehicleDriverUsagesChild.getChildNodes();
										for(int l=0;l<childNodeList.getLength();l++){
											childNode=childNodeList.item(l);
											if(childNode.getNodeName().equals("UBIVehicleDriverDiscount")){
										//Node UBIVehicleDriverDiscountNode=doc.getElementsByTagName("UBIVehicleDriverDiscount").item(j);
										node=doc5.getElementsByTagName("UBIVehicleDriverDiscount").item(0);
										list=node.getChildNodes();
										for(int m=0;m<list.getLength();m++){
											childNode1=list.item(m);
											if(!(childNode1.getNodeName().equalsIgnoreCase("#text"))){
												Element newEl=doc.createElement(childNode1.getNodeName());
												newEl.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(childNode1.getNodeName(), TC_ID, i, runManagerPath,i,i, node.getNodeName(), log, loopToStart, doc)));
												childNode.appendChild(newEl);
											}									
										}
										}
									}
									}
								}						
							}					
						}
					}
				}
				}
				
				//###############################################################################
				// Add Driver Counters
				//###############################################################################	
				
				ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
				colNum=ExcelUtilities.getColNum(log);
				String VehicleType=ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("VD_VehicleType",colNum), TC_ID, 1, log, loopToStart);
				//doc = docBuilder.parse(samplePathDirectory+"\\"+VehicleType+".xml");
				String DriverCountersPath=samplePathDirectory+"\\"+VehicleType+"DriverExperiencesCountersSample.xml";
				
				String tagName="DriverExperiences";
				RandomMethods.cloneXML(docBuilder,doc,DriverCountersPath,node,tagName);
				System.out.println("DriverExperiences counters added ");
				
				//###############################################################################
				// Extra License add for MC
				//###############################################################################
				for(int i=1;i<=DriverCount;i++){
					Document MCLicenseDoc=docBuilder.parse(MCLicense);
					ExcelUtilities.setExcelFile(runManagerPath, "Driver_TD", log);
				    colNum=ExcelUtilities.getColNum(log);
				    String MCLicenseStaus=ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("MCLicense", colNum), TC_ID, i, log, loopToStart);
				    if(MCLicenseStaus.equalsIgnoreCase("Yes")){
				    	Node driverLicenses=doc.getElementsByTagName("Licenses").item(i-1);
				    	Element License = doc.createElement("License");
				    	driverLicenses.appendChild(License);
				    	Node LicenseNode = MCLicenseDoc.getElementsByTagName("License").item(0);
				    	list = LicenseNode.getChildNodes();
				    	for(int j=0;j<list.getLength();j++){
				    		childNode = list.item(j);
				    		if(!(childNode.getNodeName().equalsIgnoreCase("#text"))){
				    			Element newEl=doc.createElement(childNode.getNodeName());
								newEl.appendChild(doc.createTextNode(DataMappingAlberta.createElementDataFetch(childNode.getNodeName(), TC_ID, i, runManagerPath,i,i, "MotorCycle", log, loopToStart, doc)));
								License.appendChild(newEl);
				    		}				    		
				    	}				    	
				    }
				}
				
				
				
				
				
				
				//###############################################################################
				// Add AutomobilePolicyCounters Counters
				//###############################################################################	
				tagName="AutomobilePolicyCounters";
				RandomMethods.cloneXML(docBuilder,doc,AutomobilePolicyCountersPath,node,tagName);
				System.out.println("AutomobilePolicyCounters counters added ");
				
				//###############################################################################
				// Add DiscountSurcharges Counters as per Vehicle type
				//###############################################################################		
				
				tagName="DiscountSurcharges";
				String SamplePath=samplePathDirectory+"\\DiscountSurchargesCounters.xml";
				RandomMethods.cloneXML(docBuilder,doc,SamplePath,node,tagName);
				System.out.println("DiscountSurcharges counters added ");
				
				//###############################################################################
				// Add DriverCounters
				//###############################################################################		
			
				tagName="DriverCounters";
				RandomMethods.cloneXML(docBuilder,doc,DriverCountersSamplePath,node,tagName);
				System.out.println("DriverCounters counters added ");
				
				//###############################################################################
				// Add Territory Values
				//###############################################################################		
				int Territory=doc.getElementsByTagName("Territory").getLength();
				String Value;
				/*<RiskPostalCodeFSA>T8N</RiskPostalCodeFSA>
				<RiskPostalCodeLDU>6T3</RiskPostalCodeLDU>*/
				ExcelUtilities.setExcelFile(runManagerPath, "Customer_TD", log);
				colNum = ExcelUtilities.getColNum(log);
				String RiskPostalCodeFSA = ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("Postal Code", colNum),TC_ID, 1, log, loopToStart);
				for(int t=0;t<Territory;t++)
				{
					Node TerritoryValue=doc.getElementsByTagName("Territory").item(t);
					Element RiskPostalCodeFSATag=doc.createElement("RiskPostalCodeFSA");
					Value= RandomMethods.postalCode("RiskPostalCodeFSA", RiskPostalCodeFSA, log);
					RiskPostalCodeFSATag.appendChild(doc.createTextNode(Value));
					Element RiskPostalCodeLDUTag=doc.createElement("RiskPostalCodeLDU");
					Value= RandomMethods.postalCode("RiskPostalCodeLDU", RiskPostalCodeFSA, log);
					RiskPostalCodeLDUTag.appendChild(doc.createTextNode(Value));
					TerritoryValue.appendChild(RiskPostalCodeFSATag);
					TerritoryValue.appendChild(RiskPostalCodeLDUTag);
					
					
				}
				//System.out.println("Territory Value added");
				
				//###############################################################################
				//  Update R10 values
				//###############################################################################						
				for (int k=0;k<VehicleCount;k++)
				{
				ExcelUtilities.setExcelFile(runManagerPath, "Coverages_TD", log);
				//int covCount=ExcelUtilities.getCount(TC_ID, log, loopToStart);
				
					colNum=ExcelUtilities.getColNum(log);
				    String R10value=ExcelUtilities.getMultiCellDataString(ExcelUtilities.getColumnNum("R10_Status", colNum), TC_ID, k+1, log, loopToStart);
				    
					
				RandomMethods.addR9Discounts(doc,k+1,R10value);
				
				}
				
			//###############################################################################
			// write the content into xml file
			//###############################################################################	
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				doc.normalizeDocument();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(newFilePath));
				transformer.transform(source, result);
				
			//###############################################################################
	
	
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
	


}
