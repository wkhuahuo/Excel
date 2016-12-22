package com.wk.NoteLearnningJava.test;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wkhuahuo on 16/10/14.
 */
public class LambdaTest1 {
    @Test
    public void testLambda(){

        List<String> names = Arrays.asList("Justin", "Monica", "Irene");
        for(String name: names){
            System.out.println(name);
        }


    }
}
