package com.co.alianza.coreclient.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil<T> {
    private final Logger logger;

    private LoggerUtil(Class<T> aClass) {
        logger = LogManager.getLogger(aClass);
    }

    public static <T> LoggerUtil<T> getLogger(Class<T> aClass) {
        return new LoggerUtil<>(aClass);
    }

    public void error(Object log) {
        logger.error(log);
    }

    public void error(Object log, Throwable t) {
        logger.error(log, t);
    }

    public void info(Object log) {
        logger.info(log);
    }

    public static String getLogStartMethod(String methodComponent, Object... params) {

        StringBuilder sbParams = new StringBuilder("[");
        if (params != null) {
            for (Object param : params) {
                sbParams.append(param);
                sbParams.append(",");
            }
            if (sbParams.charAt(sbParams.length()-1) == ',') {
                sbParams.setCharAt(sbParams.length()-1, ' ');
            }


        }
        sbParams.append("]");

        return "[" + methodComponent + "]" +
                " param:" + sbParams;
    }

    public static String generateErrorLog(String methodComponent, String error) {
        return " [method]:" + getValueNoSeparator(methodComponent) +
                " [error]:" + error;
    }

    private static String getValueNoSeparator(Object valor) {
        if (valor == null) {
            return "-";
        } else {
            return valor.toString();
        }
    }

}
