package file.excel.operations;

import file.excel.entities.Cell;
import file.excel.entities.Row;
import file.excel.entities.Worksheet;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;

public class ExcelParser {
    //TODO: Excel parser yapalim bu class'in adini ayrica belki de buradaki parse etme islemleri degisebilir excel'in formatina
    //TODO: gore. Dolayisiyla birden fazla farkli tipte parser olabilir. Bu parserlari yaratmak adina da baska bir islem yapilabilir
    //TODO: yani FactoryPattern kullanabiliriz burada cok da temiz olur sanki
    //TODO: SAX exceptionları ile ilgili olarak hic ugrasmadim. Onlari da bir handle etmek lazim
    public ArrayList<Worksheet> parseWorkbookXml(String workbookPath){   // Todo: burada da factory pattern uygulanabilir parse etme islemi benzer olacak handler kismi falan filan sadece alinacak attribute'lar hangi xml dosyasi icerisinden alinacagina bagli olarak. hani loglama dosyalarinda yapmistik ya onun gibi bir sey yapilabilir.
        ArrayList<Worksheet> worksheetList = new ArrayList<Worksheet>();

        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler(){
                boolean isSheetElement = false;

                public void startElement(String uri, String localName, String element, Attributes attributes) throws SAXException{
                    System.out.println("Start element: " + element);

                    if(element.compareTo("sheet") == 0){
                        int sheetId = Integer.valueOf(attributes.getValue("sheetId"));
                        String sheetName = attributes.getValue("name");

                        Worksheet worksheet = new Worksheet(sheetId, sheetName);
                        worksheetList.add(worksheet);
                    }

                }

                public void endElement(String uri, String localName, String element) throws SAXException{

                }

                public void characters(char ch[], int start, int length) throws SAXException{
                    if(isSheetElement){
                        isSheetElement = false;
                    }
                }
            };

            File file = new File(workbookPath);
            InputStream inputStream = new FileInputStream(file);
            Reader reader = new InputStreamReader(inputStream, "UTF-8");

            InputSource source = new InputSource(reader);

            source.setEncoding("UTF-8");

            saxParser.parse(source, handler);

            reader.close();
            inputStream.close();
        }catch(Exception exception){
            exception.printStackTrace();
        }

        return worksheetList;
    }

    public ArrayList<Row> parseSheetXml(String sheetPath){
        ArrayList<Row> rowList = new ArrayList<Row>();

        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler(){
                ArrayList<Cell> cellList =  null;
                boolean isCellElement = false;

                Row row = null;

                public void startElement(String uri, String localName, String element, Attributes attributes) throws SAXException{
                    System.out.println("Start element: " + element);

                    if(element.compareTo("row") == 0){
                        row = new Row();
                        cellList = new ArrayList<Cell>();

                        row.setCellList(cellList);
                    }
                    else if(element.compareTo("c") == 0){
                        Cell cell = new Cell();

                        cellList.add(cell);
                        isCellElement = true;
                    }
                }

                public void endElement(String uri, String localName, String qName) throws SAXException{
                    if(qName.compareTo("row") == 0){
                        rowList.add(row);
                    }
                }

                public void characters(char ch[], int start, int length) throws SAXException{
                    if(isCellElement){
                        cellList.get(cellList.size() - 1).setStringIndex(Integer.valueOf(new String(ch, start, length)));
                        isCellElement = false;
                    }
                }
            };

            File file = new File(sheetPath);
            InputStream inputStream = new FileInputStream(file);
            Reader reader = new InputStreamReader(inputStream, "UTF-8");

            InputSource source = new InputSource(reader);

            source.setEncoding("UTF-8");

            saxParser.parse(source, handler);

            reader.close();
            inputStream.close();
        }catch(Exception exception){
            exception.printStackTrace();
        }

        return rowList;
    }



    //TODO: handler kisimlarında kod tekrarı var belki de oralara bir düzen çekmek gerekebilir
    //TODO: design pattern kullanılabilir olası kod tekrarlarını azaltabilmek adina.
    public ArrayList<String> parseSharedStringsXml(String sharedStringsPath){
        ArrayList<String> valueList = new ArrayList<String>();

        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler(){
                boolean isTElement = false;

                public void startElement(String uri, String localName, String element, Attributes attributes) throws SAXException{
                    System.out.println("Start element: " + element);

                    if(element.compareTo("t") == 0){
                        isTElement = true;
                    }
                }

                public void endElement(String uri, String localName, String element) throws SAXException{
                    System.out.println("End element:" + element);
                }

                public void characters(char ch[], int start, int length) throws SAXException{
                    System.out.println(new String(ch, start, length)); // buradaki eleman <n>x</n> ise x'in yazilmasini sagliyor

                    if(isTElement){
                        valueList.add(new String(ch, start, length));

                        isTElement = false;
                    }
                }
            };

            File file = new File(sharedStringsPath);
            InputStream inputStream = new FileInputStream(file);
            Reader reader = new InputStreamReader(inputStream, "UTF-8");

            InputSource source = new InputSource(reader);

            source.setEncoding("UTF-8");

            saxParser.parse(source, handler);

            reader.close();
            inputStream.close();
        }catch(Exception exception){
            exception.printStackTrace();
        }

        return valueList;
    }


    public void mapCellValues(ArrayList<Row> rowList, ArrayList<String> valueList){
        for(Row row: rowList){
            for(Cell cell: row.getCellList()){
                String value = valueList.get(Integer.valueOf(cell.getStringIndex()));
                 cell.setValue(value);
            }
        }
    }

}
