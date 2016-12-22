package com.wk.http.test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;


/**
 * Created by wkhua on 16/12/6.
 */
public class Http {

    public static byte[]  readStreamgetByteArr(InputStream inStream){
        OutputStream outStream = new ByteArrayOutputStream();
        int buffSize=1024;
        byte buffer[] = new byte[buffSize];
        int size =0;
        try {
            while((size = inStream.read(buffer))!=-1){
                outStream.write(buffer,0,size);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return outStream.toString().getBytes();
    }

    public static void main(String[] args){

        String urlStr = "http://10.205.34.239:8080/DataInterface/assetInterface/sendAssetAndLpar";

        String parameter = "wk=wk";

        URL url = null;
        try {
            url = new URL(urlStr);

            HttpURLConnection conurl = (HttpURLConnection) url.openConnection();
            conurl.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
            conurl.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            conurl.setRequestMethod("POST");
            conurl.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conurl.getOutputStream());
            wr.writeBytes(parameter);
            wr.flush();
            wr.close();

            InputStream in = conurl.getInputStream();

            System.out.println("##: ");



            //InputStreamReader read = new InputStreamReader(in,"ISO-8859-1");
            //InputStreamReader read = new InputStreamReader(in,"GBK");
            InputStreamReader read = new InputStreamReader(in,"utf-8");//ISO-8859-1
            System.out.println("encode: "+read.getEncoding());
            BufferedReader br = new BufferedReader(read);
            String strLine;
            StringBuffer strBuffer = new StringBuffer();
            while((strLine = br.readLine())!=null){
                System.out.println(strLine);
                strBuffer.append(strLine);
            }
            String strutf = strBuffer.toString();
            String strNew = URLDecoder.decode(strutf,"utf-8");
            System.out.println("strBuffer: "+strutf);
            System.out.println("strNew: "+strNew);
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
