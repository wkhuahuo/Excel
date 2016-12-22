package com.wk.ftp.test;

/**
 * 定义了ftp链接常量
 * Created by wkhua on 16/12/8.
 */
public class Ftp {
    private String ipAddr;//ip地址
    private Integer port;//端口号
    private String userName;//用户名
    private String pwd;//密码
    private String path;//路径
    public  Ftp(){

    }
    public Ftp(String ipAddr,Integer port, String userName,String pwd){
        this.ipAddr = ipAddr;
        this.port = port;
        this.userName = userName;
        this.pwd = pwd;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
