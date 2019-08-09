package ReusableComponents;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;

public class VICCUpdate {
	
	public static void main(String args[]) throws FilloException
	{
		Fillo fillo=new Fillo();
		Connection connection=fillo.getConnection("C:\\API\\Rating\\QuickWin_Auto_Quote\\Rating\\Sample\\VICC.xlsx");
		//Connection con=fillo.getConnection("C:\\Excell\\New.xlsx");
		connection.getMetaData();
		System.out.println("Connected");
		String strQuery=" UPDATE [Vehicle_TD$] vtd  inner join [VICC$] vcc on vtd.VD_ModelYear = vcc.ModelYear AND vtd.VD_Make = vcc.Manufacturer AND vtd.VD_Model = vcc.Model "
				+ "SET vtd.[ABS]=vcc.[ABS] ,"
				+ " vtd.[Air_Bags]=vcc.[Airbags] ,"
				+ " vtd.[audibleAlarmDesc]=vcc.[Audible Alarm] ,"
				+ "vtd.[BodyStyle]=vcc.[BodyStyle] ,"
				+ "vtd.[cutOffSystemDesc]=vcc.[Fuel/Ignation/Electric Cut-Off], "
				+ "vtd.[Cylinder]=vcc.[Engine Cylinder] ,"
				+ "vtd.[DriveTrain]=vcc.[Drive train] ,"
				+ "vtd.[HorsePower]=vcc.[Horse Power] ,"
				+ "vtd.[ibcApprovedDesc]=vcc.[IBC Approved] ,vtd.[Market]=vcc.[Market] ,"
				+ "vtd.[MarketValue]=vcc.[Manufactured suggested Retail Price] ,"
				+ "vtd.[securityKeySystemDesc]=vcc.[Security Key System] ,"
				+ "vtd.[Size]=vcc.[Size code] ,vtd.[stabilityDesc]=vcc.[Stability Control] , "
				+ "vtd.[tractionControlDesc]=vcc.[Traction Control] ,"
				+ "vtd.[VehicleGeneration]=vcc.[Vehicle Generation], "
				+ "vtd.[Weight]=vcc.[Weight In Kg] ,"
				+ "vtd.[NumberWheelDrive]=vcc.[WheelBase in Millimeter] ,"
				+ "vtd.[ForcedInduction]=vcc.[Engine,Forced Induction] ,"
				+ "vtd.[Fuel]=vcc.[Engine,Fuel] ,"
				+ "vtd.[Hybrid]=vcc.[Engine,Hybrid],"
				+ " vtd.[VICCCode]=vcc.[Vehicle Code] ,"
				+ "vtd.[Year]=vcc.[ModelYear], "
				+ "vtd.[BodyCode]=vcc.[BodyStyle] ";

		connection.executeUpdate(strQuery);
		connection.close();
	}

}
