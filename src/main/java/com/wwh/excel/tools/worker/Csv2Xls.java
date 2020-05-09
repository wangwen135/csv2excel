package com.wwh.excel.tools.worker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.opencsv.CSVReader;

public class Csv2Xls {

    public static final String CHARSET = "UTF-8";

    public static void convert(File inputFile) throws Exception {
        String sourceFile = inputFile.getAbsolutePath();
        String outFile = sourceFile.subSequence(0, sourceFile.lastIndexOf(".")) + ".xlsx";
        ConvertCSVToXLS(sourceFile, outFile);
    }

    public static void ConvertCSVToXLS(String file, String destFile) throws Exception {
        SXSSFWorkbook wb = null;
        CSVReader reader = null;
        try {
            wb = new SXSSFWorkbook(200);
            Sheet sh = wb.createSheet();
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), CHARSET));
//            reader = new CSVReader(new FileReader(file));
            reader = new CSVReader(in);

            int rowNum = 0;
            String[] line;
            while ((line = reader.readNext()) != null) {

                Row row = sh.createRow(rowNum++);
                for (int cellnum = 0; cellnum < line.length; cellnum++) {
                    Cell cell = row.createCell(cellnum);
                    cell.setCellValue(line[cellnum].trim());
                }
                if (rowNum % 200 == 0) {
                    ((SXSSFSheet) sh).flushRows(200);
                    System.out.println(rowNum);
                }
            }
            if (destFile == null) {
                destFile = file.substring(0, file.indexOf(".csv")) + ".xlsx";
            }

            FileOutputStream fileOut = new FileOutputStream(destFile);
            wb.write(fileOut);
            fileOut.close();
            wb.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (wb != null) {
                wb.dispose();
            }
        }
    }
}
