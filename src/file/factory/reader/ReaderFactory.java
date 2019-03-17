package file.factory.reader;

import file.exception.FileOperationsException;
import file.excel.operations.ExcelReader;
import file.operations.FileReader;

import java.util.Arrays;
import java.util.List;

public class ReaderFactory {
    public ReaderFactory(){}

    public FileReader getReader(String fileName){
        if(isExcelFile(fileName)){
            return new ExcelReader();
        }
        else{ //TODO: Every new supported file's reader will be created in this factory.
            String errorMessage = "Filename: " + fileName + " is not a supported file format";
            throw new FileOperationsException(errorMessage);
        }
    }

    private boolean isExcelFile(String fileName){
        List<String> excelExtensions = Arrays.asList("xlsx", "xls", "xlsm", "xlm", "xlt", "xltx", "xltm", "xla", "xlam");
        String fileExtension;

        try{
            fileExtension = fileName.split("\\.")[1];
        }catch(ArrayIndexOutOfBoundsException exception){
            String errorMessage = "Filename should includes the extension";
            throw new FileOperationsException(errorMessage);
        }

        return excelExtensions.contains(fileExtension);
    }
}
