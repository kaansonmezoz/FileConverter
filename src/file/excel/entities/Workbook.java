package file.excel.entities;

import java.util.ArrayList;

public class Workbook {
    private String name; // dosyanin adina denk duser
    private ArrayList<Worksheet> sheets;

    public Workbook(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public ArrayList<Worksheet> getSheets(){
        return sheets;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSheets(ArrayList<Worksheet> sheets){
        this.sheets = sheets;
    }
}
