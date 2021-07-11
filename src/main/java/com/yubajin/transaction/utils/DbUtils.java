package com.yubajin.transaction.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtils {
    private static final String DRIVERNAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL="jdbc:mysql://localhost:3306/clouddb01?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    private static Connection conn = null;

    public static Connection getConnection(){
        if(conn == null){
            try {
                Class.forName(DRIVERNAME);
                conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}
