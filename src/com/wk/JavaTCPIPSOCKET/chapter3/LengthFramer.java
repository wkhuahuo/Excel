package com.wk.JavaTCPIPSOCKET.chapter3;

import java.io.*;

/**
 * Created by wkhua on 16/12/22.
 */
public class LengthFramer implements Framer{

    public static final int MAXMESSAGELENGTH = 65535;//一帧的最大长度
    public static final int BYTEMASK = 0xff; //两个字节
    public static final int SHORTMASK = 0xffff;//信息的编码长度
    public static final int BYTESHIFT = 8;//一个字节长度

    private DataInputStream in; //wrapper for data I/O
    public LengthFramer(InputStream in) throws IOException{
        this.in =new DataInputStream(in);
    }

    /**
     * 生成一帧消息按big-endian顺序即网络字节序
     * @param message
     * @param out
     * @throws IOException
     */
    @Override
    public void frameMsg(byte[] message, OutputStream out) throws IOException {
        if(message.length>MAXMESSAGELENGTH){
            throw new IOException("message to long");
        }
        out.write((message.length >> BYTESHIFT) & BYTEMASK);
        out.write(message.length & BYTEMASK);
        out.write(message);
        out.flush();
    }

    @Override
    public byte[] nextMsg() throws IOException {
        int length;
        try{
            length = in.readUnsignedShort(); //read 2 bytes
        }catch (EOFException e){//no (or 1 byte) message
            e.printStackTrace();
            return null;
        }
        byte[] msg = new byte[length];
        in.readFully(msg);// if exception, it's a framing error.
        return msg;
    }
}
