'Set objExcel = CreateObject("Excel.Application")
Set fso = CreateObject ("Scripting.FileSystemObject")  'use this to find current path
strScript = Wscript.ScriptFullName

strFilePath = fso.GetAbsolutePathName(strScript & "\..")

'strScript = Wscript.ScriptFullName

viccFilePath = strFilePath & "\Rating\Sample\VICC.xlsx;"
Set oCn = CreateObject("ADODB.Connection")
Dim sSQLQry
DataSource = viccFilePath ' "C:\API\Rating\QuickWin_Auto_Quote\Rating\Sample\VICC.xlsx;"
oCn.Open "Provider=Microsoft.ACE.OLEDB.12.0;" & _
         "Data Source="&DataSource & _
         "Extended Properties=Excel 12.0;"

sSQLQry = " UPDATE [Vehicle_TD$] vtd  inner join [VICC$] vcc on vtd.VD_ModelYear = vcc.ModelYear AND vtd.VD_Make = vcc.Manufacturer AND vtd.VD_Model = vcc.Model"&_
" SET vtd.[ABS]=vcc.[ABS] , vtd.[Air_Bags]=vcc.[Airbags] , vtd.[audibleAlarmDesc]=vcc.[Audible Alarm] ,vtd.[BodyStyle]=vcc.[BodyStyle] ,vtd.[cutOffSystemDesc]=vcc.[Fuel/Ignation/Electric Cut-Off],"&_
" vtd.[Cylinder]=vcc.[Engine Cylinder] ,vtd.[DriveTrain]=vcc.[Drive train] ,vtd.[HorsePower]=vcc.[Horse Power] ,vtd.[ibcApprovedDesc]=vcc.[IBC Approved] ,vtd.[Market]=vcc.[Market] ,"&_
"vtd.[MarketValue]=vcc.[Manufactured suggested Retail Price] ,vtd.[securityKeySystemDesc]=vcc.[Security Key System] ,vtd.[Size]=vcc.[Size code] ,vtd.[stabilityDesc]=vcc.[Stability Control] ,"&_
"vtd.[tractionControlDesc]=vcc.[Traction Control] ,vtd.[VehicleGeneration]=vcc.[Vehicle Generation], vtd.[Weight]=vcc.[Weight In Kg] ,vtd.[NumberWheelDrive]=vcc.[WheelBase in Millimeter] ,"&_
"vtd.[ForcedInduction]=vcc.[Engine,Forced Induction] ,vtd.[Fuel]=vcc.[Engine,Fuel] ,vtd.[Hybrid]=vcc.[Engine,Hybrid], vtd.[VICCCode]=vcc.[Vehicle Code] ,vtd.[Year]=vcc.[ModelYear], vtd.[BodyCode]=vcc.[BodyStyle]  "


oCn.Execute sSQLQry
msgbox "Updated successfully"
oCn.Close