package file.excel.operations;

import file.excel.entities.Cell;
import file.excel.entities.Row;
import file.excel.entities.Workbook;
import file.excel.entities.Worksheet;

import java.util.ArrayList;

public class ExcelOperations {
    private Workbook workbook;

    public ExcelOperations(){}

    public void setWorkbook(Workbook workbook){
        this.workbook = workbook;
    }

    public String getRowInCsv(int sheedId, int row){
        Worksheet workSheet = workbook.getSheets().get(sheedId);
        return getRowInCsv(workSheet, row);
    }

    public String getRowInCsv(Worksheet worksheet, int row){
        ArrayList<Cell> cellList = worksheet.getRows().get(row).getCellList();
        String rowValue = "";

        for(Cell cell: cellList){
            rowValue += cell.getValue() + ";";
        }

        return rowValue;
    }

    public ArrayList<String> getAllRowsInCsv(int sheetId){
        Worksheet worksheet = workbook.getSheets().get(sheetId);
        return getAllRowsInCsv(worksheet);
    }

    public ArrayList<String> getAllRowsInCsv(Worksheet worksheet){
        ArrayList<Row> rowList = worksheet.getRows();
        ArrayList<String> rowsInCsv = new ArrayList<String>();

        for(int i = 0; i < rowList.size(); i++){
            rowsInCsv.add(getRowInCsv(worksheet, i));
        }

        return rowsInCsv;
    }

    public int getSheetId(String sheetName){
        ArrayList<Worksheet> worksheets = workbook.getSheets();
        int id = -1;

        for(Worksheet worksheet: worksheets){
            if(worksheet.getName().equalsIgnoreCase(sheetName)){
                id = worksheet.getId();
            }
        }

        return id;
    }

}
