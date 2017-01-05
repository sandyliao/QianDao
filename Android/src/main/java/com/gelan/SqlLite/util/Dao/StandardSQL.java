package com.gelan.SqlLite.util.Dao;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yoyo on 2016/12/15.
 * SQL语句解析类
 */

public class StandardSQL {
    private final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * 生成建表语句
     * @param o
     * @return
     * @throws Exception
     */
    public String create(Object o) throws Exception{
        StringBuffer sb = new StringBuffer("create table if not exists ");
        sb.append(getTableName(o));
        sb.append("(");
        sb.append(getProperties(getPropertiesMap(o)));
        sb.append(")");
        return sb.toString();
    }

    /**
     * 生成插入语句
     * @param o
     * @return
     * @throws Exception
     */
    public String add(Object o) throws Exception{
        Map map = getConditionMap(o);
        Set key = map.keySet();
        if(key.size()==0){
            throw new Exception("The target object is null!");
        }
        Collection values = map.values();
        StringBuffer sb = new StringBuffer("insert into ");
        sb.append(getTableName(o));
        sb.append("("+fieldJoin(key)+") ");
        sb.append("values("+fieldJoin(values)+")");
        return sb.toString();
    }

    /**
     * 生成删除语句
     * @param o
     * @return
     * @throws Exception
     */
    public String delete(Object o) throws Exception{
        Map map = getConditionMap(o);
        StringBuffer sb = new StringBuffer("delete from ");
        if(!map.containsKey("id")){
            throw new Exception("The primary_key is null!");
        }
        sb.append(getTableName(o));
        sb.append(" where id=");
        sb.append(map.get("id"));
        return sb.toString();
    }

    /**
     * 生成修改语句
     * @param o
     * @return
     * @throws Exception
     */
    public String update(Object o) throws Exception{
        StringBuffer sb = new StringBuffer("update ");
        Map map = getConditionMap(o);
        String id = (String)map.get("id");

        if(!map.containsKey("id")){
            throw new Exception("The primary_key is null!");
        }else{
            map.remove("id");
        }
        List list = new ArrayList();
        Set key = map.keySet();
        Iterator it = key.iterator();
        while(it.hasNext()){
            String k = (String)it.next();
            String value = (String)map.get(k);
            list.add(k+"="+value);
        }

        sb.append(getTableName(o));
        sb.append(" set "+fieldJoin(list));
        sb.append(" where id=");
        sb.append(id);

        return sb.toString();
    }

    /**生成查询语句
     * @param o
     * @return
     * @throws Exception
     */
    public String query(Object o) throws Exception{
        StringBuffer sb = new StringBuffer("select ");
        String condition = getCondition(o);
        sb.append(getField(o)).append("from ").append(getTableName(o));
        if(!"".equals(condition)){
            sb.append(" where ").append(condition);
        }
        return sb.toString();
    }

    /**产生in条件语句
     * @param field
     * @param coll
     * @return
     */
    public String getBelong(String field,Collection coll){
        if(field == null){
            return "";
        }else{
            StringBuffer sb = new StringBuffer(field+" in (");
            Iterator it = coll.iterator();
            while(it.hasNext()){
                String s = toString(it.next());
                sb.append(s);
                if(it.hasNext()){
                    sb.append(",");
                }
            }
            sb.append(") ");
            return sb.toString();
        }
    }

    /**
     * 产生between或<>条件语句
     * @param field
     * @param arg0
     * @param arg1
     * @return
     */
    public String getInterval(String field,Object arg0,Object arg1){
        if(field == null){
            return "";
        }else if(arg0 instanceof Number&&arg1 instanceof Number){
            return field + " between "+arg0+" and "+arg1+" ";
        }else if(arg0 == null&&arg1 instanceof Number){
            return field + " <= "+arg1+" ";
        }else if(arg0 instanceof Number&&arg1==null){
            return field + " >= "+arg0+" ";
        }else if(arg0 instanceof Date||arg1 instanceof Date){
            SimpleDateFormat sd = new SimpleDateFormat(dateFormat);
            if(arg0 !=null&&arg1 !=null){
                return field + " between '"+sd.format((Date)arg0)+"' and '"+sd.format((Date)arg1)+"' ";
            }else if(arg0 == null){
                return field + " <= '"+sd.format((Date)arg1)+"' ";
            }else if(arg1 == null){
                return field + " >= '"+sd.format((Date)arg0)+"' ";
            }
        }
        return "";
    }

    /**
     * 根据对象得到数据库的表名称
     * @param o
     * @return
     */
    private String getTableName(Object o){
        Class clazz = o.getClass();
        String tableName = clazz.getName();
        int index = tableName.lastIndexOf(".")+1;
        tableName = tableName.substring(index,index+1).toUpperCase()+tableName.substring(index+1);
        return tableName;
    }

    /**得到比较语句
     * @param field
     * @param o
     * @return
     */
    private String toString(String field,Object o){
        if(field == null||o==null){
            return "";
        }else if(o instanceof String){
            StringBuffer sb = new StringBuffer();
            String s = (String)o;
            s = s.replaceAll("　", " ");
            String[] ss = s.split(" ");
            for(int i=0; i<ss.length; i++){
                if(i!=0){
                    sb.append(" and ");
                }
                sb.append(field+" = '"+ss[i]+"'");
            }
            return sb.toString();
        }else if(o instanceof Number){
            return field + " = "+ o;
        }else if(o instanceof Boolean){
            return field + " is "+ o;
        }else if(o instanceof Date){
            SimpleDateFormat sd = new SimpleDateFormat(dateFormat);
            return field + " = '"+ sd.format((Date)o)+"'";
        }
        return "";
    }

