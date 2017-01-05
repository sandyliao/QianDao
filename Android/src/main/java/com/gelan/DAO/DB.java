package com.gelan.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Created by yoyo on 2016/12/15.
 * 数据库连接类
 */

public class DB {
    // 数据库的登陆账号密码
    private final String USERNAME = "root";
    private final String PASSWORD = "123456a";
    // JDBC驱动程序
    private final String DRIVER = "com.mysql.jdbc.Driver";
    // 数据库地址
    private final String URL = "jdbc:mysql://192.168.6.234:3306/gelan?useUnicode=true&amp;characterEncoding=UTF-8";
    //private final String URL = "jdbc:mysql://192.168.6.231:3306/yoyotest?useUnicode=true&amp;characterEncoding=UTF-8";
    /**
     * 取得数据库链接
     * @return Connection
     */
    public Connection getConnection(){
        Connection conn =null;
        try {
            Class.forName(DRIVER).newInstance();
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 释放数据库链接
     */
    public void releaseConnection(ResultSet rs,Statement stat,Connection conn){

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
