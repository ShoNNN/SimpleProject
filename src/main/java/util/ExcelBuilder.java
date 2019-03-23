package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelBuilder {

    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet workbookSheet = workbook.createSheet("Load");

    public void fillSheetLoad(int n){

        XSSFRow row = workbookSheet.createRow(n);

        int temp = 0;
        for(int i = 0; i < FileParser.getListOftestInfo().size(); i++){

            HashMap<String, double []> map = FileParser.getListOftestInfo().get(i);
            for(int j = 0; j < 4; j++){

                Cell cell = row.createCell(temp);
                cell.setCellValue(map.get(FileParser.getServerNameArray()[i])[j]);
                temp++;
            }
        }
    }

    public void write(String fileName){
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(fileName));
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File " + fileName + " not found!!!");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IOException");
            ex.printStackTrace();
        }
    }

}
