package file.operations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileOperationsHelper {
    public void createDirectory(String path){
        File folder = new File(path);

        if(!folder.exists()){
            folder.mkdir();
        }
    }

    public void unZip(String inputPath, String outputPath){
        byte[] buffer = new byte[1024];

        createDirectory(outputPath);

        try{
            ZipInputStream zipStream = new ZipInputStream(new FileInputStream(inputPath));

            ZipEntry zipEntry;

            while((zipEntry = zipStream.getNextEntry()) != null){
                String fileName = zipEntry.getName();
                File newFile = new File(outputPath + File.separator + fileName);

                System.out.println("Unzip process started: " + newFile.getAbsolutePath());

                new File(newFile.getParent()).mkdirs(); // Creates all non exists folder

                FileOutputStream outputStream = new FileOutputStream(newFile);

                int size;

                while((size = zipStream.read(buffer)) > 0){
                    outputStream.write(buffer, 0, size);
                }

                outputStream.close();
            }

            zipStream.closeEntry();
            zipStream.close();

            System.out.println("File: " + inputPath + "  ---->  unzip to: " + outputPath);
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public void deleteFolder(String path){
        File dir = new File(path);

        for(File file: dir.listFiles()){
            if(file.isDirectory()){
                deleteFolder(file.getAbsolutePath());
            }

            file.delete();
        }

        dir.delete();
    }

}
