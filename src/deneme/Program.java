package deneme;

import file.operations.FileOperations;

public class Program {
    public static void main(String args[]){
        FileOperations fileOperations= new FileOperations();
        String fileName = "siparis ve bayi koordinatları.xlsx";
        String sourcePath = "C:\\Users\\user\\Desktop\\Yeni klasör (2)";
        String destinationPath = sourcePath;
        fileOperations.excelToCsv(sourcePath, fileName, destinationPath);
    }
}
