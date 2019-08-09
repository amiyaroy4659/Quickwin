Set objExcel = CreateObject("Excel.Application")
Set fso = CreateObject ("Scripting.FileSystemObject")  'use this to find current path
strScript = Wscript.ScriptFullName

strFilePath = fso.GetAbsolutePathName(strScript & "\..")

'strScript = Wscript.ScriptFullName
sourceFile = strFilePath & "\Rating\RunManager\Run Manager.xls"
destFile = strFilePath & "\Rating\Sample\VICC.xlsx"
'msgbox sourceFile
'msgbox destFile
'Sheet you want to copy from
Set x = objExcel.Workbooks.Open(destFile)
'Sheet you want to copy to
Set y = objExcel.Workbooks.Open(sourceFile)
Set ws1 = x.Sheets("Vehicle_TD")
Set ws2 = y.Sheets("Vehicle_TD")

ws1.Cells.Copy ws2.cells
y.Close True
x.Close False
msgbox "Paste Successfully"