package com.gelan.BLL;

/**
 * Created by hp on 2016/12/14.
 */

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtils {
    // 数据库的登陆账号密码
    private final String USERNAME = "root";
    private final String PASSWORD = "#######";
    // JDBC驱动程序
    private final String DRIVER = "com.mysql.jdbc.Driver";
    // 数据库地址
    private final String URL = "jdbc:mysql://IP:3306/DataBase";

    // 三个重要类的对象
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet resultSet;

    public JdbcUtils() {
        try {
            // 步骤1：加载驱动程序
            Class.forName(DRIVER);
            // 步骤2：建立连接，这里的处理是当实例化这个工具类对象时就完成这两个步骤
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    // 封装的update函数
    public int update(String sql, List<Object> params) throws SQLException {
        int result = 0;
        // 步骤3：创建一个Statement，添加相关参数
        ps = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++)
                // 注意数据库的下标都是从1开始的，第i个占位符填充params的第i个值
                ps.setObject(i + 1, params.get(i));
        }
        // 步骤4：执行SQL语句，步骤5和6则留给客户端处理
        result = ps.executeUpdate();
        return result;
    }

    // 封装的query函数，返回的是List套个Map，数据库是以键值对的形式存储的
    public List<Map<String, Object>> query(String sql, List<Object> params)
            throws SQLException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        // 步骤3：创建一个Statement，添加相关参数
        ps = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++)
                ps.setObject(i + 1, params.get(i));
        }
        // 步骤4：执行SQL语句
        resultSet = ps.executeQuery();
        // 步骤5：处理执行结果
        // 获取此ResultSet对象的列的属性
        ResultSetMetaData metaData = resultSet.getMetaData();
        // 列的长度
        int col_len = metaData.getColumnCount();
        // 若有下一条记录
        while (resultSet.next()) {
            // 将该条记录以map形式存储
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < col_len; i++) {
                // 根据列名获得键值并存放在map中
                String col_name = metaData.getColumnName(i + 1);
                Object col_value = resultSet.getObject(col_name);
                map.put(col_name, col_value);
            }
            // 将该记录添加到list中
            list.add(map);
        }
        // 遍历完resultSet返回list
        return list;
    }
    //应用反射机制的数据库查询函数
    public <T> List<T> queryRef(String sql, List<Object> params, Class<T> cls)
            throws Exception {
        List<T> list = new ArrayList<>();
        ps = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++)
                ps.setObject(i + 1, params.get(i));
        }
        resultSet = ps.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int col_len = metaData.getColumnCount();
        while (resultSet.next()) {
            // 存在记录时，实例化一个对象
            T record = cls.newInstance();
            for (int i = 0; i < col_len; i++) {
                String col_name = metaData.getColumnName(i + 1);
                Object col_value = resultSet.getObject(col_name);
                // 将列对应的值赋给对象的相应字段
                Field field = cls.getDeclaredField(col_name);
                field.setAccessible(true);
                field.set(record, col_value);
            }
            // 将这条记录添加到List中
            list.add(record);
        }
        return list;
    }
    // 封装步骤6关闭JDBC对象
    public void release() {
        if (resultSet != null)
            try {
                resultSet.close();
            } catch (SQLException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        if (ps != null)
            try {
                ps.close();
            } catch (SQLException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        if (connection != null)
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
    }
}
