package ReusableComponents;

import java.util.ArrayList;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelData {
	public static void main(String args[]) throws FilloException
	{
		Fillo fillo=new Fillo();
		Connection connection=fillo.getConnection("C:\\Excell\\Output.xlsx");
		Connection con=fillo.getConnection("C:\\Excell\\New.xlsx");
		
		String strQuery="Select * from Data where Vehicle_Num='1'";
		
		//String strQuery = "update Sheet1 set VD_VehicleType ='Amiya' where TC_ID='TC11000242' ";
		Recordset recordset=connection.executeQuery(strQuery);
		
		while(recordset.next()){
			
				
				//String strQuery1="INSERT INTO Sheet1 (TC_ID,Vehicle_Num) values ('"+recordset.getField("TC_ID")+"')";
				String StrQuery ="INSERT INTO Sheet1 (TC_ID,Vehicle_Num,Driver_Num,RSP_Score,IsRSPEligible,ACTUALAMOUNT,ACTUALTERMAMOUNT,LEGACYCODE_EXT,RSPPremiums,Premium_27S,Premium_27,Premium_3,Premium_2,Premium_48,MRAC_Premium,OCI_Premium,IR_Premium,IN_Premium,DT_Premium,DCB_Premium,CGH_Premium,IsRSPAssignedByAnalyst,IsRSPAssignedBySystem) "
						+ "values ('"+recordset.getField("TC_ID")+"',"
								+ "'"+recordset.getField("Vehicle_Num")+"',"
										+ "'"+recordset.getField("Driver_Num")+"',"
												+ "'"+recordset.getField("RSP_Score")+"',"
														+ "'"+recordset.getField("IsRSPEligible")+"',"
																+ "'"+recordset.getField("ACTUALAMOUNT")+"',"
																		+ "'"+recordset.getField("ACTUALTERMAMOUNT")+"',"
																				+ "'"+recordset.getField("LEGACYCODE_EXT")+"',"
																						+ "'"+recordset.getField("RSPPremiums")+"',"
																								+ "'"+recordset.getField("Premium_27S")+"',"
																										+ "'"+recordset.getField("Premium_27")+"',"
																												+ "'"+recordset.getField("Premium_3")+"',"
																														+ "'"+recordset.getField("Premium_2")+"',"
																																+ "'"+recordset.getField("Premium_48")+"',"
																																		+ "'"+recordset.getField("MRAC_Premium")+"',"
																																				+ "'"+recordset.getField("OCI_Premium")+"',"
																																						+ "'"+recordset.getField("IR_Premium")+"',"
																																								+ "'"+recordset.getField("IN_Premium")+"',"
																																										+ "'"+recordset.getField("DT_Premium")+"',"
																																												+ "'"+recordset.getField("DCB_Premium")+"',"
																																														+ "'"+recordset.getField("CGH_Premium")+"',"
																																																+ "'"+recordset.getField("IsRSPAssignedByAnalyst")+"',"
																																																		+ "'"+recordset.getField("IsRSPAssignedBySystem")+"')";
			//String StrQuery ="insert into Sheet1(TC_ID,Vehicle_Num,Driver_Num,RSP_Score,IsRSPEligible,ACTUALAMOUNT,ACTUALTERMAMOUNT,LEGACYCODE_EXT,RSPPremiums,27S_Premium,27_Premium,3_Premium,2_Premium,48_Premium,MRAC_Premium,OCI_Premium,IR_Premium,IN_Premium,DT_Premium,DCB_Premium,CGH_Premium,IsRSPAssignedByAnalyst,IsRSPAssignedBySystem) values('RG_AB_20','1','1','1498','0','622','622','BI','510','0','0','0','0','0','0','0','0','0','0','0','0','0','0')";
				con.executeUpdate(StrQuery);
				
			
			//String strQuery1="INSERT INTO Sheet1 (TC_ID) values ("+data+")";
			//connection.executeUpdate(strQuery1);
		String a =(recordset.getField("TC_ID"));
		//String strQuery1="INSERT INTO Sheet1 (TC_ID) values ('chj')";
		}
		String strQuery1= "Select TC_ID,Vehicle_Num from Sheet1 "
				+ "MINUS "
				+ "Select TC_ID,Vehicle_Num from Sheet2";
		Recordset recordset1=connection.executeQuery(strQuery1);
				
		//Select VD_VehicleType from Sheet1 where TC_ID='TC11000242'
		//recordset.close();
		
		//connection.executeUpdate(strQuery);
		con.close();
		recordset.close();
		connection.close();
	}

}
