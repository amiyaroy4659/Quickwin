package ReusableComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.w3c.dom.Node;

public class ExcelUtilities {
	
	private static HSSFSheet ExcelWSheet;
	private static HSSFWorkbook ExcelWBook;
	private static HSSFCell Cell;
	private static HSSFRow Row;
	
	public static int vehicleTypeCount = 0, vehicleTypeCounftMul = 0, cellDataInt = 0, CellDataInt = 0, NumTC_ID,
			ColumnNumber = 0, count, loopSearch, claimCount1, claimCount2, claimCount3, claimCount4, claimCount5,
			claimCount6, claimCount7, claimCount8, claimCount9, claimCount10, claimCount11;
	public static Node premiums;
	public static String CellDataString = "", ColumnName = "", StringTC_ID="";	
	public static CellType type;
	public static int convicCount1, convicCount2, convicCount3, convicCount4, convicCount5, convicCount6, convicCount7,
			convicCount8, convicCount9, convicCount10, convicCount11, suspensionCount1, suspensionCount2,
			suspensionCount3, suspensionCount4, suspensionCount5, suspensionCount6, suspensionCount7, suspensionCount8,
			suspensionCount9, suspensionCount10, suspensionCount11;
	public static ReadPropFile properties = new ReadPropFile("Environment.properties");
	public static File directory = new File(properties.getPropertyValue("directory"));
	public static String ClaimConvicCodePath = directory.getAbsolutePath()
			+ properties.getPropertyValue("ClaimConvicCodePath");
	
	// ##############################################################################
		// This method is to set the File path and to open the Excel file, Pass
		// Excel Path and Sheet name as Arguments to this method
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static void setExcelFile(String Path, String SheetName, Logger log) {

			try {

				FileInputStream ExcelFile = new FileInputStream(Path);
				ExcelWBook = new HSSFWorkbook(ExcelFile);
				ExcelWSheet = ExcelWBook.getSheet(SheetName);
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}
		}

		// ##############################################################################
		// Function to fetch the test case id in a sheet
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static String getTcId(int RowNum, int ColNum, Logger log) throws Exception {

			try {
				NumTC_ID = 0;
				StringTC_ID = "";
				Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
				type = Cell.getCellTypeEnum();
				if (type == CellType.STRING) {
					StringTC_ID = Cell.getStringCellValue();
				} else if (type == CellType.NUMERIC) {
					StringTC_ID = String.valueOf(Cell.getNumericCellValue());
				} else if (type == CellType.BLANK) {
					StringTC_ID = Cell.getStringCellValue();
				}
			} catch (Exception e) {
				Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
				NumTC_ID = (int) Cell.getNumericCellValue();
				return Integer.toString(NumTC_ID);
			}
			return StringTC_ID;

		}