    /**将对象格式化为字符
     * @param o
     * @return
     */
    private String toString(Object o){
        if(o instanceof String){
            return "'"+o+"'";
        }else if(o instanceof Number){
            return o+"";
        }else if(o instanceof Boolean){
            return o+"";
        }else if(o instanceof Date){
            SimpleDateFormat sd = new SimpleDateFormat(dateFormat);
            return "'"+ sd.format((Date)o)+"'";
        }
        return "";
    }

    /**
     * 根据对象的类型映射数据库中的类型
     * @param c
     * @return
     */
    private String getDataType(Class c) {
        if(c.isAssignableFrom(Integer.class)){
            return "int(8)";
        }else if(c.isAssignableFrom(Long.class)){
            return "bigint(16)";
        }else if(c.isAssignableFrom(Float.class)){
            return "float(10,2)";
        }else if(c.isAssignableFrom(Double.class)){
            return "double(16,4)";
        }else if(c.isAssignableFrom(String.class)){
            //return "text";
            return "varchar(100)";
        }else if(c.isAssignableFrom(Date.class)){
            //return "timestamp";
            return "datetime";
        }
        else {
            return "varchar(100)";
        }
    }

    /**得到对象中所有<变量名,变量类型>的Map
     * @param o
     * @return
     * @throws Exception
     */
    private Map getPropertiesMap(Object o) throws Exception{
        Map map = new HashMap();
        loop(o, map, new LoopOperate(){
            public void operate(Object o1, Object o2, Method method, String field) throws Exception{
                Class type = method.getReturnType();
                ((Map)o2).put(field, type);
            }
        });
        return map;
    }

    /**
     * 得到建表语句的主体部分
     * @param map
     * @return
     */
    private String getProperties(Map map){
        StringBuffer sb = new StringBuffer();
        Iterator it = map.keySet().iterator();
        while(it.hasNext()){
            String fieldName = (String)it.next();
            Class type = (Class)map.get(fieldName);
            sb.append(fieldName);
            sb.append(" "+getDataType(type));
            if(fieldName.equals("id")){
                sb.append(" NOT NULL ");
            }
            sb.append(", ");
            if(!it.hasNext()){
                sb.append("primary key(id)");
            }
        }
        return sb.toString();
    }


    /**
     * 连接所有的字段组成字符串
     * @param coll
     * @return
     */
    private String fieldJoin(Collection coll){
        StringBuffer sb = new StringBuffer();
        Iterator it = coll.iterator();
        while(it.hasNext()){
            String s = (String)it.next();
            sb.append(s);
            if(it.hasNext()){
                sb.append(", ");
            }else{
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**连接所有的条件组成字符串
     * @param coll
     * @return
     */
    private String conditionJoin(Collection coll){
        StringBuffer sb = new StringBuffer();
        Iterator it = coll.iterator();
        while(it.hasNext()){
            String s = (String)it.next();
            sb.append(s);
            if(it.hasNext()){
                sb.append(" and ");
            }else{
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**根据对象取得条件列表
     * @param o
     * @return
     * @throws Exception
     */
    private List getConditionList(Object o) throws Exception{
        List conditionList = new ArrayList();
        loop(o, conditionList, new LoopOperate(){
            public void operate(Object o1, Object o2, Method method, String field) throws Exception{
                Object obj = method.invoke(o1);
                if(obj!=null){
                    String value = StandardSQL.this.toString(field,obj);
                    ((List)o2).add(value);
                }
            }
        });
        return conditionList;
    }

    /**根据对象得到条件Map
     * @param o
     * @return
     * @throws Exception
     */
    private Map getConditionMap(Object o) throws Exception{
        Map conditionMap = new HashMap();
        loop(o, conditionMap, new LoopOperate(){
            public void operate(Object o1, Object o2, Method method, String field) throws Exception{
                Object obj = method.invoke(o1);
                if(obj!=null){
                    String value = StandardSQL.this.toString(obj);
                    ((Map)o2).put(field, value);
                }
            }
        });
        return conditionMap;
    }

    /**根据对象得到条件的字符串
     * @param o
     * @return
     * @throws Exception
     */
    private String getCondition(Object o) throws Exception{
        return conditionJoin(getConditionList(o));
    }

    /**
     * 根据对象得到所有字段列表
     * @param o
     * @return
     * @throws Exception
     */
    private List getFieldList(Object o) throws Exception{
        List fieldList = new ArrayList();
        loop(o, fieldList, new LoopOperate(){
            public void operate(Object o1, Object o2, Method method, String field){
                ((List)o2).add(field);
            }
        });
        return fieldList;
    }

    /**根据对象得到所有字段组成的字符串
     * @param o
     * @return
     * @throws Exception
     */
    private String getField(Object o) throws Exception{
        return fieldJoin(getFieldList(o));
    }

    /**
     * 循环遍历操作的主体
     * @param ob1
     * @param ob2
     * @param op
     * @return
     * @throws Exception
     */
    private Object loop(Object ob1, Object ob2, LoopOperate op) throws Exception{
        Class clazz = ob1.getClass();
        Method[] methods = clazz.getMethods();
        for(int i=0;i<methods.length;i++){
            Method method = methods[i];
            String methodName = method.getName();
            String methodType = methodName.substring(0, 3);
            if("get".equals(methodType)&&!"getClass".equals(methodName)){
                String fieldName = methodName.substring(3, 4).toLowerCase()+methodName.substring(4);
                op.operate(ob1, ob2, method, fieldName);
            }
        }
        return ob2;
    }
}
