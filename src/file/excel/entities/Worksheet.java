package file.excel.entities;

import java.util.ArrayList;

public class Worksheet {
    private int id;
    private String name;
    private ArrayList<Row> rows;

    public Worksheet(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return id;
    };

    public String getName(){
        return name;
    }

    public ArrayList<Row> getRows(){
        return rows;
    }

    public void setRows(ArrayList<Row> rows){
        this.rows = rows;
    }

}
