package ReusableComponents;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class RestFullGet {

	private final String USER_AGENT = "Mozilla/5.0";

	public static String ViccYearSearch, ViccDetailSearch, ViccCode, accidentBenefitFinalModificationCd,
			collisionFinalModificationCd, comprehensiveFinalModificationCd, directCompensationFinalModificationCd,
			effectiveDate, authSwithMode, authKey;
	public static String ABDisTDILossCostRatingGroup, ABMedTDILossCostRatingGroup, BITDILossCostRatingGroup,
			CMPTDILossCostRatingGroup, COLTDILossCostRatingGroup, DCTDILossCostRatingGroup;
	public static int ViccCodeLength;

	public static void main(String[] args) {
		try {
			Logger log = Logger.getLogger("RestFullGet");
			ReadPropFile properties = new ReadPropFile("Environment.properties");
			File directory = new File(properties.getPropertyValue("directory"));
			String runManagerPath = directory.getAbsolutePath() + properties.getPropertyValue("runManagerPath");
			String rateGroupPath = directory.getAbsolutePath() + properties.getPropertyValue("ClaimConvicCodePath");
			ExcelUtilities.setExcelFile(runManagerPath, "Environment", log);
			try {
				ViccYearSearch = ExcelUtilities.getTcId(2, 1, log);
				ViccDetailSearch = ExcelUtilities.getTcId(3, 1, log);
				ExcelUtilities.setExcelFile(runManagerPath, "Pingfed", log);
				authSwithMode = ExcelUtilities.getTcId(2, 1, log);
				System.out.println("SSO Switch is in " + authSwithMode + " state......................");
				log.info("SSO Switch is in " + authSwithMode + " state......................");
				if (authSwithMode.toUpperCase().equals("ON")) {
					System.out.println("Fetching Authentication key..............");
					log.info("Fetching Authentication key..............");
					authKey = ExcelUtilities.getTcId(2, 0, log);
					System.out.println("Key Fetched......");
					log.info("Key Fetched......");
				}

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println(e1);
				log.error("Error: " + e1);
			}
			ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
			int colNum = ExcelUtilities.getColNum(log);
			int loop = ExcelUtilities.getRowNum(log);
			for (int i = 1; i <= loop; i++) {
				try {
					ViccCode = "";
					ViccCodeLength = 0;String IsAntique="";
					String ModelName = (ExcelUtilities.getTcId(i, ExcelUtilities.getColumnNum("VD_Model", colNum), log));
					String[] ModelNamepart = ModelName.split(" ");
					String ModelNamepart1 = ModelNamepart[0];
					String ModelYear = Integer
							.toString(ExcelUtilities.getSubId(i, ExcelUtilities.getColumnNum("VD_ModelYear", colNum), log));
					String vehicleMakeName = ExcelUtilities.getTcId(i, ExcelUtilities.getColumnNum("VD_Make", colNum), log);
					String[] vehicleMakeNamepart = vehicleMakeName.split(" ");
					String vehicleMakeNamepart1 = vehicleMakeNamepart[0];
					String vehicleImport = ExcelUtilities.getTcId(i,
							ExcelUtilities.getColumnNum("VD_PPA_VehicleImported", colNum), log);
					String PPA_Purchase = ExcelUtilities.getTcId(i, ExcelUtilities.getColumnNum("VD_PPA_Purchase", colNum),
							log);
					String testCaseId = ExcelUtilities.getTcId(i, ExcelUtilities.getColumnNum("TC_ID", colNum), log);
					ExcelUtilities.setExcelFile(runManagerPath, "Policy_TD", log);
					int loopPol = ExcelUtilities.getRowNum(log);
					for (int j = 1; j < loopPol; j++) {
						String testCaseIdPol = ExcelUtilities.getTcId(j, ExcelUtilities.getColumnNum("TC_ID", colNum), log);
						if (testCaseIdPol.equals(testCaseId)) {
							effectiveDate = ExcelUtilities.getTcId(j, ExcelUtilities.getColumnNum("EffectiveDate", colNum),
									log);
							break;
						}
					}

					String ConvertedDate = RandomMethods.dateConvert(effectiveDate, log);

					ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
					if (!ModelYear.equals("0")) {
						String urlYearSearch = ViccYearSearch + "vehicleTypeCd=AU&vehicleModelYearNum=" + ModelYear
								+ "&vehicleMakeName=" + vehicleMakeNamepart1 + "&vehicleModelName=" + ModelNamepart1
								+ "&languageCd=EN";
						RestFullGet http = new RestFullGet();
						System.out.println("URL 1 - Sending Http GET request");
						log.info("URL 1 - Sending Http GET request");
						log.info("URL for VICC code search: " + urlYearSearch);
						String jsonStringYear = http.sendGet(urlYearSearch, log, authSwithMode, authKey);
						JSONObject jsonObject = new JSONObject(jsonStringYear);
						JSONArray motorVehicle = jsonObject.getJSONArray("motorVehicle");

						for (int j = 0; j < motorVehicle.length(); j++) {

							JSONArray vehicleMakeNameA = motorVehicle.getJSONObject(j).getJSONArray("vehicleMakeModel");
							for (int k = 0; k < vehicleMakeNameA.length(); k++) {
								String vehicleMakeNameToSearch = vehicleMakeNameA.getJSONObject(k)
										.getString("vehicleMakeName");
								String vehicleModelName = vehicleMakeNameA.getJSONObject(k)
										.getString("vehicleModelName");

								if (vehicleMakeNameToSearch.equalsIgnoreCase(vehicleMakeName)
										&& vehicleModelName.equalsIgnoreCase(ModelName)) {
									String viccVehicleCd = motorVehicle.getJSONObject(j).getString("viccVehicleCd");
									ViccCodeLength = viccVehicleCd.length();
									if (viccVehicleCd.substring(0, 1).equals("0")) {
										ViccCode = viccVehicleCd.substring(1, ViccCodeLength);
									} else
										ViccCode = viccVehicleCd;
									String viccExtensionCd = motorVehicle.getJSONObject(j).getString("viccExtensionCd");
									System.out.println(
											"viccVehicleCd: " + viccVehicleCd + " viccExtensionCd: " + viccExtensionCd);
									log.info(
											"viccVehicleCd: " + viccVehicleCd + " viccExtensionCd: " + viccExtensionCd);
									String urlViccSearch = ViccDetailSearch
											+ "vehicleTypeCd=AU&languageCd=EN&viccVehicleCd=" + viccVehicleCd
											+ "&viccExtensionCd=" + viccExtensionCd + "&vehicleModelYearNum="
											+ ModelYear + "&territoryCd=ON&effectiveDt=" + ConvertedDate
											+ "&effectiveDtQual=NewAsset";
									RestFullGet http1 = new RestFullGet();
									System.out.println("URL 2 - Sending Http GET request");
									log.info("URL 2 - Sending Http GET request");
									log.info("URL to be send: " + urlViccSearch);
									String jsonStringVicc = http1.sendGet(urlViccSearch, log, authSwithMode, authKey);
									log.info("json response recieved: " + jsonStringVicc);
									JSONObject jsonObject1 = new JSONObject(jsonStringVicc);
									JSONArray motorVehicleDetail = jsonObject1.getJSONArray("motorVehicleDetail");
									for (int l = 0; l < motorVehicleDetail.length(); l++) {
										JSONObject vehicleSpecificationA = motorVehicleDetail.getJSONObject(l)
												.getJSONObject("vehicleSpecification");
										JSONArray ratingGroup = motorVehicleDetail.getJSONObject(l)
												.getJSONArray("ratingGroup");
										int m = ratingGroup.length() - 1;
										String ratingTableYearNum = Integer
												.toString(ratingGroup.getJSONObject(m).getInt("ratingTableYearNum"));
										accidentBenefitFinalModificationCd = (ratingGroup.getJSONObject(m)
												.getString("accidentBenefitFinalModificationCd"));
										collisionFinalModificationCd = ratingGroup.getJSONObject(m)
												.getString("collisionFinalModificationCd");
										comprehensiveFinalModificationCd = (ratingGroup.getJSONObject(m)
												.getString("comprehensiveFinalModificationCd"));
										directCompensationFinalModificationCd = (ratingGroup.getJSONObject(m)
												.getString("directCompensationFinalModificationCd"));
										ABDisTDILossCostRatingGroup = String.valueOf(ratingGroup.getJSONObject(m)
												.get("accidentBenefitDisabilityLossCodeRateGroupNum"));
										ABMedTDILossCostRatingGroup = String.valueOf(ratingGroup.getJSONObject(m)
												.get("accidentBenefitMedicalLossCodeRateGroupNum"));
										BITDILossCostRatingGroup = String.valueOf(
												ratingGroup.getJSONObject(m).get("bodilyInjuryLossCodeRateGroupNum"));
										CMPTDILossCostRatingGroup = String.valueOf(
												ratingGroup.getJSONObject(m).get("comprehensiveLossCodeRateGroupNum"));
										COLTDILossCostRatingGroup = String.valueOf(
												ratingGroup.getJSONObject(m).get("collisionLossCodeRateGroupNum"));
										DCTDILossCostRatingGroup = String.valueOf(ratingGroup.getJSONObject(m)
												.get("directCompensationLossCodeRateGroupNum"));
										String manufactureRetailSalesPriceAmt = String
												.valueOf(vehicleSpecificationA.get("manufactureRetailSalesPriceAmt"));
										IsAntique="false";
										// =========================================================================
										// Changed Made
										// =========================================================================
										if (accidentBenefitFinalModificationCd.equals("A")
												|| collisionFinalModificationCd.equals("A")
												|| (vehicleImport.equals("Yes") && PPA_Purchase.equals("New"))) {
											String VehicleValue = Integer.toString(ExcelUtilities.getSubId(i,
													ExcelUtilities.getColumnNum("VD_PPA_NB_Value", colNum), log));
											ExcelUtilities.setExcelFile(rateGroupPath, "Vehicle Rate by Value", log);
											int colNumRate = ExcelUtilities.getColNum(log);
											accidentBenefitFinalModificationCd = RandomMethods.getRatingInfo(
													rateGroupPath, ExcelUtilities.getColumnNum("Value AB", colNumRate),
													Integer.parseInt(VehicleValue), log);
											collisionFinalModificationCd = RandomMethods.getRatingInfo(rateGroupPath,
													ExcelUtilities.getColumnNum("Value COL", colNumRate),
													Integer.parseInt(VehicleValue), log);
											comprehensiveFinalModificationCd = RandomMethods.getRatingInfo(
													rateGroupPath, ExcelUtilities.getColumnNum("Value CMP", colNumRate),
													Integer.parseInt(VehicleValue), log);
											directCompensationFinalModificationCd = RandomMethods.getRatingInfo(
													rateGroupPath, ExcelUtilities.getColumnNum("Value DC", colNumRate),
													Integer.parseInt(VehicleValue), log);
											IsAntique="true";
										}
										// =========================================================================
										ExcelUtilities.setExcelFile(runManagerPath, "Vehicle_TD", log);
										ExcelUtilities.setVicc(accidentBenefitFinalModificationCd, i,
												ExcelUtilities.getColumnNum("AccidentBenefit", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(collisionFinalModificationCd, i,
												ExcelUtilities.getColumnNum("Collision", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(comprehensiveFinalModificationCd, i,
												ExcelUtilities.getColumnNum("Comprehensive", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(directCompensationFinalModificationCd, i,
												ExcelUtilities.getColumnNum("ThirdParty", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(ABDisTDILossCostRatingGroup, i,
												ExcelUtilities.getColumnNum("ABDisTDILossCostRatingGroup", colNum),
												"Vehicle_TD", runManagerPath, log);
										ExcelUtilities.setVicc(ABMedTDILossCostRatingGroup, i,
												ExcelUtilities.getColumnNum("ABMedTDILossCostRatingGroup", colNum),
												"Vehicle_TD", runManagerPath, log);
										ExcelUtilities.setVicc(BITDILossCostRatingGroup, i,
												ExcelUtilities.getColumnNum("BITDILossCostRatingGroup", colNum),
												"Vehicle_TD", runManagerPath, log);
										ExcelUtilities.setVicc(CMPTDILossCostRatingGroup, i,
												ExcelUtilities.getColumnNum("CMPTDILossCostRatingGroup", colNum),
												"Vehicle_TD", runManagerPath, log);
										ExcelUtilities.setVicc(COLTDILossCostRatingGroup, i,
												ExcelUtilities.getColumnNum("COLTDILossCostRatingGroup", colNum),
												"Vehicle_TD", runManagerPath, log);
										ExcelUtilities.setVicc(DCTDILossCostRatingGroup, i,
												ExcelUtilities.getColumnNum("DCTDILossCostRatingGroup", colNum),
												"Vehicle_TD", runManagerPath, log);
										ExcelUtilities.setVicc(IsAntique, i,
												ExcelUtilities.getColumnNum("IsAntique", colNum),
												"Vehicle_TD", runManagerPath, log);

										String wheelbaseMeas = String
												.valueOf(vehicleSpecificationA.get("wheelbaseMeas"));
										String bodyStyleCd = String.valueOf((vehicleSpecificationA.get("bodyStyleCd")));
										String audibleAlarmDesc = String
												.valueOf(vehicleSpecificationA.get("audibleAlarmDesc"));
										String cutOffSystemDesc = String
												.valueOf(vehicleSpecificationA.get("cutOffSystemDesc"));
										String securityKeySystemDesc = String
												.valueOf(vehicleSpecificationA.get("securityKeySystemDesc"));
										String ibcApprovedDesc = String
												.valueOf(vehicleSpecificationA.get("ibcApprovedDesc"));
										String tractionControlDesc = String
												.valueOf(vehicleSpecificationA.get("tractionControlDesc"));
										String stabilityDesc = String
												.valueOf(vehicleSpecificationA.get("stabilityDesc"));
										String absDesc = String.valueOf((vehicleSpecificationA.get("absDesc")));
										String engineCylinderCnt = String
												.valueOf(vehicleSpecificationA.get("engineCylinderCnt"));
										String horsepowerMeas = String
												.valueOf(vehicleSpecificationA.get("horsepowerMeas"));
										String sizeCd = String.valueOf((vehicleSpecificationA.get("sizeCd")));
										String generationDesc = String
												.valueOf((vehicleSpecificationA.get("generationDesc")));
										String weightMeas = String.valueOf(vehicleSpecificationA.get("weightMeas"));
										String engineInductionDesc = String
												.valueOf((vehicleSpecificationA.get("engineInductionDesc")));
										String engineFuelDesc = String
												.valueOf((vehicleSpecificationA.get("engineFuelDesc")));
										String engineHybridInd = Boolean
												.toString((vehicleSpecificationA.getBoolean("engineHybridInd")));
										String airbagDesc = String.valueOf((vehicleSpecificationA.get("airbagDesc")));
										String engineDisplacementMeas = String
												.valueOf((vehicleSpecificationA.get("engineDisplacementMeas")));
										ExcelUtilities.setVicc(ViccCode, i, ExcelUtilities.getColumnNum("VICCCode", colNum),
												"Vehicle_TD", runManagerPath, log);
										ExcelUtilities.setVicc(ModelYear, i, ExcelUtilities.getColumnNum("Year", colNum),
												"Vehicle_TD", runManagerPath, log);
										ExcelUtilities.setVicc(bodyStyleCd, i,
												ExcelUtilities.getColumnNum("BodyCode", colNum), "Vehicle_TD",
												runManagerPath, log);
										if (engineDisplacementMeas.equals("null")) {
											ExcelUtilities.setVicc("0", i, ExcelUtilities.getColumnNum("CCNumber", colNum),
													"Vehicle_TD", runManagerPath, log);
										} else
											ExcelUtilities.setVicc(engineDisplacementMeas, i,
													ExcelUtilities.getColumnNum("CCNumber", colNum), "Vehicle_TD",
													runManagerPath, log);
										ExcelUtilities.setVicc(manufactureRetailSalesPriceAmt, i,
												ExcelUtilities.getColumnNum("MarketValue", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(wheelbaseMeas, i,
												ExcelUtilities.getColumnNum("NumberWheelDrive", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(viccExtensionCd, i,
												ExcelUtilities.getColumnNum("VICCCodeMultiple", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(absDesc, i, ExcelUtilities.getColumnNum("ABS", colNum),
												"Vehicle_TD", runManagerPath, log);
										ExcelUtilities.setVicc(bodyStyleCd, i,
												ExcelUtilities.getColumnNum("BodyStyle", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(engineCylinderCnt, i,
												ExcelUtilities.getColumnNum("Cylinder", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(horsepowerMeas, i,
												ExcelUtilities.getColumnNum("HorsePower", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(sizeCd, i, ExcelUtilities.getColumnNum("Size", colNum),
												"Vehicle_TD", runManagerPath, log);
										ExcelUtilities.setVicc(generationDesc, i,
												ExcelUtilities.getColumnNum("VehicleGeneration", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(weightMeas, i, ExcelUtilities.getColumnNum("Weight", colNum),
												"Vehicle_TD", runManagerPath, log);
										ExcelUtilities.setVicc(engineInductionDesc, i,
												ExcelUtilities.getColumnNum("ForcedInduction", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(engineFuelDesc, i, ExcelUtilities.getColumnNum("Fuel", colNum),
												"Vehicle_TD", runManagerPath, log);
										if (engineHybridInd.equals("false")) {
											ExcelUtilities.setVicc("N", i, ExcelUtilities.getColumnNum("Hybrid", colNum),
													"Vehicle_TD", runManagerPath, log);
										} else
											ExcelUtilities.setVicc("Y", i, ExcelUtilities.getColumnNum("Hybrid", colNum),
													"Vehicle_TD", runManagerPath, log);
										ExcelUtilities.setVicc(airbagDesc, i, ExcelUtilities.getColumnNum("Air_Bags", colNum),
												"Vehicle_TD", runManagerPath, log);
										ExcelUtilities.setVicc(audibleAlarmDesc, i,
												ExcelUtilities.getColumnNum("audibleAlarmDesc", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(cutOffSystemDesc, i,
												ExcelUtilities.getColumnNum("cutOffSystemDesc", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(securityKeySystemDesc, i,
												ExcelUtilities.getColumnNum("securityKeySystemDesc", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(ibcApprovedDesc, i,
												ExcelUtilities.getColumnNum("ibcApprovedDesc", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(tractionControlDesc, i,
												ExcelUtilities.getColumnNum("tractionControlDesc", colNum), "Vehicle_TD",
												runManagerPath, log);
										ExcelUtilities.setVicc(stabilityDesc, i,
												ExcelUtilities.getColumnNum("stabilityDesc", colNum), "Vehicle_TD",
												runManagerPath, log);
									}
								}
							}
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					log.error(e);
				}
			}
		} finally {
			CreateDialogFromOptionPane.setWarningMsg("Done");
			System.exit(0);
		}
	}

	// HTTP GET request
	public String sendGet(String url, Logger log, String SwithMode, String AuthKey) {
		try {

			log.info("URL recieved at server: " + url);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(url);
			if (SwithMode.toUpperCase().equals("ON")) {
				getRequest.addHeader("Authorization", "Bearer " + AuthKey);
			}
			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			System.out.println("Output received from Server .... \n");
			log.info("Output received from Server .... \n");
			while ((output = br.readLine()) != null) {
				// System.out.println(output);
				return output;
			}
			httpClient.getConnectionManager().shutdown();
		} catch (ClientProtocolException e) {
			System.out.println(e);
			log.error(e);
		} catch (IOException e) {
			System.out.println(e);
			log.error(e);
		}
		return "";
	}
	
	
}
