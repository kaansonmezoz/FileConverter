package file.excel.operations;

import file.excel.entities.Row;
import file.excel.entities.Workbook;
import file.excel.entities.Worksheet;
import file.operations.FileOperationsHelper;

import java.io.*;
import java.util.ArrayList;

public class ExcelReader implements file.operations.FileReader{

    public Object readFile(String sourcePath, String fileName){
        String zipOutputPath = sourcePath + File.separator + "_temp_";

        FileOperationsHelper fileHelper = new FileOperationsHelper();
        fileHelper.unZip(sourcePath + File.separator + fileName, zipOutputPath);

        Workbook workbook = new Workbook(fileName);
        ExcelParser excelHelper = new ExcelParser();

        ArrayList<String> sharedStrings = readSharedString(zipOutputPath, excelHelper);

        workbook.setSheets(readWorkbook(zipOutputPath, excelHelper));

        for(Worksheet sheet: workbook.getSheets()){
            sheet.setRows(readWorksheet(zipOutputPath, sheet.getId(), excelHelper));
            excelHelper.mapCellValues(sheet.getRows(), sharedStrings);
        }

        fileHelper.deleteFolder(zipOutputPath);

        return workbook;
    }

    private ArrayList<Worksheet> readWorkbook(String zipOutputPath, ExcelParser operationsHelper){
        String workbookFolder = zipOutputPath + File.separator + "xl";
        String workbookFileName = "workbook.xml";

        ArrayList<Worksheet> worksheetList = operationsHelper.parseWorkbookXml(workbookFolder + File.separator + workbookFileName);

        return worksheetList;
    }

    private ArrayList<Row> readWorksheet(String zipOutputPath, int worksheetId, ExcelParser operationsHelper){
        String worksheetFolder = zipOutputPath + File.separator + "xl" + File.separator + "worksheets";
        String worksheetFileName = "sheet" + worksheetId + ".xml";

        return operationsHelper.parseSheetXml(worksheetFolder + File.separator + worksheetFileName);
    }

    private ArrayList<String> readSharedString(String zipOutputPath, ExcelParser operationsHelper){
        String worksheetFolder = zipOutputPath + File.separator + "xl";
        String worksheetFileName = "sharedStrings.xml";

        return operationsHelper.parseSharedStringsXml(worksheetFolder + File.separator + worksheetFileName);
    }
}
