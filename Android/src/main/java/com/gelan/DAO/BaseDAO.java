package com.gelan.DAO;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoyo on 2016/12/15.
 * DAO基类
 */

public class BaseDAO {
    StandardSQL standardSQL = new StandardSQL();
    DB db = new DB();

    /**
     * 执行插入操作
     * @param o
     * @return
     * @throws Exception
     */
    public int add(Object o) throws Exception{
        String sql = standardSQL.add(o);
        return executeUpdate(sql);
    }
    public int addRollBack(Object o) throws Exception{
        String sql = standardSQL.add(o);
        return executeUpdateRollBack(sql);
    }

    /**
     * 执行修改操作
     * @param o
     * @return
     * @throws Exception
     */
    public int update(Object o) throws Exception{
        String sql = standardSQL.update(o);
        return executeUpdate(sql);
    }

    /**
     * 执行删除操作
     * @param o
     * @return
     * @throws Exception
     */
    public int delete(Object o) throws Exception{
        String sql = standardSQL.delete(o);
        return executeUpdate(sql);
    }

    /**
     * 执行查询操作
     * @param o
     * @return List
     * @throws Exception
     */
    public List query(Object o) throws Exception{
        String sql = standardSQL.query(o);
        return executeQuery(sql, o);
    }

    /**
     * 执行查询操作
     * @param o
     * @return List
     * @throws Exception
     */
    public List query(Object o,String wheresql) throws Exception{
        String sql = standardSQL.query(o);
        if(wheresql!=null && wheresql.length() > 0){
            sql+=" and "+wheresql;
        }
        return executeQuery(sql, o);
    }
    public List query(Object o,String wheresql,int top) throws Exception{
        String sql = standardSQL.query(o);
        if(wheresql!=null && wheresql.length() > 0){
            sql+=" and "+ wheresql;
        }
        if(top>0){
            sql+=" limit "+top;
        }
        return executeQuery(sql, o);
    }

    /**
     * 根据SQL语句执行修改数据库方面的操作
     * @param sql
     * @return
     * @throws Exception
     */
    public int executeUpdate(String sql){
        System.out.println("[Execeute SQL] "+sql);
        int set = 0;
        Connection conn = null;
        Statement stat = null;
        try{
            conn = db.getConnection();
            stat = conn.createStatement();
            set = stat.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.releaseConnection(null, stat, conn);
        }
        return set;
    }

    public int executeUpdateRollBack(String sql) throws SQLException {
        System.out.println("[Execeute SQL] "+sql);
        int set = 0;
        Connection conn = null;
        Statement stat = null;
        try{
            conn = db.getConnection();
            conn.setAutoCommit(false);
            stat = conn.createStatement();
            set = stat.executeUpdate(sql);
            conn.commit();
        }catch(Exception e){
            if (conn!=null) {
                conn.rollback();
            }
            e.printStackTrace();
        }finally{
            db.releaseConnection(null, stat, conn);
        }
        return set;
    }


    /**
     * 根据SQL语句执行查询数据库方面的操作
     * @param sql
     * @return List
     * @throws Exception
     */
    public List executeQuery(String sql, Object o){
        System.out.println("[Execeute SQL] "+sql);
        List list = new ArrayList();
        Connection conn = null;
        Statement stat = null;
        try{
            conn = db.getConnection();
            stat = conn.createStatement();
            stat.executeQuery(sql);
            ResultSet rs = stat.getResultSet();
            int total = rs.getMetaData().getColumnCount();
            while(rs.next()){
                Object obj = this.getObj(rs, o);
                list.add(obj);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.releaseConnection(null, stat, conn);
        }
        return list;
    }

    /**
     * 根据id从数据库中取得实例
     * @return Object
     * @throws Exception
     */
    public Object getObject(Object o, String id){
        Object bean = null;
        Connection conn = null;
        Statement stat = null;
        try{
            conn = db.getConnection();
            Object newObj = o.getClass().newInstance();
            String sql = standardSQL.query(newObj);
            sql += " where id='"+id+"'";
            stat = conn.createStatement();
            stat.executeQuery(sql);
            ResultSet rs = stat.getResultSet();
            while(rs.next()){
                bean = getObj(rs,o);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.releaseConnection(null, stat, conn);
        }
        return bean;
    }

    /**
     * 根据ResultSet返回结果得到Object
     * @param rs
     * @param o
     * @return
     * @throws Exception
     */
    private Object getObj(ResultSet rs,Object o) throws Exception{
        if(o==null){
            return null;
        }
        Class clazz = o.getClass();
        Object newObject =clazz.getConstructor(new Class[]{}).newInstance(new Object[]{});
        Method[] methods = clazz.getMethods();
        for(int i=0;i<methods.length;i++){
            Method method = methods[i];
            String methodName = method.getName();
            String methodType = methodName.substring(0, 3);
            if("set".equals(methodType)&&method.getParameterTypes().length==1){
                String fieldName = methodName.substring(3, 4).toLowerCase()+methodName.substring(4);
                Class classType = method.getParameterTypes()[0];
                Object value = rs.getObject(fieldName);
                method.invoke(newObject, new Object[] { value });
            }
        }
        return newObject;
    }

}
