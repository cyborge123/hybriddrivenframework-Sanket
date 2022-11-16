package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOperations {

	public static Object[][] readExcelData(String inputFile, String sheetName) throws IOException {

		// *** Open the file first
		File file = new File(inputFile); // Open File
		FileInputStream inputStream = new FileInputStream(file); // give file read-only access

		// *** We need to create Workbook --> Interface / to read xlsx --> XSSFWorkbook
		Workbook wb = new XSSFWorkbook(inputStream); // need to open inputStream file as Excel Sheet

		// *** We need to tell that which sheet need to open from workbook
		Sheet sheet = wb.getSheet(sheetName);
		int totalRows = sheet.getLastRowNum();
		int totalCols = sheet.getRow(0).getLastCellNum();

//		System.out.println("Total Rows : " + totalRows);
//		System.out.println("Total Columns : " + totalCols);

		Object[][] data = new Object[totalRows][totalCols];

		for (int rowIndex = 0; rowIndex < totalRows; rowIndex++) {

			for (int colIndex = 0; colIndex < totalCols; colIndex++) {

				Cell cell = sheet.getRow(rowIndex + 1).getCell(colIndex);
				if (cell.getCellType() == CellType.STRING)
					data[rowIndex][colIndex] = cell.getStringCellValue();
				else if (cell.getCellType() == CellType.BOOLEAN)
					data[rowIndex][colIndex] = cell.getBooleanCellValue();
				else if (cell.getCellType() == CellType.NUMERIC)
					if (DateUtil.isCellDateFormatted(cell)) {
						// SimpleDateFormat formatter = new SimpleDateFormat("DD-MM-YYYY");
						data[rowIndex][colIndex] = cell.getDateCellValue();
					} else
						data[rowIndex][colIndex] = cell.getNumericCellValue();
			}
		}
		return data;
	}
}