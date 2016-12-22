package com.wk.chapter1;

import java.util.concurrent.Exchanger;

/**
 * Created by wkhua on 16/12/6.
 */
public class LearnExchanger {
    public void test(){
        try{
            Exchanger<String> exch = new Exchanger<String>();
            System.out.println("exchange: "+exch.exchange("ChinaA"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
