package file.excel.entities;

public class Cell {
    private String value;
    private int stringIndex; //SharedStrings.xml'deki ilgili deger.

    public Cell(){}

    public void setStringIndex(int stringIndex){
        this.stringIndex = stringIndex;
    }

    public void setValue(String value){
        this.value = value;
    }

    public int getStringIndex(){
        return stringIndex;
    }

    public String getValue(){
        return value;
    }
}
