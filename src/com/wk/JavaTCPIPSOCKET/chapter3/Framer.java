package com.wk.JavaTCPIPSOCKET.chapter3;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wkhua on 16/12/22.
 */
public interface Framer {
    void frameMsg(byte[] message, OutputStream out) throws IOException;
    byte[] nextMsg() throws IOException;
}
