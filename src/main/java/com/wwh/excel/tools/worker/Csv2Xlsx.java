package com.wwh.excel.tools.worker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.opencsv.CSVReader;

/**
 * 文件转换工具类
 * 
 * @author wwh
 *
 */
public class Csv2Xlsx {

    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * @param inputFile  输入的CSV文件
     * @param outputFile 输出文件
     * @param charset    原文件编码格式
     * @throws Exception
     */
    public static boolean convert(String inputFile, String outputFile, String charset) throws Exception {
        if (StringUtils.isBlank(inputFile)) {
            System.err.println("原文件不能为空！");
            return false;
        }

        int lastIndexOf = inputFile.lastIndexOf(".");
        if (lastIndexOf == -1) {
            System.err.println("输入文件必须是.CSV格式的文件！");
            return false;
        }
        String suffix = inputFile.substring(lastIndexOf);
        if ("CSV".equalsIgnoreCase(suffix)) {
            System.err.println("输入文件必须是.CSV格式的文件！");
            return false;
        }

        // 判断文件是否存在
        File input = new File(inputFile);
        if (!input.exists()) {
            System.err.println("原文件【" + inputFile + "】不存在！");
            return false;
        }
        // 输出文件为空时默认在在当前目录生成同名的.xlsx文件
        if (StringUtils.isBlank(outputFile)) {
            outputFile = inputFile.subSequence(0, lastIndexOf) + ".xlsx";
        }

        if (StringUtils.isBlank(charset)) {
            charset = DEFAULT_CHARSET;
        }

        return ConvertCsvToXlsx(inputFile, outputFile, charset);
    }

    public static void convert(File inputFile) throws Exception {
        String sourceFile = inputFile.getAbsolutePath();
        String outFile = sourceFile.subSequence(0, sourceFile.lastIndexOf(".")) + ".xlsx";
        ConvertCsvToXlsx(sourceFile, outFile, DEFAULT_CHARSET);
    }

    public static boolean ConvertCsvToXlsx(String file, String destFile, String charset) throws Exception {
        if (!Charset.isSupported(charset)) {
            System.out.println("不支持的字符集：" + charset);
            return false;
        }

        SXSSFWorkbook wb = null;
        CSVReader reader = null;
        try {
            wb = new SXSSFWorkbook(200);
            Sheet sh = wb.createSheet();
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
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
        return true;
    }
}
