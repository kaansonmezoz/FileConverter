package file.operations;

import file.excel.entities.Workbook;
import file.excel.entities.Worksheet;
import file.excel.operations.ExcelOperations;
import file.exception.FileOperationsException;
import file.factory.reader.ReaderFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileOperations {
    public void excelToCsv(String sourcePath, String fileName, String destinationPath){
        Workbook workbook = (Workbook) readFile(sourcePath, fileName);
        ArrayList<Worksheet> sheetList = workbook.getSheets();

        FileOperationsHelper fileOperationsHelper = new FileOperationsHelper();

        String destinationFolder = destinationPath + File.separator + workbook.getName().split("\\.")[0];
        fileOperationsHelper.createDirectory(destinationFolder);

        ExcelOperations excelOperations = new ExcelOperations();

        for(Worksheet worksheet: sheetList){
            String rows = String.join(System.lineSeparator(), excelOperations.getAllRowsInCsv(worksheet));
            String filePath = destinationFolder + File.separator + worksheet.getName() + ".csv";

            try{
                FileOutputStream outputStream = new FileOutputStream(filePath);
                outputStream.write(rows.getBytes());
                outputStream.flush();
                outputStream.close();
            }catch(IOException exception){
                exception.printStackTrace();
                throw new FileOperationsException(exception.getMessage());
            }

        }
    }

    public Object readFile(String sourcePath, String fileName){
        System.out.println("Filename: " + fileName);
        FileReader fileReader = new ReaderFactory().getReader(fileName);
        return fileReader.readFile(sourcePath, fileName);
    }
}
