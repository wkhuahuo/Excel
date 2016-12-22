package com.wk.excel.test;


import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;


/**
 * Created by wkhua on 16/12/8.
 */
public class TrainPoint {
    public static void main(String[] args){
        String filePath = "C:\\项目文档\\TM数据信息提取结果-20161208.xlsx";
        File xlsxFile = new File(filePath);
        try {
            FileInputStream in = new FileInputStream(xlsxFile);
            XSSFWorkbook result = new XSSFWorkbook(in);
            int numOfSheet = result.getNumberOfSheets();
            XSSFSheet inSheet = result.getSheetAt(0);
            XSSFSheet outSheet = result.getSheetAt(1);



        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
