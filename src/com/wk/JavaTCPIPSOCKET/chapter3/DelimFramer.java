package com.wk.JavaTCPIPSOCKET.chapter3;

import java.io.*;

/**
 * Created by wkhua on 16/12/22.
 */
public class DelimFramer implements Framer{

    private InputStream in;//dataSource
    private static final byte DELINITER = '\n'; //message delimiter;单字节界定符

    public DelimFramer(InputStream in){
        this.in = in;
    }

    /**
     * 实现对信息流的扫描。如果出现界定词，则替换界定词。本方法直接抛出异常了。
     * @param message
     * @param out
     * @throws IOException
     */
    @Override
    public void frameMsg(byte[] message, OutputStream out) throws IOException {
        //ensure that the message does not contains delimiter
        for(byte b : message){
            if(b == DELINITER){
                throw new IOException("Message contains delimiter");
            }
        }
        out.write(message);
        out.write(DELINITER);
        out.flush();
    }

    @Override
    public byte[] nextMsg() throws IOException {
        ByteArrayOutputStream messageBuffer = new ByteArrayOutputStream();
        int nextByte;

        //fetch bytes util find delimiter
        while((nextByte = in.read()) != DELINITER){
            if(nextByte == -1){
                if(messageBuffer.size() ==0 ){// if no byte read
                    return null;
                }else{//if bytes followed by end of stream: framing error
                    throw new EOFException("Non-empty message without delimiter");
                }

            }
            messageBuffer.write(nextByte);//write byte to buffer
        }
        return messageBuffer.toByteArray();
    }
}
