package com.gelan.Utils;

/**
 * Created by hp on 2017/1/5.
 */

public class StringUtils {
    public static int getCounts(String str ,String find){
        int count=0;
        int a = find.length();
        for(int i=0;i<=str.length()-1;i++) {
            String getstr=str.substring(i,i+a);
            if(getstr.equals(find)){
                count++;
            }
        }
        return count;
    }
}
