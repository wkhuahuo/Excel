package com.wk.CodeProblem;

/**
 * Created by wkhua on 16/12/6.
 */
public class EnDeCode {

    public String encode(String str){


        return str;
    }
    public String decode(String str){
        return str;
    }

    public static void main(String[] args){
        String str = "你好，我是王锴。hello! I'm wk.";

        EnDeCode endeCode = new EnDeCode();

        String enstr = endeCode.encode(str);
        System.out.println("encode: "+enstr);

        String destr = endeCode.decode(enstr);
        System.out.println("decode: "+destr);

        try {
            byte[] b1 = str.getBytes("UTF-8");
            String str2 = new String(b1,"UTF-8");
            System.out.println(str2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
