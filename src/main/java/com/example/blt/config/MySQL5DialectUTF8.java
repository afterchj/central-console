package com.example.blt.config;

import org.hibernate.dialect.MySQL55Dialect;

/**
 * @author hongjian.chen
 * @date 2019/7/23 10:37
 */
public class MySQL5DialectUTF8 extends MySQL55Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}