package com.gelan.DAO;

import java.lang.reflect.Method;
/**
 * Created by yoyo on 2016/12/15.
 */

interface LoopOperate{
    public void operate(Object o1,Object o2, Method method, String field) throws Exception;
}
