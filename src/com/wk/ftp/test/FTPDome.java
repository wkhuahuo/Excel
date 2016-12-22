package com.wk.ftp.test;


import org.apache.commons.net.ftp.FTPFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by wkhua on 16/12/7.
 */
public class FTPDome {
    Ftp ftpSerInfo =null;
    FtpUtil ftputil = new FtpUtil();
    @Before
    public void connected(){
        String ipAddr = "10.103.240.73";//ip地址
        Integer port =null;//端口号
        String userName = "Share";//用户名
        String pwd = "yang_1290";//密码

        ftpSerInfo = new Ftp(ipAddr,port,userName,pwd);
        ftputil.connectFtp(ftpSerInfo);

        System.out.println("Connecting Succeed...");
    }

    @Test
    public void checkFiles(){
        String[] strNames = ftputil.listFiles("/Hello/Socket示例");
        for(String str : strNames){
            System.out.println(str);
        }
    }

    @Test
    public void upload(){


        File file = new File("C:\\Users\\wkhua\\Desktop\\TestOut.xls");
        ftputil.upload(file,"/Hello/SocketDemo");
        System.out.println("Uplod Succeed...");



    }

    @Test
    public void download(){
       System.out.println("准备下载文件...");

        ftputil.startDown(ftpSerInfo,"C:\\Users\\wkhua\\Desktop\\","/Hello/SocketDemo");
        System.out.println("下载文件成功。");
    }

    @After
    public void closeConnected(){
        ftputil.closeFtp();
    }
}
