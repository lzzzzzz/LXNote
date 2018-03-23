package com.lxvoice.domain.utils;

/**
 * Created by psp on 2018/3/13.
 */

public class StringUtils {

    public static boolean isEmpty(String str){
        if(null==str||str.isEmpty()||"".equals(str.trim())){
            return true;
        }
        return false;
    }
}