	//##############################################################################
	//Function to fetch the sub-iteration number for a row
	//Author : TDI Automation Team
	//Date : September, 2017
	//##############################################################################
	public static int getSubId(int RowNum, int ColNum, Logger log) throws Exception{

		try{

			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);		
		    type=Cell.getCellTypeEnum();	    
		    if(type==CellType.NUMERIC){
		    	cellDataInt = (int) Cell.getNumericCellValue();
				return cellDataInt;
		    }
		    else if(type==CellType.STRING){
		    	CellDataString=Cell.getStringCellValue();
		    	return Integer.parseInt(CellDataString);
		    }
			}catch (Exception e){
			System.out.println(e);
			log.error(e);
			}
		return 0;

	}

		// ##############################################################################
		// Function to get the number of rows present in a sheet
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static int getRowNum(Logger log) {

			try {

				int rowNum = ExcelWSheet.getLastRowNum();
				return rowNum;
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}
			return 0;
		}

		// ##############################################################################
		// Function to calculate the cell number for a particular field under a
		// sheet
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static int getColNum(Logger log) {

			try {

				int cellNum = ExcelWSheet.getRow(0).getLastCellNum();
				return cellNum;
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}
			return 0;

		}

		// ##############################################################################
		// Function to calculate number of rows present for a particular test id
		// under a particular sheet
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static int getCount(String testId, Logger log, int loopToStart) {

			try {

				count = 0;
				int loop = 1;
				int row = ExcelWSheet.getLastRowNum();
				if (row < loopToStart) {
					loop = 1;
				} else
					loop = loopToStart;
				for (int i = loop; i <= row; i++) {
					String TC_ID = ExcelUtilities.getTcId(i, 0, log);
					if (!TC_ID.equals("")) {
						if (TC_ID.equals(testId)) {
							count = count + 1;
						}
					}
				}
				return count;

			} catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
				return count;
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
				return count;
			}
		}

		public static int getColumnNum(String fieldName, int colNum) {

			ColumnNumber = 0;

			for (int i = 0; i < colNum; i++) {
				String colName = ExcelWSheet.getRow(0).getCell(i).getStringCellValue();
				if (fieldName.equals(colName)) {
					ColumnNumber = i;
					break;
				}
			}
			return ColumnNumber;
		}

		// ##############################################################################
		// Function to get the column name under a sheet using cell number
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static String getColName(int colNum) {

			ColumnName = "";
			ColumnName = ExcelWSheet.getRow(0).getCell(colNum).getStringCellValue();
			return ColumnName;
		}

		// ##############################################################################
		// Function to calculate number of claims for a driver id
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static int getClaimCount(String testId, int row, int col, Logger log) {

			try {

				count = 0;
				int rowNum = ExcelWSheet.getLastRowNum();
				for (int i = 1; i <= rowNum; i++) {
					String TC_ID = ExcelUtilities.getTcId(i, 0, log);
					if (TC_ID.equals(testId)) {
						int driverId = (int) ExcelWSheet.getRow(i).getCell(col).getNumericCellValue();
						if (driverId == row) {
							count = count + 1;
						}
					}
				}
				return count;

			} catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
				return count;
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
				return count;
			}
		}

		// ##############################################################################
		// Function to calculate convictions/suspensions count for a driver id
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static int getConvicCount(String testId, int row, int col, Logger log, String convicTypeCol,
				String convicType) {

			try {

				count = 0;
				int rowNum = ExcelWSheet.getLastRowNum();
				int colNum = ExcelUtilities.getColNum(log);

				for (int i = 1; i <= rowNum; i++) {
					String TC_ID = ExcelUtilities.getTcId(i, 0, log);
					if (TC_ID.equals(testId)) {
						String convicTypeName = ExcelWSheet.getRow(i)
								.getCell(ExcelUtilities.getColumnNum(convicTypeCol, colNum)).getStringCellValue();
						if (convicTypeName.equalsIgnoreCase(convicType)) {
							int driverId = (int) ExcelWSheet.getRow(i).getCell(col).getNumericCellValue();
							if (driverId == row) {
								count = count + 1;
							}
						}
					}
				}
				return count;
			} catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
				return count;
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
				return count;
			}
		}

		// ##############################################################################
		// Function to fetch data having single sub-iteration
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static String getCellDataString(int ColNum, String textToFind, Logger log, int loopToStart) {

			try {

				int loop = 1;
				CellDataString = "";
				int rows = ExcelWSheet.getLastRowNum();

				if (rows < loopToStart) {
					loop = 1;
				} else
					loop = loopToStart;
				for (int i = loopToStart; i < rows; i++) {
					String TC_ID = ExcelUtilities.getTcId(i, 0, log);
					if (TC_ID.equals(textToFind)) {
						CellDataString = ExcelWSheet.getRow(i).getCell(ColNum).getStringCellValue();
						break;
					}
				}
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}
			return CellDataString;

		}

		// ##############################################################################
		// Function to fetch Policy level coverage limit/deduct value
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static String getPolLevelCovLimitVal(String textToFind, String covStatusFieldName, String covValueFieldName,
				Logger log, int colNum, int loopToStart) throws Exception {

			try {

				CellDataInt = 0;
				CellDataString = Integer.toString(0);
				int rows = ExcelWSheet.getLastRowNum(), loop = 1;
				if (rows < loop) {
					loop = 1;
				} else
					loop = loopToStart;
				for (int i = loopToStart; i <= rows; i++) {
					String TC_ID = ExcelUtilities.getTcId(i, 0, log);
					if (TC_ID.equals(textToFind)) {
						String IsActive = ExcelWSheet.getRow(i)
								.getCell(ExcelUtilities.getColumnNum(covStatusFieldName, colNum)).getStringCellValue();
						if (IsActive.toLowerCase().equals("yes")) {
							Cell = ExcelWSheet.getRow(i).getCell(ExcelUtilities.getColumnNum(covValueFieldName, colNum));
							type = Cell.getCellTypeEnum();
							if (type == CellType.NUMERIC) {
								CellDataInt = (int) Cell.getNumericCellValue();
							} else if (type == CellType.STRING) {
								CellDataInt = Integer.parseInt(Cell.getStringCellValue());
							}
							break;
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}
			return Integer.toString(CellDataInt);

		}

		// ##############################################################################
		// Function to fetch data having multiple for single sub-iteration
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static int getCellDataInt(int ColNum, String textToFind, Logger log, int loopToStart) throws Exception {

			try {

				CellDataInt = 0;
				int rows = ExcelWSheet.getLastRowNum(), loop = 1;
				if (rows < loopToStart) {
					loop = 1;
				} else
					loop = loopToStart;
				for (int i = loop; i < rows; i++) {
					String TC_ID = ExcelUtilities.getTcId(i, 0, log);
					if (TC_ID.equals(textToFind)) {
						CellDataInt = (int) ExcelWSheet.getRow(i).getCell(ColNum).getNumericCellValue();
						break;
					}
					return 0;
				}
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}
			return CellDataInt;
		}

		// ##############################################################################
		// Function to fetch data having multiple sub-iteration
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static String getMultiCellDataString(int ColNum, String textToFind, int subIter, Logger log,
				int loopToStart) {

			try {

				CellDataString = "";
				int loop = 1;
				int rows = ExcelWSheet.getLastRowNum();
				if (rows < loopToStart) {
					loop = 1;
				} else
					loop = loopToStart;
				for (int i = loop; i <= rows; i++) {
					String TC_ID = ExcelUtilities.getTcId(i, 0, log);
					if (TC_ID.equals(textToFind)) {
						int subId = (int) ExcelWSheet.getRow(i).getCell(2).getNumericCellValue();
						String driver_id = Integer.toString(subId);
						String recSubId = Integer.toString(subIter);
						if (driver_id.equals(recSubId)) {
							Cell = ExcelWSheet.getRow(i).getCell(ColNum);
							type = Cell.getCellTypeEnum();
							if (type == CellType.STRING) {
								CellDataString = ExcelWSheet.getRow(i).getCell(ColNum).getStringCellValue();
							} else if (type == CellType.NUMERIC) {
								CellDataString = Integer
										.toString((int) ExcelWSheet.getRow(i).getCell(ColNum).getNumericCellValue());
							} else if (type == CellType.BLANK) {
								CellDataString = ExcelWSheet.getRow(i).getCell(ColNum).getStringCellValue();
							}
							break;
						}
					}
				}
			} catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}
			return CellDataString;

		}

		// ##############################################################################
		// Function to fetch data having multiple sub-iteration for
		// claims/convictions
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static String getMultiCellDataString(int ColNum, String textToFind, int subIter, int driverId, int driverCol,
				Logger log, int loopToStart, String claimConvicType) throws Exception {

			try {

				CellDataString = "";
				int rows = ExcelWSheet.getLastRowNum(), loop = 1;
				int colCount = ExcelUtilities.getColNum(log);
				for (int i = loop; i <= rows; i++) {
					String TC_ID = ExcelUtilities.getTcId(i, 0, log);
					if (claimConvicType.equalsIgnoreCase("Conviction") || claimConvicType.equalsIgnoreCase("Suspension")) {
						if (TC_ID.equals(textToFind)) {
							String Convic_Suspen_type = ExcelWSheet.getRow(i)
									.getCell(ExcelUtilities.getColumnNum("Convic_Suspen_type", colCount)).getStringCellValue();
							if (Convic_Suspen_type.equalsIgnoreCase(claimConvicType)) {
								int driId = (int) ExcelWSheet.getRow(i).getCell(driverCol).getNumericCellValue();
								if (driId == driverId) {
									int subId = (int) ExcelWSheet.getRow(i)
											.getCell(ExcelUtilities.getColumnNum("Conviction_Id", colCount))
											.getNumericCellValue();
									String driver_id = Integer.toString(subId);
									String recSubId = Integer.toString(subIter);
									if (driver_id.equals(recSubId)) {
										Cell = ExcelWSheet.getRow(i).getCell(ColNum);
										type = Cell.getCellTypeEnum();
										if (type == CellType.STRING) {
											CellDataString = ExcelWSheet.getRow(i).getCell(ColNum).getStringCellValue();
										} else if (type == CellType.NUMERIC) {
											CellDataString = Integer.toString(
													(int) ExcelWSheet.getRow(i).getCell(ColNum).getNumericCellValue());
										} else if (type == CellType.BLANK) {
											CellDataString = ExcelWSheet.getRow(i).getCell(ColNum).getStringCellValue();
										}
										break;
									}
								}
							}
						}
					} else {
						if (TC_ID.equals(textToFind)) {
							int driId = (int) ExcelWSheet.getRow(i).getCell(driverCol).getNumericCellValue();
							if (driId == driverId) {
								int subId = (int) ExcelWSheet.getRow(i)
										.getCell(ExcelUtilities.getColumnNum("Claim_Id", colCount)).getNumericCellValue();
								String driver_id = Integer.toString(subId);
								String recSubId = Integer.toString(subIter);
								if (driver_id.equals(recSubId)) {
									Cell = ExcelWSheet.getRow(i).getCell(ColNum);
									type = Cell.getCellTypeEnum();
									if (type == CellType.STRING) {
										CellDataString = ExcelWSheet.getRow(i).getCell(ColNum).getStringCellValue();
									} else if (type == CellType.NUMERIC) {
										CellDataString = Integer.toString(
												(int) ExcelWSheet.getRow(i).getCell(ColNum).getNumericCellValue());
									} else if (type == CellType.BLANK) {
										CellDataString = ExcelWSheet.getRow(i).getCell(ColNum).getStringCellValue();
									}
									break;
								}
							}
						}
					}
				}
			} catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}
			return CellDataString;

		}

		// ##############################################################################
		// Function to fetch data having multiple sub-iteration
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static String getMultiCellDataInt(int ColNum, String textToFind, int subIter, Logger log, int loopToStart) {

			try {

				CellDataInt = 0;
				CellDataString = "";
				int rows = ExcelWSheet.getLastRowNum(), loop = 1;
				if (rows < loop) {
					loop = 1;
				} else
					loop = loopToStart;
				for (loopSearch = loop; loopSearch <= rows; loopSearch++) {
					String TC_ID = ExcelUtilities.getTcId(loopSearch, 0, log);
					if (TC_ID.equals(textToFind)) {
						int subId = (int) ExcelWSheet.getRow(loopSearch).getCell(2).getNumericCellValue();
						String driver_id = Integer.toString(subId);
						String recSubId = Integer.toString(subIter);
						if (driver_id.equals(recSubId)) {
							Cell = ExcelWSheet.getRow(loopSearch).getCell(ColNum);
							if (Cell == null || Cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								return "";
							} else {
								type = Cell.getCellTypeEnum();

								if (type == CellType.NUMERIC) {
									CellDataInt = (int) ExcelWSheet.getRow(loopSearch).getCell(ColNum)
											.getNumericCellValue();
								} else if (type == CellType.STRING) {
									CellDataInt = Integer
											.parseInt(ExcelWSheet.getRow(loopSearch).getCell(ColNum).getStringCellValue());
								} else if (type == CellType.BLANK) {
									CellDataInt = Integer
											.parseInt(ExcelWSheet.getRow(loopSearch).getCell(ColNum).getStringCellValue());
								}
							}
							break;
						}
					}
				}
			} catch (NumberFormatException ne) {
				CellDataString = ExcelWSheet.getRow(loopSearch).getCell(ColNum).getStringCellValue();
				return CellDataString;
			} catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
				return "";
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
				CellDataString = ExcelWSheet.getRow(loopSearch).getCell(ColNum).getStringCellValue();
				return CellDataString;
			}
			return Integer.toString(CellDataInt);
		}

		// ##############################################################################
		// Function to fetch data from Driver sheet for occasional drivers
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static String getMultiCellDataOcc(int ColNum, String textToFind, int subIter, Logger log, int loopToStart) {

			try {

				CellDataInt = 0;
				CellDataString = "";
				int rows = ExcelWSheet.getLastRowNum(), loop = 1;
				if (rows < loop) {
					loop = 1;
				} else
					loop = loopToStart;
				for (int i = loop; i <= rows; i++) {
					String TC_ID = ExcelUtilities.getTcId(i, 0, log);
					if (TC_ID.equals(textToFind)) {
						int subId = (int) ExcelWSheet.getRow(i).getCell(2).getNumericCellValue();
						String driver_id = Integer.toString(subId);
						String recSubId = Integer.toString(subIter);
						if (driver_id.equals(recSubId)) {
							try {
								Cell = ExcelWSheet.getRow(i).getCell(ColNum);
								if (Cell == null || Cell.getCellType() == Cell.CELL_TYPE_BLANK) {
									return "";
								} else {
									type = Cell.getCellTypeEnum();
									if (type == CellType.NUMERIC) {
										CellDataInt = (int) ExcelWSheet.getRow(i).getCell(ColNum).getNumericCellValue();
									} else if (type == CellType.STRING) {
										CellDataInt = Integer
												.parseInt(ExcelWSheet.getRow(i).getCell(ColNum).getStringCellValue());
									} else if (type == CellType.BLANK) {
										CellDataInt = Integer
												.parseInt(ExcelWSheet.getRow(i).getCell(ColNum).getStringCellValue());
									}
								}
								break;
							} catch (Exception e) {
								CellDataString = ExcelWSheet.getRow(i).getCell(ColNum).getStringCellValue();
								return CellDataString;
							}
						}
					}
				}
			} catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}
			return Integer.toString(CellDataInt);

		}


		// ##############################################################################
		// Function to fetch data having multiple sub-iteration for
		// claims/convictions
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##############################################################################
		public static String getMultiCellDataInt(int ColNum, String textToFind, int subIter, int driverId, int driverCol,
				Logger log, int loopToStart, String claimConvicType) throws Exception {

			try {
				CellDataInt = 0;

				int rows = ExcelWSheet.getLastRowNum(), loop = 1;
				int colCount = ExcelUtilities.getColNum(log);
				for (int i = loop; i <= rows; i++) {
					String TC_ID = ExcelUtilities.getTcId(i, 0, log);
					if (claimConvicType.equalsIgnoreCase("Conviction") || claimConvicType.equalsIgnoreCase("Suspension")) {
						if (TC_ID.equals(textToFind)) {
							String Convic_Suspen_type = ExcelWSheet.getRow(i)
									.getCell(ExcelUtilities.getColumnNum("Convic_Suspen_type", colCount)).getStringCellValue();
							if (Convic_Suspen_type.equalsIgnoreCase(claimConvicType)) {
								int driId = (int) ExcelWSheet.getRow(i).getCell(driverCol).getNumericCellValue();
								if (driId == driverId) {
									int subId = (int) ExcelWSheet.getRow(i)
											.getCell(ExcelUtilities.getColumnNum("Conviction_Id", colCount))
											.getNumericCellValue();
									String driver_id = Integer.toString(subId);
									String recSubId = Integer.toString(subIter);
									if (driver_id.equals(recSubId)) {
										Cell = ExcelWSheet.getRow(i).getCell(ColNum);
										if (Cell == null || Cell.getCellType() == Cell.CELL_TYPE_BLANK) {
											return "";
										} else {
											type = Cell.getCellTypeEnum();
											if (type == CellType.NUMERIC) {
												CellDataInt = (int) ExcelWSheet.getRow(i).getCell(ColNum)
														.getNumericCellValue();
											} else if (type == CellType.STRING) {
												CellDataInt = Integer.parseInt(
														ExcelWSheet.getRow(i).getCell(ColNum).getStringCellValue());
											} else if (type == CellType.BLANK) {
												CellDataInt = Integer.parseInt(
														ExcelWSheet.getRow(i).getCell(ColNum).getStringCellValue());
											}
										}
										break;
									}
								}
							}
						}
					} else {
						if (TC_ID.equals(textToFind)) {
							int driId = (int) ExcelWSheet.getRow(i).getCell(driverCol).getNumericCellValue();
							if (driId == driverId) {
								int subId = (int) ExcelWSheet.getRow(i)
										.getCell(ExcelUtilities.getColumnNum("Claim_Id", colCount)).getNumericCellValue();
								String driver_id = Integer.toString(subId);
								String recSubId = Integer.toString(subIter);
								if (driver_id.equals(recSubId)) {
									Cell = ExcelWSheet.getRow(i).getCell(ColNum);
									if (Cell == null || Cell.getCellType() == Cell.CELL_TYPE_BLANK) {
										return "";
									} else {
										type = Cell.getCellTypeEnum();
										if (type == CellType.NUMERIC) {
											CellDataInt = (int) ExcelWSheet.getRow(i).getCell(ColNum).getNumericCellValue();
										} else if (type == CellType.STRING) {
											CellDataInt = Integer
													.parseInt(ExcelWSheet.getRow(i).getCell(ColNum).getStringCellValue());
										} else if (type == CellType.BLANK) {
											CellDataInt = Integer
													.parseInt(ExcelWSheet.getRow(i).getCell(ColNum).getStringCellValue());
										}
									}
									break;
								}
							}
						}
					}
				}
			} catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}
			return Integer.toString(CellDataInt);

		}
		
		// ##########################################################################
		// Function to calculate the number of a particular vehicle level coverage
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##########################################################################
		public static int getCoverageCount(String testId, int subNum, Logger log) throws Exception {

			try {

				int count = 0;
				int rows = ExcelWSheet.getLastRowNum();
				for (int i = 1; i <= rows; i++) {
					String TC_ID = ExcelUtilities.getTcId(i, 0, log);
					int subId = (int) ExcelWSheet.getRow(i).getCell(2).getNumericCellValue();
					String vehicle_id = Integer.toString(subId);
					System.out.println("vehicle: " + vehicle_id);
					String recSubId = Integer.toString(subNum);
					System.out.println("subid: " + recSubId);
					System.out.println(TC_ID);
					if (TC_ID.equals(testId)) {
						if (recSubId.equals(vehicle_id)) {
							for (int j = 5; j < 29; j++) {
								String coverageValue = ExcelWSheet.getRow(i).getCell(j).getStringCellValue();
								if (!(coverageValue.equals(""))) {
									count = count + 1;
								}
							}
							return count;
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}
			return 0;

		}

		
		// ##########################################################################
		// Function to calculate the protectionDeviceCount in Vehicle level
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##########################################################################
		public static int protectionDeviceCount(String testId, int subNum, Logger log) throws Exception {

			try {

				int count = 0;
				int rows = ExcelWSheet.getLastRowNum();
				for (int i = 1; i <= rows; i++) {
					String TC_ID = ExcelUtilities.getTcId(i, 0, log);
					int colNum = ExcelUtilities.getColNum(log);
					if (TC_ID.equals(testId)) {
						int subId = (int) ExcelWSheet.getRow(i).getCell(2).getNumericCellValue();
						String vehicle_id = Integer.toString(subId);
						String recSubId = Integer.toString(subNum);
						if (recSubId.equals(vehicle_id)) {
							int start = ExcelUtilities.getColumnNum("Protection_Devices", colNum);
							String protectionDevice = ExcelWSheet.getRow(i).getCell(start).getStringCellValue();
							System.out.println("Protection_Devices: " + protectionDevice);
							try {
								
								if (protectionDevice == "") {
									return 0;
								} else
								{
									String[] array = protectionDevice.split(",");
									return array.length;
								}
									
							} catch (Exception e) {
								System.out.println("Protection_Devices: " + protectionDevice);
								return 1;
								
							}
						}
					}
				}
			} catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}
			return 0;
		
		}
		
		
		// ##########################################################################
		// Function to calculate the protectionDeviceValue in Vehicle level
		// Author : TDI Automation Team
		// Date : September, 2017
		// ##########################################################################		
		
		public static String[] protectionDeviceValue(String testId, int subNum, Logger log) throws Exception {

			try {

				int count = 0;
				int rows = ExcelWSheet.getLastRowNum();
				for (int i = 1; i <= rows; i++) {
					String TC_ID = ExcelUtilities.getTcId(i, 0, log);
					int colNum = ExcelUtilities.getColNum(log);
					if (TC_ID.equals(testId)) {
						int subId = (int) ExcelWSheet.getRow(i).getCell(2).getNumericCellValue();
						String vehicle_id = Integer.toString(subId);
						String recSubId = Integer.toString(subNum);
						if (recSubId.equals(vehicle_id)) {
							int start = ExcelUtilities.getColumnNum("Protection_Devices", colNum);
							String protectionDevice = ExcelWSheet.getRow(i).getCell(start).getStringCellValue();
							System.out.println("Protection_Devices: " + protectionDevice);
							try {
								
								if (protectionDevice == "") {
									return null;
								} else
								{
									String[] array = protectionDevice.split(",");
									return array;
								}
									
							} catch (Exception e) {
								System.out.println("Protection_Devices: " + protectionDevice);
								String[] strArray = new String[] {protectionDevice};
								return strArray;
								
							}
						}
					}
				}
			} catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}
			return null;
			
		
		}
		
		// ###############################################################
		// Function to calculate number of occasional drivers for a particular
		// vehicle
		// Author : TDI Automation Team
		// Date : September, 2017
		// ###############################################################
		public static int occassionalCount(String testId, int subNum, Logger log) throws Exception {

			try {

				int count = 0;
				int rows = ExcelWSheet.getLastRowNum();
				for (int i = 1; i <= rows; i++) {
					String TC_ID = ExcelUtilities.getTcId(i, 0, log);
					int colNum = ExcelUtilities.getColNum(log);
					if (TC_ID.equals(testId)) {
						int subId = (int) ExcelWSheet.getRow(i).getCell(2).getNumericCellValue();
						String vehicle_id = Integer.toString(subId);
						String recSubId = Integer.toString(subNum);
						if (recSubId.equals(vehicle_id)) {
							int start = ExcelUtilities.getColumnNum("Occasional ", colNum);
							int end = ExcelUtilities.getColumnNum("Override_Occasional", colNum);
							try {
								int Occ = (int) ExcelWSheet.getRow(i).getCell(start).getNumericCellValue();
								System.out.println("Occassional: " + Occ);
								if (Occ == 0) {
									return 0;
								} else
									return 1;
							} catch (Exception e) {
								String Occ = ExcelWSheet.getRow(i).getCell(start).getStringCellValue();
								System.out.println("Occassional: " + Occ);
								String[] array = Occ.split(",");
								return array.length;
							}
						}
					}
				}
			} catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}
			return 0;

		}

	//###############################################################
	//Function to calculate number of count for a particular Policy level coverage
	//Author : TDI Automation Team
	//Date : September, 2017
	//###############################################################
	public static int covPolLevelCount(String testId,String feildName, Logger log, int colNum){
		
	try{		
			count=0;		
			String covStat;
			int CellDataInt=ExcelWSheet.getLastRowNum();
			for (int i=1;i<=CellDataInt;i++){			
				String TC_ID=ExcelUtilities.getTcId(i, 0, log);			
				if(TC_ID.equals(testId)){	
						covStat= ExcelWSheet.getRow(i).getCell(ExcelUtilities.getColumnNum(feildName, colNum)).getStringCellValue();
					if(covStat.toLowerCase().equals("yes")){				
				count=count+1;
				break;
					}
				}
			}		
			return count;
			}catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
				return count;
			}
	         catch (Exception e){
	        System.out.println(e);
			log.error(e);
			return count;
			}	
	}

	//###############################################################
	//Function to calculate number of count for Endorsement 2 name
	//Author : TDI Automation Team
	//Date : September, 2017
	//###############################################################
	public static int endo2NameCount(String testId,String feildName, Logger log, int colNum){
		
	try{		
			count=0;		
			String endorsementName;
			int CellDataInt=ExcelWSheet.getLastRowNum();
			for (int i=1;i<=CellDataInt;i++){			
				String TC_ID=ExcelUtilities.getTcId(i, 0, log);			
				if(TC_ID.equals(testId)){
					String covStatus = ExcelWSheet.getRow(i).getCell(ExcelUtilities.getColumnNum("Acov_2_Pcov", colNum)).getStringCellValue();
					if(covStatus.equalsIgnoreCase("Yes")){
					for(int j=1;j<=2;j++){
						endorsementName= ExcelWSheet.getRow(i).getCell(ExcelUtilities.getColumnNum("Acov_2_Pcov_Name"+j, colNum)).getStringCellValue();
						if(!(endorsementName.equalsIgnoreCase(""))){
							count=count+1;
						}
					}
					break;	
				}
							
				}
			}		
			return count;
			}catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
				return count;
			}
	       catch (Exception e){
	      System.out.println(e);
			log.error(e);
			return count;
			}	
	}
	
	public static void setData(String Result,  int RowNum, int ColNum, String SheetName, String path, Logger log) throws Exception    {
		try {
	        //Get the excel file.
	        FileInputStream file = new FileInputStream(new File(path));
	 
	        //Get workbook for XLS file.
	        HSSFWorkbook yourworkbook = new HSSFWorkbook(file);
	 
	        //Get first sheet from the workbook.
	        //If there have >1 sheet in your workbook, you can change it here IF you want to edit other sheets.
	        HSSFSheet sheet1 = yourworkbook.getSheet(SheetName);
	 
	        // Get the row of your desired cell.
	        // Let's say that your desired cell is at row 2.
	        HSSFRow row = sheet1.getRow(RowNum+1);
	        // Get the column of your desired cell in your selected row.
	        // Let's say that your desired cell is at column 2.
	        HSSFCell column = row.createCell(ColNum);
	        // If the cell is String type.If double or else you can change it.
	        //String updatename = column.getStringCellValue();
	        //Set the new content to your desired cell(column).
	        column.setCellValue(Result);
	        //Close the excel file.
	        file.close();
	        //Where you want to save the updated sheet.
	        FileOutputStream out = 
	            new FileOutputStream(new File(path));
	        yourworkbook.write(out);
	        out.close();
	 
	    } catch (FileNotFoundException fnfe) {
	    	System.out.println(fnfe);
	        log.error(fnfe);
	    } catch (IOException ioe) {
	    	System.out.println(ioe);
	        log.error(ioe);
	    }
		catch (Exception e) {
			System.out.println(e);
	        log.error(e);
	    }
			}
	
	public static void setStatus(String Result,  String textToFind, int ColNum, String SheetName, String path, Logger log) throws Exception    {
		try {
	        //Get the excel file.
			ExcelUtilities.setExcelFile(path, SheetName, log);
			int rows=ExcelWSheet.getLastRowNum();
	        
	        for(int i=0;i<=rows;i++){
	        	
			//String TC_ID = ExcelWSheet.getRow(i).getCell(0).getStringCellValue();
	        	
	        String TC_ID=ExcelUtilities.getTcId(i, 0, log);
			
			if(TC_ID.equals(textToFind)){
	        FileInputStream file = new FileInputStream(new File(path));
	 
	        //Get workbook for XLS file.
	        HSSFWorkbook yourworkbook = new HSSFWorkbook(file);
	 
	        //Get first sheet from the workbook.
	        //If there have >1 sheet in your workbook, you can change it here IF you want to edit other sheets.
	        HSSFSheet sheet1 = yourworkbook.getSheet(SheetName);
	 
	        // Get the row of your desired cell.
	        // Let's say that your desired cell is at row 2.
	        HSSFRow row = sheet1.getRow(i);
	        // Get the column of your desired cell in your selected row.
	        // Let's say that your desired cell is at column 2.
	        HSSFCell column = row.createCell(ColNum);
	        // If the cell is String type.If double or else you can change it.
	        //String updatename = column.getStringCellValue();
	        //Set the new content to your desired cell(column).
	        if(Result.equals("200")){

	        	column.setCellValue("Success");
	        }
	       
	        
	        else column.setCellValue("Failed with error code: "+Result);
	        //Close the excel file.
	        file.close();
	        //Where you want to save the updated sheet.
	        FileOutputStream out = 
	            new FileOutputStream(new File(path));
	        yourworkbook.write(out);
	        out.close();
			}
	        }
	 
	    } catch (FileNotFoundException e) {
	    	System.out.println(e);
	        log.error(e);
	    } catch (IOException e) {
	    	System.out.println(e);
	        log.error(e);
	    }
		catch (Exception e) {
			System.out.println(e);
	        log.error(e);
	    }
			}
	public static void setVicc(String Result,  int rowNum , int ColNum, String SheetName, String path, Logger log) throws Exception    {
		try {
	        //Get the excel file.
			int rows=ExcelWSheet.getLastRowNum();     
	        		
	        FileInputStream file = new FileInputStream(new File(path));	 
	        //Get workbook for XLS file.
	        HSSFWorkbook yourworkbook = new HSSFWorkbook(file);
	 
	        //Get first sheet from the workbook.
	        //If there have >1 sheet in your workbook, you can change it here IF you want to edit other sheets.
	        HSSFSheet sheet1 = yourworkbook.getSheet(SheetName);
	 
	        // Get the row of your desired cell.
	        // Let's say that your desired cell is at row 2.
	        HSSFRow row = sheet1.getRow(rowNum);
	        // Get the column of your desired cell in your selected row.
	        // Let's say that your desired cell is at column 2.
	        HSSFCell column = row.createCell(ColNum);
	        // If the cell is String type.If double or else you can change it.
	        //String updatename = column.getStringCellValue();
	        //Set the new content to your desired cell(column).	       
                 column.setCellValue(Result);	        
	        
	        //Close the excel file.
	        file.close();
	        //Where you want to save the updated sheet.
	        FileOutputStream out = 
	            new FileOutputStream(new File(path));
	        yourworkbook.write(out);
	        out.close();        			
	       
	 
	    } catch (FileNotFoundException e) {
	    	System.out.println(e);
	        log.error(e);
	    } catch (IOException e) {
	    	System.out.println(e);
	        log.error(e);
	    }
		catch (Exception e) {
			System.out.println(e);
	        log.error(e);
	    }
			}
	
	public static void setDataInt(int Result,  int RowNum, int ColNum, String SheetName, String path, Logger log) throws Exception    {
		try {
	        //Get the excel file.
	        FileInputStream file = new FileInputStream(new File(path));
	 
	        //Get workbook for XLS file.
	        HSSFWorkbook yourworkbook = new HSSFWorkbook(file);
	 
	        //Get first sheet from the workbook.
	        //If there have >1 sheet in your workbook, you can change it here IF you want to edit other sheets.
	        HSSFSheet sheet1 = yourworkbook.getSheet(SheetName);
	 
	        // Get the row of your desired cell.
	        // Let's say that your desired cell is at row 2.
	        HSSFRow row = sheet1.getRow(RowNum+1);
	        // Get the column of your desired cell in your selected row.
	        // Let's say that your desired cell is at column 2.
	        HSSFCell column = row.createCell(ColNum);
	        // If the cell is String type.If double or else you can change it.
	        //String updatename = column.getStringCellValue();
	        //Set the new content to your desired cell(column).
	        column.setCellValue(Result);
	        //Close the excel file.
	        file.close();
	        //Where you want to save the updated sheet.
	        FileOutputStream out = 
	            new FileOutputStream(new File(path));
	        yourworkbook.write(out);
	        out.close();
	 
	    } catch (FileNotFoundException e) {
	    	System.out.println(e);
	       log.error(e);
	    } catch (IOException e) {
	    	System.out.println(e);
	        log.error(e);
	    }
		catch (Exception e) {
			System.out.println(e);
	        log.error(e);
	    }
			}
	
	public static void setDataIntFirst(int Result,  int RowNum, int ColNum, String SheetName, String path, Logger log) throws Exception    {
		try {
	        //Get the excel file.
	        FileInputStream file = new FileInputStream(new File(path));
	 
	        //Get workbook for XLS file.
	        HSSFWorkbook yourworkbook = new HSSFWorkbook(file);
	 
	        //Get first sheet from the workbook.
	        //If there have >1 sheet in your workbook, you can change it here IF you want to edit other sheets.
	        HSSFSheet sheet1 = yourworkbook.getSheet(SheetName);
	 
	        // Get the row of your desired cell.
	        // Let's say that your desired cell is at row 2.
	        HSSFRow row = sheet1.createRow(RowNum+1);
	        // Get the column of your desired cell in your selected row.
	        // Let's say that your desired cell is at column 2.
	        HSSFCell column = row.createCell(ColNum);
	        // If the cell is String type.If double or else you can change it.
	        //String updatename = column.getStringCellValue();
	        //Set the new content to your desired cell(column).
	        column.setCellValue(Result);
	        //Close the excel file.
	        file.close();
	        //Where you want to save the updated sheet.
	        FileOutputStream out = 
	            new FileOutputStream(new File(path));
	        yourworkbook.write(out);
	        out.close();
	 
	    } catch (FileNotFoundException e) {
	    	
	    	System.out.println(e);
	       log.error(e);
	    } catch (IOException e) {
	    	
	    	System.out.println(e);
	        log.error(e);
	    }
		catch (Exception e) {
			System.out.println(e);
	        log.error(e);
	    }
			}
	
	public static void setDataStringFirst(String Result,  int RowNum, int ColNum, String SheetName, String path, Logger log) throws Exception    {
		try {
	        //Get the excel file.
	        FileInputStream file = new FileInputStream(new File(path));
	 
	        //Get workbook for XLS file.
	        HSSFWorkbook yourworkbook = new HSSFWorkbook(file);
	 
	        //Get first sheet from the workbook.
	        //If there have >1 sheet in your workbook, you can change it here IF you want to edit other sheets.
	        HSSFSheet sheet1 = yourworkbook.getSheet(SheetName);
	 
	        // Get the row of your desired cell.
	        // Let's say that your desired cell is at row 2.
	        HSSFRow row = sheet1.createRow(RowNum+1);
	        // Get the column of your desired cell in your selected row.
	        // Let's say that your desired cell is at column 2.
	        HSSFCell column = row.createCell(ColNum);
	        // If the cell is String type.If double or else you can change it.
	        //String updatename = column.getStringCellValue();
	        //Set the new content to your desired cell(column).
	        column.setCellValue(Result);
	        //Close the excel file.
	        file.close();
	        //Where you want to save the updated sheet.
	        FileOutputStream out = 
	            new FileOutputStream(new File(path));
	        yourworkbook.write(out);
	        out.close();
	 
	    } catch (FileNotFoundException e) {
	       log.error(e);
	    } catch (IOException e) {
	        log.error(e);
	    }
		catch (Exception e) {
	        log.error(e);
	    }
			}
	
	
	    // #################################################################################
		// Function To create output excel template
		// Author : TDI Automation Team
		// Date : September, 2017
		// #################################################################################
		public static void createOutputExcel(String path, String province) {

			try {
				ReadPropFile OutputSchemaProperties = new ReadPropFile("OutputSchema.properties");
				File fileName = new File(path);
				FileOutputStream fos = new FileOutputStream(fileName);
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("xml_out");
				HSSFRow row = sheet.createRow(0);
				for (int i = 0; i < 25; i++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellValue(OutputSchemaProperties.getPropertyValue(Integer.toString(i + 1)+"_"+province));
				}
				workbook.write(fos);
				fos.flush();
				fos.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

		// #################################################################################
		// Function To fetch Claim/Conviction codes depending on Claim/Convictions
		// name
		// Author : TDI Automation Team
		// Date : October,2017
		// #################################################################################
		public static String getClaimConvCode(String CovName, Logger log, int Col) {
			try {
				String covPatCode = "";
				int rows = ExcelWSheet.getLastRowNum();

				for (int i = 0; i <= rows; i++) {
					String coverage = ExcelWSheet.getRow(i).getCell(0).getStringCellValue();
					if (coverage.equals(CovName)) {
						covPatCode = ExcelWSheet.getRow(i).getCell(Col).getStringCellValue();
						return covPatCode;
					}
				}
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

		
		
		
		// #################################################################################
		// Function To change claim id in Claim_TD sheet
		// Author : TDI Automation Team
		// Date : October,2017
		// #################################################################################
		public static void makeClaimIdChange(String textToFind, String RunManager, String sheetName, Logger log,
				String feildName) {
			String TC_ID;
			int firstDriverId;
			claimCount1 = 0;
			claimCount2 = 0;
			claimCount3 = 0;
			claimCount4 = 0;
			claimCount5 = 0;
			claimCount6 = 0;
			claimCount7 = 0;
			claimCount8 = 0;
			claimCount9 = 0;
			claimCount10 = 0;
			try {
				ExcelUtilities.setExcelFile(RunManager, sheetName, log);
				int row = ExcelUtilities.getRowNum(log);
				int colNum = ExcelUtilities.getColNum(log);
				for (int i = 1; i <= row; i++) {
					TC_ID = ExcelUtilities.getTcId(i, 0, log);
					if (TC_ID.equals(textToFind)) {
						firstDriverId = ExcelUtilities.getSubId(i, ExcelUtilities.getColumnNum("Driver", colNum), log);
						if (firstDriverId == 1) {
							claimCount1 = claimCount1 + 1;
							ExcelUtilities.setDataInt(claimCount1, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
									sheetName, RunManager, log);
						} else if (firstDriverId == 2) {
							claimCount2 = claimCount2 + 1;
							ExcelUtilities.setDataInt(claimCount2, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
									sheetName, RunManager, log);
						} else if (firstDriverId == 3) {
							claimCount3 = claimCount3 + 1;
							ExcelUtilities.setDataInt(claimCount3, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
									sheetName, RunManager, log);
						} else if (firstDriverId == 4) {
							claimCount4 = claimCount4 + 1;
							ExcelUtilities.setDataInt(claimCount4, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
									sheetName, RunManager, log);
						} else if (firstDriverId == 5) {
							claimCount5 = claimCount5 + 1;
							ExcelUtilities.setDataInt(claimCount5, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
									sheetName, RunManager, log);
						} else if (firstDriverId == 6) {
							claimCount6 = claimCount6 + 1;
							ExcelUtilities.setDataInt(claimCount6, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
									sheetName, RunManager, log);
						} else if (firstDriverId == 7) {
							claimCount7 = claimCount7 + 1;
							ExcelUtilities.setDataInt(claimCount7, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
									sheetName, RunManager, log);
						} else if (firstDriverId == 8) {
							claimCount8 = claimCount8 + 1;
							ExcelUtilities.setDataInt(claimCount8, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
									sheetName, RunManager, log);
						} else if (firstDriverId == 9) {
							claimCount9 = claimCount9 + 1;
							ExcelUtilities.setDataInt(claimCount9, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
									sheetName, RunManager, log);
						} else if (firstDriverId == 10) {
							claimCount10 = claimCount10 + 1;
							ExcelUtilities.setDataInt(claimCount10, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
									sheetName, RunManager, log);
						}
					}
				}
			} catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}

		}

		// #################################################################################
		// Function To Change Conviction Id & add Conviction types in conviction_TD
		// sheet
		// Author : TDI Automation Team
		// Date : October,2017
		// #################################################################################
		public static void makeConvitionIdChange(String textToFind, String RunManager, String sheetName, Logger log,
				String feildName) {
			String TC_ID, convicDesc;
			int firstDriverId;
			convicCount1 = 0;
			convicCount2 = 0;
			convicCount3 = 0;
			convicCount4 = 0;
			convicCount5 = 0;
			convicCount6 = 0;
			convicCount7 = 0;
			convicCount8 = 0;
			convicCount9 = 0;
			convicCount10 = 0;
			suspensionCount1 = 0;
			suspensionCount2 = 0;
			suspensionCount3 = 0;
			suspensionCount4 = 0;
			suspensionCount5 = 0;
			suspensionCount6 = 0;
			suspensionCount7 = 0;
			suspensionCount8 = 0;
			suspensionCount9 = 0;
			suspensionCount10 = 0;
			try {
				ExcelUtilities.setExcelFile(RunManager, sheetName, log);
				int row = ExcelUtilities.getRowNum(log);
				int colNum = ExcelUtilities.getColNum(log);
				for (int i = 1; i <= row; i++) {
					TC_ID = ExcelUtilities.getTcId(i, 0, log);
					convicDesc = ExcelUtilities.getTcId(i, 7, log);
					if (TC_ID.equals(textToFind)) {
						if (convicDesc.equalsIgnoreCase("Driving Permit Suspended < 1 year")
								|| convicDesc.equalsIgnoreCase("Driving Permit Suspended >= 1 year")
								|| convicDesc.equalsIgnoreCase("Administrative Lapse or Suspension")) {
							firstDriverId = ExcelUtilities.getSubId(i, ExcelUtilities.getColumnNum("Driver", colNum), log);
							if (firstDriverId == 1) {
								suspensionCount1 = suspensionCount1 + 1;
								ExcelUtilities.setDataInt(suspensionCount1, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Suspension", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 2) {
								suspensionCount2 = suspensionCount2 + 1;
								ExcelUtilities.setDataInt(suspensionCount2, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Suspension", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 3) {
								suspensionCount3 = suspensionCount3 + 1;
								ExcelUtilities.setDataInt(suspensionCount3, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Suspension", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 4) {
								suspensionCount4 = suspensionCount4 + 1;
								ExcelUtilities.setDataInt(suspensionCount4, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Suspension", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 5) {
								suspensionCount5 = suspensionCount5 + 1;
								ExcelUtilities.setDataInt(suspensionCount5, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Suspension", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 6) {
								suspensionCount6 = suspensionCount6 + 1;
								ExcelUtilities.setDataInt(suspensionCount6, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Suspension", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 7) {
								suspensionCount7 = suspensionCount7 + 1;
								ExcelUtilities.setDataInt(suspensionCount7, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Suspension", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 8) {
								suspensionCount8 = suspensionCount8 + 1;
								ExcelUtilities.setDataInt(suspensionCount8, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Suspension", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 9) {
								suspensionCount9 = suspensionCount9 + 1;
								ExcelUtilities.setDataInt(suspensionCount9, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Suspension", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 10) {
								suspensionCount10 = suspensionCount10 + 1;
								ExcelUtilities.setDataInt(suspensionCount10, i - 1,
										ExcelUtilities.getColumnNum(feildName, colNum), sheetName, RunManager, log);
								ExcelUtilities.setData("Suspension", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							}
						} else {
							firstDriverId = ExcelUtilities.getSubId(i, ExcelUtilities.getColumnNum("Driver", colNum), log);
							if (firstDriverId == 1) {
								convicCount1 = convicCount1 + 1;
								ExcelUtilities.setDataInt(convicCount1, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Conviction", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 2) {
								convicCount2 = convicCount2 + 1;
								ExcelUtilities.setDataInt(convicCount2, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Conviction", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 3) {
								convicCount3 = convicCount3 + 1;
								ExcelUtilities.setDataInt(convicCount3, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Conviction", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 4) {
								convicCount4 = convicCount4 + 1;
								ExcelUtilities.setDataInt(convicCount4, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Conviction", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 5) {
								convicCount5 = convicCount5 + 1;
								ExcelUtilities.setDataInt(convicCount5, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Conviction", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 6) {
								convicCount6 = convicCount6 + 1;
								ExcelUtilities.setDataInt(convicCount6, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Conviction", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 7) {
								convicCount7 = convicCount7 + 1;
								ExcelUtilities.setDataInt(convicCount7, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Conviction", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 8) {
								convicCount8 = convicCount8 + 1;
								ExcelUtilities.setDataInt(convicCount8, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Conviction", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 9) {
								convicCount9 = convicCount9 + 1;
								ExcelUtilities.setDataInt(convicCount9, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Conviction", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							} else if (firstDriverId == 10) {
								convicCount10 = convicCount10 + 1;
								ExcelUtilities.setDataInt(convicCount10, i - 1, ExcelUtilities.getColumnNum(feildName, colNum),
										sheetName, RunManager, log);
								ExcelUtilities.setData("Conviction", i - 1,
										ExcelUtilities.getColumnNum("Convic_Suspen_type", colNum), sheetName, RunManager, log);
							}
						}
					}
				}
			} catch (NullPointerException e) {
				System.out.println("Warning: No value found in DataSheet....");
				log.error("Warning: No value found in DataSheet....");
				log.error(e);
			} catch (Exception e) {
				System.out.println(e);
				log.error(e);
			}

		}

}
