package com.co.alianza.coreclient.util;

import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Map;

public class Util {

    private Util() { }

    public static boolean validateEmptyParams(Object... params) {

        if (params != null) {
            for (Object param : params) {
                if(!isNullOrEmptyObject(param)) return false;
            }
        }

        return true;
    }

    public static boolean validRequiredParams(Object... params) {

        if (params != null) {
            for (Object param : params) {
                if(isNullOrEmptyObject(param)) return false;
            }
        }

        return true;
    }

    public static boolean isNullOrEmptyObject(Object value) {
        if (value == null) return  true;

        if (value instanceof String)
            return ((String) value).trim().isEmpty();

        if (value instanceof Collection)
            return ((Collection<?>) value).isEmpty();

        if (value instanceof Map)
            return ((Map<?,?>) value).isEmpty();

        if (value instanceof Page)
            return ((Page<?>) value).isEmpty();
        return false;
    }

}
