package file.excel.entities;

import java.util.ArrayList;

public class Row {
    private ArrayList<Cell> cellList; // ilki map edildigi zaman bizlere row'un idsini verecek

    public void setCellList(ArrayList<Cell> cellList){
        this.cellList = cellList;
    }

    public ArrayList<Cell> getCellList(){
        return cellList;
    }
}
