package com.wk.ftp.test;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import sun.nio.cs.ext.GBK;

import java.io.*;
import java.nio.charset.Charset;

/**
 * 提供相关方法
 * Created by wkhua on 16/12/8.
 */
public class FtpUtil {
    //private static Logger logger = Logger.getLogger(FtpUtil.class);
    private static FTPClient ftpClient;

    /**
     * 获取链接
     *
     */
    public  boolean connectFtp(Ftp ftpSerInfo) {
        ftpClient = new FTPClient();
        boolean flag = false;

        int reply ;
        if(ftpSerInfo.getPort()==null){
            ftpSerInfo.setPort(ftpClient.getDefaultPort());
        }
        try {
            ftpClient.connect(ftpSerInfo.getIpAddr(), ftpClient.getDefaultPort());
            ftpClient.login(ftpSerInfo.getUserName(),ftpSerInfo.getPwd());
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding("GBK");
            ftpClient.setCharset(Charset.forName("GBK"));
            reply = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)){
                ftpClient.disconnect();
                return flag;
            }
            ftpClient.changeWorkingDirectory(ftpSerInfo.getPath());
            flag = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 关闭ftp链接
     */
    public  void closeFtp(){
        if(ftpClient!=null && ftpClient.isConnected()){
            try{
                ftpClient.logout();
                ftpClient.disconnect();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取指定目录下的所有文件
     */
    public  String[] listFiles(String remoteDir){
        String[] filesName = null;
        try {
            boolean changeDir = ftpClient.changeWorkingDirectory(remoteDir);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            filesName = new String[ftpFiles.length];
            for(int i=0; i<ftpFiles.length;i++){
                filesName[i] = ftpFiles[i].getName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (filesName ==null){
            filesName = new String[]{""};
        }
        return filesName;

    }

    /**
     * ftp上传文件到指定目录
     */
    public  void upload(File upFile, String remoteDir){
        try {

            if(ftpClient.changeWorkingDirectory(remoteDir)) {

                if (upFile.isDirectory()) {
                    try {
                        ftpClient.makeDirectory(upFile.getName());
                        ftpClient.changeWorkingDirectory(upFile.getName());
                        String[] filesName = upFile.list();
                        for (String fileName : filesName) {
                            File innerDir = new File(upFile.getPath() + "/" + fileName);
                            if (innerDir.isDirectory()) {
                                upload(innerDir);
                                ftpClient.changeToParentDirectory();
                            } else {
                                File innerFile = new File(upFile.getPath() + "/" + fileName);
                                FileInputStream fileInStream = new FileInputStream(innerFile);
                                ftpClient.storeFile(innerFile.getName(), fileInStream);
                                fileInStream.close();
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    File file = new File(upFile.getPath());
                    try {
                        FileInputStream fileInStream = new FileInputStream(file);
                        ftpClient.storeFile(file.getName(), fileInStream);
                        fileInStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("目录指定错误,上传失败");
            e.printStackTrace();
        }
    }

    /**
     * ftp上传文件到根目录
     *
     */
    public  void upload(File upFile){
        upload(upFile,"/");
    }

    /**
     * 获得下载连接配置
     *
     */
    public  void startDown(Ftp ftpSerInfo, String localBaseDir, String remoteBaseDir){
        if(connectFtp(ftpSerInfo)){
            try{
                FTPFile[] ftpFiles =null;
                try {
                    boolean changeDir = ftpClient.changeWorkingDirectory(remoteBaseDir);
                    if(changeDir){
                        ftpClient.setControlEncoding("GBK");
                        ftpFiles = ftpClient.listFiles();
                        for(int i = 0;i<ftpFiles.length;i++){
                            System.out.println(ftpFiles[i].getName());
                            try{
                                downloadFile(ftpFiles[i], localBaseDir, remoteBaseDir);;

                            }catch (Exception e){
                                System.out.println("下载失败。");
                                e.printStackTrace();
                            }
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }catch(Exception e){
                System.out.println("下载过程中出现异常。");
            }
        }else{
            System.out.println("连接失败！");
        }
    }
    /**
     * ftp下载文件
     */
    private static void downloadFile(FTPFile ftpFile, String relativeLocalPath, String relativeRemotePath){
        if(ftpFile.isFile()){
            if (ftpFile.getName().indexOf("?") == -1) {//这句是什么意思？

                OutputStream outputStream = null;
                try {
                    File localFile = new File(relativeLocalPath + ftpFile.getName());
                    if (localFile.exists()) {
                        System.out.print("本地文件已经存在");
                        return;
                    } else {
                        outputStream = new FileOutputStream(relativeLocalPath + ftpFile.getName());
                        ftpClient.retrieveFile(ftpFile.getName(),outputStream);
                        outputStream.flush();
                        outputStream.close();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(outputStream !=null){
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }//
        }else {//ifFile
            String newlocalrelatePath = relativeLocalPath+ftpFile.getName();
            String newRomote = new String(relativeRemotePath+ftpFile.getName().toString());
            File newLocalF = new File(newlocalrelatePath);
            if(!newLocalF.exists()){
                newLocalF.mkdir();
            }
            newlocalrelatePath = newlocalrelatePath+'/';
            newRomote = newRomote+"/";
            String currentWorkDir = ftpFile.getName().toString();
            boolean changeDir = false;
            try {
                changeDir = ftpClient.changeWorkingDirectory(currentWorkDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(changeDir){
                FTPFile[] files = null;
                try {
                    files = ftpClient.listFiles();
                    for(int i = 0;i<files.length;i++){
                        downloadFile(files[i],newlocalrelatePath,newRomote);
                    }
                    ftpClient.changeToParentDirectory();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }//else is dir

    }//downloadFile

}
