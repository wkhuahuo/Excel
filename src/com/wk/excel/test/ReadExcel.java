package com.wk.excel.test;



import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;

/**
 * Created by wkhua on 16/12/6.
 */
public class ReadExcel {
    public static void main(String[] str){
        String path = "C:\\Users\\wkhua\\Desktop";
        String fileName = "Test.xls";
        File xlsf = new File(path+"\\"+fileName);

        String outFileName = "TestOut.xls";
        File resultxlsf= new File(path+"\\"+outFileName);

        FileInputStream xlsfin=null;
        FileOutputStream xlsfout=null;
        try {
            xlsfin = new FileInputStream(xlsf);
            xlsfout = new FileOutputStream(resultxlsf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(xlsfin==null||xlsfout == null){
            System.out.println("文件不存在");
            return;
        }

        HSSFWorkbook test = null;
        HSSFWorkbook  test2 = null;
        try {
            test = new HSSFWorkbook(xlsfin);
            test2 = new HSSFWorkbook();

            int numOfSheets = test.getNumberOfSheets();
            if(numOfSheets<=0) {
                System.out.println("文档为空");
                return;
            }
            HSSFSheet sheet = test.getSheetAt(numOfSheets - 1);
            HSSFSheet sheet2 = test2.createSheet("Result");

            double[] tmpResult = new double[6];

            for(int i=1;i<=sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);

                Row row2 = sheet2.createRow(i);

                System.out.print(i+" =[ ");
                if(i==1){
                    row2.createCell(1).setCellValue("类型");
                    row2.createCell(2).setCellValue("类型名称");
                }
                for(int j=row.getFirstCellNum();j<row.getLastCellNum();j++){
                    if(j == 0) {
                        row2.createCell(j).setCellValue(row.getCell(j).toString());
                    }
                    if(j>0 && i>1) {

                        tmpResult[j - 1] = Double.valueOf(row.getCell(j).toString());
                        int resultnum = -1;
                        resultnum = MAX(tmpResult);
                        row2.createCell(1).setCellValue(resultnum);
                        row2.createCell(2).setCellValue(nameOfValue(resultnum));

                    }


                    System.out.print(row.getCell(j).toString()+" ");
                }

                System.out.println();
            }
            test2.write(xlsfout);
            xlsfin.close();
            xlsfout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }





    }

    private static String nameOfValue(int resultnum) {
        String[] reflectArr = {"边界图片0","耕地1","林地2","草块3","水体4","其他5"};
        //耕地1，林地2，草地3，水体4，其他5
        return reflectArr[resultnum];
    }

    private static int MAX(double[] tmpResult) {
        String str = new String();

        int max =0;
        for(int i=0;i<tmpResult.length;i++){
            if(tmpResult[i]>tmpResult[max]){
                max = i;
            }
        }
       // String[] reflectArr = {"耕地0","林地1","水体2","草块3","其他4","边界图片5"};
        //边界0,耕地1，林地2，草地3，水体4，其他5
        switch (max){
            case 0: return 1;//耕
            case 1: return 2;//林
            case 2: return 4;//水体
            case 3: return 3;//草地
            case 4: return 5;//其他
            case 5: return 0;//边界
            default: return -1;
        }


    }
}
