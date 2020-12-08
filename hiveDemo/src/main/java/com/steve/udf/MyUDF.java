package com.steve.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

public class MyUDF extends UDF {

    public String evaluate (final String s) {
        if(s!=null){
           return s.toLowerCase();
        }
        return s;
    }
}
